package dev.aabstractt.wombolag.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NonNull;

public class AbstractLoader {

    public final static @NonNull Gson GSON = new GsonBuilder()
            .serializeNulls()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .create();


}