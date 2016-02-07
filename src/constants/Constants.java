/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package constants;

import java.awt.Dimension;
import java.util.ResourceBundle;


/**
 * Constants class
 *
 */

public class Constants {
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    public static final String DEFAULT_RESOURCE_FILE = "resources";
    public static final ResourceBundle RESOURCES =
            ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_RESOURCE_FILE);
    public static final Dimension DEFAULT_WINDOW_SIZE =
            new Dimension(Integer.parseInt(RESOURCES.getString("defaultWindowWidth")),
                          Integer.parseInt(RESOURCES.getString("defaultWindowHeight")));
    public static final int TOOLBAR_HEIGHT = Integer.parseInt(RESOURCES.getString("toolbarHeight"));

    /**
     * Private constructor to prevent utility class instantiation
     */
    private Constants () {

    }
}
