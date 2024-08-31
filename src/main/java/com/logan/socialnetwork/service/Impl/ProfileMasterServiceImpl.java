package com.logan.socialnetwork.service.Impl;

import com.logan.socialnetwork.model.*;
import com.logan.socialnetwork.repository.ProfileContentRepository;
import com.logan.socialnetwork.repository.ProfileRepository;
import com.logan.socialnetwork.repository.ProfileSubsRepository;
import com.logan.socialnetwork.security.UserRepository;
import com.logan.socialnetwork.service.ProfileMasterService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
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
    @NonNull
    private ProfileSubsRepository profileSubsRepository;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");


    private ModelAndView modelAndView;
    private String targetedUserlogin;
    private boolean subscribed;

    @Override
    public ModelAndView saveProfile(@Valid Profiles profiles, ModelAndView modelAndView) {
        profileRepository.save(profiles);
        Users user = userRepository.findByUsername(profiles.getUserlogin());
        user.setRole("USER");
        userRepository.save(user);
        this.modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @Override
    public ModelAndView getProfile(String username, ModelAndView modelAndView, Authentication authentication) {
        targetedUserlogin = username;
        subscribed = profileSubsRepository.findByProfileLoginAndSubscriberLogin(targetedUserlogin, authentication.getName()) != null;
        Profiles profileData = profileRepository.findByUserlogin(username);
        List<ProfileContent> profileContent = profileContentRepository.findByUserlogin(username);
        List<ProfileSubscribers> profileSubscribers = profileSubsRepository.findByProfileLogin(targetedUserlogin);
        List<ProfileSubscribers> subscriptions = profileSubsRepository.findBySubscriberLogin(targetedUserlogin);

        modelAndView.setViewName("profile");
        modelAndView.addObject("profileContent", profileContent);
        modelAndView.addObject("newProfileContent", new ProfileContent());
        modelAndView.addObject("profileData", profileData);
        modelAndView.addObject("regDate", formatter.format(profileData.getRegistrationDate()));
        modelAndView.addObject("currentUser", Objects.equals(profileData.getUserlogin(), authentication.getName()));
        modelAndView.addObject("subscribers", profileSubscribers);
        modelAndView.addObject("subscriptions", subscriptions);
        modelAndView.addObject("subsAmount", profileSubscribers.size());
        modelAndView.addObject("subscriptionsAmount", subscriptions.size());
        modelAndView.addObject("cUserName", authentication.getName());
        modelAndView.addObject("cUserIcon", profileRepository.findByUserlogin(authentication.getName()).getProfilePictureUrl());
        modelAndView.addObject("subscribed", subscribed);

        return modelAndView;
    }

    @Override
    public ModelAndView postNewContent(@ModelAttribute("newProfileContent")ProfileContent profileContent,
                                       Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        profileContent.setUserlogin(authentication.getName());
        profileContentRepository.save(profileContent);
        modelAndView.setViewName("redirect:/api/v1/profilePage/" + authentication.getName());
        modelAndView.addObject("profileContent", profileContentRepository.findByUserlogin(authentication.getName()));
        return modelAndView;
    }

    @Override
    public ModelAndView unSubscribeOnUser(Authentication authentication, ProfileSubscribers profileSubscribers){
        profileSubscribers.setProfileLogin(targetedUserlogin);
        profileSubscribers.setSubscriberLogin(authentication.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/api/v1/profilePage/" + targetedUserlogin);
        if(subscribed){
            subscribed = false;
            profileSubsRepository.delete(profileSubsRepository.findByProfileLoginAndSubscriberLogin(targetedUserlogin, authentication.getName()));
            modelAndView.addObject("subscribed", subscribed);
        }
        else {
            subscribed = true;
            profileSubsRepository.save(profileSubscribers);
            modelAndView.addObject("subscribed", subscribed);
        }
        return modelAndView;
    }
}
