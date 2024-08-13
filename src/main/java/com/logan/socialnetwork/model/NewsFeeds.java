package com.logan.socialnetwork.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Date;

@Embeddable
@Data
public class NewsFeeds {
    private Long profileId;
    private Long contentId;
    private Date addedAt = new Date();
}
