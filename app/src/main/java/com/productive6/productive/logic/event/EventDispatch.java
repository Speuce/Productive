package com.productive6.productive.logic.event;

import com.productive6.productive.objects.events.ProductiveEvent;
import com.productive6.productive.objects.events.ProductiveEventHandler;
import com.productive6.productive.objects.events.ProductiveListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manages Listeners and Dispatches events to listeners as appropriate
 * All Event Listener Calls will be done on the UI thread.
 *
 * @author matt
 */
public class EventDispatch {

    /**
     * Internal map of
     * EventType to (Listener Instance and Accepting  methods)
     */
    private static final Map<Class<?>,Map<ProductiveListener, List<Method>>> listenerMap = new HashMap<>();


    /**
     * Registers the given listener
     * @param l the listener to register
     */
    public static void registerListener(ProductiveListener l){
        for(Method m: l.getClass().getDeclaredMethods()){
            if(m.isAnnotationPresent(ProductiveEventHandler.class)){
                if(m.getParameterCount() == 1){
                    Class<?> clazz = m.getParameterTypes()[0];
                    if(ProductiveEvent.class.isAssignableFrom(clazz)){
                        if(!listenerMap.containsKey(clazz)){
                            listenerMap.put(clazz, new LinkedHashMap<>());
                        }
                        if(!listenerMap.get(clazz).containsKey(l)){
                            List<Method> methodList = new LinkedList<>();
                            listenerMap.get(clazz).put(l, methodList);
                        }
                        listenerMap.get(clazz).get(l).add(m);
                    }else{
                        throw new IllegalArgumentException("A method annotated with ProductiveEventHandler should have a parameter type that extends ProductiveEvent!");
                    }
                }else{
                    throw new IllegalArgumentException("A method annotated with ProductiveEventHandler should have exactly 1 parameter!");
                }
            }
        }
    }

    /**
     * Dispatches the given event to listening listeners.
     * @param e the event to dispatch.
     */
    public static void dispatchEvent(ProductiveEvent e){
        Class<?> clazz = e.getClass();
        while(clazz != null){
            if(listenerMap.containsKey(clazz)){
                for (Map.Entry<ProductiveListener, List<Method>> entry: listenerMap.get(clazz).entrySet()){
                    for(Method m: entry.getValue()){
                        m.setAccessible(true);
                        try {
                            m.invoke(entry.getKey(), e);
                        } catch (IllegalAccessException | InvocationTargetException illegalAccessException) {
                            illegalAccessException.printStackTrace();
                        }
                    }
                }
            }
            if(clazz.getGenericSuperclass() != ProductiveEvent.class){
                clazz = clazz.getSuperclass();
            }else{
                clazz = null;
            }
        }
     }
}
