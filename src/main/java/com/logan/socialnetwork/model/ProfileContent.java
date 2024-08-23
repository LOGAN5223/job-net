package com.logan.socialnetwork.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Getter
@Setter
public class ProfileContent {
    private String userlogin;
    private String content;
    private Date creatingDate = new Date();
    private List<String> viewersList;
}
