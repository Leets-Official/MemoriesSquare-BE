package leets.memoriessquare.domain.photo.presentation.dto;

import leets.memoriessquare.domain.photo.domain.PhotoGet;
import lombok.Getter;

@Getter
public class PhotoDTO {
    private final String id;
    private final String userId;
    private final String imageUrl;

    private PhotoDTO(String id, String userId, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.imageUrl = imageUrl;
    }

    public static PhotoDTO from(PhotoGet photo) {
        return new PhotoDTO(
                photo.getId().toString(),
                photo.getUser().getId().toString(),
                photo.getImageUrl()
        );
    }
}
