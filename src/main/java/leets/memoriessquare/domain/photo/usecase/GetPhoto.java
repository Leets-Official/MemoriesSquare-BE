package leets.memoriessquare.domain.photo.usecase;

import leets.memoriessquare.domain.photo.domain.PhotoGet;
import leets.memoriessquare.domain.photo.repository.PhotoGetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GetPhoto {
    private final PhotoGetRepository photoGetRepository;

    public PhotoGet execute(UUID photoId) {
        return photoGetRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("Photo not found"));
    }
}
