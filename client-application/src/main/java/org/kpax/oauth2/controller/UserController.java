package org.kpax.oauth2.controller;

import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.payload.ApiResponse;
import org.kpax.oauth2.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse getChatMembers(@AuthenticationPrincipal UserPrincipal principal) {
        return new ApiResponse(true, userService.getAll());
    }

}
