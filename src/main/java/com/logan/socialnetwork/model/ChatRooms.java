package com.logan.socialnetwork.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "chat_rooms")
public class ChatRooms {
    @Id
    private Long chatRoomId;
    private String chatRoomName;

}
