package com.logan.socialnetwork.service.Impl;

import com.logan.socialnetwork.model.ProfileContent;
import com.logan.socialnetwork.model.Profiles;
import com.logan.socialnetwork.model.Users;
import com.logan.socialnetwork.repository.ProfileContentRepository;
import com.logan.socialnetwork.repository.ProfileRepository;
import com.logan.socialnetwork.security.UserRepository;
import com.logan.socialnetwork.service.ProfileMasterService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Service
@Data
public class ProfileMasterServiceImpl implements ProfileMasterService {
    @NonNull
    private ProfileRepository profileRepository;
    @NonNull
    private UserRepository userRepository;
    @NonNull
    private ProfileContentRepository profileContentRepository;
    private ModelAndView modelAndView;

    @Override
    public ModelAndView saveProfile(@Valid Profiles profiles, Errors errors, ModelAndView modelAndView) {
        profileRepository.save(profiles);
        Users user = userRepository.findByUsername(profiles.getUserlogin());
        user.setRole("USER");
        userRepository.save(user);
        this.modelAndView = new ModelAndView();
        modelAndView.setViewName("info");
        return modelAndView;
    }

    @Override
    public ModelAndView getProfile(String username, ModelAndView modelAndView, Authentication authentication) {
        Profiles profileData = profileRepository.findByUserlogin(username);
        List<ProfileContent> profileContent = profileContentRepository.findByUserlogin(username);
        modelAndView.setViewName("profile");
        modelAndView.addObject("profileContent", profileContent);
        modelAndView.addObject("newProfileContent", new ProfileContent());
        modelAndView.addObject("profileData", profileData);
        modelAndView.addObject("currentUser", Objects.equals(profileData.getUserlogin(), authentication.getName()));
        return modelAndView;
    }

    public ModelAndView postNewContent(@ModelAttribute("newProfileContent")ProfileContent profileContent, Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        profileContent.setUserlogin(authentication.getName());
        profileContentRepository.save(profileContent);
        modelAndView.setViewName("redirect:/api/v1/profilePage/" + authentication.getName());
        modelAndView.addObject("profileContent", profileContentRepository.findByUserlogin(authentication.getName()));
        return modelAndView;
    }

}
