package pl.sdacademy.chat.Server;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
        try {
            ServerSocketDispatcher serverSocketDispatcher = new ServerSocketDispatcher(5567);
            serverSocketDispatcher.dispatch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
