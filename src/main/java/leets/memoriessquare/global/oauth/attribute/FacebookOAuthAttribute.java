package leets.memoriessquare.global.oauth.attribute;

import java.util.Map;

public class FacebookOAuthAttribute extends OAuthAttribute {
    public FacebookOAuthAttribute(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }
}
