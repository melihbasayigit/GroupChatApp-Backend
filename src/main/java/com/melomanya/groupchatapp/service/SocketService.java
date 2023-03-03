package com.melomanya.groupchatapp.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.melomanya.groupchatapp.data.Message;
import com.melomanya.groupchatapp.data.Room;
import com.melomanya.groupchatapp.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class SocketService {

    private final UserService userService;

    private SocketIOServer socketIOServer;
    //logger için;
    private MessageService messageService;
    private Logger logger=  LoggerFactory.getLogger(getClass());

    public SocketService(SocketIOServer socketIOServer, MessageService messageService, UserService userService) {
        this.socketIOServer = socketIOServer;
        this.messageService=messageService;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        //Gelen mesaj vs gibi objeler
        socketIOServer.addEventListener("send_message", Message.class, messageIsSent());
        this.userService = userService;
    }

    private String generateRandomUser() {
        int rand = new Random().nextInt(899999)+100000;
        return "User" + rand;
    }

    private ConnectListener onConnected() {
        return socketIOClient ->{
            logger.debug("SocketId: "+ socketIOClient.getSessionId().toString()+"connected");
            User test = new User();
            if ( socketIOClient.getHandshakeData().getSingleHeader("name") != null) {
                test.setDisplayName(socketIOClient.getHandshakeData().getSingleHeader("name"));
            } else {
                test.setDisplayName(generateRandomUser());
            }
            test.setSocketId(socketIOClient.getSessionId().toString());
            userService.registerUser(test);
            String roomId = socketIOClient.getHandshakeData().getSingleUrlParam("room");
            socketIOClient.joinRoom(roomId);
            Room messageRoom = createMessageRoom(socketIOClient);
            sendMessages(roomId, "get_message", socketIOClient, messageRoom);
        };
    }
    private DisconnectListener onDisconnected() {
        return socketIOClient->{
            logger.info("SocketId: "+socketIOClient.getSessionId().toString()+"disconnected");
            Room messageRoom = createMessageRoom(socketIOClient);
            sendMessages(messageRoom.getId(), "get_message", socketIOClient, messageRoom);
        };
    }
    //Mesaj gitti mi gitmedi mi mesaj bilgilerinin falan göndermek için DataListener kullanılıypor
    //Mesajın gideceği yerleri buradan belirliyoruz.ackRequest mesjain gidip gitmediği
    private DataListener<Message> messageIsSent() {
        return (socketIOClient, message, ackRequest) -> {
            logger.info("" + socketIOClient.getSessionId() + "" + message.getMessage());
            String roomId = socketIOClient.getHandshakeData().getSingleUrlParam("room");
            message.setRoom(roomId);
            message.setSenderId(socketIOClient.getSessionId().toString());
            message.setSender(getSenderName(socketIOClient.getSessionId().toString()));
            messageService.saveMessage(message);
            Room messageRoom = createMessageRoom(socketIOClient);
            sendMessages(roomId, "get_message", socketIOClient, messageRoom);
        };
    }
    private void sendMessages(String roomId, String eventName, SocketIOClient senderClient, Room room) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(roomId).getClients()) {
            client.sendEvent(eventName, room);
        }
    }

    private String getSenderName(String socketId) {
        return userService.getDisplayNameFromId(socketId);
    }

    private int getRoomUserCount(SocketIOClient client, String roomId) {
        return client.getNamespace().getRoomOperations(roomId).getClients().size();
    }

    private List<Message> getMessageListFromRoomId(String roomId) {
        return messageService.getMessagesFromRoom(roomId);
    }

    private Room createMessageRoom(SocketIOClient client) {
        Room messageRoom = new Room();
        String roomId = client.getHandshakeData().getSingleUrlParam("room");
        messageRoom.setId(roomId);
        messageRoom.setUserCount(getRoomUserCount(client, roomId));
        messageRoom.setMessages(getMessageListFromRoomId(roomId));
        return messageRoom;
    }


}
