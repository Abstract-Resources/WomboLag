package dev.aabstractt.wombolag.shared.command;

import dev.aabstractt.wombolag.shared.profile.Sender;
import lombok.NonNull;

import java.util.Set;

public final class BaseCommand {

    private final @NonNull Set<@NonNull Argument> arguments = new java.util.HashSet<>();

    public @NonNull BaseCommand addArgument(@NonNull Argument argument) {
        this.arguments.add(argument);

        return this;
    }

    public void execute(@NonNull Sender commandSender, @NonNull String commandLabel, @NonNull String[] args) {
        commandSender.sendMessage("&cThis command is not implemented yet.");
    }
}