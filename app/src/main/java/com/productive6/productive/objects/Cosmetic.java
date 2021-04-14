package com.productive6.productive.objects;

public class Cosmetic implements Comparable<Cosmetic> {

    /**
     * id of this cosmetic
     */
    private int id;

    /**
     * Internal representation of the resource that represents this cosmetic
     */
    private int resource;

    /**
     * The cost of this cosmetic (in coins)
     */
    private int cost;

    /**
     * the name of this cosmetic
     */
    private String name;

    public Cosmetic(int id, int resource, int cost, String name) {
        this.id = id;
        this.resource = resource;
        this.cost = cost;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Cosmetic item) {

        int val = 1;

        if (getCost() < item.getCost())
            val = -1;
        else if (getCost() == item.getCost())
            val = 0;

        return val;
    }
}
