package pl.sdacademy.chat.Client;

import pl.sdacademy.chat.model.ChatMessage;
import pl.sdacademy.chat.model.DatedChatMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTerminal implements Runnable {
    private final Socket connectionToServer;

    public ClientTerminal() throws IOException {
        connectionToServer = new Socket("IP", 5567);
    }

    @Override
    public void run() {
        try (ObjectOutputStream streamToServer = new ObjectOutputStream(connectionToServer.getOutputStream())) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Your username: ");
            scanner.nextLine();
            String userName = scanner.nextLine();
            String message;
            do {
                System.out.print("> ");
                message = scanner.nextLine();
                ChatMessage messageToSend = new ChatMessage(userName, message);
                streamToServer.writeObject(messageToSend);
                streamToServer.flush();

            } while (!message.equalsIgnoreCase("exit"));

        }catch (IOException e) {
            System.out.println("server closed connection.");
            e.printStackTrace();
        }
        System.out.println("disconnecting");
        //wytworzenie strumieni musi w try with resources -> try()
        //pobierz nick od usera
        //petla az user wpisze exit
        //pobierac tekst do wyslania od uzytkownika
        //torzymy nowy obiekt chatmessage
        //wysylamy go do serwera -> wpisujemy do OOS
        //!!! komunikat exit tez wysylamy na serwer
    }
}
