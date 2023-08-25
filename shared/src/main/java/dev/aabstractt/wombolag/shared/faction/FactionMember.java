package dev.aabstractt.wombolag.shared.faction;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record FactionMember(@NonNull String xuid, @NonNull String name) {}