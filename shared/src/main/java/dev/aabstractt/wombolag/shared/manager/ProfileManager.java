package dev.aabstractt.wombolag.shared.manager;

import dev.aabstractt.wombolag.shared.profile.Profile;
import dev.aabstractt.wombolag.shared.profile.Sender;
import dev.aabstractt.wombolag.shared.repository.MongoRepository;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public final class ProfileManager {

    @Getter private final static @NonNull ProfileManager instance = new ProfileManager();

    private @Nullable MongoRepository<Profile> mongoRepository = null;

    @Getter private @Nullable Function<@NonNull String, @Nullable Sender> wrapperSender;

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
}