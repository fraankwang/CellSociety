package cellsociety_team03;

import java.awt.Dimension;
import java.util.ResourceBundle;

public class Constants {
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    public static final String DEFAULT_RESOURCE_FILE = "resources";
    public static final ResourceBundle RESOURCES = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_RESOURCE_FILE);
    public static final Dimension DEFAULT_SIZE = new Dimension(Integer.parseInt(RESOURCES.getString("defaultWindowWidth")), Integer.parseInt(RESOURCES.getString("defaultWindowHeight")));
    public static final int TOOLBAR_HEIGHT = Integer.parseInt(RESOURCES.getString("toolbarHeight"));
}
