package com.opn.demo.service.impl;

import com.opn.demo.exception.base.TooManyRequestException;
import com.opn.demo.service.RateLimitService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RateLimitServiceImpl implements RateLimitService {
  private static final String OTP_BUCKET_PREFIX = "otp-bucket:";
  public static final String IP_BUCKET_PREFIX = "ip-bucket:";
  private static final String BLOCKED_USER_PREFIX = "blocked-user:";
  private static final String BLOCKED_IP_PREFIX = "blocked-ip:";
  private static final long OTP_LIMIT = 5;
  private static final long IP_LIMIT = 1000;
  private static final Duration REFILL_DURATION = Duration.ofMinutes(1);
  private static final Duration BLOCK_DURATION = Duration.ofDays(1);
  private final RedisTemplate<String, Object> redisTemplate;

  public Bucket resolveOtpBucket(String userId) {
    String otpBucketKey = OTP_BUCKET_PREFIX + userId;
    return resolveBucket(otpBucketKey, OTP_LIMIT, REFILL_DURATION);
  }

  public Bucket resolveIpBucket(String ipAddress) {
    String ipBucketKey = IP_BUCKET_PREFIX + ipAddress;
    return resolveBucket(ipBucketKey, IP_LIMIT, REFILL_DURATION);
  }

  private Bucket resolveBucket(String key, long limit, Duration duration) {
    Long tokensLeft = (Long) redisTemplate.opsForValue().get(key);
    Bucket bucket;

    if (tokensLeft != null) {
      Bandwidth bandwidth = Bandwidth.classic(limit, Refill.intervally(limit, duration)).withInitialTokens(tokensLeft);
      bucket = Bucket.builder().addLimit(bandwidth).build();
      log.info("Restoring bucket for key {} with {} tokens left", key, tokensLeft);
    }
    else {
      bucket = createBucket(limit, duration);
      saveBucketState(key, bucket);
      log.info("Created new bucket for key {} with limit {}", key, limit);
    }

    return bucket;
  }

  // Tạo bucket trong bộ nhớ với giới hạn
  private Bucket createBucket(long limit, Duration duration) {
    Bandwidth bandwidth = Bandwidth.classic(limit, Refill.intervally(limit, duration));
    return Bucket.builder().addLimit(bandwidth).build();
  }

  public void saveBucketState(String key, Bucket bucket) {
    long tokensLeft = bucket.getAvailableTokens();  // Lấy số token còn lại sau khi tiêu thụ
    log.info("tokensLeft:{}",tokensLeft);
    redisTemplate.opsForValue().set(key, tokensLeft, REFILL_DURATION.getSeconds(), TimeUnit.SECONDS);  // Lưu lại vào Redis
  }

  public boolean isIpBlocked(String ipAddress) {
    String blockKey = BLOCKED_IP_PREFIX + ipAddress;
    return redisTemplate.hasKey(blockKey);
  }

  // Kiểm tra xem tài khoản có bị block không
  public boolean isUserBlocked(String email) {
    String blockKey = BLOCKED_USER_PREFIX + email;
    return redisTemplate.hasKey(blockKey);
  }

  // Block IP và lưu trạng thái vào Redis
  public void blockIp(String ipAddress) {
    String blockKey = BLOCKED_IP_PREFIX + ipAddress;
    redisTemplate.opsForValue().set(blockKey, "blocked", BLOCK_DURATION.getSeconds(), TimeUnit.SECONDS);
    log.info("IP " + ipAddress + " đã bị block trong 1 ngày.");
  }

  // Block tài khoản và lưu trạng thái vào Redis
  public void blockUser(String userId) {
    String blockKey = BLOCKED_USER_PREFIX + userId;
    redisTemplate.opsForValue().set(blockKey, "blocked", BLOCK_DURATION.getSeconds(), TimeUnit.SECONDS);
    log.info("Tài khoản " + userId + " đã bị khóa trong 1 ngày.");
  }

  public boolean trySendOtp(String email) {


    // Kiểm tra xem tài khoản có bị block không
    if (isUserBlocked(email)) {
      throw new TooManyRequestException("Tài khoản " + email + " đã bị khóa.");
    }

    Bucket otpBucket = resolveOtpBucket(email);
    if (!otpBucket.tryConsume(1)) {
      blockUser(email);
      throw new TooManyRequestException("Tài khoản đã vượt quá số lần gửi OTP và bị khóa.");
    }
    saveBucketState(OTP_BUCKET_PREFIX + email, otpBucket);

    return true;
  }
}
