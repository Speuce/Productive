# Paying off technical debt

### Changing Task Object From java Epoch Millis to date

When we first started the project (and didn't know much better), we represented the creation time of a task object in Epoch Milliseconds. At the time, this was fast and allowed us to not have to think of storing dates in the DB in any special way, or any timezone conversions, this was the benefit of the debt, Unfortunately, not using a proper Date object would mean every time we want to get the actual day that the time represents, we would have to wrap it in an extra conversion, this was the interest on the debt. And finally, after having integrated that piece of code into many difference places in our project, we decided to pay off the debt. This commit details all the changes needed to pay off the debt: https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/commit/10b4716d978bc70946f5390d987038ec681f1d19.

Here for example, when paying off this debt, we had to change the default System.currentTimeMillis to LocalDate.now() in all the constructors of task objects, https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/commit/10b4716d978bc70946f5390d987038ec681f1d19#2ae9bc26a5ebc7e457e7c6daa456f0dc9fbfc80e_79_78. 
We also had to come up with converters to save/retrieve the LocalDates in the DB.

We would classify this debt as the inadvertent prudent kind. At the time, we didn't know of any better methods to represent dates, and this way seemed to work for us great at first, so why would we waste time looking for the 'best' solution if it worked? This is a 'now we know how we should've done it' situation -- inadvertant prudent debt.

### Refactoring everything out of MainActivity

When we first started adding features to the UI, much of that code ended up in the MainActivity file. The problem is, we had three separate fragments (subsections) in the MainActivity, and each fragment really should have its own code in a separate location. This was the debt. We knew it wasn't the best thing to do, but we had reason to create the debt.

At the time, having everything in MainActivity was okay, because we needed to be agile and move quick. But as the codebase grew, it became quite 'spaghetti code'-like and hard to manuever and maintain. (The additional time it took to make changes in this file is the interest on said debt).

So, through this iteration we worked on removing code out of the MainActivity, and properly separating out different parts of the code into their own locations.

Here, for example, we pulled out code for the Add task UI into into the DashboardFragment, which meant that the Task list UI stuff would atleast all be in one location, and not intertwined with the schedule and rewards. This was us paying off our debt:

https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/commit/f5d0f7840fa3b44a21ef5892777d909a70b136f2?merge_request_iid=47

We would classify this as the deliberate prudent kind of debt. Deliberate because we knew what we were doing wasn't exactly the best. We forsaw future issues caused by the debt, and took it into consideration. Prudent because we did it by the means of being agile. At the time, the debt was small and easy to manage. The cost of figuring out what to change to make it work in the DashboardFragment just wasn't worth dealing with the debt. But of course, as the project grew, so did the debt. So we eventually had to pay it off.

# SOLID
Link to issue: [TermSetter-Group 6-A02](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-6/aurora-but-better-a02-group-6/-/issues/33)

# Retrospective
After the retrospective for iteration 1, our group has acknowledged more efficient way to do the project. As in iteration 1, our group had to push back one user story because we underestimated workload for one feature, our feature in iteration 1 is too general and it has too much work to do. Therefore, in this iteration, we divided user stories and dev tasks more reasonably, which is eveident in the completion of all features before the due date. We do not have to rush in last few hours and push back any task to later iteration. For example, in first iteration, we have feature [Manage a To-Do List](#1) whose description is too general and it had 4 user stories at first, which we decided to push back [Schedule Due Dates](#19). Meanwhile, in iteration 2, feature [Prioritize Tasks](#4) has more specific description so it is easier to create reasonable workload user stories. Moreover, our group decided to create label "To Do" and "Doing" to be easier to keep track on each other's workflow. For example, dev task [UI for user to create due date variable in a task](#42) was changed label from "To Do" to "Doing" when in progress and removed "Doing" when had finished.

# Design patterns

### Observer Pattern
As described [here](https://refactoring.guru/design-patterns/observer), we used a generified observer pattern as a central part of our codebase.

Any class can subscribe itself to listening to any kind of 'event', as seen [here](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/develop/app/src/main/java/com/productive6/productive/logic/rewards/impl/RewardManager.java#L149), so long as it registers itself by calling [this static function.](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/develop/app/src/main/java/com/productive6/productive/logic/event/EventDispatch.java#L36)

Then, any other class can notify all subscribed methods of a particular 'event', as seen [here](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/develop/app/src/main/java/com/productive6/productive/logic/rewards/impl/RewardManager.java#L175) by calling [this static function.](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/develop/app/src/main/java/com/productive6/productive/logic/event/EventDispatch.java#L73) 

[We even wrote up an extra piece of documentation for the event system.](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/develop/EventSystem.md).

### Adapter Pattern

Much like the adapter pattern described [here](https://refactoring.guru/design-patterns/adapter), [this](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/develop/app/src/main/java/com/productive6/productive/ui/dashboard/TaskAdapter.java) class is for displaying Task objects. It _adapts_ a list of tasks into a list of UI elements that the device can then display to the user.


# Iteration 1 Feedback Fixes

The only issue we got was https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/issues/64

We fixed this by moving the ProductiveApp file to the proper package (the ui layer).
This happened in this commit: https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/commit/abf0474070bb27a7ad26374a36024c53367d2c90

Thanks to Android Studio's beautiful refactoring tools, any references to the file in the manifest was automatically changed as well.