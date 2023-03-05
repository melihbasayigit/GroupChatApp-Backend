package com.melomanya.groupchatapp.util;

import java.util.Random;

public class UserUtils {

    public static String generateRandomUserName() {
        int rand = new Random().nextInt(899999) + 100000;
        return "User" + rand;
    }
}