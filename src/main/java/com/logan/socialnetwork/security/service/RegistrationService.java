package com.logan.socialnetwork.security.service;

import com.logan.socialnetwork.model.Profiles;
import com.logan.socialnetwork.model.Users;
import com.logan.socialnetwork.security.RegistrationForm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface RegistrationService {
    ModelAndView processRegistration(RegistrationForm registrationForm, HttpServletRequest request, ModelAndView modelAndView, Profiles profiles);
    void autoAuthenticateUserAndSetSession(Users users, HttpServletRequest request);
    ModelAndView registerForm(ModelAndView modelAndView);
    void setAuthenticationManager();
}
