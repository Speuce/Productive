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

- tests @before 

Discuss a Feature or User Story that was cut/re-prioritized
============================================

When did you change the priority of a Feature or User Story? Why was it
re-prioritized? Provide a link to the Feature or User Story. This can be from any
iteration.

Our group decided to leave out the feature [Let Multiple People use the app](#10)
at the beginning of iteration 3 though it has been considered whether it is needed
at the beginning of the project. Because people don't typically let others use
their phone so the feature wouldn't make sense and is redundant. Maybe the feature
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

Velocity/teamwork
=================

Did your estimates get better or worse through the course? Show some
evidence of the estimates/actuals from tasks.

The differences between our estimates and actuals time are quite stable.
Though the commit time and actual time is less than iteration 1, but we used more
of our time in iteration 2 and 3 for fixing the bugs in UI, in databases and writing tests.

Evidence: *i'm waiting for time estimation of iteration 3 so ...*