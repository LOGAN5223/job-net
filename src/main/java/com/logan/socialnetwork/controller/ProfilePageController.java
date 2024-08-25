package com.logan.socialnetwork.controller;

import com.logan.socialnetwork.model.ProfileContent;
import com.logan.socialnetwork.model.ProfileSubscribers;
import com.logan.socialnetwork.service.ProfileMasterService;
import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/v1/profilePage")
@SessionAttributes(value = "profileData")
@Data
public class ProfilePageController {
    @NonNull
    ProfileMasterService profilePageService;

    @GetMapping(value = "/{username}")
    public ModelAndView getProfile(@PathVariable String username, ModelAndView modelAndView, Authentication authentication){
        return profilePageService.getProfile(username, modelAndView, authentication);
    }

    @PostMapping("/post")
    public ModelAndView postNewContent(ProfileContent profileContent, Authentication authentication){
        return profilePageService.postNewContent(profileContent, authentication);
    }

    @PostMapping("/subscribe")
    public ModelAndView unSubscribeOnUser(Authentication authentication, ProfileSubscribers profileSubscribers){
        return profilePageService.unSubscribeOnUser(authentication, profileSubscribers);
    }
}
