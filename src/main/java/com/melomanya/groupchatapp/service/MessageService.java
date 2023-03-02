package com.melomanya.groupchatapp.service;

import com.melomanya.groupchatapp.data.Message;
import com.melomanya.groupchatapp.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Message saveMessage(Message message) {
        return repository.save(message);
    }

    public Message getMessageById(String messageId) {
        return repository.findById(messageId).orElse(null);
    }

    public List<Message> getMessagesFromRoom(String room) {
        return repository.findByRoom(room);
    }

}
