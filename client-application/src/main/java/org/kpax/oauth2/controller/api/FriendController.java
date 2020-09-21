package org.kpax.oauth2.controller.api;

import org.kpax.oauth2.dto.model.UserDto;
import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.payload.ApiResponse;
import org.kpax.oauth2.service.relationship.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private RelationshipService relationshipService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse getFriends(@AuthenticationPrincipal UserPrincipal principal) {
        List<UserDto> friends = relationshipService.findFriendsByUserId(principal.getId());
        return new ApiResponse(true, "friends", friends);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse addFriend(@AuthenticationPrincipal UserPrincipal principal, @RequestBody UserDto userDto) {
        relationshipService.addFriend(principal.getId(), userDto.getId());
        return new ApiResponse(true, "friendId", null);
    }

    @DeleteMapping(value = "/{friendId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse deleteFriend(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long friendId) {
        relationshipService.deleteFriend(principal.getId(), friendId);
        return new ApiResponse(true, "friendId", null);
    }

}
