package leets.memoriessquare.domain.photo.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PhotoWithDateDTO {
    private final UUID id;
    private final UUID userId;
    private final String imageUrl;
    private final boolean isCrop;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
