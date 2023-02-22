package com.melomanya.groupchatapp.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.melomanya.groupchatapp.data.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class SocketService {

    private SocketIOServer socketIOServer;
    //logger için;
    private Logger logger=  LoggerFactory.getLogger(getClass());

    public SocketService(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        //Gelen mesaj vs gibi objeler
        socketIOServer.addEventListener("send_message", Message.class,messageIsSent());

    }

    private ConnectListener onConnected() {
        return connectMessage->{
            logger.debug("SocketId: "+connectMessage.getSessionId().toString()+"connected");
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
            logger.info("" + socketIOClient.getSessionId() + "" + message.getMessage());
            socketIOClient.getNamespace().getBroadcastOperations().sendEvent("getMessage",message.getMessage());
        };
    }

}
