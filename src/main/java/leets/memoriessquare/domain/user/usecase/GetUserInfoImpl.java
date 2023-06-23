package leets.memoriessquare.domain.user.usecase;

import leets.memoriessquare.domain.user.domain.User;
import leets.memoriessquare.domain.user.exception.UserNotFoundException;
import leets.memoriessquare.domain.user.presentation.dto.UserInfoResponse;
import leets.memoriessquare.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GetUserInfoImpl implements GetUserInfo {
    private final UserRepository userRepository;

    @Override
    public UserInfoResponse execute(UUID id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return new UserInfoResponse(user.getId(), user.getEmail(), user.getNickname(), user.getVendor(), user.getCreatedAt());
    }
}
