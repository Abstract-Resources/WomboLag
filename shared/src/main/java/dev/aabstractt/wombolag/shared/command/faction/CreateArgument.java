package dev.aabstractt.wombolag.shared.command.faction;

import dev.aabstractt.wombolag.shared.Messages;
import dev.aabstractt.wombolag.shared.command.Argument;
import dev.aabstractt.wombolag.shared.faction.Faction;
import dev.aabstractt.wombolag.shared.faction.FactionMember;
import dev.aabstractt.wombolag.shared.manager.FactionManager;
import dev.aabstractt.wombolag.shared.manager.ProfileManager;
import dev.aabstractt.wombolag.shared.profile.Profile;
import dev.aabstractt.wombolag.shared.profile.Sender;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

public final class CreateArgument extends Argument {

    public CreateArgument(@NonNull String name, @NonNull String usage, @Nullable String permission) {
        super(name, usage, permission);
    }

    @Override
    public void execute(@NonNull Sender commandSender, @NonNull String commandLabel, @NonNull String[] args) {
        Profile profile = ProfileManager.getInstance().byXuid(commandSender.getId());
        if (profile == null) {
            commandSender.sendMessage(Messages.AN_ERROR_OCCURRED.build());

            return;
        }

        if (FactionManager.getInstance().getPlayerFaction(commandSender) != null) {
            commandSender.sendMessage(Messages.YOU_ALREADY_IN_FACTION.build());

            return;
        }

        if (FactionManager.getInstance().getFactionByName(args[0]) != null) {
            commandSender.sendMessage(Messages.FACTION_ALREADY_EXISTS.build(args[0]));

            return;
        }

        Faction faction = FactionManager.getInstance().createFaction(args[0], false);
        faction.addMember(FactionMember.builder()
                .xuid(commandSender.getId())
                .name(commandSender.getName())
                .build()
        );

        profile.setFactionId(faction.getConvertedId());
        ProfileManager.getInstance().save(profile);
    }
}