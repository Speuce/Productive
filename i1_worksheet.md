Iteration 1 Worksheet
=====================

Adding a feature
-----------------

Tell the story of how one of your features was added to the project.
Provide links to the
feature, user stories, and merge requests (if used), associated tests, and merge commit
that was used complete the feature.

Use one or two paragraphs which can have point-form within them.

Our project has a feature named Incentive Systems including: 'experience', 'level', 'coin' and 'title'; which are used for incentivizing users
to get their tasks done quick and on-time. First, we divided the feature into user stories for receive rewards, level up and earn titles. Then,
we created a User Object to handle each user's rewards variable and made it as persistance data. We also created RewardManager to handle coins
and level ups. TitleManager is used for handling users' titles. After that, UI for displaying level, experience, title was created and is made as
header in home fragment. For selecting another title option, we created TitleSelection Activity which is opened by clicking on title of header UI.
We use EventListener to link data between classes. Multiple tests were created to test on each Manager class. For each task, after all tests passed,
we commited and made a merge request for other member to check on it and accept if there is no problem.

Feature: [Incentive System](#7)

User Stories: [Level Up](#17), [Receive Rewards](#16), [Earn Titles](#18)

Associated Tests: [RewardManagerTest](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/develop/app/src/test/java/com/productive6/productive/RewardManagerTest.java),
[TitleManagerTest](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/develop/app/src/test/java/com/productive6/productive/TitleManagerTest.java),
[UserManagerTest](https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/develop/app/src/test/java/com/productive6/productive/UserManagerTest.java)

Exceptional code
----------------

Provide a link to a test of exceptional code. In a few sentences,
provide an explanation of why the exception is handled or thrown
in the code you are testing.

In the TaskManagerTest.java, we tested an the TaskManager's validation of the priority
field of a task.
https://code.cs.umanitoba.ca/3350-winter-2021-a01/Productive-6/-/blob/develop/app/src/test/java/com/productive6/productive/TaskManagerTest.java#L145

In this specific code, we chose to go with a 'throw error if input data in invalid' approach.
The user will eventually be able to enter a custom value in for the 'priority' of a task.
In order to prevent the user from entering extraneous negative values, we do some validation in the logic layer
that the provided priority is not negative. If it is, an appropriate descriptive exception is thrown, that
later should be meaningfully displayed to the user.

Branching
----------

Provide a link to where you describe your branching strategy.

Provide screen shot of a feature being added using your branching strategy
successfully. The [GitLab Graph tool can do this](https://code.cs.umanitoba.ca/comp3350-summer2019/cook-eBook/-/network/develop),
as well as using `git log --graph`.

[Branching Strategy](BranchingStratagy.md)

![Picture of git graph](Branching.png)

SOLID
-----

Find a SOLID violation in the project of group `(n%12)+1` (group 12 does group 1).
Open an issue in their project with the violation,
clearly explaining the SOLID violation - specifying the type, provide a link to that issue. Be sure
your links in the issues are to **specific commits** (not to `main`, or `develop` as those will be changed).

Link to issue:
https://code.cs.umanitoba.ca/3350-winter-2021-a01/umhub-7/-/issues/35

Agile Planning
--------------

Write a paragraph about any plans that were changed. Did you
'push' any features to iteration 2? Did you change the description
of any Features or User Stories? Have links to any changed or pushed Features
or User Stories.

Our group decided to push back [Schedule Due Dates](#19) to iteration 2. This is because we spent a lot of time getting the database and UI while throughly checking our code
for bugs before merging. Even though we had created UI to pick a due date, it took longer than we expected to integrate the button
to the create task popup window and work around with the due date variable. By leaving it to iteration 2, we can spend time on error checking and perfecting
our existing code.  
Our group also decided to change the title/description of ['Calendar' UI](#27). At first, our group wanted to display all to-do tasks week by week,
but we found that display tasks by choosing a date in calendar is much easier to implement while performing the same function.