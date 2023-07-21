package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.domain.Photo;
import leets.memoriessquare.domain.photo.exception.PhotoNotFoundException;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import leets.memoriessquare.domain.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetPhotoByIdImpl implements GetPhotoById {
    private final PhotoRepository photoRepository;

    @Override
    public PhotoDTO execute(UUID photoId) {
        Photo photo = photoRepository.findById(photoId).orElseThrow(PhotoNotFoundException::new);
        return new PhotoDTO(photo.getId(), photo.getUser().getId(), photo.getImageUrl(), photo.isCrop());
    }
}
