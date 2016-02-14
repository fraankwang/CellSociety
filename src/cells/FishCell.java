/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import constants.Location;
import states.State;


/**
 * The class for Fish
 */
public class FishCell extends DataCell {
    private int fishBreedTime;
    private int fishCurrentBreedTime;

    public FishCell (State currentState,
                     int row,
                     int col,
                     int breed) {
        super(currentState, row, col);
        fishBreedTime = breed;
        fishCurrentBreedTime = fishBreedTime;
    }

    public FishCell (FishCell fish, Location location) {
        super(fish.getMyCurrentState(), location.getRow(), location.getCol());

        fishCurrentBreedTime = fish.getTimeUntilBreed();
        fishBreedTime = fish.getBreedTime();
    }

    @Override
    public void update () {
        if (fishCurrentBreedTime != 0) {
            fishCurrentBreedTime--;
        }
        else {
            fishCurrentBreedTime = fishBreedTime;
        }

    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public int getTimeUntilBreed () {
        return fishCurrentBreedTime;
    }

    private int getBreedTime () {
        return fishBreedTime;
    }

    public void setBreedTime (int setTime) {
        fishBreedTime = setTime;
        fishCurrentBreedTime = setTime;
    }

}
