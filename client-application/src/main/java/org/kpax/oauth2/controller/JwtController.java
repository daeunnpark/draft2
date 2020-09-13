package org.kpax.oauth2.controller;

import org.kpax.oauth2.model.User;
import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.payload.ApiResponse;
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
    @PreAuthorize("hasRole('USER')")
    public ApiResponse user(@AuthenticationPrincipal UserPrincipal principal) {
        return new ApiResponse(true, userRepository.findById(principal.getId()).get());
    }

}