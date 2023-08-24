package dev.aabstractt.wombolag.manager;

import dev.aabstractt.wombolag.profile.Profile;
import dev.aabstractt.wombolag.repository.MongoRepository;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

public final class ProfileManager {

    @Getter private final static @NonNull ProfileManager instance = new ProfileManager();

    private @Nullable MongoRepository<Profile> mongoRepository = null;

    public void init(@NonNull String uri) {
        this.mongoRepository = new MongoRepository<>();
        this.mongoRepository.init(uri);
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