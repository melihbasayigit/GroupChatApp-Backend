package com.melomanya.groupchatapp.controller;

import com.melomanya.groupchatapp.model.Message;
import com.melomanya.groupchatapp.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping(path = "/{roomId}/{pageNumber}")
    public Page<Message> getAllCustomers(@PathVariable String roomId, @PathVariable int pageNumber) {
        return null;
    }






}

