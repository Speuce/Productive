package com.productive6.productive.objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Encapsulates information of a user including XP, Coins, and applicable level
 */
@Entity(tableName = "users")
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
     * The nothing constructor
     */
    public User() {
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
        this.selectedTitle = selectedTitle;
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
}
