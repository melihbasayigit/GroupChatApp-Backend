package com.melomanya.groupchatapp.controller;

import com.melomanya.groupchatapp.data.Message;
import com.melomanya.groupchatapp.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    @RequestMapping("/{room}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String room){
        return ResponseEntity.ok(messageService.getMessagesFromRoom(room));
    }
}
