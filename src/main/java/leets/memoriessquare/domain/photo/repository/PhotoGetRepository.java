package leets.memoriessquare.domain.photo.repository;

import leets.memoriessquare.domain.photo.domain.PhotoGet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhotoGetRepository extends JpaRepository<PhotoGet, UUID> {
}
