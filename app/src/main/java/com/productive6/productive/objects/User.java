package com.productive6.productive.objects;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.productive6.productive.persistence.room.adapters.CosmeticConverter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Encapsulates information of a user including XP, Coins, and applicable level
 */
@Entity(tableName = "users")
@TypeConverters({CosmeticConverter.class})
public class User {

    /**
     * id -- for persistence purposes.
     */
    @PrimaryKey(autoGenerate = true)
    private long id;

    /**
     * The amount of experience
     */
    private int exp;

    /**
     * The current level of the user
     */
    private int level;

    /**
     * The amount of coins that the user has
     */
    private int coins;

    /**
     * The currently-selected title for the user
     */
    private String selectedTitle;


    /**
     * The user's chosen 'favourite' cosmetic
     */
    private Cosmetic favouriteCosmetic;

    /**
     * List of all cosmetics owned by the user
     */
    private List<Cosmetic> ownedCosmetics = new LinkedList<>();

    /**
     * The nothing constructor
     */
    @Ignore
    public User() {
        this.exp = 0;
        this.level = 0;
        this.coins = 0;
        selectedTitle = "n00b tasker";
    }

    /**
     * Construct a new user object
     * @param exp The amount of experience
     * @param level The current level of the user
     * @param coins The amount of coins that the user has
     */
    public User(int exp, int level, int coins) {
        this.exp = exp;
        this.level = level;
        this.coins = coins;
        selectedTitle = "n00b tasker";
    }

    /**
     * Construct a new user object
     * @param exp The amount of experience
     * @param level The current level of the user
     * @param coins The amount of coins that the user has
     * @param selectedTitle The current title of the user
     */
    @Ignore
    public User(int exp, int level, int coins, String selectedTitle) {
        this.exp = exp;
        this.level = level;
        this.coins = coins;
        this.selectedTitle = selectedTitle;
    }

    /**
     * @return The amount of experience
     */
    public int getExp() {
        return exp;
    }

    /**
     * @param exp The amount of experience
     */
    public void setExp(int exp) {
        this.exp = exp;
    }

    /**
     * @return The current level of the user
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level The current level of the user
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return The amount of coins that the user has
     */
    public int getCoins() {
        return coins;
    }

    /**
     * @param coins The amount of coins that the user has
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }

    /**
     * @return The currently-selected title for the user
     */
    public String getSelectedTitle() {
        return selectedTitle;
    }

    /**
     * @param selectedTitle The currently-selected title for the user
     */
    public void setSelectedTitle(String selectedTitle) {
        if(selectedTitle == null){
            this.selectedTitle = "";
        }else{
            this.selectedTitle = selectedTitle;
        }

    }

    public Cosmetic getFavouriteCosmetic() {
        return favouriteCosmetic;
    }

    public void setFavouriteCosmetic(Cosmetic favouriteCosmetic) {
        this.favouriteCosmetic = favouriteCosmetic;
    }

    /**
     * @return a list of all cosmetics owned by the user
     */
    public List<Cosmetic> getOwnedCosmetics() {
        return ownedCosmetics;
    }

    /**
     * Adds the given cosmetic to the user's cosmetic collection.
     */
    public void addToOwnedCosmetics(Cosmetic c){
        if(ownedCosmetics == null){
            ownedCosmetics = new LinkedList<>();
        }
        this.ownedCosmetics.add(c);
    }

    /**
     * @return id -- for persistence purposes.
     */
    public long getId() {
        return id;
    }

    /**
     * @param id -- for persistence purposes.
     */
    public void setId(long id) {
        this.id = id;
    }

    public void setOwnedCosmetics(List<Cosmetic> ownedCosmetics) {
        this.ownedCosmetics = ownedCosmetics;
    }
}