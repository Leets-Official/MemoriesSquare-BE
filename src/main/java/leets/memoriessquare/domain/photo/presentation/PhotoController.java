package leets.memoriessquare.domain.photo.presentation;

import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import leets.memoriessquare.domain.photo.presentation.dto.UploadPhotoResponse;
import leets.memoriessquare.domain.photo.presentation.dto.UploadPhotoRequest;
import leets.memoriessquare.domain.photo.usecase.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import java.io.IOException;

@RestController
@RequestMapping("/photo")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping("/upload")
    public UploadPhotoResponse uploadPhoto(
            @Valid UploadPhotoRequest request,
            @RequestParam("file") MultipartFile file) throws Exception {
        PhotoDTO photoDTO = photoService.uploadPhoto(file, request.getUserId());
        return new UploadPhotoResponse(photoDTO.getId());
    }
}

