package leets.memoriessquare.domain.photo.repository;

import leets.memoriessquare.domain.photo.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
    List<Photo> findAllByUser_IdAndCreatedAtBetweenOrderByCreatedAtDesc(UUID userId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT DATE(createdAt) as date, COUNT(*) as count FROM photos WHERE user.id = :userId AND YEAR(createdAt) = :year AND MONTH(createdAt) = :month AND isCrop = false GROUP BY DATE(createdAt) ")
    List<Map<String, Object>> countByYearAndMonth(@Param("userId") UUID userId, @Param("year") int year, @Param("month") int month);

    List<Photo> findAllByUser_Id(UUID userId);
}
