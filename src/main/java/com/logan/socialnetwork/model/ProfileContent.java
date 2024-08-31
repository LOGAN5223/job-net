package com.logan.socialnetwork.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Document
@Data
public class ProfileContent {
    private String userlogin;
    private String content;
    private Date creatingDate = new Date();
    private List<String> viewersList;


    public String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return formatter.format(creatingDate);
    }
}