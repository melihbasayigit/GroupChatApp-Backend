package com.melomanya.groupchatapp.data;

import java.util.List;

public class Room {
    private int number;
    private int connectedUsers;
    private List<Message> messages;

    public Room() {}

    public Room(int number, int connectedUsers, List<Message> messages) {
        this.number = number;
        this.connectedUsers = connectedUsers;
        this.messages = messages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getConnectedUsers() {
        return connectedUsers;
    }

    public void setConnectedUsers(int connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
