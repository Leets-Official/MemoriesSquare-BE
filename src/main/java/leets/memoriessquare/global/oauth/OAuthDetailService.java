package leets.memoriessquare.global.oauth;

import leets.memoriessquare.domain.user.domain.User;
import leets.memoriessquare.domain.user.repository.UserRepository;
import leets.memoriessquare.domain.user.type.Vendor;
import leets.memoriessquare.global.error.ErrorCode;
import leets.memoriessquare.global.error.exception.ServiceException;
import leets.memoriessquare.global.oauth.attribute.FacebookOAuthAttribute;
import leets.memoriessquare.global.oauth.attribute.GoogleOAuthAttribute;
import leets.memoriessquare.global.oauth.attribute.OAuthAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OAuthDetailService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String vendor = userRequest.getClientRegistration().getRegistrationId();
        Optional<OAuthAttribute> attribute = Optional.empty();

        if (vendor.equalsIgnoreCase(Vendor.FACEBOOK.getVendor())) {
            attribute = Optional.of(new FacebookOAuthAttribute(oAuth2User.getAttributes()));
        }
        if (vendor.equalsIgnoreCase(Vendor.GOOGLE.getVendor())) {
            attribute = Optional.of(new GoogleOAuthAttribute(oAuth2User.getAttributes()));
        }

        OAuthAttribute attr = attribute.orElseThrow(() -> new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR));
        String email = attr.getEmail();
        String name = attr.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            User createdUser = User.builder()
                    .email(email)
                    .nickname(name)
                    .vendor(Vendor.valueOf(vendor.toUpperCase()))
                    .build();
            user = userRepository.save(createdUser);
        }

        return new OAuthDetails(user.getId(), user.getEmail(), user.getNickname(), attr.getAttributes());
    }
}
