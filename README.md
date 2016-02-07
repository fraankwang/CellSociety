# cellsociety
Duke CompSci 308 Cell Society Project

#### General
  * Names: Jeremy Schreck, Frank Wang, Madhav Kumar
  * Date started: Thursday, January 28
  * Date finished: Saturday, February 6 (Basic Implementation)
  * Hours worked: 35? (we'll be more specific in our individual sprint assessments)

#### Each person's role:
  * All: big picture design (the Plan), refactor and clean up code
  * Frank: Set up the GUI and xml parser 
  * Jeremy: Implement Grid superclass and Fire/Game of Life simulations
  * Madhav: Implement Segregation/Predator-prey simulations

#### Resources used:
  * JavaFX online documentation
  * CS 308 readings

#### Files used to start project
  * Main.java (in main package)

#### Files used to test project
  * All files with extension .xml

The following xml format should be used:

	```xml
	<root>
	<gameType>GameOfLife</gameType>
	<width>100</width>
	<height>100</height>
	<rows>5</rows>
	<columns>5</columns>
	<delay>150</delay>
	<initialStates>00000,00100,01110,00100,00000</initialStates>
	</root>
 	```
Description
  * gameType: type of simulation
  * width: grid width (int)
  * height: grid height (int)
  * rows: number of rows
  * columns: number of columns
  * delay: amount of time (in milliseconds) between each step of the simulation
  * initialStates: a comma-separated list of initial states of each cell. each comma indicates a new row, and each character within two commas is a different column
  	* i.e. 000,110,001 in GameOfLife means the following
  	  *row 1: all 0s
  	  *row 2: col 0 and 1 are 1, col 2 is 0
  	  *row 3: col 0 and 1 are 0, col 2 is 1
  	* numbers represent the following states
  	  * Game of Life
  	    * 0 --> Dead
  	    * 1 --> Alive
  	  * Fire
  	    * 0 --> Empty
  	    * 1 --> Tree
  	    * 2 --> Burning
  	  * Segregation
  	  	* 0 --> Empty
  	  	* 1 --> Race 1
  	  	* 2 --> Race 1
  	  * Predator-Prey
  	    * 0 --> Empty
  	    * 1 --> Shark
  	    * 2 --> Fish

#### Data or resources files required by the project 
  * resources.properties (variables should be self-explanatory)
	
#### Information about using the program
  * Run main.java. A window will pop up with a toolbar at the top. Click "New Game" and select an xml file that specifies game paramters in the appropriate format (see above). Once loaded, press "Start" to start the simulation. Press "Stop" at any point during the simulation to stop it. Press "Start" again to resume the simulation. Press "Step" to step one cycle through the simulation. Press "Reset" to reset the simulation. At any point, press "New Game" to select a new xml file and change the simulation.

#### Known bugs
  * If the xml gridsize parameter is bigger than the screen size, no error message is given.

#### Extra Features
  * If the xml gridsize parameter is bigger than the default window size, the window will grow to accommodate the extra needed space. 

#### Impressions/Suggestions
  * We think we will have a better idea of how to answer this question after finishing the complete implementation next week.
