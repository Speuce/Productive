package com.productive6.productive.logic.adapters.impl;

import com.productive6.productive.logic.adapters.ICosmeticAdapter;
import com.productive6.productive.logic.rewards.impl.DefaultTitleManager;
import com.productive6.productive.objects.Cosmetic;

import java.util.ArrayList;
import java.util.Iterator;


public class DefaultCosmeticAdapter implements ICosmeticAdapter {

    private ArrayList<Cosmetic> cosmeticList;

    public DefaultCosmeticAdapter(int[][] idConverter, int[] cosmeticCosts, String[] cosmeticNames){
        cosmeticList = new ArrayList<>();
        initializeCosmeticList(idConverter, cosmeticCosts, cosmeticNames);
    }

    private void initializeCosmeticList(int[][] idConverter, int[] cosmeticCosts ,String[] cosmeticNames) {
        for(int i = 0; i < idConverter[0].length; i++){
            cosmeticList.add(new Cosmetic(idConverter[0][i],idConverter[1][i], cosmeticCosts[i], cosmeticNames[i]));
        }
    }

    public ArrayList<Cosmetic> getAllCosmeticOptions(){
        return cosmeticList;
    }

    @Override
    public Cosmetic idToCosmetic(int id) {
        Cosmetic item = null;
        boolean found = false;

        Iterator<Cosmetic> iterator = cosmeticList.listIterator();

        while(iterator.hasNext() && !found){
            Cosmetic curr = iterator.next();

            if(id == curr.getId()){ //check if found item
                item = curr;
                found = true;
            }

        }

        return item;
    }

}
