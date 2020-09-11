package org.kpax.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class ClientController {

    @Autowired
    private OAuth2RestOperations restOperations;
    /*
    @RequestMapping("/its-me")
    public String home(Principal principal) {
        return "The principal's name is: " + principal.getName();
    }

    @RequestMapping("/its-also-me")
    public String home() {
        String message = restOperations.getForObject("http://localhost:8080/auth/user/also-me", String.class);
        return "Message from auth server: " + message;
    }


    @RequestMapping("/its-me")
    public String home(Principal principal) {
        return "The principal's name is: " + principal.getName();
    }

    @RequestMapping("/its-also-me")
    public String home() {
        String message = restOperations.getForObject("http://localhost:8080/auth/user/also-me", String.class);
        return "Message from auth server: " + message;
    }

*/

    @RequestMapping("/userInfo")
    @ResponseBody
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User user) {
        System.out.println("**user info heree");
        return user.getAttributes();
    }



}