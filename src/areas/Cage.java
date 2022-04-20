package areas;

import animals.*;

import java.util.ArrayList;

public class Cage extends Habitats{

    private int capacity;
    private ArrayList<Animal> inhabitants = new ArrayList<>();

    public Cage(int capacity) {
        this.capacity = capacity;
        ArrayList<Integer> adjacentAreasID = new ArrayList<>();
    }

    @Override
    public ArrayList<Animal> getInhabitants() {
        return inhabitants;
    }

    public boolean isCageFull() {
        /** to check the cage is full, checks if the capacity
         * is greater than the number of inhabitants
         */
        if (capacity > inhabitants.size())
        {
            return false;
        }
        return true;
    }

    public void addInhabitant(Animal animal) {
        inhabitants.add(animal);
    }

    // check if cage inhabitants are compatible with the new animal
    public boolean areCageAnimalsCompatible(Animal animal) {
        for (Animal inhabitant : inhabitants) {
            // if any inhabitant is not compatible, the animal can't be added
            if (!inhabitant.isCompatibleWith(animal)) {
                return false;
            }
        }
        return true;
    }

}
