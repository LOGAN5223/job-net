package com.logan.socialnetwork.security;

import com.logan.socialnetwork.model.Users;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class RegistrationForm {

    private final String username;
    private final String password;
    private final String email;

    public Users toUser(BCryptPasswordEncoder passwordEncoder){
        return new Users(
                username, passwordEncoder.encode(password),
                email, "PREUSER"
        );
    }

}
