package com.melomanya.groupchatapp.repository;

import com.melomanya.groupchatapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findByRoomOrderByDate(String room);
/*
    List<Message> findByRoomOrderByDate(String room, Pageable pageable);*/
}