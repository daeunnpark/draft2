package org.kpax.oauth2.controller;

import org.kpax.oauth2.model.User;
import org.kpax.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JwtController {

    @Autowired UserRepository userRepository;

    @RequestMapping("/")
    public ModelAndView home(){
        ModelAndView page = new ModelAndView("home");
        return page;
    }

    @RequestMapping("/check")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public Map<String, Object> user(Authentication auth) {
        System.out.println("checkinggg");
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername()).get();

        System.out.println(user.getName());
        //System.out.println(user.getName());

        HashMap<String, Object> res = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Object> userData = new HashMap<>();

        userData.put("id", 1);
        userData.put("name", user.getName());
        userData.put("image", "https://hayvets.co.uk/wp-content/uploads/rabbit.png");

        data.put("user", userData);

        res.put("code", 200);
        res.put("message", "OK");
        res.put("data", data);

        return res;
    }

}