package com.melomanya.groupchatapp.service;

import com.melomanya.groupchatapp.model.Message;
import com.melomanya.groupchatapp.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public void saveMessage(Message message) {
        repository.save(message);
    }

    public List<Message> getMessages(String roomId) {
        return repository.findByRoomOrderByDate(roomId);
    }

    /*public List<Message> getMessages(String roomId, Pageable pageable) {
        return repository.findByRoomOrderByDate(roomId, pageable);
    }*/
}