package com.logan.socialnetwork.service;

import com.logan.socialnetwork.model.ProfileContent;
import com.logan.socialnetwork.model.ProfileSubscribers;
import com.logan.socialnetwork.model.Profiles;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

public interface ProfileMasterService {
    ModelAndView saveProfile(Profiles profiles, ModelAndView modelAndView);
    ModelAndView getProfile(String username, ModelAndView modelAndView, Authentication authentication);
    ModelAndView postNewContent(ProfileContent profileContent, Authentication authentication);
    ModelAndView unSubscribeOnUser(Authentication authentication, ProfileSubscribers profileSubscribers);
}
