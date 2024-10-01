  package com.opn.demo.config.sercurity;

  import static org.springframework.security.config.Customizer.withDefaults;

  import com.opn.demo.service.PrincipalService;
  import lombok.RequiredArgsConstructor;
  import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.security.authentication.AuthenticationManager;
  import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
  import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
  import org.springframework.security.config.annotation.web.builders.HttpSecurity;
  import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
  import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
  import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
  import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
  import org.springframework.security.web.SecurityFilterChain;
  import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
  import org.springframework.context.annotation.Lazy;

  @Configuration
  @EnableWebSecurity
  @EnableMethodSecurity
  @RequiredArgsConstructor
  public class SecurityConfig {


    protected static final String[] WHITELIST_URLS = {
        "/api/v1/auth/login",
        "/api/v1/users/**",
        "/api/v1/auth/**",
        "/h2-console/**",
        "/oauth2/**",
        "/oauth2/authorize",
        "/oauth2/token",
        "/login",
        "/login/oauth2/code/**"
    };

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity httpSecurity) throws Exception {
      return httpSecurity
          .authorizeHttpRequests(configurer -> configurer
              .requestMatchers(WHITELIST_URLS).permitAll()
              .requestMatchers(PathRequest.toH2Console()).permitAll()
              .anyRequest().authenticated()
          )
          .oauth2Login(oauth2 -> oauth2
              .authorizationEndpoint(authorization -> authorization
                  .authorizationRequestRepository(authorizationRequestRepository()))  // Sửa tại đây
          )
          .oauth2Client(withDefaults())
          .csrf(AbstractHttpConfigurer::disable)
          .cors(AbstractHttpConfigurer::disable)
          .build();
    }



    @Bean
    public AuthenticationManager authenticationManagerBean(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
      return new HttpSessionOAuth2AuthorizationRequestRepository();
    }
  }
