package leets.memoriessquare.domain.user.domain;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import leets.memoriessquare.domain.shared.entity.BaseTimeEntity;
import leets.memoriessquare.domain.user.type.Vendor;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at=now() where id=?")
@Where(clause = "deleted_at IS NULL")
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(columnDefinition = "CHAR(10)")
    @Enumerated(EnumType.STRING)
    private Vendor vendor;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column()
    private LocalDateTime deletedAt;

    @PreDestroy()
    public void preDestroy() {
        this.deletedAt = LocalDateTime.now();
    }
}
