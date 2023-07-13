package leets.memoriessquare.domain.photo.repository;

import leets.memoriessquare.domain.photo.domain.Photo;
import leets.memoriessquare.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
//    List<Photo> findByUser(User user);
}
