package leets.memoriessquare.domain.photo.presentation;

import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import leets.memoriessquare.domain.photo.presentation.dto.UploadPhotoResponse;
import leets.memoriessquare.domain.photo.presentation.dto.UploadPhotoRequest;
import leets.memoriessquare.domain.photo.usecase.UploadPhoto;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/upload")
    public UploadPhotoResponse uploadPhoto(
            @Valid UploadPhotoRequest request,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal(expression = "userId") UUID userId) throws Exception {
        PhotoDTO photoDTO = uploadPhoto.execute(file, userId);
        return new UploadPhotoResponse(photoDTO.getId());
    }
}
