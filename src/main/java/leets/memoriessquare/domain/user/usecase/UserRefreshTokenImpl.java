package leets.memoriessquare.domain.user.usecase;

import io.jsonwebtoken.Claims;
import leets.memoriessquare.global.jwt.AuthRole;
import leets.memoriessquare.global.jwt.JwtProvider;
import leets.memoriessquare.global.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserRefreshTokenImpl implements UserRefreshToken {
    private final JwtProvider jwtProvider;

    @Override
    public JwtResponse execute(String refreshToken) {
        jwtProvider.validateToken(refreshToken, true);
        Claims claims = jwtProvider.parseClaims(refreshToken, true);
        UUID id = claims.get("id", UUID.class);
        String nickname = claims.get("nickname", String.class);
        String role = claims.get("role", String.class);

        String newAccessToken = jwtProvider.generateToken(id, claims.getSubject(), nickname, AuthRole.valueOf(role), false);
        return new JwtResponse(newAccessToken, null);
    }
}
