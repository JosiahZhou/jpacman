#### Story 5: Multi-Level Games
```

As a player;
I want to continue the game;
So that I can play the next level.

Scenario S5.1: The player continues the game
Given the player has won the current level,
and   the current number of level is smaller than 3;
Then  the next level can be played.

Scenario S5.2: The player ends the game
Given the player has won the current level,
and   the current number of level is greater or equal to 3;
Then  the game ends.