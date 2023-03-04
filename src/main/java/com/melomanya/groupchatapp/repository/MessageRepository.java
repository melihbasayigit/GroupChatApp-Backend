package com.melomanya.groupchatapp.repository;

import com.melomanya.groupchatapp.data.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    List<Message> findByRoom(String room);
    List<Message> findByRoomOrderByDate(String room);


}
