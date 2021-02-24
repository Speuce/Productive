## Branching Stratagy

### General flow

We chose to use the git flow for our branching stratagy. As such we will create "dev task" branches off of a stable `Develop` branch.

### Branch Creation

The name of the branches will be of the form: `[dev task id]-[dev task name]`.

New branches will be created by running `git checkout -b [branch name]` off of the stable develop branch.  

### Merge Requests

Prior to posting merge requests, branches will run `git fetch` and `git pull origin develop`. Resolving all merge conflicts and re-testing the feature branch.

When writing merge requests, the requester will include a short summary explaining how the branch solves the issue described in the dev task.

When reviewing merge requests, the requestee will check the posted code meets coding standards. Additionally, the requestee will pull the change branch and run all unit tests, and perform a manual system test. If code passes all stages, it is approved and merged with `develop`.

### After Merge

Developers are encouraged to quickly pull develop changes after a merge to solve and prevent as many merge conflicts as possible.