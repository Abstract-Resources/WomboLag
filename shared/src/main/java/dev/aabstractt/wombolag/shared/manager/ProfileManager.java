package dev.aabstractt.wombolag.shared.manager;

import dev.aabstractt.wombolag.shared.profile.Profile;
import dev.aabstractt.wombolag.shared.profile.Sender;
import dev.aabstractt.wombolag.shared.repository.MongoRepository;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public final class ProfileManager {

    @Getter private final static @NonNull ProfileManager instance = new ProfileManager();

    private @Nullable MongoRepository<Profile> mongoRepository = null;

    @Getter private @Nullable Function<@NonNull String, @Nullable Sender> wrapperSender;

    private final @NonNull Map<@NonNull String, @NonNull Profile> profilesStored = new ConcurrentHashMap<>();

    public void init(@NonNull String uri, @NonNull Function<@NonNull String, @Nullable Sender> wrapperSender) {
        this.mongoRepository = new MongoRepository<>();
        this.mongoRepository.init(uri);

        this.wrapperSender = wrapperSender;
    }

    public void load(@NonNull String xuid, @NonNull String name) {
        boolean pendingUpdate = false;

        if (this.mongoRepository == null) {
            throw new IllegalStateException("MongoRepository is null!");
        }

        Profile profile = this.mongoRepository.findOne(xuid);
        if (profile == null) {
            profile = new Profile(xuid);
        }

        if (profile.isOutdated(name)) {
            profile.setLastName(profile.getName());
            profile.setName(name);

            pendingUpdate = true;
        }

        if (pendingUpdate) {
            this.mongoRepository.insertOne(profile);
        }
    }

    public void save(@NonNull Profile profile) {
        if (this.mongoRepository == null) {
            throw new IllegalStateException("MongoRepository is null!");
        }

        this.mongoRepository.insertOne(profile);
    }

    public @Nullable Profile byXuid(@NonNull String xuid) {
        if (this.mongoRepository == null) {
            throw new IllegalStateException("MongoRepository is null!");
        }

        return this.profilesStored.get(xuid);
    }

    public @Nullable Sender wrapSender(@NonNull String playerName) {
        if (this.wrapperSender == null) {
            throw new IllegalStateException("Wrapper sender is null!");
        }

        return this.wrapperSender.apply(playerName);
    }
}