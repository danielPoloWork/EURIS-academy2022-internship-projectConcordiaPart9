### In general, the GIT-push policy should be as follows:
- Every time you start a new business, you perform a git pull from the "master" branch;
- Once the pull is done, you should create a new branch that will be called:
   > "feature/< task-name >"

  As < task-name > you should write a maximum of three words, all lowercase, separated by a minus.
  > Eg. "feature/max-three-words"

- Once the task is completed, you must bring the changes from the "master" branch to the one you want to merge, so as to solve any conflicts;
- If any conflicts are solved, make a merge from your branch to "master" branch;
### Notes
- You can use GIT commands both from terminal or sourcetree. Those who govern GIT bash correctly are omnipotent, but those who use sourcetree live a simpler and more visual life. **It's up to you**
- Absolutely do not merge on the branch < master > if you are not sure that yousolved all conflicts. No one dies except the patience of colleagues in case of mistakes.

###### by Gualtieri Dario