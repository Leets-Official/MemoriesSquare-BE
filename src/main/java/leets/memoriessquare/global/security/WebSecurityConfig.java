package leets.memoriessquare.global.security;

import leets.memoriessquare.global.filter.ExceptionHandleFilter;
import leets.memoriessquare.global.jwt.AuthRole;
import leets.memoriessquare.global.jwt.JwtFilter;
import leets.memoriessquare.global.jwt.JwtProvider;
import leets.memoriessquare.global.oauth.OAuthDetailService;
import leets.memoriessquare.global.oauth.OAuthSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtProvider jwtProvider;
    private final OAuthDetailService oAuthDetailService;
    private final OAuthSuccessHandler oAuthSuccessHandler;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(401))
                .accessDeniedHandler((request, response, accessDeniedException) -> response.sendError(403))
                .and()
                .authorizeHttpRequests()
                .requestMatchers(CorsUtils::isCorsRequest).permitAll()

                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()

                .requestMatchers("/user/**").hasAuthority(AuthRole.ROLE_USER.getRole())

                .anyRequest().authenticated()
                .and()

                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth/login")
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth/callback/**")
                .and()
                .userInfoEndpoint()
                .userService(oAuthDetailService)
                .and()
                .successHandler(oAuthSuccessHandler)
                .and()

                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandleFilter(), JwtFilter.class);

        return http.build();
    }
}