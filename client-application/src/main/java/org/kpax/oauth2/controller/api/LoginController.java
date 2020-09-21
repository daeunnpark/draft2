package org.kpax.oauth2.controller.api;

import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.payload.ApiResponse;
import org.kpax.oauth2.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/check")
    public ApiResponse user(@AuthenticationPrincipal UserPrincipal principal) {
        return new ApiResponse(true, "user", userService.findById(principal.getId()));
    }

}