package org.kpax.oauth2.security.oauth2;

import java.util.Map;

public class CustomOAuth2UserInfo extends OAuth2UserInfo {

    public CustomOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUsername() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getPhone() {
        return (String) attributes.get("phone");
    }
}
