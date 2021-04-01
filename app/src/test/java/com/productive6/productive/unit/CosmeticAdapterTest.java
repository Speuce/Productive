package com.productive6.productive.unit;

import com.productive6.productive.logic.adapters.ICosmeticAdapter;
import com.productive6.productive.logic.adapters.impl.DefaultCosmeticAdapter;
import com.productive6.productive.objects.Cosmetic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CosmeticAdapterTest {

    ICosmeticAdapter cosmeticAdapter;

    @Before
    public void init(){

        int keyConverter[][] = {{1,2,3},{11,22,33}};
        int costs[] = {111,222,333};
        String names[] = {"cost1" , "cost2", "cost3"};

        cosmeticAdapter = new DefaultCosmeticAdapter(keyConverter,costs,names);

    }

    @Test
    public void testFindCosmetic(){
        Cosmetic testCos = cosmeticAdapter.idToCosmetic(1);
        assertEquals("Resource id was not 11",11, testCos.getResource());

        testCos = cosmeticAdapter.idToCosmetic(2);
        assertEquals("Resource id was not 22",22, testCos.getResource());

        testCos = cosmeticAdapter.idToCosmetic(3);
        assertEquals("Resource id was not 33",33, testCos.getResource());
    }

    @Test
    public void testCosNotInAdapter(){
        Cosmetic testCos = cosmeticAdapter.idToCosmetic(400);
        assertEquals("Resource id was not NULL",null, testCos);

    }

}
