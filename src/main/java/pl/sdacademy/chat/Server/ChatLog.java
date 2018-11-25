package pl.sdacademy.chat.Server;

import pl.sdacademy.chat.model.ChatMessage;
import pl.sdacademy.chat.model.DatedChatMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatLog implements Observer {
    private Map<Socket, ObjectOutputStream> registeredClients;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ChatLog() {
        registeredClients = new ConcurrentHashMap<>();
    }

    //kolekcja z użytkownikami
    public boolean register(Socket client) {
        try {
            ObjectOutputStream streamToClient =
                    new ObjectOutputStream(client.getOutputStream());
            registeredClients.put(client, streamToClient);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //zapisz klienta w kolekcji klientów
    }

    public boolean unregister(Socket client) {
        ObjectOutputStream connectionToClient = registeredClients.remove(client);
        if (connectionToClient != null) {
            try {
                connectionToClient.close();
                return true;
            } catch (IOException e) {
                //nothing to dooooOoooooooooooo
            }
            //usuń klienta z kolekcji
        }
        return false;
    }

    public void acceptMessage(ChatMessage message) {
        DatedChatMessage datedMessage = new DatedChatMessage(message);
        System.out.println(dateFormatter.format(datedMessage.getReceiveDate())
                + datedMessage.getAuthor()
                + ":"
                + datedMessage.getMessage()
        );

        //przekonwertować ChatMessage na DatedChatMessage
        //wypisz na ekran wiadomość w formacie:
        //<Data><Author>: <Message>
        //wyslij DatedChatMessage do wszystkich klientów
        //jesli sie nie udalo, to wyrejestruj tego klienta
    }


    private void updateClients(DatedChatMessage datedMessage) {
        Set<Map.Entry<Socket, ObjectOutputStream>> allEntries =
                registeredClients.entrySet();
        for(Map.Entry<Socket, ObjectOutputStream> entry : allEntries){
            ObjectOutputStream connectionToClient = entry.getValue();
            try {
                connectionToClient.writeObject(datedMessage);
                connectionToClient.flush();
            } catch (IOException e) {
                unregister(entry.getKey());
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
