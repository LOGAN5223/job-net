package com.logan.socialnetwork.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@Builder
public class Messages {

    private String sender;
    private String chatRoomId;
    private String content;
    private Date sentAt;
    private MessageType type;

    public enum MessageType{
        CHAT,
        JOIN,
        LEAVER
    }
}


