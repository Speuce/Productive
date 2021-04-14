package com.productive6.productive.logic.cosmetics.impl;

import com.productive6.productive.logic.adapters.ICosmeticAdapter;
import com.productive6.productive.logic.cosmetics.ICosmeticManager;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.Cosmetic;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.user.UserLoadedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class CosmeticManager implements ICosmeticManager,ProductiveListener {

    private ICosmeticAdapter adapter;
    private IUserManager data;
    private User person;

    public CosmeticManager(ICosmeticAdapter newAdapter, IUserManager data){
        EventDispatch.registerListener(this);
        this.adapter = newAdapter;
        this.data = data;
        person = null;
    }

    public ArrayList<Cosmetic> getOwnedItems(){
        ArrayList<Cosmetic> owned = new ArrayList<>(person.getOwnedCosmetics());
        Collections.sort(owned);
        return owned;
    }

    public ArrayList<Cosmetic> getPurchasable(){
        ArrayList<Cosmetic> allCosmetics = adapter.getAllCosmeticOptions();
        ArrayList<Cosmetic> ownedCosmetics = new ArrayList<>(person.getOwnedCosmetics());
        ArrayList<Cosmetic> purchasable = new ArrayList<>();

        Iterator<Cosmetic> cosmeticIterator = allCosmetics.iterator();

        while(cosmeticIterator.hasNext()){

            Cosmetic curr = cosmeticIterator.next();
            boolean owned = false;
            Iterator<Cosmetic> ownedCosmeticIterator = ownedCosmetics.iterator();

            while(ownedCosmeticIterator.hasNext()){
                Cosmetic ownedCosmetic = ownedCosmeticIterator.next();

                if(curr.getId() == ownedCosmetic.getId())
                    owned = true;

            }

            if(!owned)
                purchasable.add(curr);

        }

        Collections.sort(purchasable);
        return purchasable;
    }

    public Cosmetic getFavorite(){
        return person.getFavouriteCosmetic();
    }


    public void setFavorite(Cosmetic item){
        person.setFavouriteCosmetic(item);
        data.updateUser(person);
    }

    @Override
    public void purchaseCosmetic(Cosmetic item) {
        person.addToOwnedCosmetics(item);
        data.updateUser(person);
    }

    @Override
    public boolean isInitialized() {
        return person != null;
    }

    @ProductiveEventHandler
    public void userLoadedHandler(UserLoadedEvent event){
        person = event.getUser();
    }

}
