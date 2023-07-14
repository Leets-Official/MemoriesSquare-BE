package leets.memoriessquare.domain.photo.domain;

import jakarta.persistence.*;
import leets.memoriessquare.domain.shared.entity.BaseTimeEntity;
import leets.memoriessquare.domain.user.domain.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "photos")
public class Photo extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String imageUrl;

    @Builder.Default
    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isCrop = false;
}
