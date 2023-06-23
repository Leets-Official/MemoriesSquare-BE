package leets.memoriessquare.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import leets.memoriessquare.domain.user.presentation.dto.UserInfoResponse;
import leets.memoriessquare.domain.user.usecase.GetUserInfo;
import leets.memoriessquare.global.error.ErrorResponse;
import leets.memoriessquare.global.oauth.OAuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 및 유저 계정")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final GetUserInfo getUserInfo;

    @Operation(summary = "유저 정보 가져오기", description = "AccessToken으로 유저 정보를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/me")
    public UserInfoResponse getUser(@AuthenticationPrincipal OAuthDetails auth) {
        return getUserInfo.execute(auth.getId());
    }
}
