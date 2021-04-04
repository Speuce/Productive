package com.productive6.productive.persistence.room.adapters;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.productive6.productive.logic.adapters.ICosmeticAdapter;
import com.productive6.productive.objects.Cosmetic;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts Cosmetic objects to id for database storage, and vice-versa -- using the
 * ICosmeticAdapter logic
 */
@ProvidedTypeConverter
public class CosmeticConverter {

    /**
     * The cosmetic adapter to use to convert id -> objects
     */
    private final ICosmeticAdapter adapter;

    private final Gson gson = new Gson();

    public CosmeticConverter(ICosmeticAdapter adapter) {
        this.adapter = adapter;
    }


    @TypeConverter
    public int cosmeticToInt(Cosmetic c){
        if(c == null){
            return 0;
        }
        return c.getId();
    }

    @TypeConverter
    public Cosmetic intToCosmetic(int i){
        if(i == 0){
            return null;
        }
        return adapter.idToCosmetic(i);
    }

    @TypeConverter
    public String cosmeticListToString(List<Cosmetic> c){
        return gson.toJson(c.stream().mapToInt(this::cosmeticToInt).boxed().collect(Collectors.toList()));

    }

    @TypeConverter
    public List<Cosmetic> stringToCosmeticList(String jsonList){
        if (jsonList == null) {
            return Collections.emptyList();
        }
        return stringToIntList(jsonList).stream().map(this::intToCosmetic).collect(Collectors.toList());
    }

    /**
     * Helper method. First converts a string to a list of integers, to then be used later.
     */
    private List<Integer> stringToIntList(String jsonList){
        Type listType = new TypeToken<List<Integer>>() {}.getType();

        return gson.fromJson(jsonList, listType);
    }


}
