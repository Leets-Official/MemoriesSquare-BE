package leets.memoriessquare.domain.photo.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PhotoDTO {
    private final UUID id;
    private final UUID userId;
    private final String imageUrl;
    private final boolean isCrop;
}
