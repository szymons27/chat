package pl.sdacademy.chat.Client;

import java.io.IOException;

public class ClientTerminalMain {
    public static void main(String[] args) {
        try {
            ClientTerminal clientTerminal = new ClientTerminal();
            Thread thread = new Thread(clientTerminal);
            thread.setName("Terminal");
            thread.start();

            thread.join();

        } catch (IOException e) {
            System.out.println("could not connect to server: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("application was forcibly closed");
            e.printStackTrace();
        }
    }
}
