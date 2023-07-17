package leets.memoriessquare.domain.photo.repository;

import leets.memoriessquare.domain.photo.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
    List<Photo> findAllByUser_IdAndCreatedAtBetweenOrderByCreatedAtDesc(UUID userId, LocalDateTime start, LocalDateTime end);
}
