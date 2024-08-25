package com.logan.socialnetwork.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@Entity
public class NewsFeeds {
    @Id
    private Long id;
    private Long profileId;
    @ElementCollection
    private ArrayList<Long> contentId;
    private Date addedAt = new Date();
}