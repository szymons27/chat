package pl.sdacademy.chat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDispatcher {

    private final ServerSocket server;
    private final ChatLog chatLog;

    public ServerSocketDispatcher(int portNumber) throws IOException {
        server = new ServerSocket(portNumber);
        chatLog = new ChatLog();
    }

    public void dispatch(){
        try {
            ClientLoginHandler();
        }catch (IOException e){
            System.out.println("fak ju in de stomak");
            e.printStackTrace();
        }
    }

    private void ClientLoginHandler() throws IOException {
        while(true) {
            Socket client = server.accept();
            Runnable clientTask = new ServerSocketReaderRunnable(client, chatLog);
            new Thread(clientTask).start();
        }
    }
}
