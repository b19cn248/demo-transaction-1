package com.opn.demo.service.impl;

import com.opn.demo.exception.base.TooManyRequestException;
import com.opn.demo.service.EmailService;
import com.opn.demo.service.RateLimitService;
import jakarta.transaction.Transactional;
import java.util.Random;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

  private final Session mailSession;
  private final RedisTemplate<String, Object> redisTemplate;
  private final RateLimitService rateLimitService;

  @Value("${spring.mail.username}")
  private String fromMail;

  public void sendSimpleMessage(String to, String otp)  {
    log.info("(sendSimpleMessage): to:{}, otp:{}", to, otp);

    try {
      Message message = new MimeMessage(mailSession);
      message.setFrom(new InternetAddress(fromMail));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      message.setSubject("Your OTP Code");
      message.setText("Your OTP code is: " + otp);

      Transport.send(message);

    } catch (AuthenticationFailedException e) {
      log.error("Authentication failed: {}", e.getMessage(), e);
    } catch (MessagingException e) {
      log.error("Email sending failed: {}", e.getMessage(), e);
    } catch (Exception e) {
      log.error("Unexpected error: {}", e.getMessage(), e);
    }
  }

  private String generateOtp() {
    Random random = new Random();
    int otp = 1000 + random.nextInt(9000);
    return String.valueOf(otp);
  }

  public void storeOtp(String key, String otp) {
    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(key, otp, 300, TimeUnit.SECONDS);
  }

  @Transactional
  @Override
  public String handleOtpRequest(String email) {
    // Check rate limit for both email and IP address
    if (!rateLimitService.trySendOtp(email)) {
      throw new TooManyRequestException("Too many OTP requests or IP has been blocked.");
    }

    // Generate and send OTP
    String otp = generateOtp();
    sendSimpleMessage(email, otp);
    storeOtp(email, otp);

    log.info("otp :{}",getValue(email));

    return (String) getValue(email);
  }
  @Override
  public Object getValue(String key) {
    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
    return valueOperations.get(key);
  }
}