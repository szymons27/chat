package pl.sdacademy.chat.Client;

import com.sun.org.apache.xml.internal.security.algorithms.implementations.SignatureDSA;

import java.io.IOException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ClientTerminalMain {
    public static void main(String[] args) throws InterruptedException {
        int reconnectionArrempts = 3;
        int secondBetweenReconnectAttempt = 30;

        System.out.println("welcome to awesome chat!");
        while (reconnectionArrempts > 0) {
            try {
                System.out.println("attempting to connect to server");
                ClientTerminal clientTerminal = new ClientTerminal();
                Thread thread = new Thread(clientTerminal);
                thread.setName("Terminal");
                thread.start();
                thread.join();

            } catch (IOException e) {
                System.out.println("could not connect to server: " + e.getMessage());
                reconnectionArrempts--;
                countdown(secondBetweenReconnectAttempt);
            }
        }
        System.out.println("see you soon!");
    }

    private static void countdown(int second) throws InterruptedException{
        while (second > 0){
            System.out.println("waiting " + second + " seconds till reconnect attempt");
            Thread.sleep(1000);
            second--;
        }
    }
}
