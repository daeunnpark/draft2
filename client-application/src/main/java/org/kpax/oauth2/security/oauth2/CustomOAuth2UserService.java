package org.kpax.oauth2.security.oauth2;

import org.kpax.oauth2.model.User;
import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.repository.UserRepository;
import org.kpax.oauth2.security.oauth2.CustomOAuth2UserInfo;
import org.kpax.oauth2.security.oauth2.OAuth2UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = new CustomOAuth2UserInfo(oAuth2User.getAttributes());

        if (StringUtils.isEmpty(oAuth2UserInfo.getUsername())) {
            //throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByUsername(oAuth2UserInfo.getUsername());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        System.out.println("****registering new");
        User user = new User();
        user.setUsername(oAuth2UserInfo.getUsername());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setPhone(oAuth2UserInfo.getPhone());
        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        System.out.println("****updating existing");
        //existingUser.setUsername(oAuth2UserInfo.getUsername());
        existingUser.setName(oAuth2UserInfo.getName());
        existingUser.setEmail(oAuth2UserInfo.getEmail());
        existingUser.setPhone(oAuth2UserInfo.getPhone());
        return userRepository.save(existingUser);
    }

}
