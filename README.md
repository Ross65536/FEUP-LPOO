# LPOO1617_T4G10 Final Project
## [GAME_NAME]

### Members:

Rostyslav Khoptiy | ID: up201506219 | Email: up201506219@fe.up.pt

João Carlos Oliveira Lago | ID: up201504374 | Email: up201504374@fe.up.pt

## Architecture Design

### Package and Class diagram

[UML here]

### State Machine of game

[state machine here]

### Design Patterns used

* A singleton for LevelAssetManager class. This class uses the LIBGDX AssetManager class which is unique for all the levels that use it, and since the game does a lot of loading and unloading of levels a single universal acess point for the asset manager is useful, to load the correct assets for each level and to get those assets to be able to draw them and play them.


## GUI Design

[place here]

## Test Design

This project uses an external library (LIBGDX) to make mobile application development easier, therefore this project is divided into 2 main parts to allow for unit testing: 
* (Input and LIBGDXwrapper packages) Graphics and other library related stuff (camera, input, asset management, etc)
* (gameLogic package) Game Logic where all the objects such as the user character, enemies, platforms, etc are represented and interact.

Unit test are only really applicable to the 2nd part, so all of the objects in gameLogic package should be tested.

* Character class will be tested for correct movement and position, to ascertain that it moves correctly.
* GameWorld will be tested for character collisions, to see if the hero can destroy enemies (by jumping on them) and if the hero can be killed (and the GameWorld correctly sets the game as lost)
* LevelDirector class will be tested to see if it correctly generates enemies for GameWorld, with the appropriate difficulty given a time and user input and world statistics
* Hero Lamp will be tested to see if it generates light apporpriatly given some hero actions
* Also any other objects in this package that we might need would be tested appropriatly.
