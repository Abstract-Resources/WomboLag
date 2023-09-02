package dev.aabstractt.wombolag.shared.manager;

import dev.aabstractt.wombolag.shared.faction.Faction;
import dev.aabstractt.wombolag.shared.faction.FactionMember;
import dev.aabstractt.wombolag.shared.faction.impl.PlayerFaction;
import dev.aabstractt.wombolag.shared.profile.Sender;
import dev.aabstractt.wombolag.shared.repository.MongoRepository;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class FactionManager {

    @Getter
    private final static @NonNull FactionManager instance = new FactionManager();

    private @Nullable MongoRepository<Faction> mongoRepository = null;

    private final @NonNull Map<UUID, Faction> factionUUIDMap = new ConcurrentHashMap<>();
    private final @NonNull Map<String, UUID> factionNameMap = new ConcurrentHashMap<>();

    private final @NonNull Map<@NonNull String, UUID> playerFactionMap = new ConcurrentHashMap<>();
    private final @NonNull Map<@NonNull String, UUID> playerNameFactionMap = new ConcurrentHashMap<>();

    public void init(@NonNull String uri) {
        this.mongoRepository = new MongoRepository<>();
        this.mongoRepository.init(uri, "factions");

        //this.mongoRepository.findAll().forEach(this::cacheFaction);
    }

    public @NonNull Faction createFaction(@NonNull String factionName, boolean admin) {
        if (admin) {
            throw new UnsupportedOperationException("Admin factions are not supported yet!");
        }

        if (this.mongoRepository == null) {
            throw new IllegalStateException("MongoRepository cannot be null!");
        }

        Faction faction = new PlayerFaction(this.buildAvailableId().toString(), factionName);

        this.cacheFaction(faction);

        this.mongoRepository.insertOne(faction);

        return faction;
    }

    private void cacheFaction(@NonNull Faction faction) {
        this.factionUUIDMap.put(faction.getConvertedId(), faction);
        this.factionNameMap.put(faction.getName().toLowerCase(), faction.getConvertedId());

        faction.getMembers().forEach(member -> this.cacheMember(member, faction.getConvertedId()));
    }

    public @Nullable Faction getFactionByName(@NonNull String factionName) {
        UUID factionId = this.factionNameMap.get(factionName.toLowerCase());
        return factionId != null ? this.factionUUIDMap.get(factionId) : null;
    }

    public @Nullable Faction getPlayerFaction(@NonNull Sender sender) {
        UUID factionId = this.playerFactionMap.get(sender.getId());
        return factionId != null ? this.factionUUIDMap.get(factionId) : null;
    }

    public @Nullable Faction getPlayerFaction(@NonNull String name) {
        UUID factionId = this.playerNameFactionMap.get(name.toLowerCase());
        return factionId != null ? this.factionUUIDMap.get(factionId) : null;
    }

    public void cacheMember(@NonNull FactionMember factionMember, @NonNull UUID factionId) {
        this.playerFactionMap.put(factionMember.xuid(), factionId);
        this.playerNameFactionMap.put(factionMember.name().toLowerCase(), factionId);
    }

    private @NonNull UUID buildAvailableId() {
        UUID uuid = UUID.randomUUID();
        while (this.factionUUIDMap.containsKey(uuid)) {
            uuid = UUID.randomUUID();
        }

        return uuid;
    }
}