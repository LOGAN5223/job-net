package com.logan.socialnetwork.service.Impl;

import com.logan.socialnetwork.model.Profiles;
import com.logan.socialnetwork.model.Users;
import com.logan.socialnetwork.repository.ProfileRepository;
import com.logan.socialnetwork.security.UserRepository;
import com.logan.socialnetwork.service.ProfileMasterService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

@Service
@Data
public class ProfileMasterServiceImpl implements ProfileMasterService {
    @NonNull
    private ProfileRepository profileRepository;
    @NonNull
    private UserRepository userRepository;
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
    public ModelAndView getProfile(String username, ModelAndView modelAndView) {
        Profiles profileData = profileRepository.findByUserlogin(username);
        modelAndView.setViewName("profile");
        modelAndView.addObject("profileData", profileData);
        return modelAndView;
    }
}
