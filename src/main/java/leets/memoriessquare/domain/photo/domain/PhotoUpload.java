package leets.memoriessquare.domain.photo.domain;

import leets.memoriessquare.domain.shared.entity.BaseTimeEntity;
import leets.memoriessquare.domain.user.domain.User;
import lombok.*;

import jakarta.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "photo_uploads")
public class PhotoUpload extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String imageUrl;
}
