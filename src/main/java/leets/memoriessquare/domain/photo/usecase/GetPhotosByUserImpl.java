package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.domain.Photo;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;
import leets.memoriessquare.domain.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPhotosByUserImpl implements GetPhotosByUser {
    private final PhotoRepository photoRepository;

    @Override
    public List<PhotoDTO> execute(UUID userId) {
        List<Photo> photos = photoRepository.findAllByUser_Id(userId);
        return photos.stream()
                .map(photo -> new PhotoDTO(photo.getId(), photo.getUser().getId(), photo.getImageUrl(), photo.isCrop()))
                .collect(Collectors.toList());
    }
}
