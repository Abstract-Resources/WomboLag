package dev.aabstractt.wombolag.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.lang.Nullable;
import dev.aabstractt.wombolag.shared.command.BaseCommand;
import dev.aabstractt.wombolag.shared.command.faction.CreateArgument;
import lombok.Getter;
import lombok.NonNull;

import java.util.function.Function;

public class AbstractLoader {

    public final static @NonNull Gson GSON = new GsonBuilder()
            .serializeNulls()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    @Getter private final static @NonNull AbstractLoader instance = new AbstractLoader();

    @Getter private final @Nullable Function<String, String> messageWrapper = null;

    public @NonNull BaseCommand buildFactionsCommand() {
        return new BaseCommand()
                .addArgument(new CreateArgument(
                        "create",
                        "/<label> create <name>",
                        null
                ));
    }
}