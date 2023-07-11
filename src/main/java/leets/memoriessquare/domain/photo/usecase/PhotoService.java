package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.domain.Photo;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import leets.memoriessquare.domain.photo.repository.PhotoRepository;
import leets.memoriessquare.domain.user.domain.User;
import leets.memoriessquare.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final S3Service s3Service;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;

    @Transactional
    public PhotoDTO uploadPhoto(MultipartFile file, UUID userId) throws Exception {
        UUID userUuid = UUID.fromString(userId); // String을 UUID로 변환

        User user = userRepository.findById(userUuid)
                .orElseThrow(() -> new Exception("User not found"));

        String imageUrl = s3Service.uploadImage(file);

        Photo photo = Photo.builder()
                .user(user)
                .imageUrl(imageUrl)
                .build();
        photoRepository.save(photo);

        return new PhotoDTO(photo.getId().toString(), user.getId().toString(), photo.getImageUrl());
    }
}
