package animals;

import areas.*;


/**
 * You can modify the contents of this class, but you cannot:
 * - change the name, parameters or return types of provided methods
 * - remove it entirely
 */
public abstract class Animal
{

	/**
	 * @return Returns this animal's given name.
	 */
	public abstract String getNickname();
	
	/**
	 * Check whether two animals can live together.
	 * @param animal The animal for which to check compatibility with this animal.
	 * @return Returns true for compatible animals and false otherwise.
	 */
	public abstract boolean isCompatibleWith(Animal animal);

	// this method checks the area is the correct habitat for the animal
	public boolean isCorrectHabitat(IArea area, Animal animal) {
		/** only checks the animals that belong to a certain habitat
		 *  e.g for Enclosures, only checks if the animal is a lion, zebra or gazelle to return true
		 */
		if ( (animal instanceof Zebra || animal instanceof Lion || animal instanceof Gazelle) && area instanceof Enclosure)
		{
			return true;
		}
		else if ((animal instanceof Shark || animal instanceof Starfish || animal instanceof Seal) && area instanceof Aquarium)
		{
			return true;
		}
		else if ((animal instanceof Parrot || animal instanceof Buzzard) && area instanceof Cage)
		{
			return true;
		}
		return false;
	}

	/**
	 * This method checks the compatibility of a given animal
	 * with the inhabitants of a given habitat
	 * @param area the selected habitat
	 * @param animal the given animal to check compatibility with
	 * @return true if the animal is compatible with all inhabitants
	 */
	public boolean areAnimalsCompatible(IArea area, Animal animal) {
		/** first checks the habitat type, then calls the appropriate method
		 * in the respective class to check for animal compatibility
		 */
		if (area instanceof Aquarium && ((Aquarium) area).areAquariumAnimalsCompatible(animal))
		{
			return true;
		}
		else if (area instanceof Enclosure && ((Enclosure) area).areEnclosureAnimalsCompatible(animal))
		{
			return true;
		}
		else if (area instanceof Cage && ((Cage) area).areCageAnimalsCompatible(animal))
		{
			return true;
		}
		return false;
	}

}
