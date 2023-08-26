package dev.aabstractt.wombolag.shared.command;

import dev.aabstractt.wombolag.shared.profile.Sender;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor @Data
public abstract class Argument {

    private final @NonNull String name;
    private final @NonNull String usage;
    private final @Nullable String permission;

    public abstract void execute(@NonNull Sender commandSender, @NonNull String commandLabel, @NonNull String[] args);
}