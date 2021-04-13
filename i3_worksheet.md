What technical debt has been cleaned up
========================================

Show links to a commit where you paid off technical debt. Write 2-5 sentences
that explain what debt was paid, and what its classification is.

We have cleaned up the technical debt for priority and difficulty choices. In
iteration 2, our group used condition statements to assign strings to each value
of priority and difficulty, such as `if (priority = 1) return "High"`. It also
eventually violates OCP. In iteration 3, we cleaned up the debt by using enum classes
after having acknowledged the smell. Using enum classes makes it easier to modify or
add more choices in the future. This is inadvertent prudent debt.

Link: [Commit link](ab8b371c1f35c1805b280a2e19c3b1eb1a44d1bd)

What technical debt did you leave?
==================================

What one item would you like to fix, and can't? Anything you write will not
be marked negatively. Classify this debt.

One item that our group would like to fix is using many constructors in @Before
in tests. This is deliberate prudent debt.

Discuss a Feature or User Story that was cut/re-prioritized
============================================

When did you change the priority of a Feature or User Story? Why was it
re-prioritized? Provide a link to the Feature or User Story. This can be from any
iteration.

Our group decided to leave out the feature [Let Multiple People use the app](#10)
at the beginning of iteration 3 though it has been considered whether it is needed
at the beginning of the project. Because people don't typically let others use
their phone so the feature wouldn't make sense and is rather redundant. Maybe the feature
can be developed to be a login logout system for users to transfer data between
devices in the future.

Acceptance test/end-to-end
==========================

Write a discussion about an end-to-end test that you wrote. What did you test,
how did you set up the test so it was not flaky? Provide a link to that test.

We test our basic but important feature: "Add Task". We perform add task with name
and check whether the appearing task has the correct information or not.
In order to prevent from having flaky test, we used deterministic value for our
test input. To be specific, input "Espresso" as the name of the task and let other
choices as default.

Test link: [Add Task](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/tree/master/app/src/androidTest/java/com/productive6/productive/system/AddTaskTest.java)

Acceptance test, untestable
===============

What challenges did you face when creating acceptance tests? What was difficult
or impossible to test?

By far the biggest challenge we faced with acceptance tests was testing the notifcation feature of out app. The english acceptance test was simple enough to figure out -- exit the app, wait for notification. But when it came to automating this acceptance test; that was an issue. The reason for this was because the library we used for automating our acceptance tests, Espresso, only worked within the context of the app itself. So we tried to combine Espresso with another library (UIAutomator) that allowed us to test inside the context of the device, instead of only the app. The issue with this being that the test itself ended up super flaky. Often times the test will fail simply due to the previous state of the device, which is not something we can control for with the acceptance test. Sometimes it would fail because the device blocks notifications; Sometimes it would fail because the emulator device would _refuse_ to show the notification panel; and sometimes it would fail on other devices because the manner in which the notifiction panel opens varies from device-to-device. The feature of "notifying the user of impending deadlines" was very very difficult to test. This issue is further discussed in the readme. 

Velocity/teamwork
=================

Did your estimates get better or worse through the course? Show some
evidence of the estimates/actuals from tasks.

The differences between our estimates and actuals time are quite stable. But actual time
is less than commit time in iteration 2 and 3 because we did everything faster when we got used
to the course project. We also had a more reasonable workload for each iteration, which is reflected
on the velocity chart and we didn't have to leave anything to later iteration like in iteration 1.
Though the commit time and actual time decreased by each iteration, but we used more
of our time in iteration 2 and 3 for fixing the bugs in UI, in databases and writing tests.

Evidence: [Picture of velocity chart for each iteration](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/master/velocity-chart2.png)