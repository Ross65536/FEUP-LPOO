# LPOO1617_T4G10 Final Project
## [GAME_NAME]

### Members:

Rostyslav Khoptiy | ID: up201506219 | Email: up201506219@fe.up.pt

João Carlos Oliveira Lago | ID: up201504374 | Email: up201504374@fe.up.pt

## Architecture Design

### Package and Class diagram

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/UML/classUML.png)

### State Machine of game

[state machine here]

### Design Patterns used

* Singleton for LevelAssetManager class. This class uses the LIBGDX AssetManager class which is unique for all the levels that use it, and since the game does a lot of loading and unloading of levels a single universal acess point for the asset manager is useful, to load the correct assets for each level and to get those assets to be able to draw them and play them.
* Observer for all classes in Input package. These classes extend the InputAdapter class from LIBGDX which implements this Pattern. It's used to notify Screen objects (such as GameScreen) when user input happens (touch, gyroscope) and to transmit it.
* Factory in MyGame class. In this class there are methods to create specific levels (GameWorld instances) and giving them to GameLevel class to be manipulated.
* State in LevelDirector. Different enemies for GameWorld class are generated based on this class's internal state (time since game start, numbers of present enemies, user input statistics which should "user stress", etc).
* State in GameLevel. This class usually updates GameWorld which is attached to it and draws the objects present in it, but if the hero dies it shouldn't this anymore and the screen should be changeed to show a menu.
* GameLoop is used in GameScreen. This class extends Screen which implements this. A method is called peridically to update the game state (update game World, draws objects) and it receives input to control the game World state.
* Template pattern is used extensively, on the gui alone there are four abstractions: AbstractGUI, AbstractSingleStageGUI, WidgetsInput and WidgetsGeneric. On the game logic there is abstraction for the game level so as to create diferent levels based on the abstraction.
* State pattern is used in the MenuManager class when changing between menus, the class holds a field named currentMenu which is initialized as the abstract class that all menus extends from(AbstractGUI), when changing between menus the MenuManager's setMenu method is called to replace the currentMenu field of MenuManager to the menu specified in the only argument of the method.
* Component pattern is used for multi stage menus, the abstract class AbstractGUI holds a list of Stage components for menus that use more then one Stage, AbstractGUI has a functions 'act' and 'draw' which call the 'act' and 'draw' of all Stage components.
* Abstract factory is used on the GUI, the AbstractGUI class holds two fields that are declared as abstract classes, the subclasses of AbstractGUI decide what concrete class those two field are going to be initialized as. In the case of the subclass MainGui those two fields are initialized as MainGUIWidgetsInput and MainGUIWidgetsProperties.
* Observer pattern is used on the AbstractGUI class because when adding new components they get automatically included in the update functions (act and draw) of the class.
* Update method is used in the AbstractGUI since when calling the act and draw functions of the AbstractGUI class all components belonging to it get their respective act and draw functions called.
* Object pool is used in the MenuManager class when setting up a new menu to display and allow input for, when setMenu method of the MenuManager is called a menu is only created if it hasn't been already created or if its usage has been too low and was deleted in consequence. When an initialized menu class hasn't been called for display for a certain number of setMenu calls the menu class is nullified and set up for the garbage collector to delete.
* Singleton pattern is used on the static enum struct on the MenuManager class, each value of the enum is initialized with a menu class type which is used when calling createInstance which either returns an already created menu or creates the menu if it is not initialized.

## GUI Design

### Main Menu

The main menu should have a simple design, buttons with their use written on them which lead the user to the menu they intends to view. The play button will lead the user to the play menu, the high score button will lead the user to the high score menu, the settings button will show the settings menu and the exit button will exit the application. The library provided by libgdx for lighting might be used for lighting effects on the background.

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUImock-up/Menu.png)


### Play Menu

The play menu will have all the modes for the game, the user must slide left to see the other modes. A play button will be in the screen to play the game mode. A text area will be bellow the play button to tell the user about the mode and maybe the controlls. There will be two buttons on the bottom of the screen, one to return to the main menu and another to view the settings menu.

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUImock-up/PlayMenu.png)


### Hight Scores

The high scores menu will have a listing of all the game modes and their respective highest scores. There will also be a button on the bottom that takes the user back to the main menu.

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUImock-up/High%20Scores.png)


### Pause Menu

The pause menu will be shown when the player is ingame and clicks the pause button on the edge of the screen. The pause menu will have the current score of the player. There will be a button to continue the game, restart, view the settings and to exit the game.

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUImock-up/Pause.png)


### Settings

The settings menu should be available from the main menu, the play menu and from the paused game. It should appear in the middle of the screen and display several settings options to select or deselect. There will be a big X button on the top left corner that exits the settings menu when clicked.

#### From the main menu
![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUImock-up/SettingsMenu.png)
#### From the play menu
![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUImock-up/SettingsPlayMenu.png)
#### From ingame
![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUImock-up/InGameSettings.png)


### HUD

The HUD for now is just a pause button on the corner of the screen

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUImock-up/InGame.png)


## Test Design

This project uses an external library (LIBGDX) to make mobile application development easier, therefore this project is divided into 2 main parts to allow for unit testing: 
* (Input and LIBGDXwrapper packages) Graphics and other library related stuff (camera, input, asset management, etc)
* (gameLogic package) Game Logic where all the objects such as the user character, enemies, platforms, etc are represented and interact.

Unit test are only really applicable to the 2nd part, so all of the objects in gameLogic package should be tested.

* Character class will be tested for correct movement and position, to ascertain that it moves correctly.
* GameWorld will be tested for character collisions, to see if the hero can destroy enemies (by jumping on them) and if the hero can be killed (and the GameWorld correctly sets the game as lost)
* LevelDirector class will be tested to see if it correctly generates enemies for GameWorld, with the appropriate difficulty given a time and user input and world statistics
* Hero Lamp will be tested to see if it generates light apporpriatly given some hero actions
* Also any other objects in this package that we might need would be tested appropriately.
