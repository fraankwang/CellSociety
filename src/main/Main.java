/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package main;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import constants.Constants;
import game.Game;
import input.Parser;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import view.CellSocietyViewer;


/**
 * Main class of cell society. Sets up GUI
 *
 *
 */
public class Main extends Application {

    /**
     * 
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
