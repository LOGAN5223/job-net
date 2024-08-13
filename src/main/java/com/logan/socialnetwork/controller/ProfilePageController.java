package com.logan.socialnetwork.controller;

import com.logan.socialnetwork.service.ProfileMasterService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/v1/profilePage")
@SessionAttributes(value = "profileData")
@Data
public class ProfilePageController {
    ProfileMasterService profilePageService;

    @GetMapping(value = "/{username}")
    public ModelAndView getProfile(@PathVariable String username, ModelAndView modelAndView){
        return profilePageService.getProfile(username, modelAndView);
    }
}
