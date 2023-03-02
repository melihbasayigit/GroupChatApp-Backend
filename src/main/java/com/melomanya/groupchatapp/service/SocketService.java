package com.melomanya.groupchatapp.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.melomanya.groupchatapp.data.Message;
import com.melomanya.groupchatapp.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SocketService {

    private final UserService userService;

    private SocketIOServer socketIOServer;
    //logger için;
    private Logger logger=  LoggerFactory.getLogger(getClass());

    public SocketService(SocketIOServer socketIOServer, UserService userService) {
        this.socketIOServer = socketIOServer;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        //Gelen mesaj vs gibi objeler
        socketIOServer.addEventListener("send_message", Message.class,messageIsSent());
        this.userService = userService;
    }

    private String generateRandomUser() {
        int rand = new Random().nextInt(900000)+100000;
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
            socketIOClient.getNamespace().getBroadcastOperations().sendEvent("get_messages",message.getContext());
        };
    }

}
