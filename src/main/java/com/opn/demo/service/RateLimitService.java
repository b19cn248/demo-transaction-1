package com.opn.demo.service;

import io.github.bucket4j.Bucket;

public interface RateLimitService {
  Bucket resolveOtpBucket(String userId);

  Bucket resolveIpBucket(String ipAddress);

  void saveBucketState(String key, Bucket bucket);


  boolean isIpBlocked(String ipAddress);

  boolean isUserBlocked(String userId);

  void blockIp(String ipAddress);

  void blockUser(String userId);

  boolean trySendOtp(String email);

}
