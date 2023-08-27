package dev.aabstractt.wombolag.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.aabstractt.wombolag.shared.command.BaseCommand;
import dev.aabstractt.wombolag.shared.command.faction.CreateArgument;
import lombok.Getter;
import lombok.NonNull;

public class AbstractLoader {

    public final static @NonNull Gson GSON = new GsonBuilder()
            .serializeNulls()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Getter private final static @NonNull AbstractLoader instance = new AbstractLoader();

    public @NonNull BaseCommand buildFactionsCommand() {
        return new BaseCommand()
                .addArgument(new CreateArgument(
                        "create",
                        "/<label> create <name>",
                        null
                ));
    }
}