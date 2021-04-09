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

Discuss a Feature or User Story that was cut/re-prioritized
============================================

When did you change the priority of a Feature or User Story? Why was it
re-prioritized? Provide a link to the Feature or User Story. This can be from any
iteration.

Our group decided to leave out the feature [Let Multiple People use the app](#10)
at the beginning of iteration 3 though it has been considered whether it is needed
at the beginning of the project. Because people don't typically let others use
their phone so the feature wouldn't make sense and is redundant.

Acceptance test/end-to-end
==========================

Write a discussion about an end-to-end test that you wrote. What did you test,
how did you set up the test so it was not flaky? Provide a link to that test.

Acceptance test, untestable
===============

What challenges did you face when creating acceptance tests? What was difficult
or impossible to test?

Velocity/teamwork
=================

Did your estimates get better or worse through the course? Show some
evidence of the estimates/actuals from tasks.
