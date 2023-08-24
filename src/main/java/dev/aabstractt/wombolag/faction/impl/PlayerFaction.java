package dev.aabstractt.wombolag.faction.impl;

import dev.aabstractt.wombolag.faction.Faction;
import lombok.NonNull;

public final class PlayerFaction extends Faction {

    public PlayerFaction(@NonNull String id, @NonNull String name) {
        super(id, name);
    }
}