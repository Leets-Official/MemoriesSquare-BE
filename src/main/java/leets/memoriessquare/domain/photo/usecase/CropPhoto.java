package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface CropPhoto {
    PhotoDTO execute(MultipartFile originalImage, UUID userId, UUID originalPhotoId) throws IOException;
}
