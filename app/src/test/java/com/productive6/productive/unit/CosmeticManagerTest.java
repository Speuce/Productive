package com.productive6.productive.unit;

import com.productive6.productive.logic.adapters.ICosmeticAdapter;
import com.productive6.productive.logic.cosmetics.ICosmeticManager;
import com.productive6.productive.logic.cosmetics.impl.CosmeticManager;
import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.logic.user.IUserManager;
import com.productive6.productive.objects.Cosmetic;
import com.productive6.productive.objects.User;
import com.productive6.productive.objects.events.user.UserLoadedEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CosmeticManagerTest {

    @Mock
    static IUserManager data = mock(IUserManager.class);

    @Mock
    static ICosmeticAdapter adapter = mock(ICosmeticAdapter.class);

    private ICosmeticManager cosmeticManager;

    private User person;

    @Before
    public void init(){
        EventDispatch.clear();
        cosmeticManager = new CosmeticManager(adapter,data);
        person = new User();
        EventDispatch.dispatchEvent(new UserLoadedEvent(person));

    }

    @Test
    public void purchasableTest(){
        ArrayList<Cosmetic> all = new ArrayList<>();
        ArrayList<Cosmetic> owned = new ArrayList<>();

        when(adapter.getAllCosmeticOptions()).thenReturn(all);

        Cosmetic item1 = new Cosmetic(1,11,0, "item1");
        Cosmetic item2 = new Cosmetic(2,22,0, "item2");
        Cosmetic item3 = new Cosmetic(3,33,0, "item3");
        Cosmetic item4 = new Cosmetic(4,44,0, "item4");

        all.add(item1);
        all.add(item2);
        all.add(item3);
        all.add(item4);

        owned.add(item1);
        owned.add(item3);

        User person = new User();
        person.setOwnedCosmetics(converter(owned));
        EventDispatch.dispatchEvent(new UserLoadedEvent(person));

        assertEquals("Did not return the correct number of cosmetics", 2, cosmeticManager.getPurchasable().size());

    }

    @Test
    public void favoriteTest(){
        Cosmetic item1 = new Cosmetic(1,11,0, "item1");
        cosmeticManager.setFavorite(item1);
        assertEquals("Did not return expected favorite cosmetic", item1.getId(), cosmeticManager.getFavorite().getId());
    }



        List<Cosmetic> converter(ArrayList<Cosmetic> arrList){
        List<Cosmetic> list = new LinkedList<>();
        Iterator<Cosmetic> iterator =  arrList.iterator();

        while(iterator.hasNext()){
            list.add(iterator.next());
        }

        return list;
    }

}
