package areas;

import java.util.ArrayList;

/** this class is a superclass for all the areas
 * this allows the getAdjacentAreas method to be applied to non-habitat areas as well as habitats
 * since Entrance and PicnicArea cannot inherit from the Habitats class
 * also reduces code duplication
 */

public abstract class Areas implements IArea {

    // this ArrayList is used to keep track
    // of all the adjacent areas to the given area
    private ArrayList<Integer> adjacentAreasID = new ArrayList<>();

    // checks if given area is a habitat
    public boolean isAreaAHabitat(IArea area) {
        // this checks if the given area is an entrance or a picnic area
        // since these are the only non-habitat areas
        if (area instanceof areas.Entrance || area instanceof areas.PicnicArea) {
            return false;
        }
        return true;
    }

    /**
     * this method returns the IDs of all its neighbouring areas
     * @return an ArrayList of all the IDs of adjacent areas
     */
    public ArrayList<Integer> getAdjacentAreas() {
        return adjacentAreasID;
    }

}
