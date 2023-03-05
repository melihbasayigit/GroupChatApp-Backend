package com.melomanya.groupchatapp.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.melomanya.groupchatapp.model.Message;
import com.melomanya.groupchatapp.model.Room;
import com.melomanya.groupchatapp.model.User;
import com.melomanya.groupchatapp.util.response.ValidatorResponse;
import com.melomanya.groupchatapp.util.validator.EmptyStringValidator;
import com.melomanya.groupchatapp.util.validator.NullValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.melomanya.groupchatapp.util.UserUtils.generateRandomUserName;

@Component
public class SocketService {

    private final UserService userService;

    private final SocketIOServer socketIOServer;

    private final MessageService messageService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public SocketService(SocketIOServer socketIOServer, MessageService messageService, UserService userService) {
        this.socketIOServer = socketIOServer;
        this.messageService = messageService;
        this.userService = userService;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener("send_message", Message.class, onReceived());
    }

    private ConnectListener onConnected() {
        return socketIOClient -> {
            logger.debug("SocketId: " + socketIOClient.getSessionId().toString() + " connected.");
            User user = createUser(socketIOClient);
            userService.registerUser(user);
            String roomId = socketIOClient.getHandshakeData().getSingleUrlParam("room");
            socketIOClient.joinRoom(roomId);
            Room messageRoom = createMessageRoom(socketIOClient);
            sendMessages(roomId, "get_message", socketIOClient, messageRoom);
        };
    }

    private DisconnectListener onDisconnected() {
        return socketIOClient -> {
            logger.debug("SocketId: " + socketIOClient.getSessionId().toString() + " disconnected.");
            Room messageRoom = createMessageRoom(socketIOClient);
            sendMessages(messageRoom.getId(), "get_message", socketIOClient, messageRoom);
        };
    }

    //Mesaj gitti mi gitmedi mi mesaj bilgilerinin falan göndermek için DataListener kullanılıypor
    //Mesajın gideceği yerleri buradan belirliyoruz.ackRequest mesjain gidip gitmediği
    private DataListener<Message> onReceived() {
        return (socketIOClient, message, ackRequest) -> {
            String newMessage = message.getMessage();
            ValidatorResponse validator = new ValidatorResponse(
                    new NullValidator(newMessage), new EmptyStringValidator(newMessage)
            );
            ackRequest.sendAckData(validator.getResponse());
            if (!validator.isSuccess()) {
                return;
            }
            logger.debug("SocketId: " + socketIOClient.getSessionId() + " Message: " + message.getMessage());
            String roomId = socketIOClient.getHandshakeData().getSingleUrlParam("room");
            message.setRoom(roomId);
            message.setSenderId(socketIOClient.getSessionId().toString());
            message.setSenderName(getSenderName(socketIOClient.getSessionId().toString()));
            messageService.saveMessage(message);
            Room messageRoom = createMessageRoom(socketIOClient);
            sendMessages(roomId, "get_message", socketIOClient, messageRoom);
        };
    }

    /**
     * Yeni bir User değişkeni oluşturur. Buna socket id verir ve kullanıcı ismi verilmişse kullanıcı ismini verir
     * verilmemişse 6 haneli rakam verir.
     **/
    public static User createUser(SocketIOClient socketIOClient) {
        User user = new User();
        if (socketIOClient.getHandshakeData().getSingleHeader("name") != null) {
            user.setDisplayName(socketIOClient.getHandshakeData().getSingleHeader("name"));
        } else {
            user.setDisplayName(generateRandomUserName());
        }
        user.setSocketId(socketIOClient.getSessionId().toString());
        return user;
    }

    private Room createMessageRoom(SocketIOClient client) {
        Room messageRoom = new Room();
        String roomId = client.getHandshakeData().getSingleUrlParam("room");
        messageRoom.setId(roomId);
        messageRoom.setUserCount(getRoomUserCount(client, roomId));
        messageRoom.setMessages(getMessageList(roomId));
        return messageRoom;
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

    private List<Message> getMessageList(String roomId) {
        return messageService.getMessages(roomId);
    }
}