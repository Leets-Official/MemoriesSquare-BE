package leets.memoriessquare.domain.photo.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import leets.memoriessquare.domain.photo.exception.MimeTypeIsNotImageException;
import leets.memoriessquare.domain.photo.presentation.dto.CountPhotoResponse;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoWithDateDTO;
import leets.memoriessquare.domain.photo.presentation.dto.UploadPhotoResponse;
import leets.memoriessquare.domain.photo.usecase.*;
import leets.memoriessquare.global.error.ErrorResponse;
import leets.memoriessquare.global.oauth.OAuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
public class PhotoController {
    private final UploadPhoto uploadPhoto;
    private final CropPhoto cropPhoto;
    private final GetPhotosByDate getPhotoByDate;
    private final CountPhotoByDate countPhotoByDate;
    private final GetPhotosByUser getPhotosByUser;
    private final GetPhotoById getPhotoById;

    @Operation(summary = "사진 업로드", description = "새로운 사진을 업로드합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public UploadPhotoResponse uploadPhoto(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal OAuthDetails auth) throws Exception {
        if (!Objects.requireNonNull(file.getContentType(), "").contains("image/")) throw new MimeTypeIsNotImageException();

        PhotoDTO photoDTO = uploadPhoto.execute(file, auth.getId());
        PhotoDTO croppedPhotoDTO = cropPhoto.execute(file, auth.getId(), photoDTO.getId());
        return new UploadPhotoResponse(photoDTO, croppedPhotoDTO);
    }

    @Operation(summary = "날짜별 사진 가져오기", description = "특정 날짜에 올린 사진을 모두 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/date")
    public List<PhotoWithDateDTO> getPhotoByDateApi(@AuthenticationPrincipal OAuthDetails auth, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return getPhotoByDate.execute(auth.getId(), localDate);
    }

    @Operation(summary = "이번달 날짜별 사진 개수 가져오기", description = "이번달에 올린 날짜별 사진 개수를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/count")
    public CountPhotoResponse getCountByDate(@AuthenticationPrincipal OAuthDetails auth) {
        LocalDate now = LocalDate.now();
        return new CountPhotoResponse(countPhotoByDate.execute(auth.getId(), now.getYear(), now.getMonthValue()));
    }

    @Operation(summary = "사용자별 사진 가져오기", description = "특정 사용자의 모든 사진을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping()
    public List<PhotoWithDateDTO> getPhotosByUserApi(@AuthenticationPrincipal OAuthDetails auth) {
        return getPhotosByUser.execute(auth.getId());
    }

    @Operation(summary = "특정 사진 가져오기", description = "특정 사진의 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{photoId}")
    public PhotoDTO getPhotoByUUID(@PathVariable UUID photoId) {
        return getPhotoById.execute(photoId);
    }
}
