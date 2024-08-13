package com.logan.socialnetwork.security.controller;


import com.logan.socialnetwork.model.Profiles;
import com.logan.socialnetwork.security.RegistrationForm;
import com.logan.socialnetwork.security.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/register")
@SessionAttributes("profiles")
@Data
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping
    public ModelAndView registerForm(ModelAndView modelAndView){
        return registrationService.registerForm(modelAndView);
    }

    @ModelAttribute(name = "profiles")
    public Profiles profiles(){
        return new Profiles();
    }

    @PostMapping ModelAndView processRegistration(RegistrationForm registrationForm,
                                                  HttpServletRequest request, ModelAndView modelAndView,
                                                  @ModelAttribute("profiles") Profiles profiles){
        return registrationService.processRegistration(registrationForm, request, modelAndView, profiles);
    }

}
