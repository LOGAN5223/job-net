package com.logan.socialnetwork.model;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@Builder
public class ProfileContent {
    @Id
    private final String contentId;
    private final String profileId;
    private final String content;
    private final Date creatingDate;
}
