package leets.memoriessquare.domain.user.presentation.dto;

import leets.memoriessquare.domain.user.type.Vendor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private UUID id;
    private String email;
    private String nickname;
    private Vendor vendor;
    private LocalDateTime createdAt;
}
