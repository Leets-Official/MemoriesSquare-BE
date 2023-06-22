package leets.memoriessquare.domain.user.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Vendor {
    FACEBOOK("FACEBOOK");

    private final String vendor;
}
