package com.melomanya.groupchatapp.data;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.data.util.TypeUtils.type;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name ="message")
public class Message {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "message_id")
    private String id;



    // Room değişkenini frontend tarafına göndermememiz lazım bunun için bir annotation filan var mı?
    //Getterlarını silmek yeterliymiş :)
    @Column(name = "room")
    private String room;
    @Column(name = "context")
    private String context;

    @Column(name = "sender")
    private String sender;
    @Column(name = "sender_id")
    private String senderId;
    @Column(name = "local_date_time")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime localDateTime;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }




    public Message() {}



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public void setRoom(String room) {
        this.room = room;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }


}
