package dev.aabstractt.wombolag.profile;

import dev.aabstractt.wombolag.repository.codec.Storable;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Objects;

@RequiredArgsConstructor @Data
public final class Profile implements Storable {

    private final @NonNull String id;

    private @Nullable String name;
    private @Nullable String lastName;

    public boolean isOutdated(@NonNull String currentName) {
        return !Objects.equals(this.name, currentName);
    }
}