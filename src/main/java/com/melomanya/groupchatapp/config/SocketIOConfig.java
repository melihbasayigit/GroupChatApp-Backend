package com.melomanya.groupchatapp.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class SocketIOConfig {
    @Value("${socket.host}")
    private String SOCKETHOST;
    @Value("${socket.port}")
    private int SOCKETPORT;
    private SocketIOServer server;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(SOCKETHOST);
        config.setPort(SOCKETPORT);
        server = new SocketIOServer(config);
        return server;
    }
}