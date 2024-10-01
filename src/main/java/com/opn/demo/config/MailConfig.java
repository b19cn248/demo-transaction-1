package com.opn.demo.config;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MailConfig {
  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.port}")
  private int port;

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  @Value("${spring.mail.properties.mail.smtp.auth}")
  private String auth;

  @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
  private String starttls;

  @Bean
  public Session getMailSession() {
    Properties props = new Properties();
    props.put("mail.smtp.auth", auth);
    props.put("mail.smtp.starttls.enable", starttls);
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", port);

    return Session.getInstance(props,
        new javax.mail.Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
          }
        });
  }

}
