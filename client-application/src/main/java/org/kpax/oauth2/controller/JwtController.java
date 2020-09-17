package org.kpax.oauth2.controller;

import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.payload.ApiResponse;
import org.kpax.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class JwtController {

    @Autowired UserRepository userRepository;

    @RequestMapping("/home")
    public ModelAndView home(){
        ModelAndView page = new ModelAndView("home");
        return page;
    }

    @RequestMapping("/check")
    public ApiResponse user(@AuthenticationPrincipal UserPrincipal principal) {
        Object data = Collections.singletonMap("user", userRepository.findById(principal.getId()).get());
        return new ApiResponse(true, data);
    }

}