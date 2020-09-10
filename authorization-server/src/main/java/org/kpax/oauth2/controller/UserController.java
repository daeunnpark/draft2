package org.kpax.oauth2.controller;

import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class UserController {

    @Autowired UserRepository userRepository;

    @RequestMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping("/user/also-me")
    public String test(Principal user) {
        return "The principal's name is: " + user.getName();
    }


    @RequestMapping("/user/info")
    public Map<String, Object> userData(@AuthenticationPrincipal OAuth2Authentication auth, Principal p) {
        Map<String, Object> profile = new HashMap<String, Object>();

        Set<String> scopes = auth.getOAuth2Request().getScope();
        User user = userRepository.findByUsername(p.getName()).get();

        System.out.println(scopes);

        if (scopes.contains("id")) {
            profile.put("id", user.getUsername());
        }
        if (scopes.contains("name")) {
            profile.put("name", user.getName());
        }
        if (scopes.contains("email")) {
            profile.put("email", user.getEmail());
        }
        if (scopes.contains("phone")) {
            profile.put("phone", user.getPhone());
        }

        return profile;
    }

}
