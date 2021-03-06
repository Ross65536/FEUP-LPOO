# LPOO1617_T4G10 Final Project

### Members:

Rostyslav Khoptiy | ID: up201506219 | Email: up201506219@fe.up.pt

João Carlos Oliveira Lago | ID: up201504374 | Email: up201504374@fe.up.pt

## Setup
This project was developed in Android Studio with gradle for the dependencies, so that's the easiest way to compile and run this project:

0 - Clone the repo.

1 - Download android studio.

2 - follow this [guide](https://github.com/libgdx/libgdx/wiki/Setting-up-your-Development-Environment-(Eclipse,-Intellij-IDEA,-NetBeans)#setting-up-android-studio) to load the project.

3 - In andorid studio under Run -> Edit Configurations, select the '+' button and add Application (and DesktopLauncher as Main class) to build the project for desktop. The Android builder configuration should already be setup by the IDE.

3.1 - To run tests select Run -> Edit Configurations, select the '+' button and add Android JUnit with the 'All in package' option and selecting the 'tests' package of the project.

4 - To Run the Desktop simply Run the application in the IDE (from the dropdown), for android you can run it in the provided Emulator, or connect an android device and you can run the game there.

## User Manual

### Using the GUI

#### Main Menu

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUI_MANUAL/mainmenu.png)

* **Play Button** - The play button takes the user to the game mode choice menu where the user has to pick the mode to play.
* **High Score** - The high score button menu shows the max user score of both game modes.(Not Yet Implemented)
* **Settings** - The settings opens a settings window, it allows the user to change the game settings.
* **About** - This button shows meta information regarding the application, should contain credits and mention art creators for copyright reasons. (Not Yet Implemented)

#### Play Menu

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUI_MANUAL/playmenu.png)

* **Start Button** - The start button takes the user to the game corresponding to the game mode.
* **Back** - The back button takes the user back to the main menu.
* **Settings** - The settings opens a settings window, it allows the user to change the game settings.
* **Changing mode** - To change the mode slide your finger to the left on the area where it says "SWIPE".

#### Pause Menu

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUI_MANUAL/pausemenu.png)

* **Resume Button** - The resume button resumes the game. 
* **Restart Button** - The restart button restarts the game.
* **Settings** - The settings opens a settings window, it allows the user to change the game settings.
* **Exit button** - The exit button returns the screen to the play menu.

#### Settings Menu

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUI_MANUAL/settingsmenu.png)

* **Exit Button** - The exit button exits the settings menu.
* **Sound Bar** - The user can slide the sound bar to increase or decrease the sound.


### Gameplay

#### Playing the Survival Game Mode

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUI_MANUAL/survival.png)

This game mode is based on the player avoiding the enemies that are spawned (ground and flying based enemies) for as long as possible.
The player has only one life and the score is the time since the beggining of the game mode.
The player can move the character to the left and right and make him jump.
To pause the game the user has to press the pause button on the top right corner of the screen.

#### Playing the Platforms Game Mode

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/GUI_MANUAL/platforms.png)

This game mode has the player searching for recharger items so that the players light does not run out, if the player's light runs out the game will end.
There are also enemies that the player has to avoid, the player is given three lifes, each time an enemy hits the player he jumps back and a life is lost.
The objective of the game is to catch as many rechargers as possible, the score is the number of rechargers caught.
To pause the game the user has to press the pause button on the top right corner of the screen.

#### Controls:

On Desktop:
* The left and right arrow keys make the character move left and right
* The up arrow key or a left mouse click makes the hero jump. Pressing these for longer, makes the hero jump higher.

On Android:
* Tilting the phone to the left or right makes the character move left and right, with the degree of inclination setting the speed at which the character moves.
* Touching the screen makes the character jump. Pressing it for longer, makes the hero jump higher.


## Architecture Design

### Package and Class diagram

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/UML/classUML.png)

### State Machine of game

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/UML/stateUML.png)

### Overarching Design Decisions
On this project we decided to separate the logic from the specific android implementation, so the gameLogic package should contain all the classes necessary to run the application without it running specifically in android or any other LIBGDX supported machine. 

As a result we implemented a MVC (Model-View-Controller) architectural design pattern throughout the project, where the model is all the classes in gameLogic package, with the main GameWorld class joining and coordinating all of the other classes in this package (that contain all the necessary information to run the game), the view is most of the classes in LIBGDXwrapper with the AbstractGameWorldAdapter class serving as the main class that glues all the graphical and sound stuff that the user sees (polling the gameLogic package for information on what to display), and as the Controller we have the classes in the Input package which listen to the user for controlls (some inputs are polled and some are implemented with interrupts) and then send them to the model (GameWorld class) to change the state of the model (by modifying the player controlled Hero character's state).

Throughout the project we tried to follow the SOLID principles, that is, mainly to achieve a low coupling between the different classes and to achieve a separation of interfaces in order to gain a code that is friendly to change and maintenance.

### Design Patterns used

* Singleton for GameAssetHandler class. This class uses the LIBGDX AssetManager class which is unique for all the levels that use it, and since the game does a lot of loading and unloading of levels a single universal acess point for the asset manager is useful, to load the correct assets for each level and to get those assets to be able to draw them and play them.
* Strategy used in StageDirector class. This class has a Curve object (abstract class, with on abstract method) which has different behaviours depending on which specific Curve object it takes.
* Observer for all classes in Input package. These classes extend the InputAdapter class from LIBGDX which implements this Pattern. It's used to notify Screen objects (such as GameScreen) when user input happens (touch, gyroscope) and to transmit it.
* Factory in MyGame class. In this class there are methods to create specific levels (GameWorld instances) and giving them to GameLevel class to be manipulated.
* State in LevelDirector. Different enemies for GameWorld class are generated based on this class's internal state (time since game start, numbers of present enemies, user input statistics which should "user stress", etc).
* State in GameLevel. This class usually updates GameWorld which is attached to it and draws the objects present in it, but if the hero dies it shouldn't this anymore and the screen should be changeed to show a menu.
* GameLoop is used in GameScreen. This class extends Screen which implements this. A method is called peridically to update the game state (update game World, draws objects) and it receives input to control the game World state.
* Template pattern is used extensively, on the gui alone there are four abstractions: AbstractGUI, AbstractSingleStageGUI, WidgetsInput and WidgetsGeneric. On the game logic there is abstraction for the game level so as to create diferent levels based on the abstraction.
* State pattern is used in the MenuManager class when changing between menus, the class holds a field named currentMenu which is initialized as the abstract class that all menus extend from(AbstractGUI), when changing between menus the MenuManager's setMenu method is called to replace the currentMenu field of MenuManager to the menu specified in the only argument of the method.
* Component pattern is used for multi stage menus, the abstract class AbstractGUI holds a list of Stage components for menus that use more then one Stage, AbstractGUI has a functions 'act' and 'draw' which call the 'act' and 'draw' of all Stage components.
* Abstract factory is used on the GUI, the AbstractGUI class holds two fields that are declared as abstract classes, the subclasses of AbstractGUI decide what concrete class those two field are going to be initialized as. In the case of the subclass MainGui those two fields are initialized as MainGUIWidgetsInput and MainGUIWidgetsProperties.
* Observer pattern is used on the AbstractGUI class because when adding new components they get automatically included in the update functions (act and draw) of the AbstractGUI class.
* Update method is used in the AbstractGUI since when calling the act and draw functions of the AbstractGUI class all components belonging to it get their respective act and draw functions called.
* Object pool is used in the MenuManager class. Each menu is initialized in the begining of the game and laid in a pool on menus and each time a menu is needed the already created menu is used. When a certain menu needs just a few changes a function is called for this.
* Singleton pattern is used on the static enum struct on the MenuManager class, each value of the enum is initialized with a menu class type which is used when calling createInstance which either returns an already created menu or creates the menu if it is not initialized.
* Decorator pattern is used with the HeroLifesWrapper class that encapsulated the regular Hero class and adds the new functionality of getting hit and being nocked back, losing lifes and being immune for a few seconds.
* Facade pattern is very used for the game worlds, each game world can implement different actions since each gameworld can have diferent game features. The game worlds are simplified classes that call the update methods for each gamefeature.
* Delegation pattern for the gameworld, each gameworld holds diferent features (delagates) that are used to delagate their methods.

### Major Difficulties
* One major difficulty was finding the right level of abstraction and the right separation between the modules in order to achieve a low level of coupling between the classes. 
* Creating Tests that tested Modules independently of others was difficult, since some classes depended on others, which we overcame by created simple mock classes (and using EasyMock)

### Lessons Learned 
* The value of low coupling in code
* EasyMock for mocking classes
* Developing applications for Android
* How to use LIBGDX to develop applications
* Improved knoweledge of java features.
* Using JSON files and sprite sheets to implement a GUI.

### Test Design

This project uses an external library (LIBGDX) to make mobile application development easier, therefore this project is divided into 2 main parts to allow for unit testing: 
* (Input and LIBGDXwrapper packages) Graphics and other library related stuff (camera, input, asset management, etc)
* (gameLogic package) Game Logic where all the objects such as the user character, enemies, platforms, etc are represented and interact.

All the classes in the gameLogic package have been tested (tests package) with unit tests, where each class was tested individually and separately (with Mock classes) from the other classes they might depend on. 

### Overall time spent and Distribution
We, overall, have spent 120 or more hours on this project.

We consider that both team members had a 50/50 work distribution.
