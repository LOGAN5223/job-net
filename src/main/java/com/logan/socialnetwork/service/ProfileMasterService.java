package com.logan.socialnetwork.service;

import com.logan.socialnetwork.model.Profiles;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

public interface ProfileMasterService {
    ModelAndView saveProfile(Profiles profiles, Errors errors, ModelAndView modelAndView);
    ModelAndView getProfile(String username, ModelAndView modelAndView);
}
