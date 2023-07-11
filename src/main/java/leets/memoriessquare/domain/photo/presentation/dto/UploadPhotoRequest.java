package leets.memoriessquare.domain.photo.presentation.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class UploadPhotoRequest {
    @NotBlank(message = "User ID is required")
    private String userId;
    private MultipartFile file;
}
