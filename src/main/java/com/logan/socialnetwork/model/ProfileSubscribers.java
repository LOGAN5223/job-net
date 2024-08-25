package com.logan.socialnetwork.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProfileSubscribers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profileLogin;
    private String subscriberLogin;
}
