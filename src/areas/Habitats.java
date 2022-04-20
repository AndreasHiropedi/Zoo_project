package areas;

import animals.Animal;

import java.util.ArrayList;

/** this class is a superclass for all the animal habitats
 * this reduces code duplication in the habitat subclasses (aquarium, cage, enclosure)
 * has a size for a given habitat
 * has a number of inhabitants for a given habitat
 * also has a list of all animals living in that habitat
 */
public abstract class Habitats extends Areas {

    // checks if the habitat is full
    public boolean isHabitatFull(IArea area) {
        // checks the habitat type and calls the appropriate method to check if full
        if (area instanceof Enclosure && ((Enclosure) area).isEnclosureFull())
        {
            return true;
        }
        else if (area instanceof Cage && ((Cage) area).isCageFull())
        {
            return true;
        }
        else if (area instanceof Aquarium && ((Aquarium) area).isAquariumFull())
        {
            return true;
        }
        return false;
    }

    // returns a list of all the inhabitants
    public abstract ArrayList<Animal> getInhabitants();


    /** this method adds an inhabitant to a given area
     * @param animal the animal to be added to the area
     * @param area the given habitat to add the animal to
     */
    public void addInhabitant(Animal animal, IArea area)
    {
        if (area instanceof Aquarium)
        {
            ((Aquarium) area).addInhabitant(animal);
        }
        else if (area instanceof Enclosure)
        {
            ((Enclosure) area).addInhabitant(animal);
        }
        else if (area instanceof Cage)
        {
            ((Cage) area).addInhabitant(animal);
        }
    }


}
