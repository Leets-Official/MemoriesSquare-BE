package leets.memoriessquare.domain.photo.usecase;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import leets.memoriessquare.domain.user.exception.UserNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UploadPhoto {
    PhotoDTO execute(MultipartFile file, UUID userId) throws UserNotFoundException, Exception;
}