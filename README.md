# Group 6

# Project's Purpose
Productive is an app that allows the user to track and monitor their productivity using a task system and a difficulty scale. The app will encourage and reward the user by gamifying the tasks that they define for themselves.  These mechanics will create the incentive necessary to encourage the user to keep on schedule, entirely for free.

# How To Run
```
1. Install the apk file and open the app
2. There is navigation bar at the bottom of your screen
- 💰 "Rewards" is to display your title, coins, XP and favorite item:
    - You can change your title when you level up
    - Go to "Shop" to buy cosmetics by using coins
    - Go to "Inventory" to choose your favorite item
- ⏳ "To-do" is to manage your tasks:
    - Add, edit, sort and complete tasks
    - Go to "Statistics" to see your record/history
- 🗓 "Calendar" is to manage tasks by date:
    - Select a date to see your incomplete tasks on that date
3. Close the app
4. Enjoy your day! (until you receive notifications "You have an incomplete task!" on deadline date)
```
# Required Documentation

### Vision Statement
[VISION.md](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/master/VISION.md)

### Branching Strategy
[BranchingStrategy](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/master/BranchingStratagy.md)

### Architecture
[Architecture.png](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/master/architecture.png)

### Worksheet
**Iteration 1:** [Worksheet 1](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/master/i1_worksheet.md)

**Iteration 2:** [Worksheet 2](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/master/i2_worksheet.md)

**Iteration 3:** [Worksheet 3](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/master/i3_worksheet.md)

# Device

We are using the **Nexus 7 API 30**, with target: **Android 11.0(Google APIs)**.

# Flaky Tests
## Notification Test
Our notification test is a textbook example of a flaky test. Sometimes it works, sometimes it doesn't. The reason for this is because in order for the notification to show, you need to EXIT the app. So we had to use another library to EXIT the app and test that the notification is actually sent. Problem is, this test is kinda dependant on your device. Some devices block notifications by default (so the test will fail). Other times, the emulator may have issues with being able to swipe down to see the notification panel (this happened more than once for us), and therefore the system test will fail to open the notification pane, and fail.
Its a symptom of the device itself.
If you'd like to test the notification by hand, do the following
```
1) Start the app from a fresh install
2) Tap 'todo' to navigate to the todo list
3) Tap the 'add task' button to open the task addition popup
4) tap the switch next to 'due date' to activate the due date
5) tap 'submit'
6) exit the app
7) wait up to one minute, and a notification should appear saying that you have 1 task(s) due today
```


# Known Warnings/Errors

### Warning: 'Accessing Hidden Field', Error 'Access denied finding property "ro.serialno"'
Occasionally, when running the application in Android Studio, tens to hundreds of lines of 'Accessing hidden field ...'. This seems to be a result of utilizing Android Studio's built in 'database inspector', rather than the direct result of our code. See here for more info: https://stackoverflow.com/a/66201148/6047183

### "Failed to choose config with EGL SWAP BEHAVIOR PRESERVED, retrying without"
Not a result of our code. See: https://stackoverflow.com/a/54917041/6047183.

### Use of @SuppressWarning annotation
Although we do not directly use this annotation in our project, some of the libraries that we use (Hilt and Room) auto-generate class implementations which DO use these annotations. This isn't a direct result of our code.


# Libraries in Use
All of these libraries are added using gradle. You (the person trying to run this app in Android Studio) may need to open this gradle file at first and Click 'Sync Now' to have gradle download the required libraries for the project.
Additionally, some of these libraries generate class implementations at compile-time, so until you first build the project, there MAY be some apparent red compiler issues. Have no fear, a build shall fix all of this!

### Room
&emsp; [Room](https://developer.android.com/jetpack/androidx/releases/room) is an ORM that takes care of our real persistence layer with relative ease. Annotations are used throughout our codebase for integration with room. Although room is android specific, many of the annotations used are _very_ similar to annotations for desktop ORM. Additionally, we added a layer of Interfaces ontop of what is required by room so that we could easily swap out implementations if we so decide to switch libraries.

### Hilt
&emsp; We use [Hilt](https://dagger.dev/hilt/) to take care of some of our dependency injection of single-instance classes. Hilt can be used for both android and desktop applications. The annotations @AndroidEntryPoint is specific to the android hilt, but other with all other fields marked with @Inject are platform non-specific.  

### Mockito  
&emsp; We use [Mockito](https://mvnrepository.com/artifact/org.mockito/mockito-core) to create mocks of classes (mostly databases) to allow for easy unit testing of logic layer classes. Mockito is an industry standard and cross-platform testing framework for java. mock(myClass.class) creates an object that mocks or dummies myClass. Which can be used with .when(method parameters) and .returns() to simulate dependant classes for testing purposes.

### CompactCalendarView
&emsp; We use [CompactCalendarView](https://github.com/SundeepK/CompactCalendarView), a library specific to android applications, which offers a wide range of features in addition to the default CalendarView. The library allows theming, animations, and adding dots underneath dates as an indication to the user about the presence of tasks on that day.

### MPAndroidChart
&emsp; We use [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart), a library specific to android applications, which allows us to display our data in graphs for the user. This library enables lots of customization for how the graphs look and behave, including a little animation we use that is played upon first opening the graph. This Library is entirely front-end in its use, so we expanded our logic layer to include a more specific get functions route to accommodate this limitation.

### UIAutomator
&emsp; We use [UIAutomator](https://developer.android.com/training/testing/ui-automator), a library specific to android applications, for one specific system test that needs to test *outside* the context of the app itself.


# Unit/Integration Tests
We have our unit tests under the com.productive6.productive (test) folder.
Our integration tests are instrumented tests, so they are under the com.productive6.productive (androidTest) folder

Code coverage cannot be obtained normally with instrumented tests in Android Studio, so you will need to go through some extra steps to get coverage of our integration tests:

## Coverage of integration tests

First, make sure you have a device emulator running.

Click 'gradle' in the top right (its written sideways).

Then under productive > tasks > other, find 'createDebugAndroidTestCoverageReport' and right click it and run it.

Let it do its thing. Then once it's done, you will have to go into file explorer and find where the project directory is located.

Once you do, go to app > build > reports > coverage > debug, and open 'index.html' in your favourite web browser.

There ya go!

## Website

The index.html for the website is located: `./productive-website/public/`