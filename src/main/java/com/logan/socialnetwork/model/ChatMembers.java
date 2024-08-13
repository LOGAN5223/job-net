package com.logan.socialnetwork.model;

import jakarta.persistence.*;
import lombok.Data;


@Embeddable
@Data
@Table(name = "chat_members")
public class ChatMembers {
    private String username;
    private String chatRoomId;
    private String type;
}
