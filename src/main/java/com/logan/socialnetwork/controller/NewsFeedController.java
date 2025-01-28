package com.logan.socialnetwork.controller;

import com.logan.socialnetwork.model.Profiles;
import com.logan.socialnetwork.repository.NewsFeedRepository;
import com.logan.socialnetwork.repository.ProfileContentRepository;
import com.logan.socialnetwork.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/v1/feed")
@AllArgsConstructor
public class NewsFeedController {
    private NewsFeedRepository newsFeedRepository;
    private ProfileContentRepository profileContentRepository;
    private ProfileRepository profileRepository;


    @GetMapping
    public ModelAndView getFeed(Authentication authentication){
//        List<ProfileContent> profileContents = newsFeedRepository.findByUserlogin(authentication.getName()).getContentId().stream()
//                .map(x -> profileContentRepository.getProfileContentById(x))
//                .toList();
        Profiles profiles = profileRepository.findByUserlogin(authentication.getName());
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("profileContents", profileContents);
        modelAndView.addObject("profile", profiles);
        modelAndView.setViewName("newsFeed");
        return modelAndView;
    }


}
