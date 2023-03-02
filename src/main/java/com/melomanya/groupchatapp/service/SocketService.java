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
        return connectMessage->{
            logger.debug("SocketId: "+connectMessage.getSessionId().toString()+"connected");
            User test = new User();
            if ( connectMessage.getHandshakeData().getSingleHeader("name") != null) {
                test.setDisplayName(connectMessage.getHandshakeData().getSingleHeader("name"));
            } else {
                test.setDisplayName(generateRandomUser());
            }
            test.setSocketId(connectMessage.getSessionId().toString());
            userService.registerUser(test);
            String room = connectMessage.getHandshakeData().getSingleUrlParam("room");
            connectMessage.joinRoom(room);
        };
    }
    private DisconnectListener onDisconnected() {
        return connectMessage->{
            logger.info("SocketId: "+connectMessage.getSessionId().toString()+"disconnected");
        };
    }
    //Mesaj gitti mi gitmedi mi mesaj bilgilerinin falan göndermek için DataListener kullanılıypor
    //Mesajın gideceği yerleri buradan belirliyoruz.ackRequest mesjain gidip gitmediği
    private DataListener<Message> messageIsSent() {
        return (socketIOClient, message, ackRequest) -> {
            logger.info("" + socketIOClient.getSessionId() + "" + message.getContext());
            messageService.saveMessage(message);
            String room = socketIOClient.getHandshakeData().getSingleUrlParam("room");
            sendMessage(room, "get_message", socketIOClient, message.getContext());
        };
    }

    private void sendMessage(String room, String eventName, SocketIOClient senderClient, String message) {
            for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
                client.sendEvent(eventName, message);
            }
    }

}
