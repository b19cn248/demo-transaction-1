package com.opn.demo.config.sercurity;

import com.opn.demo.constant.MessageExceptionConstant;
import com.opn.demo.entity.Principal;
import com.opn.demo.service.PrincipalService;
import com.opn.demo.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;


@RequiredArgsConstructor
@Component
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private final PrincipalService principalService;

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
  ) throws ServletException, IOException {

    String accessTokenBearer = request.getHeader(HttpHeaders.AUTHORIZATION);
    log.info("Authorization: {}", accessTokenBearer);

    if (Objects.isNull(accessTokenBearer) ) {
      log.debug("Token null or invalid, token: {}", accessTokenBearer);
      filterChain.doFilter(request, response);
      return;
    }

    String accessToken = accessTokenBearer.substring(7);
    log.debug("Access_token: {}", accessToken);

    if(!jwtTokenProvider.validateJwtToken(accessToken)){
      log.debug(MessageExceptionConstant.JWT_IS_NOT_VALID);
      filterChain.doFilter(request,response);
      return ;
    }

    final String username= jwtTokenProvider.getUserNameFromJwtToken(accessToken);

    Principal principal = (Principal) principalService.loadUserByUsername(username);
    if(Objects.isNull(principal)){
      log.debug(MessageExceptionConstant.ACCOUNT_IS_NOT_FOUND);
      filterChain.doFilter(request,response);
      return ;
    }

    Authentication authentication = new UsernamePasswordAuthenticationToken(
          principal,
          null,
          null
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);

  }


}
