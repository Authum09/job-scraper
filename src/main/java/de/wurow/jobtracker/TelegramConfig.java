package de.wurow.jobtracker;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="telegram")
public record TelegramConfig(
        String token,
        String chatId
) {
}
