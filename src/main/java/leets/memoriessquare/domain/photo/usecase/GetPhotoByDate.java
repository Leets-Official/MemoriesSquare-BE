package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.presentation.dto.PhotoWithDateDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface GetPhotoByDate {
    List<PhotoWithDateDTO> execute(UUID userId, LocalDate date);
}
