package leets.memoriessquare.domain.photo.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PhotoDTO {
    private final String id;
    private final String userId;
    private final String imageUrl;
    private final boolean isCrop;
}
