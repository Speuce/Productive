# The Event System In productive

You may notice the EventDispatch.java and a few “@ProductiveEventListener” annotations around and wonder what the hell is going on. This document explains that. 

Essentially the idea behind this is for any class in the application to be able to listen to “Events” from other components of the System.

## ‘Well what is an event?’

Anything, really. 

Whenever something of interest happens.

A new task is added to the task list. The task list data is ready for the UI. Your mother told you to clean your room. Anything is an event.

## ‘Okay… uh why don’t we just do things the normal way?’


Really this is about abstraction and information hiding. 

Lets say you wanted the UI to be told when a new task is added to the user list.

Option regular is to get the UI to communicate directly with the task creator, whatever that may be, and have the UI say to the task maker: “Hey! Call this method of mine when you make a new task”. And the task maker will say “OK whatever ya want pal”.

Thats nice. But what if you add a new class that makes tasks. Maybe tasks are sent to the user by their Boss. Well now you need to change the UI to tell the Boss Communicator to let the UI know of new tasks as well. 

### *There’s got to be a better way.*

Thus, the task System.

Let me first define Listening to an Event as ‘*seeking notification when a specific Event occurs*’.

With that out of the way: 
We need a way to distinguish between classes that will listen to events and classes that won’t (Because not every class will need to listen to events), and collectively refer to classes that listen to events as one type. Since java requires OOP, we really mean refer to objects that listen to events. Thus, any object that will listen to events will implement the empty ‘ProductiveListener’ interface. That way, we can refer to the object as an instance of ProductiveListener without knowing a single thing about the object or the class, other than that it listens to events, of course.


## So what about events?

Well, we also need to represent events in some way, thus we use an ProductiveEvent class. To be able to distinguish between a mouse click and an earthquake warning event, each event extends ProductiveEvent in some way, and includes relevant information about the given event (i.e where the mouseclick occurred, etc).

## ‘Okay, so classes can listen to events. But how?’

*Methods.*

We need to mark specific methods as ‘listener methods’ that will be called when an event occurs. Very similar to the ‘regular’ approach, but with some extra information. 
Each method that listens to an event is called an ‘listener method’. And in order to distinguish between mouse click events and earthquake events, these ‘listener methods’ will specify exactly which event it is that they’re listening to (as a parameter). 

Additionally, not all methods inside of a listener class have to be listener methods. There can be other stuff to. So we need a way of distinguishing between listener methods and non-listener methods. Thus, the: 

## @ProductiveEventHandler annotation

An annotation is essentially code metadata -- data about your code. By putting @ProductiveEventHandler above a listenever method, you are essentially attaching a tag to the method itself, and later we can filter out method in a class by whether or not they have this specific tag. Pretty cool, huh?

### So, putinng everything together so far:

```
public class MyClass implements ProductiveListener{
	
	public void notAListenerMethod(){
		//CODE
}

	@ProductiveEventHandler
	public void ahhhEarthQuake(EarthQuakeEvent e){
		//hopefully code to notify someone….
	}

}
```
notice that the specific name of the a listener method doesn’t particularly matter, as long as it works within the class itself, and makes sense to you the programmer. The Event System shouldn’t care, as long as the class and method and properly marked.

Okay, so we have events and we have classes that listen to events. Now we must connect them.

This occurs through:

## The EventDispatch class

The idea is that the Event Dispatch keeps an internal mapping of all possible event types, to all methods that listen for them. I won’t dive into detail of how this happens, as java has terribly complicated function pointer implementations. But at a high level, it’s just a mapping of Event to List<HandlingMethod>.

So when an event happens, any class which makes the event, just has to tell EventDispatch to call all the listening methods: EventDispatch.dispatchEvent(new MouseClickEvent(450, 500))

The listener classes, on the other hand, need to interface with the EventDispatch class to tell it “hey! Add me to your list!” or “subscribe me to future events pls”. This can usually just happens in the constructor of the class, but sometimes you may need to put this in an ‘init’ method instead. But either way, in the class constructor for instance, the class can call “EventDispatch.registerListener(this)” to tell the EventDispatch that this object listens to events. EventDIspatch then goes through the object, looks at its methods, figures out which are marked by the @ProductiveEventHandler annotation, and figures out which event each method is trying to listen for, and adds it to the appropriate place in the map.

### And that's it!
 *(phew)*

