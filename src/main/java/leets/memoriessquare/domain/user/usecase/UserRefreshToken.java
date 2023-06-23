package leets.memoriessquare.domain.user.usecase;

import leets.memoriessquare.global.jwt.dto.JwtResponse;

public interface UserRefreshToken {
    JwtResponse execute(String refreshToken);
}
