package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.presentation.dto.PhotoDTO;

import java.util.UUID;

public interface GetPhotoById {
    PhotoDTO execute(UUID photoId);
}
