package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;

import java.util.List;
import java.util.UUID;

public interface GetPhotosByUser {
    List<PhotoDTO> execute(UUID userId);
}
