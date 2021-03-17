# Paying off technical debt

## Changing Task Object From java Epoch Millis to date

When we first started the project (and didn't know much better), we represented the creation time of a task object in Epoch Milliseconds. At the time, this was fast and allowed us to not have to think of storing dates in the DB in any special way, or any timezone conversions, this was the benefit of the debt, Unfortunately, not using a proper Date object would mean every time we want to get the actual day that the time represents, we would have to wrap it in an extra conversion, this was the interest on the debt. And finally, after having integrated that piece of code into many difference places in our project, we decided to pay off the debt. This commit details all the changes needed to pay off the debt: https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/commit/10b4716d978bc70946f5390d987038ec681f1d19.

Here for example, when paying off this debt, we had to change the default System.currentTimeMillis to LocalDate.now() in all the constructors of task objects, https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/commit/10b4716d978bc70946f5390d987038ec681f1d19#2ae9bc26a5ebc7e457e7c6daa456f0dc9fbfc80e_79_78. 
We also had to come up with converters to save/retrieve the LocalDates in the DB.

We would classify this debt as the inadvertent prudent kind. At the time, we didn't know of any better methods to represent dates, and this way seemed to work for us great at first, so why would we waste time looking for the 'best' solution if it worked? This is a 'now we know how we should've done it' situation -- inadvertant prudent debt.