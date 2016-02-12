/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package main;

import controllers.MainController;
import javafx.application.Application;
import javafx.stage.Stage;
import views.MainView;

/**
 * Main class of cell society. Sets up GUI
 */
public class Main extends Application {

    /**
     * Begins the game by creating the View which takes in the Game (Model) which
     * is created within the view.
     */
    public void start (Stage s) throws Exception {
    	MainView view = new MainView(s);
    	MainController controller = new MainController(view);
    	view.setController(controller);
    	controller.start();
    }

    public static void main (String[] args) {
        launch(args);
    }

}
