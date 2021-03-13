package com.productive6.productive.unit;

import com.productive6.productive.logic.event.EventDispatch;
import com.productive6.productive.objects.events.DummyEvent;
import com.productive6.productive.objects.events.ProductiveEvent;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;
import com.productive6.productive.objects.events.SubDummyEvent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * Tests Event Dispatch Methods
 */
public class EventDispatchTest {

    @Test
    public void testBasicEvent(){
        final AtomicBoolean bool = new AtomicBoolean(false);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onEvent(DummyEvent e){
                bool.set(true);
            }
        });
        EventDispatch.dispatchEvent(new DummyEvent());
        assertTrue("Event Dispatch was unsucessful with a simple non-hierarchical event", bool.get());
    }

    @Test
    public void testEventFiltering(){
        final AtomicBoolean bool = new AtomicBoolean(true);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onEvent(DummyEvent e){
                bool.set(false);
            }
        });
        EventDispatch.dispatchEvent(new ProductiveEvent() {
        });
        assertTrue("Event Dispatch was unsucessful in filtering out a non-event", bool.get());
    }

    @Test
    public void testHierarchicalEvent(){
        final AtomicBoolean bool = new AtomicBoolean(false);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onEvent(DummyEvent e){
                bool.set(true);
            }
        });
        EventDispatch.dispatchEvent(new SubDummyEvent());
        assertTrue("Event Dispatch was unsuccessful in dispatching events in a hierarchy", bool.get());
    }

    @Test
    public void testHierarchicalEventFiltering(){
        final AtomicBoolean bool = new AtomicBoolean(true);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onEvent(SubDummyEvent e){
                bool.set(false);
            }
        });
        EventDispatch.dispatchEvent(new DummyEvent());
        assertTrue("Event Dispatch dispatched a child event with a parent event call (bad!)", bool.get());
    }

    @Test
    public void testMultiDispatch(){
        final AtomicInteger i = new AtomicInteger(0);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onEvent(SubDummyEvent e){
                i.incrementAndGet();
            }
        });
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onEvent(SubDummyEvent e){
                i.incrementAndGet();
            }
        });
        EventDispatch.dispatchEvent(new SubDummyEvent());
        assertEquals("Event Dispatch failed to dispatch events to multiple listeners", 2, i.get());
    }

    @Test
    public void testMultiSameClassDispatch(){
        final AtomicInteger i = new AtomicInteger(0);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onEvent(SubDummyEvent e){
                i.incrementAndGet();
            }
            @ProductiveEventHandler
            public void onEvent2(SubDummyEvent e){
                i.incrementAndGet();
            }
        });
        EventDispatch.dispatchEvent(new SubDummyEvent());
        assertEquals("Event Dispatch failed to dispatch events to multiple listening methods within the same class", 2, i.get());
    }

    @Test
    public void testRejectNonProductiveEvents(){
        assertThrows("EventDispatch failed to catch a listener registered of the wrong parameter type",IllegalArgumentException.class,() -> EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onEvent(Object e){

            }
        }));
    }

    @Test
    public void testRejectMultiParamEvents(){
        assertThrows("EventDispatch failed to catch a listener registered of the wrong parameter legnth",IllegalArgumentException.class,() -> EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onEvent(DummyEvent e, DummyEvent e2){

            }
        }));
    }

    @Test
    public void testMultipleDispatch(){
        final AtomicInteger i = new AtomicInteger(0);
        EventDispatch.registerListener(new ProductiveListener() {
            @ProductiveEventHandler
            public void onEvent(SubDummyEvent e){
                i.incrementAndGet();
            }
        });
        final int tests = 5;
        for (int x = 0; x < tests; x++) {
            EventDispatch.dispatchEvent(new SubDummyEvent());
        }
        assertEquals("Event Dispatch to properly dispatch multiples of the same event. Expected: " + tests + " Actually dispatched: " + i.get(), tests, i.get());
    }
}
