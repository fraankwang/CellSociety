/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package main;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Main class of cell society. Sets up GUI
 */
public class Main extends Application {

    /**
     * Begins the game by creating the View and Controller. MainView only has access
     * to the MainController, which contains the Game class which creates
     * the GridViews and Grids (Model).
     */
    @Override
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
