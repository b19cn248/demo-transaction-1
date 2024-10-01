//package com.opn.demo.filter;
//
//import static com.opn.demo.service.impl.RateLimitServiceImpl.IP_BUCKET_PREFIX;
//
//import com.opn.demo.service.RateLimitService;
//import io.github.bucket4j.Bucket;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//
//
//@RequiredArgsConstructor
//@Component
//@Slf4j
//@Order(0)
//public class RateLimiterFilter extends OncePerRequestFilter {
//
//  private final RateLimitService rateLimitService;
//
//  @Override
//  protected void doFilterInternal(
//      HttpServletRequest request,
//      HttpServletResponse response,
//      FilterChain filterChain
//  ) throws ServletException, IOException {
//    HttpServletRequest httpRequest =  request;
//    HttpServletResponse httpResponse =  response;
//
//    String ipAddress = httpRequest.getRemoteAddr();
//
//    // Kiểm tra IP có bị block không
//    if (rateLimitService.isIpBlocked(ipAddress)) {
//      httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
//      httpResponse.getWriter().write("IP is blocked for 24 hours due to too many requests.");
//      return;
//    }
//
//    Bucket bucket = rateLimitService.resolveIpBucket(ipAddress);
//    log.info("Tokens left before consume: {}", bucket.getAvailableTokens());
//    if (!bucket.tryConsume(1)) {
//      rateLimitService.blockIp(ipAddress);
//      httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
//      httpResponse.getWriter().write("Too many requests. IP is now blocked for 24 hours.");
//      return;
//    }
//
//    rateLimitService.saveBucketState(IP_BUCKET_PREFIX + ipAddress, bucket);
//
//    log.info("Tokens left after consume: {}", bucket.getAvailableTokens());
//    filterChain.doFilter(request, response);
//  }
//}
