package leets.memoriessquare.domain.photo.usecase;

import java.util.Map;
import java.util.UUID;

public interface CountPhotoByDate {
    Map<String, Long> execute(UUID userId, int year, int month);
}
