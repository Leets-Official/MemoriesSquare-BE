package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.domain.Photo;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoWithDateDTO;
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
    public List<PhotoWithDateDTO> execute(UUID userId) {
        List<Photo> photos = photoRepository.findAllByUser_Id(userId);
        return photos.stream()
                .map(photo -> new PhotoWithDateDTO(photo.getId(), photo.getUser().getId(), photo.getImageUrl(), photo.isCrop(), photo.getOriginalPhoto() == null ? null : photo.getOriginalPhoto().getId(), photo.getCreatedAt(), photo.getUpdatedAt()))
                .collect(Collectors.toList());
    }
}
