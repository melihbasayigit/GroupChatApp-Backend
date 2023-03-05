package com.melomanya.groupchatapp.repository;

import com.melomanya.groupchatapp.data.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findByRoomOrderByDate(String room);
}