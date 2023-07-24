package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.presentation.dto.PhotoWithDateDTO;

import java.util.List;
import java.util.UUID;

public interface GetPhotosByUser {
    List<PhotoWithDateDTO> execute(UUID userId);
}
