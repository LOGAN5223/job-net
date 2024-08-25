package com.logan.socialnetwork.controller;

import com.logan.socialnetwork.model.Profiles;
import com.logan.socialnetwork.service.ProfileMasterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/preProfileSettings")
@SessionAttributes(value = "profiles")
@AllArgsConstructor
public class ProfileSettingsController {
    ProfileMasterService profileSettingsService;
    @PostMapping
    public ModelAndView saveProfile(@Valid Profiles profiles, ModelAndView modelAndView){
        return profileSettingsService.saveProfile(profiles, modelAndView);
    }
}
