# LPOO1617_T4G10 Final Project

### Members:

Rostyslav Khoptiy | ID: up201506219 | Email: up201506219@fe.up.pt

João Carlos Oliveira Lago | ID: up201504374 | Email: up201504374@fe.up.pt

### Setup
This project was developed in Android Studio with gradle for the dependencies, so that's the easiest way to compile and run this project:

1 - Download android studio.

2 - follow this [guide](https://github.com/libgdx/libgdx/wiki/Setting-up-your-Development-Environment-(Eclipse,-Intellij-IDEA,-NetBeans)#setting-up-android-studio) to setup the project.

3 - In andorid studio under Run -> Edit Configurations, select the '+' button and add Application (and DesktopLauncher as Main class) to build the project for desktop, the Android builder configuration should already be setup by the IDE.

3.1 - To run tests select Run -> Edit Configurations, select the '+' button and add Android JUnit with the 'All in package' option and selecting the 'tests' package of the project.

4 - To Run the Desktop simply Run the application in the IDE, for android you can run it in the provided Emulator, or connect an andorid device and you can run the apk there.


## Architecture Design

### Package and Class diagram

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/UML/classUML.png)

### State Machine of game

![alt tag](https://github.com/joaolago1996/LPOO1617_T4G10/blob/master/UML/stateUML.png)

### Overarching Design Decisions
On this project we decided to separate the logic from the specific android implementation, so the gameLogic package should contain all the classes necessary to run the application without it running specifically in android or any other LIBGDX supported machine.

Throughout the project we tried to follow the SOLID principles, that is, mainly to achieve a low coupling between the different classes and to achieve a separation of interfaces in order to gain a code that is friendly to change and maintenance.

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



### Major Difficulties
* One major difficulty was finding the right level of abstraction and the right separation between the modules in order to achieve a low level of coupling between the classes. 
* Creating Tests that tested Modules independently of others was difficult, since some classes depended on others, which we overcame by created simple mock classes (and using EasyMock)

### Lessons Learned 
* The value of low coupling in code
* EasyMock for mocking classes
* Developing applications for Android
* How to use LIBGDX to develop applications
* Improved knoweledge of java features.

### Overall time spent and Distribution
We overall spent 100 or more hours on this project.
We consider that both team members had a 50/50 work distribution.


# User Manual

## Using the GUI

// Screenshots and text here//

## Playing the Survival Game Mode

//screenshot here //

This game mode is based on the player avoiding the enemies that are spawned (ground and flying based enemies).
The player has only one life and the score is the time since the begging of the game mode.
The player can move the character to the left and right and make him jump.

On Desktop:
* The left and right arrow keys make the character move left and right
* The up arrow key or a left mouse click makes the hero jump. Pressing these for longer, makes the hero jump higher.

On Android:
* Tilting the phone to the left or right makes the character move left and right, with the degree of inclination setting the speed at which the character moves.
* Touching the screen makes the character jump. Pressing it for longer, makes the hero jump higher.



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
