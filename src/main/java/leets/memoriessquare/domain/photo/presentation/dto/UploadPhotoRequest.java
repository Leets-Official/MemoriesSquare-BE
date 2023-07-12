package leets.memoriessquare.domain.photo.presentation.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class UploadPhotoRequest {
    private MultipartFile file;
}