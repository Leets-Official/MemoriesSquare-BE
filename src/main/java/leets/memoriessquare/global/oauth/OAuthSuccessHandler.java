package leets.memoriessquare.global.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import leets.memoriessquare.global.jwt.AuthRole;
import leets.memoriessquare.global.jwt.JwtProvider;
import leets.memoriessquare.global.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuthDetails oAuthDetails = (OAuthDetails) authentication.getPrincipal();
        UUID uuid = oAuthDetails.getId();
        String email = oAuthDetails.getEmail();
        String nickname = oAuthDetails.getName();
        String authority = ((SimpleGrantedAuthority) oAuthDetails.getAuthorities().toArray()[0]).getAuthority();
        AuthRole role = AuthRole.valueOf(authority);

        String accessToken = jwtProvider.generateToken(uuid, email, nickname, role, false);
        String refreshToken = jwtProvider.generateToken(uuid, email, nickname, role, true);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        Map<String, Object> result = new HashMap<>();
        result.put("result", new JwtResponse(accessToken, refreshToken));

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
