package dev.aabstractt.wombolag.profile;

import lombok.NonNull;

import javax.annotation.Nullable;

public interface Sender {

    @NonNull String getName();

    @NonNull String getId();

    void sendMessage(@NonNull String message);

    void sendTitle(@NonNull String title, @Nullable String subtitle);

    void kick(@NonNull String message);
}