package pl.sdacademy.chat.Server;

import pl.sdacademy.chat.model.ChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerSocketReaderRunnable implements Runnable{
    private final Socket client;
    private final ChatLog chatLog;

    public ServerSocketReaderRunnable(Socket clientConnection, ChatLog chatLog, Socket client){

        this.client = client;
        this.chatLog = chatLog;
    }

    @Override
    public void run() {
        boolean success = chatLog.register(client);

        if (success) {
            try (ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream())) {
                ProcessClientInput(inputStream);

            } catch (IOException e) {
                System.out.println("### Client disconnected due to network problem ###");
            } catch (ClassNotFoundException e){
                System.out.println("### Client disconnected due to invalid message format");
            }
            chatLog.unregister(client);
            //zarejestruj klienta w czatlogu
            //jezeli sie udalo to pobierz ObjectInputStream dla tego klienta (try-with-resources)
            //w pętli odczytaj komunikaty od klienta, tak dlugo, az nie pojawi sie napis exit
            //komunikat przekaz do czatlogu, ale nie przekazuj komunikatu exit
            //jezeli pojawil sie exit, lub nie udalo sie odczytac komunikatu od klienta to
            // wyrejestruj sie z chatlogu i zakończ to zadanie
        }
    }

    private void ProcessClientInput(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        ChatMessage message;
        do {
            message = (ChatMessage) inputStream.readObject();
            if (message.getMessage() == null || message.getMessage().equalsIgnoreCase("exit")){
                break;
            }
            chatLog.acceptMessage(message);
        } while (message.getMessage().equalsIgnoreCase("exit"));
    }
}
