package com.logan.socialnetwork.security.service.impl;

import com.logan.socialnetwork.model.Profiles;
import com.logan.socialnetwork.model.Users;
import com.logan.socialnetwork.security.RegistrationForm;
import com.logan.socialnetwork.security.UserRepository;
import com.logan.socialnetwork.security.service.RegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
@Data
public class RegistrationServiceImpl implements RegistrationService {
    @NonNull
    private UserRepository userRepository;
    @NonNull
    private BCryptPasswordEncoder passwordEncoder;
    private ModelAndView modelAndView;
    protected AuthenticationManager authenticationManager;

    @Override
    public ModelAndView processRegistration(RegistrationForm registrationForm, HttpServletRequest request, ModelAndView modelAndView, Profiles profiles) {
        userRepository.save(registrationForm.toUser(passwordEncoder));
        autoAuthenticateUserAndSetSession(userRepository.findByUsername(registrationForm.getUsername()), request);
        this.modelAndView = new ModelAndView();
        profiles.setUserlogin(String.valueOf(userRepository.findByUsername(registrationForm.getUsername()).getUsername()));
        modelAndView.setViewName("preProfileSettings");
        modelAndView.addObject("profiles", profiles);
        return modelAndView;
    }

    @Override
    public void autoAuthenticateUserAndSetSession(Users users, HttpServletRequest request) {
        setAuthenticationManager();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword());

        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));

        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    @Override
    public ModelAndView registerForm(ModelAndView modelAndView) {
        this.modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @Override
    public void setAuthenticationManager() {
        authenticationManager =  new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        };
    }
}
