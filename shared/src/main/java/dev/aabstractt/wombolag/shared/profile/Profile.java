package dev.aabstractt.wombolag.shared.profile;

import dev.aabstractt.wombolag.shared.manager.ProfileManager;
import dev.aabstractt.wombolag.shared.repository.codec.Storable;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

@RequiredArgsConstructor @Data
public final class Profile implements Storable {

    private final @NonNull String id;

    private @Nullable String name;
    private @Nullable String lastName;

    public boolean isOutdated(@NonNull String currentName) {
        return !Objects.equals(this.name, currentName);
    }

    public @Nullable Sender toSender() {
        if (this.name == null) {
            return null;
        }

        Function<String, Sender> wrapperSender = ProfileManager.getInstance().getWrapperSender();
        if (wrapperSender == null) {
            return null;
        }

        return wrapperSender.apply(this.name);
    }
}