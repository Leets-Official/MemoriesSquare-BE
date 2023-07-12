package leets.memoriessquare.domain.photo.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import leets.memoriessquare.domain.photo.presentation.dto.UploadPhotoResponse;
import leets.memoriessquare.domain.photo.presentation.dto.UploadPhotoRequest;
import leets.memoriessquare.domain.photo.usecase.UploadPhoto;
import leets.memoriessquare.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
public class PhotoController {
    private final UploadPhoto uploadPhoto;

    @Operation(summary = "사진 업로드", description = "새로운 사진을 업로드합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload")
    public UploadPhotoResponse uploadPhoto(
            @Valid UploadPhotoRequest request,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal(expression = "userId") UUID userId) throws Exception {
        PhotoDTO photoDTO = uploadPhoto.execute(file, userId);
        return new UploadPhotoResponse(photoDTO.getId());
    }
}
