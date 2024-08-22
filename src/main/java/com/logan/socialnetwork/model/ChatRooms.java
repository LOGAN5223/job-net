package com.logan.socialnetwork.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "chat_rooms")
public class ChatRooms {
    @Id
    private String chatRoomId;
    private String chatRoomName;

}
