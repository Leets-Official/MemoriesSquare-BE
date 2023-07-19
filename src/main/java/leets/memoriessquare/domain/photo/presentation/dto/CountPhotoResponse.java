package leets.memoriessquare.domain.photo.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class CountPhotoResponse {
    private final Map<String, Long> count;
}
