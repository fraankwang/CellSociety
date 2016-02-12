/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package constants;

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
    
    
    /**
     * Private constructor to prevent utility class instantiation
     */
    private Constants () {

    }
}
