package com.logan.socialnetwork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ProfileSubscribers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profileLogin;
    private String subscriberLogin;

    public ProfileSubscribers(String profileLogin, String subscriberLogin) {
        this.profileLogin = profileLogin;
        this.subscriberLogin = subscriberLogin;
    }
}
