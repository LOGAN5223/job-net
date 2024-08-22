package com.logan.socialnetwork.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "chat_members")
@NoArgsConstructor
public class ChatMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userlogin;
    private String chatRoomId;
    private String type;

    public ChatMembers(String userlogin, String chatRoomId, String type) {
        this.userlogin = userlogin;
        this.chatRoomId = chatRoomId;
        this.type = type;
    }
}
