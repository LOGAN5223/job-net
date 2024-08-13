package com.logan.socialnetwork.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ProfileSubscribers {
    private Long profileLogin;
    private Long subscriberLogin;
}
