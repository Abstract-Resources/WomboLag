package dev.aabstractt.wombolag.shared;

import lombok.NonNull;

import java.util.function.Function;

public enum Messages {

    YOU_ALREADY_IN_FACTION(
            "player.already_in_faction"
    ),
    FACTION_ALREADY_EXISTS(
            "faction.already_exists"
    ),
    AN_ERROR_OCCURRED(
            "general.an_error_occurred"
    );

    private final @NonNull String messageKey;
    private final @NonNull String[] arguments;

    Messages(@NonNull String messageKey, @NonNull String... arguments) {
        this.messageKey = messageKey;
        this.arguments = arguments;
    }

    public @NonNull String build(@NonNull String... args) {
        Function<String, String> messageWrapper = AbstractLoader.getInstance().getMessageWrapper();
        if (messageWrapper == null) {
            return "Translation not found: " + this.messageKey;
        }

        return messageWrapper.apply(this.messageKey);
    }
}