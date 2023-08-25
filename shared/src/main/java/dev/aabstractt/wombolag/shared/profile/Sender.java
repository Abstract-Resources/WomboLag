package dev.aabstractt.wombolag.shared.profile;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

public interface Sender {

    @NonNull String getName();

    @NonNull String getId();

    void sendMessage(@NonNull String message);

    void sendTitle(@NonNull String title, @Nullable String subtitle);

    void kick(@NonNull String message);
}