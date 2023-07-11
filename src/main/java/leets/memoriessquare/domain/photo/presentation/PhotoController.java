package leets.memoriessquare.domain.photo.presentation;

import leets.memoriessquare.domain.photo.domain.Photo;
import leets.memoriessquare.domain.photo.domain.PhotoGet;
import leets.memoriessquare.domain.photo.domain.PhotoUpload;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import leets.memoriessquare.domain.photo.usecase.GetPhoto;
import leets.memoriessquare.domain.photo.usecase.UploadPhoto;
import leets.memoriessquare.global.error.ErrorCode;
import leets.memoriessquare.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/photos")
public class PhotoController {
    private final GetPhoto getPhoto;
    private final UploadPhoto uploadPhoto;

    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoDTO> getPhoto(@PathVariable UUID photoId) {
        PhotoGet photo = getPhoto.execute(photoId);
        PhotoDTO photoDTO = PhotoDTO.from(photo);
        return photoDTO;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPhoto(@Valid @RequestParam("file") MultipartFile file) {
        try {
            PhotoUpload uploadedPhoto = uploadPhoto.execute(file);
            PhotoGet photoGet = createPhotoGetFromUpload(uploadedPhoto);
            PhotoDTO photoDTO = PhotoDTO.from(photoGet);
            return ResponseEntity.status(HttpStatus.CREATED).body(photoDTO);
        } catch (IOException e) {
            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    private PhotoGet createPhotoGetFromUpload(PhotoUpload photoUpload) {
        return PhotoGet.builder()
                .id(photoUpload.getId())
                .user(photoUpload.getUser())
                .imageUrl(photoUpload.getImageUrl())
                .build();
    }
}
