package org.example;

public record TelegramConfig(
        String telegramToken,
        String telegramChatId
) {
}
