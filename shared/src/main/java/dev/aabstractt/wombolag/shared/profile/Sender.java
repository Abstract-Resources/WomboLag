package dev.aabstractt.wombolag.shared.profile;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

public interface Sender {

    @NonNull String CONSOLE_NAME = "CONSOLE";

    @NonNull String getName();

    @NonNull String getId();

    boolean hasPermission(@NonNull String permission);

    void sendMessage(@NonNull String message);

    void sendTitle(@NonNull String title, @Nullable String subtitle);

    void kick(@NonNull String message);
}