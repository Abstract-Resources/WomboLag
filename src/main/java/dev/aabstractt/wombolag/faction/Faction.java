package dev.aabstractt.wombolag.faction;

import dev.aabstractt.wombolag.repository.codec.Storable;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor @Data
public abstract class Faction implements Storable {

    private final @NonNull String id;

    private @NonNull String name;

    private final @NonNull Set<@NonNull FactionMember> members = new HashSet<>();

    public @NonNull UUID getConvertedId() {
        return UUID.fromString(this.id);
    }

    public void addMember(@NonNull FactionMember member) {
        this.members.add(member);
    }

    public boolean isMember(@NonNull String name) {
        return this.members.stream().anyMatch(member -> member.name().equalsIgnoreCase(name));
    }
}