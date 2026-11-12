package de.wurow.jobtracker;

public record TelegramConfig(
        String telegramToken,
        String telegramChatId
) {
}
