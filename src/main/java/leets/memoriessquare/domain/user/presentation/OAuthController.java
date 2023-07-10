package leets.memoriessquare.domain.user.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "소셜로그인")
@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
public class OAuthController {
    @Operation(summary = "페이스북 소셜로그인", description = "페이스북 계정으로 로그인합니다. API 문서 기록용으로 Swagger에서 호출할 수 없습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/login/facebook")
    public void facebookLogin() {
        // Auto-generated method by Spring Security
    }

    @Operation(summary = "구글 소셜로그인", description = "구글 계정으로 로그인합니다. API 문서 기록용으로 Swagger에서 호출할 수 없습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/login/google")
    public void googleLogin() {
        // Auto-generated method by Spring Security
    }

    @Operation(summary = "페이스북 Callback", description = "백엔드 내부에서 사용하는 페이스북 로그인 API입니다. API 문서 기록용으로 Swagger에서 호출할 수 없습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/callback/facebook")
    public void facebookCallback() {
        // Auto-generated method by Spring Security
    }

    @Operation(summary = "구글 Callback", description = "백엔드 내부에서 사용하는 구글 로그인 API입니다. API 문서 기록용으로 Swagger에서 호출할 수 없습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/callback/google")
    public void googleCallback() {
        // Auto-generated method by Spring Security
    }
}
