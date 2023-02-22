package com.melomanya.groupchatapp.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//Configuration classını ayağa kaldırmak için
@Component
public class StartConfiguration implements CommandLineRunner {


    private final SocketIOServer socketIOServer;
    //Autowired yerine constructor injection
    public StartConfiguration(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void run(String... args) throws Exception {
        //Başlatmak için
        socketIOServer.start();

    }
}
