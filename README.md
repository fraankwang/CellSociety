# Cell Society Team 03
Duke CompSci 308 Cell Society Project

#### General
  * Names: Jeremy Schreck, Frank Wang, Madhav Kumar
  * Date started: Thursday, January 28
  * Date finished: Saturday, February 6 (Basic Implementation)
  * Hours worked: 80

#### Each person's role:
  * Frank: Setting up initial design and skeleton code, XML parsing and saving, hexagon/triangle/rectangle shapes, State enumerations and subclassing, XML file formatting, cell reshaping and resizing abilities, neighbor-type changing ability, main GridCell hierarchy (sprint 1/2), code clean up, and overall organization. 
  * Jeremy: Implementing Fire/Game of Life/Sugarscape/Foraging Ants simulations, primary interface extracting (Main to MainView and MainController, Grid to Grid (and subclasses of Grid) and GridView (and subclasses of GridView), GridCell hierarchy for Patch/Agent (sprint 3), Offset calculations, duplicated code removal.
  * Madhav: Implementing Segregation and Predator Prey simulations, created all grid-specific UI elements and parameter altering capabilities by extracting UIView class and subclasses, documenting initial design.

#### Resources used:
  * JavaFX online documentation
  * CS 308 readings
  * StackOverflow

#### Files used to start project
  * Main.java (in main package)

#### Files used to test project
  * All files with extension .xml

The following xml format should be used:

	```xml
	<root>
	<gameType>GameOfLife</gameType>
	<width>500</width>
	<height>500</height>
	<rows>5</rows>
	<columns>5</columns>
	<delay>150</delay>
	<initialStates>
		<row>000000</row>
		<row>000000</row>
		<row>000000</row>
		<row>000000</row>
		<row>000000</row>
	</initialStates>
	</root>
 	```
 	
Description of XML parameters:
  * gameType: type of simulation (String)
  * width: grid width (int)
  * height: grid height (int)
  * rows: number of rows (int)
  * columns: number of columns (int)
  * delay: amount of time (in milliseconds) between each step of the simulation (int)
  * initialStates: a Node element containing sub-elements of row values (String of numbers) numbers represent the following states
  	
  	* Game of Life
    	* 0 --> Dead
  		* 1 --> Alive
  	    
  	* Fire
  	    * 0 --> Empty
  	    * 1 --> Tree
  	    * 2 --> Burning
  	    * 3 --> Burned
  	* Segregation
  	  	* 0 --> Empty
  	  	* 1 --> Red
  	  	* 2 --> Blue
  	* Predator Prey
  	    * 0 --> Empty
  	    * 1 --> Shark
  	    * 2 --> Fish
  	* Sugarscape
  	  	* 0 --> Agent
  	  	* 1 --> Empty
  	  	* 2 --> Low-saturation sugar patch
  	  	* 3 --> Medium-saturation sugar patch
  	  	* 4 --> High-saturation sugar patch
  	  	* 5 --> Strong-saturation sugar patch
  	  	
#### Data or resources files required by the project 
  * resources.properties (variables should be self-explanatory)
	
#### Information about using the program
  * Run main.java. A window will pop up with a toolbar at the top. Click "New Game" and select an xml file. Once loaded, press "Start" to start the simulation. Press "Stop" at any point during the simulation to stop it. Press "Start" again to resume the simulation. Press "Step" to step one cycle through the simulation. Press "Reset" to reset the simulation. At any point, press "New Game" to select a new xml file and change the simulation. 
  * Additional toolbar features include:
  	* Animation speed slider: drag right to increase speed of Cellular Automata animation
  	* Cell shape selector: choose between Rectangle/Triangle/Hexagon (will not affect current game)
  	* Cell neighbor type: choose between Cardinal (compass directions), Diagonal, All (Cardinal and Diagonal) 
  	* + and - buttons to increase/decrease the size of the cells displayed (will not affect current game)
  * Button names and further descriptions can be found in the resources package in the resources.properties file. The default values are as follows:
  	* Cell size: 15
  	* Cell shape: Rectangle
  	* Edge type: Finite
  	* Outline: Yes
  	* Direction: Cardinal

#### Known bugs
  * Configuration changing abilities for sugarscape do not update in real time.
  * Incorrectly parsed XML files do not explain formatting error
  * Choosing between neighbor types may not work on the first selection (i.e. select Cardinal, something else, then Cardinal again for it to work)
  * Ant foraging is incomplete
  * Toggling states (mouse press) does not work for Predator Prey due to persistent data within the GridCell types.
  * Each time the program is run, the saved file counting mechanism resets, and previously saved files may be overwritten if not renamed
  * Infinite grid does not work but it is an option in the code.
  * XML parsing does not differentiate between randomization of initial states or given initial states. Therefore XML file must be written explicitly for each simulation.

#### Extra Features
  * Cell sizes and shapes can be changed dynamically and do not affect current simulation
  * Left pane includes user input abilities to change current simulation parameters
  * Grid of cells can be scrolled
  * Sugarscape preset 1
  * Current game state can be saved as an XML file into the same folder
  * Grid edge types can be changed from finite to toroidal.
  * User can press on each cell to toggle states and affect the simulation in real time.
  

#### Impressions/Suggestions
  * Overall we are very impressed by our code hierarchy and cohesiveness. Classes do exactly what they need to do, and most of the backend and frontend are separated. Code has been painstakingly vetted to ensure all toolbar buttons and most parameter-configuring functionalities do not break the program. As such, adding new functionalities required only changing code in relevant classes, allowing for lower coupling and dry-er code in general.
  * We would have liked to see completed implementations of the Foraging Ants and Slimemold simulations, an infinitely expanding grid, and a dynamically changing line chart that graphs populations over time. We believe that, given more time, we would be able to implement these without significantly changing our code hierarchy. We valued extraction of interfaces and minimizing duplicated code over adding novel implementations, and as such we focused on more robustly built code rather than fragile but flashy functionalities. 
  
