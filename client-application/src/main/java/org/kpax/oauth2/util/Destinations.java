package org.kpax.oauth2.util;

public class Destinations {

    public static class ChatRoom {

        public static String publicMessages(Long chatId) {
            return "/topic/" + chatId+ ".public.messages";
        }

        public static String privateMessages(Long chatId) {
            return "/queue/" + chatId + ".private.messages";
        }

        public static String connectedUsers(Long chatId) {
            return "/topic/" + chatId + ".connected.users";
        }
    }
}
