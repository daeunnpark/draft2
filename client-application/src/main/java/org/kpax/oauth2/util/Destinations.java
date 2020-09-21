package org.kpax.oauth2.util;

public class Destinations {

    public static class ChatRoom {

        public static String MessagesInRoom(Long chatId) {
            return "/sub/chat/rooms/" + chatId;
        }

        public static String MessagesInList(Long userId) {
            return "/sub/chat/list/" + userId;
        }
        /*
        public static String privateMessages(Long chatId) {
            return "/queue/" + chatId + ".private.messages";
        }

        public static String connectedUsers(Long chatId) {
            return "/topic/" + chatId + ".connected.users";
        }
         */
    }
}
