package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.domain.Photo;
import leets.memoriessquare.domain.photo.presentation.dto.PhotoWithDateDTO;
import leets.memoriessquare.domain.photo.presentation.mapper.PhotoMapper;
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
    private final PhotoMapper photoMapper;

    @Override
    public List<PhotoWithDateDTO> execute(UUID userId, LocalDate date) {
        List<Photo> photos = photoRepository
                .findAllByUser_IdAndCreatedAtBetweenOrderByCreatedAtDesc(
                        userId, date.atTime(0, 0), date.atTime(23, 59)
                );
        return photos.stream().map(photoMapper::mappingPhototoDto).toList();
    }
}
