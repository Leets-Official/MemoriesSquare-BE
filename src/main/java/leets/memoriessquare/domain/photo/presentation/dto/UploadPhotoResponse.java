package leets.memoriessquare.domain.photo.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadPhotoResponse {
    private PhotoDTO photo;
    private PhotoDTO croppedPhoto;
}
