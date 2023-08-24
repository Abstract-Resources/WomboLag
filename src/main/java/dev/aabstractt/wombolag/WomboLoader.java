package dev.aabstractt.wombolag;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NonNull;

public class WomboLoader {

    public final static @NonNull Gson GSON = new GsonBuilder()
            .serializeNulls()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .create();


}