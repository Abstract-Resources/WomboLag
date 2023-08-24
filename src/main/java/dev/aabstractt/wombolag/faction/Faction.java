package dev.aabstractt.wombolag.faction;

import dev.aabstractt.wombolag.repository.codec.Storable;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor @Data
public abstract class Faction implements Storable {

    private final @NonNull String id;
    private @Nullable UUID convertedId = null;

    private @NonNull String name;

    private final @NonNull Set<@NonNull FactionMember> members = new HashSet<>();

    public @NonNull UUID getConvertedId() {
        if (this.convertedId == null) {
            this.convertedId = UUID.fromString(this.id);
        }

        return this.convertedId;
    }

    public void addMember(@NonNull FactionMember member) {
        this.members.add(member);
    }

    public boolean isMember(@NonNull String name) {
        return this.members.stream().anyMatch(member -> member.name().equalsIgnoreCase(name));
    }
}