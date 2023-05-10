package com.codestates.edusync.auth.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class CustomDefaultOAuth2User extends DefaultOAuth2User {

    private OAuth2Attributes oAuth2Attributes;

    public CustomDefaultOAuth2User(Collection<? extends GrantedAuthority> authorities,
                                   Map<String, Object> attributes,
                                   String nameAttributeKey,
                                   OAuth2Attributes oAuth2Attributes) {
        super(authorities, attributes, nameAttributeKey);
        this.oAuth2Attributes = oAuth2Attributes;
    }

    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */

}