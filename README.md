# Group 6

# Productive Vision Statement 

<hr/>

&emsp; Productive is an app that allows the user to track and monitor their productivity using a task system and a difficulty scale. The app will encourage and reward the user by gamifying the tasks that they define for themselves.  These mechanics will create the incentive necessary to encourage the user to keep on schedule, entirely for free. Most importantly, Productive makes both difficult and mundane tasks fun!  

&emsp; Productive is an app designed for those who may struggle with scheduling and self-motivation, especially students. In these uncertain times, it is more arduous than ever for students to find a way to motivate themselves and stay on task. Productive will provide a satisfying anchor for those busy, forgetful, and easily-distracted students. Students are the largest consumer of games –– among other distractions –– which will expedite their indoctrination into the world of gamified tasks!  

&emsp; Unlike existing productivity apps, Productive will assist the users in the completion of their mundane or difficult tasks, such as doing the dishes or writing an essay through the use of an experience system. Users will be able to attach a visible value to their tasks, and Productive will use these values to assign an experience value as a reward for completing a task. Similar to a video game, a user will use this experience to unlock various cosmetic features for their personalization within the app. This gamifying of tasks will aid the user by making their tasks feel as satisfying as the other distractions available to them. The experience system in Productive will also incentivise users to complete repeated tasks, such as weekly laundry, through the use of ‘streaks’. As the user keeps up with their repeated tasks, they will build a ‘streak’ which will increase their experience gain. These pieces come together to build a cohesive and game-like productivity tracking app.  

&emsp; A growing issue for students in the modern world is procrastination –– the ‘pushing off’ of their schedule, especially given the multitude of distractions available. Productive will encourage users to stay on track with their designed schedule through the use of schedule reminders and experience rewards for completing their tasks on schedule. Users will add and manage tasks to create a schedule to help the users organize their day. Productive will use this schedule to remind users of the tasks that they still need to complete that day, thereby alleviating procrastination caused by distraction or forgetfulness. Users can rest assured that they will be reminded of their tasks, small and large alike, and won’t need to occupy their memory with every menial item on their to-do list.

&emsp; The goal of the app is to reach as many people as possible. As such, the goal of Productive is to achieve 100,000 regular users. The reason we chose this as our success criteria is because the number of total downloads would include many people who download the app without using it regularly, and thus fails to portray the engagement of the user base. A regular user is a user who adds or completes at least one task, a minimum of three days a week. This way, we can objectively measure the usefulness of Productive, instead of its marketing. 

# Known Warnings/Errors

### Warning: 'Accessing Hidden Field', Error 'Access denied finding property "ro.serialno"'
Occassionally, when running the application in Android Studio, tens to hundreds of lines of 'Accessing hidden field ...'. This seems to be a result of utilizing Android Studio's built in 'database inspector', rather than the direct result of our code. See here for more info: https://stackoverflow.com/a/66201148/6047183

### "Failed to choose config with EGL SWAP BEHAVIOR PRESERVED, retrying without"
Not a result of our code. See: https://stackoverflow.com/a/54917041/6047183.

### Use of @SupressWarning annotation
Although we do not directly use this annotation in our project, some of the libraries that we use (Hilt and Room) autogenerate class implementations which DO use these annotations. This isn't a direct result of our code.

# Libaries in Use
All of these libaries are added using gradle. You (the person trying to run this app in Android Studio) may need to open this gradle file at first and Click 'Sync Now' to have gradle download the required libraries for the project.
Additionally, some of these libaries generate class implemenations at compile-time, so until you first build the project, there MAY be some apparent red compiler issues. Have no fear, a build shall fix all of this!

### Room
&emsp; [Room](https://developer.android.com/jetpack/androidx/releases/room) is an ORM that takes care of our real persistence layer with relative ease. Annotations are used throughout our codebase for integration with room. Although room is android specific, many of the annotations used are _very_ similar to annotations for desktop ORM. Additionally, we added a layer of Interfaces ontop of what is required by room so that we could easily swap out implementations if we so decide to switch libraries.

### Hilt
&emsp; We use [Hilt](https://dagger.dev/hilt/) to take care of some of our dependency injection of single-instance classes. Hilt can be used for both android and desktop applications. The annotations @AndroidEntryPoint is specific to the android hilt, but other with all other fields marked with @Inject are platform-inspecific.  

### Mockito  
&emsp; We use [Mockito](https://mvnrepository.com/artifact/org.mockito/mockito-core) to create mocks of classes (mostly databases) to allow for easy unit testing of logic layer classes. Mokito is an industry standerd and cross-platform testing framework for java. mock(myClass.class) creates an object that mocks or dummies myClass. Which can be used with .when(method parameters) and .returns() to simulate dependant classes for testing purposes.

### CompactCalendarView
&emsp; We use [CompactCalendarView](https://github.com/SundeepK/CompactCalendarView), a library specific to android applications, which offers a wide range of features in addition to the default CalendarView. The library allows theming, animations, and adding dots underneath dates as an indication to the user about the presence of tasks on that day.
