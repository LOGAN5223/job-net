package com.logan.socialnetwork.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class UserSubscriptions {
    private Long profileLogin;
    private Long subProfileLogin;
}
