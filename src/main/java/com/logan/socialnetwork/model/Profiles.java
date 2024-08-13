package com.logan.socialnetwork.model;

import com.mongodb.lang.Nullable;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "profiles")
@Data
public class Profiles {
    @Id
    private String id;
    @Setter
    private String userlogin;
    private String userName;
    private String sureName;
    private String biography;
    private String profilePictureUrl = "";
    private Date registrationDate = new Date();
}

