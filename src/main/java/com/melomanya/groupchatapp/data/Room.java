package com.melomanya.groupchatapp.data;

import java.util.List;

public class Room {
    private String id;
    private int userCount;
    private List<Message> messages;

    public Room() {}

    public Room(String id, int userCount, List<Message> messages) {
        this.id = id;
        this.userCount = userCount;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
