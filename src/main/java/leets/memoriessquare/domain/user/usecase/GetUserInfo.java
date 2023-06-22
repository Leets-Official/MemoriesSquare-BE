package leets.memoriessquare.domain.user.usecase;

import leets.memoriessquare.domain.user.presentation.dto.UserInfoResponse;

import java.util.UUID;

public interface GetUserInfo {
    UserInfoResponse execute(UUID id);
}
