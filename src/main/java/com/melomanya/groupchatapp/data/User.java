package com.melomanya.groupchatapp.data;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User {

    @Id
    @Column(name = "socket_id")
    private String socketId;
    @Column(name = "display_name")
    private String displayName;

    public User(String displayName, String socketId) {
        this.displayName = displayName;
        this.socketId = socketId;
    }

    public User() {}

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
