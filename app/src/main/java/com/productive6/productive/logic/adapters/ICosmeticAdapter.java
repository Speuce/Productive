package com.productive6.productive.logic.adapters;

import com.productive6.productive.objects.Cosmetic;

import java.util.ArrayList;

public interface ICosmeticAdapter {

    Cosmetic idToCosmetic(int id);

    ArrayList<Cosmetic> getAllCosmeticOptions();

}
