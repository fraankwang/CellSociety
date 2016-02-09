/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package main;

import game.Game;
import view.CellSocietyViewer;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class of cell society. Sets up GUI
 *
 *
 */
public class Main extends Application {

    /**
     * Begins the game by creating the View which takes in the Game (Model) which
     * is created within the view.
     */
    public void start (Stage s) throws Exception {

    	Game myPrimaryGame = null;
    	CellSocietyViewer view = new CellSocietyViewer(s, myPrimaryGame);
    	view.display();
    	
    }

    public static void main (String[] args) {
        launch(args);
    }

}
