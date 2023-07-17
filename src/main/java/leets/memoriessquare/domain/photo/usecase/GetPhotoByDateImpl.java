package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.domain.Photo;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoWithDateDTO;
import leets.memoriessquare.domain.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GetPhotoByDateImpl implements GetPhotoByDate {
    private final PhotoRepository photoRepository;

    @Override
    public List<PhotoWithDateDTO> execute(UUID userId, LocalDate date) {
        List<Photo> photos = photoRepository.findAllByUser_IdAndCreatedAtOrderByCreatedAtDesc(userId, date);
        return photos.stream().map(i ->
                new PhotoWithDateDTO(i.getId(), i.getUser().getId(), i.getImageUrl(), i.isCrop(), i.getCreatedAt(), i.getUpdatedAt())
        ).toList();
    }
}
