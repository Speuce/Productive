package com.productive6.productive.logic.adapters;

import com.productive6.productive.objects.Cosmetic;

import java.util.ArrayList;

public interface ICosmeticAdapter {

    /**
     *
     * @param id the id of a cosmetic
     * @return that cosmetic as a Cosmetic object
     */
    Cosmetic idToCosmetic(int id);

    /**
     * @return ArrayList of all cosmetics that the app has
     */
    ArrayList<Cosmetic> getAllCosmeticOptions();

}
