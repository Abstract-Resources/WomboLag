package dev.aabstractt.wombolag.shared;

import lombok.NonNull;

public enum Messages {

    YOU_ALREADY_IN_FACTION(),
    FACTION_ALREADY_EXISTS(),
    AN_ERROR_OCCURRED;

    public @NonNull String build(@NonNull String... args) {
        return String.format(this.message, args);
    }
}