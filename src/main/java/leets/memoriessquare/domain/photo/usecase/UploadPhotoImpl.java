package leets.memoriessquare.domain.photo.usecase;
import leets.memoriessquare.domain.photo.domain.Photo;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import leets.memoriessquare.domain.photo.repository.PhotoRepository;
import leets.memoriessquare.domain.user.domain.User;
import leets.memoriessquare.domain.user.exception.UserNotFoundException;
import leets.memoriessquare.domain.user.repository.UserRepository;
import leets.memoriessquare.global.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadPhotoImpl implements UploadPhoto {
    private final S3Service s3Service;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public PhotoDTO execute(MultipartFile file, UUID userId) throws UserNotFoundException, Exception {
        UUID userUuid = UUID.fromString(String.valueOf(userId));

        User user = userRepository.findById(userUuid).orElseThrow(UserNotFoundException::new);

        String imageUrl = s3Service.uploadImage(file);

        Photo photo = Photo.builder()
                .user(user)
                .imageUrl(imageUrl)
                .build();

        photoRepository.save(photo);

        return new PhotoDTO(photo.getId(), user.getId().toString(), photo.getImageUrl(), false);
    }
}
