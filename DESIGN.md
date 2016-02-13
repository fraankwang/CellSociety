CompSci 308: Cell Society Design
===================

> This is the link to the Design Description: [Plan - Cell Society](http://www.cs.duke.edu/courses/compsci308/spring16/assign/02_cellsociety/part1.php)

### Introduction
The goal of this project is to create a program that runs multiple different types of Cellular Automata and displays it graphically. Our program will be flexible enough to deal with all the CAs specified in the assignment description.  Our code should be able to add even more types of CAs if needed.

The basic architecture of our program is as follows. There's a UI that will allow you to load in an XML file. Once that is loaded in, depending on the type of CA the XML file describes, the program creates a grid filled with cells that follow the rules of CA.



### Overview
![we drew this on a white board](classes.jpg)

![we also drew this on the board](events.jpg)


The way we structured our program, Main simply launches the application and also allows you to read in an XML file or exit the program. When you load an XML file it will be parsed. After the XML file is parsed, the information is used to create a Game object. When the Game is initialized, a new Grid is made based on the which CA the XML file specifies. The specific Grid will change the UI with information relevant to it. The Grid updates by calling the step() function. The Grid class itself is an abstract super class and each specific Grid extends it. The way they implement step() is specific to the rules of the CA. The Grid is populated by Cell objects. There are two major Cell subclasses, SimpleCell and DataCell. SimpleCells simply exist in one of the states listed in the enumeration class State. DataCells on the other hand have persistent information that needs to be tracked.  


### User Interface
Users will load an XML file with the specifications for the CA. Once the file is loaded, they will be able to step through the CA or run it continuously and stop whenever they want. They can then exit that CA and load another XML file to run a different one. When a CA is chosen, the application will display all pertinent information as well as the visuals of the simulation. There will be buttons to step, start, and reset the CA. If an error occurs, an error window will pop-up giving the explanation for what happened.


![also on the whiteboard](ui.jpg)


### Design Details

#### Main
* initializes the screen to the splash along with the menu buttons
* parses the XML file and creates a new Game object with that information

#### Game
* Takes the parsed information and creates the correct type of Grid
* starts the game loop

#### Grid
* Abstract class that defines the actions almost all the Grid subclasses will need to take
* the step() method is abstract as that is where each of the subclasses will implement the logic necessary for the rules specified by each CA, i.e. Game of Life, Segregation, Predator/Pray, and Fire.
* if it is necessary for us to implement a new type of CA, we would just have to create a new subclass that overrides step() as appropriate
* contains a two dimensional array of Cells which represents the Grid which it initializes
* it then has to create the actual visuals by adding each Cell to the root

#### Cell
* Cell is an abstract class. Each Cell represents one spot in the Grid.
* any Cell can have a State. These States are enumerated in the Enumeration class State.
* There are two main types of cells:
##### SimpleCell
* A SimpleCell only has states that are subject to change
##### DataCell
* A DataCell has persistent information that must be kept track of when the Grid steps.


#### Use Cases

##### Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
    1. the GameOfLifeGrid class will call step()
    2. step() will iterate through the Grid, most likely with helper methods
    3. every Cell has a currentState and a nextState, so the nextState will be updated based on the rules and the Cell's surroundings
    4. then each Cell's currentState will be set equal to the nextState
    5. this allows us to update all of the Cells at once without having their behavior affected by another Cell updating before it

##### Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing


      1. Essentially the same as above, but the when setting the Cell's nextState we always will check if the its neighbors are in valid locations


##### Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically

    1. After the Grid steps, all the Cells should be updated to their nextState which includes a change in their visuals.


##### Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire

    1. When the Main method parses the XML file, it creates a Map whose key is a parameter and its value is the value of the parameter when parsed.
    2. The overriden step() method will then use that information to make sure the CA acts correctly.

##### Switch simulations: use the GUI to change the current simulation from Game of Life to Wator

    1. There will always be a menu at the top of the screen that allows someone to step, reset, and start the CA as well as load a new one.
    2. By clicking the new button, you can then load in a new XML file which will be parsed. A new Game object will be made with a WatorGrid instead of a GameOfLifeGrid.



### Design Considerations
#### Pros of our Design
* it's flexible in that multiple new CA types can be implemented with relative ease

#### Cons
* a lot of dependencies, like between Main, Game, and Grid

A point of discussion between the three of us was which class will be extended to differentiate between the different CAs. We decided the Grid is where everything should be different. No matter what, the Game will always do the same thing of building a Grid and running the game loop. The Grid itself behaves differently depending on the CA. This was inspired by the fact that the Wator grid exists as a toroid and must behave much differently from the other Grids. However this main difference is taken care of in the step() method.

### Team Responsibilities
We've decided to split the work into two parts. One person will be responsible for dealing with the interactions between and implementing the Game, Grid, and Cell class. The second half of the work will be split between two people. Each of those people will work on implementing the specific Grid types that follow rules of the CAs. They'll each do two each as there are four.
