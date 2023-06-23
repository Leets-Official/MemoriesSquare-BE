package leets.memoriessquare.global.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import leets.memoriessquare.global.jwt.AuthRole;
import leets.memoriessquare.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${social-login.redirect}")
    private String redirectUrl;
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
        cookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(cookie);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl + "?token=" + accessToken);
    }
}
