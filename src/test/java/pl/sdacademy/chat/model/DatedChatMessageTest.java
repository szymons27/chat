package pl.sdacademy.chat.model;


import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatedChatMessageTest {

    @Test
    public void shouldHaveDateSameAsChatMessage() {
       // Given
        ChatMessage chatMessage = new ChatMessage(
                "DuckAnihilator69",
                "what's up DUCK?");
        DatedChatMessage message = new DatedChatMessage(chatMessage);
        // When

       // Then
       assertNotNull(message.getReceiveDate());
       assertEquals(message.getAuthor(),"DuckAnihilator69");
       assertEquals(message.getMessage(),"what's up DUCK?");
    }

}