package com.productive6.productive.logic.cosmetics;

import com.productive6.productive.objects.Cosmetic;

import java.util.ArrayList;

public interface ICosmeticManager {

    ArrayList<Cosmetic> getOwnedItems();

    ArrayList<Cosmetic> getPurchasable();

    Cosmetic getFavorite();

    void setFavorite(Cosmetic item);

    void purchaseCosmetic(Cosmetic item);

    boolean isInitialized();

}
