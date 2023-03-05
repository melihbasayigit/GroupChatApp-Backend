package com.melomanya.groupchatapp.utils;

import com.corundumstudio.socketio.SocketIOClient;
import com.melomanya.groupchatapp.data.User;

import java.util.Random;

public class UserUtils {

    public static String generateRandomUserName() {
        int rand = new Random().nextInt(899999) + 100000;
        return "User" + rand;
    }
}