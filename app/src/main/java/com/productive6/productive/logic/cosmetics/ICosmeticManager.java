package com.productive6.productive.logic.cosmetics;

import com.productive6.productive.objects.Cosmetic;

import java.util.ArrayList;

public interface ICosmeticManager {

    /**
     * @return ArrayList of all cosmetics the user owns
     */
    ArrayList<Cosmetic> getOwnedItems();

    /**
     * @return ArrayList of all the cosmetics the user could buy (does not own)
     */
    ArrayList<Cosmetic> getPurchasable();

    /**
     * getter for the user's favorited cosmetic
     */
    Cosmetic getFavorite();

    /**
     * setter for the user's favorited cosmetic
     */
    void setFavorite(Cosmetic item);

    /**
     * @param item the cosmetic the user has purchased
     */
    void purchaseCosmetic(Cosmetic item);

    /**
     * @return true if the manager has been initialized
     */
    boolean isInitialized();

}
