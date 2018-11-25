package pl.sdacademy.chat.model;

import java.time.LocalDateTime;

public class DatedChatMessage extends ChatMessage{
    private final LocalDateTime recieveDate;

    public LocalDateTime getRecieveDate() {
        return recieveDate;
    }

    public DatedChatMessage(ChatMessage chatMessage) {
        super(chatMessage.getAuthor(),chatMessage.getMessage());

        this.recieveDate = LocalDateTime.now();


    }
}
