package zoo;

import animals.*;
import areas.*;
import dataStructures.CashCount;
import dataStructures.ICashCount;

import java.util.ArrayList;
import java.util.HashMap;


public class Zoo implements IZoo {

    /**
    the hashmap is used to keep track of all the added areas and their respective IDs
    the entrance was already added
    since there can only be one entrance and to avoid having no entrance
     */
    private HashMap<Integer, IArea> zooAreas = new HashMap<>() {{ put(0, new areas.Entrance());}};
    /** this variable is used to generate a unique ID for every area of the zoo
     * exception to the entrance, which has an ID of 0 and there is only one
     */
    private int areaID = 0;
    /**
     * this hashmap stores all the area IDs
     * of the areas that can be reached from any certain area
     * uses the area ID as a key, with a corresponding list of all the IDs of reachable areas
     * from that original area
     */
    private HashMap<Integer, ArrayList<Integer>> areaConnections = new HashMap<>() {{ put(0, new ArrayList<>());}};
    /**
     * this string is used to store the entrance fee
     * accommodates for the fact that the fee can't be a double
     * but must look like a double (should look like e.g 17.50 but can't be a double)
     */
    private int entranceFee;
    /** this object variable is used to keep track of the cash supply
     * in the zoo's ticket machine
     * used to stock up and retrieve the cash supply
     */
    private CashCount ticketMachine = new CashCount();

    // Constructor
    public Zoo() {

    }


    // ---------------- BASIC ----------------


    /**
     * the method adds an area to the zoo
     * and gives it a corresponding unique ID
     * @param area The area to be added.
     * @return the ID of the area
     */
    public int addArea(IArea area) {
        // this is done to ensure no other entrance can be added
        if (area instanceof areas.Entrance)
        {
            System.out.println("Entrance already exists.");
            return 0;
        }
        // if the area already exists, reject adding it again
        else if (zooAreas.containsValue(area))
        {
            System.out.println("Area already exists");
            return -1;
        }
        // if the area is not an entrance, and does not exist already
        // then it can be added as normal
        areaID++;
        zooAreas.put(areaID, area);
        // also, the area is added to the areaConnections, with an empty list of connections
        // which can later be updated based on the paths that are unblocked
        areaConnections.put(areaID, new ArrayList<>());
        return areaID;
    }


    /**
     * this method removes an area from the zoo
     * @param areaId The ID of the area to be removed.
     */
    public void removeArea(int areaId) {
        // this ensures that the entrance cannot be removed
        if (areaId == 0)
        {
            System.out.println("Cannot remove the entrance.");
        }
        // if the area ID is not the one for an entrance, the area is discarded
        // and all its connections are removed
        else
        {
            zooAreas.remove(areaId);
            areaConnections.remove(areaId);
        }
    }


    /** this method is used to retrieve an area of the zoo by its ID
     * @param areaId The ID of the area to be fetched.
     * @return the area matching the given ID
     */
    public IArea getArea(int areaId) {
        return zooAreas.get(areaId);
    }


    /**
     * this method adds an animal to a certain area
     * @param areaId The ID of the area the animal is to be added to.
     * @param animal The animal to be added.
     * @return a byte code to confirm whether the animal was successfully added or not
     */
    public byte addAnimal(int areaId, Animal animal) {
        IArea selectedArea = zooAreas.get(areaId);
        // first checks if the area is a habitat
        if (!((areas.Areas) selectedArea).isAreaAHabitat(selectedArea))
        {
            return Codes.NOT_A_HABITAT;
        }
        // then if the area is the right habitat for the animal
        else if (!animal.isCorrectHabitat(selectedArea, animal))
        {
            return Codes.WRONG_HABITAT;
        }
        // then if the habitat has space for the animal
        // this already assumes the area is a habitat (and casts the area to type Habitats)
        // since it would have failed the previous tests otherwise
        else if (((Habitats) selectedArea).isHabitatFull(selectedArea))
        {
            return Codes.HABITAT_FULL;
        }
        // then if the animals already in the habitat are compatible
        // with the animal to be added
        else if (!animal.areAnimalsCompatible(selectedArea, animal))
        {
            return Codes.INCOMPATIBLE_INHABITANTS;
        }
        ((Habitats) selectedArea).addInhabitant(animal, selectedArea);
        return Codes.ANIMAL_ADDED;
    }


    // ---------------- INTERMEDIATE ----------------


    /**
     * Allows visitors to move between areas in the given direction (from -> to).
     * @param fromAreaId The ID of the area from which the destination is to be accessible.
     * @param toAreaId The ID of the destination area.
     */
    public void connectAreas(int fromAreaId, int toAreaId) {
        // add the toAreaID area to the list of connections for the fromAreaID area
        areaConnections.get(fromAreaId).add(toAreaId);
        // also add the toAreaID area to the list of adjacent areas of the fromAreaID area
        zooAreas.get(fromAreaId).getAdjacentAreas().add(toAreaId);
    }


    /**
     * Checks if the given path obeys the one-way system.
     * @param areaIds The path is provided as a list of area IDs. It starts with the area ID at index 0.
     * @return Returns true iff visitors are allowed to visit the areas in the order given by the passed in list.
     */
    public boolean isPathAllowed(ArrayList<Integer> areaIds) {
        // if the length of the path is one, then the path is valid
        if (areaIds.size() == 1)
        {
            return true;
        }
        // this variable keeps track of how many areas are connected
        // used to determine if the whole path is valid, or just part of it
        int countConnectedAreas = 0;
        for (int areasCounter = 0; areasCounter < areaIds.size() - 1; areasCounter++) {
            // for each area on the given path, get all the reachable areas
            ArrayList<Integer> adjacentAreasIds = areaConnections.get(areaIds.get(areasCounter));
            for (Integer adjacentAreasId : adjacentAreasIds) {
                // and check if the area ID of the next area on the given path
                // matches any of the IDs of the reachable areas
                if (areaIds.get(areasCounter + 1).equals(adjacentAreasId)) {
                    countConnectedAreas++;
                }
            }
        }
        // return true only if every area on the path can be reached from the previous area
        if (countConnectedAreas == areaIds.size() - 1)
        {
            return true;
        }
        return false;
    }


    /**
     * Visits the areas in the specified order and records the names of all animals seen.
     * @param areaIdsVisited Areas IDs in the order they were visited.
     * @return Returns a list of the names of all animals seen during the visit in the order they were seen.
     */
    public ArrayList<String> visit(ArrayList<Integer> areaIdsVisited) {
        // check if the one-way system is broken, and the path is invalid
        if (!isPathAllowed(areaIdsVisited))
        {
            return null;
        }
        else
        {
            // keeps track of all the animals seen, across all habitats
            ArrayList<String> animalsSeen = new ArrayList<>();
            for (Integer integer : areaIdsVisited) {
                // this variable is used to make the code neater and more readable
                IArea currentArea = zooAreas.get(integer);
                // check if the area is a habitat, since if it isn't, no animals can be recorded
                if (((Areas) currentArea).isAreaAHabitat(currentArea)) {
                    // add all the inhabitants' names of the given habitat to the animalsSeen list
                    ArrayList<Animal> inhabitants = ((Habitats) currentArea).getInhabitants();
                    for (Animal inhabitant : inhabitants) {
                        animalsSeen.add(inhabitant.getNickname());
                    }
                }
            }
            return animalsSeen;
        }
    }


    /**
     * This method is used to find all the areas that can't be reached
     * from the entrance of the zoo
     * @return a list of the area IDs of all the unreachable areas from the entrance
     */
    public ArrayList<Integer> findUnreachableAreas() {
        Integer[] allAreaIds = zooAreas.keySet().toArray(new Integer[0]);
        // this list keeps track of all unreachable area IDs
        ArrayList<Integer> allUnreachableAreaIds = new ArrayList<>();
        // this hashmap is used to keep track of whether each area from the zoo
        // can be reached from the entrance or not
        HashMap<Integer, Boolean> reachableAreas = new HashMap<>();
        // entrance is set to true, since the entrance can always be reached
        reachableAreas.put(0, true);
        // all the default values are false (apart from the entrance), before any checks are performed
        for (int currentAreaId = 1; currentAreaId < allAreaIds.length; currentAreaId++) {
            reachableAreas.put(allAreaIds[currentAreaId], false);
        }
        // perform the necessary checks, and eliminate all the reachable areas
        addUnreachableAreas(0, new ArrayList<>(), reachableAreas);
        // based on the result of the addUnreachableAreas function, the list of unreachable area IDs is updated
        for (int givenAreaID : reachableAreas.keySet()) {
            // if the value for the specific ID is still false, then the area is unreachable
            // and is added to the unreachable list
            if (!reachableAreas.get(givenAreaID))
            {
                allUnreachableAreaIds.add(givenAreaID);
            }
        }
        return allUnreachableAreaIds;
    }


    /**
     * This is a helper method for the findUnreachableAreas method
     * checks whether all the areas in the zoo are connected to the entrance or not
     * and hence can be reached from the entrance or not
     * @param areaId          the ID of the current area being checked
     * @param checkedAreas  List that keeps track of all areas that have been checked
     * @param reachableAreas HashMap containing all areas with corresponding boolean values
     *                       that indicate whether or not the area can be reached from the entrance
     */
    private void addUnreachableAreas(int areaId, ArrayList<Integer> checkedAreas, HashMap<Integer, Boolean> reachableAreas) {
        // retrieve all the adjacent areas for the specified areaID
        ArrayList<Integer> adjacentAreas = zooAreas.get(areaId).getAdjacentAreas();
        // check if the specified area has adjacent areas
        if (adjacentAreas.size() > 0)
        {
            for (int areaIdCounter : adjacentAreas) {
                // if it does, set all the adjacent areas to true, since they can be reached from the entrance
                reachableAreas.put(areaIdCounter, true);
                // this ensures that no duplicates are added to the list
                if (checkedAreas.contains(areaIdCounter))
                {
                    continue;
                }
                // if it isn't already in the list, then add it to the checked areas list
                checkedAreas.add(areaIdCounter);
                // repeat this until no more adjacent areas are found
                addUnreachableAreas(areaIdCounter, checkedAreas, reachableAreas);
            }
        }
    }


    // ---------------- ADVANCED ----------------


    /**
     * Sets a new ticket price in pounds and pence.
     * @param pounds The first part of the cost before the point e.g. 17 for a ticket that costs £17.50
     * @param pence The second part of the cost after the point e.g. 50 for a ticket that costs £17.50
     */
    public void setEntranceFee(int pounds, int pence) {
        entranceFee = pounds * 100 + pence;
    }


    /**
     * Stocks the ticket machine with the provided pool of cash.
     * @param coins The number of notes and coins of different denominations available.
     */
    public void setCashSupply(ICashCount coins) {
        ticketMachine.setNrNotes_20pounds(coins.getNrNotes_20pounds());
        ticketMachine.setNrNotes_10pounds(coins.getNrNotes_10pounds());
        ticketMachine.setNrNotes_5pounds(coins.getNrNotes_5pounds());
        ticketMachine.setNrCoins_2pounds(coins.getNrCoins_2pounds());
        ticketMachine.setNrCoins_1pound(coins.getNrCoins_1pound());
        ticketMachine.setNrCoins_50p(coins.getNrCoins_50p());
        ticketMachine.setNrCoins_20p(coins.getNrCoins_20p());
        ticketMachine.setNrCoins_10p(coins.getNrCoins_10p());
    }


    /**
     * Used to inspect the ticket machine's currently available pool of cash.
     * @return The amount of each note and coin currently in the machine.
     */
    public ICashCount getCashSupply() {
        return ticketMachine;
    }


    /**
     * Takes an amount of cash inserted into the ticket machine and returns the appropriate change
     * (if any) after deducting the amount of the entrance fee as set by @setEntranceFee.
     * @param cashInserted The notes and coins inserted by the user buying a ticket.
     * @return The change returned to the user (see assignment instructions for precise specification).
     */
    public ICashCount payEntranceFee(ICashCount cashInserted) {
        // calculate the total cash inserted and the total stock of the ticket machine
        int totalInserted = calculateTotalInserted(cashInserted);
        int totalCashLeft = calculateTotalCashLeft();
        // check if the user payed enough first
        if (entranceFee > totalInserted)
        {
            return cashInserted;
        }
        // then check if the user payed the exact amount
        else if (entranceFee == totalInserted)
        {
            addCashSupply(cashInserted);
            return ((CashCount) cashInserted).setEmptyCashCount(cashInserted);
        }
        int paymentDifference = totalInserted - entranceFee;
        // check if the ticket machine has enough stocks to pay the full change
        if (paymentDifference > totalCashLeft)
        {
                return cashInserted;
        }
        // if the machine has enough stock, and the amount payed is more than the entrance fee
        // calculate the appropriate change to be returned based on the available denominations
        addCashSupply(cashInserted);
        ICashCount change = calculateChange(paymentDifference);
        return change;
    }


    /**
     * This method is used to calculate the total amount of cash in pennies
     * of the cash inserted into the machine
     * @param cashInserted the amount of cash inserted as an object
     * @return an integer value of the cash inserted
     */
    private int calculateTotalInserted(ICashCount cashInserted) {
        // calculate the total cash inserted in pence
        int totalNotesInserted = cashInserted.getNrNotes_20pounds() * 2000 + cashInserted.getNrNotes_10pounds() * 1000 + cashInserted.getNrNotes_5pounds() * 500;
        int totalCoinsInserted = cashInserted.getNrCoins_2pounds() * 200 + cashInserted.getNrCoins_1pound() * 100 + cashInserted.getNrCoins_50p() * 50 + cashInserted.getNrCoins_20p() * 20 + cashInserted.getNrCoins_10p() * 10;
        int totalInserted = totalNotesInserted + totalCoinsInserted;
        return totalInserted;
    }


    // this method stocks up the cash supply with the additional cash inserted
    private void addCashSupply(ICashCount cashInserted) {
        ticketMachine.setNrNotes_20pounds(cashInserted.getNrNotes_20pounds() + ticketMachine.getNrNotes_20pounds());
        ticketMachine.setNrNotes_10pounds(cashInserted.getNrNotes_10pounds() + ticketMachine.getNrNotes_10pounds());
        ticketMachine.setNrNotes_5pounds(cashInserted.getNrNotes_5pounds() + ticketMachine.getNrNotes_5pounds());
        ticketMachine.setNrCoins_2pounds(cashInserted.getNrCoins_2pounds() + ticketMachine.getNrCoins_2pounds());
        ticketMachine.setNrCoins_1pound(cashInserted.getNrCoins_1pound() + ticketMachine.getNrCoins_1pound());
        ticketMachine.setNrCoins_50p(cashInserted.getNrCoins_50p() + ticketMachine.getNrCoins_50p());
        ticketMachine.setNrCoins_20p(cashInserted.getNrCoins_20p() + ticketMachine.getNrCoins_20p());
        ticketMachine.setNrCoins_10p(cashInserted.getNrCoins_10p() + ticketMachine.getNrCoins_10p());
    }


    /**
     * This method is used to calculate the total amount of cash in pennies
     * that is currently available in the ticket machine
     * used to check if change can be given or the amount inserted is given back
     * @return an integer value of the total cash left
     */
    private int calculateTotalCashLeft() {
        // calculate the total cash left in the ticket machine
        int totalNotesLeft = ticketMachine.getNrNotes_20pounds() * 2000 + ticketMachine.getNrNotes_10pounds() * 1000 + ticketMachine.getNrNotes_5pounds() * 500;
        int totalCoinsLeft = ticketMachine.getNrCoins_2pounds() * 200 + ticketMachine.getNrCoins_1pound() * 100 + ticketMachine.getNrCoins_50p() * 50 + ticketMachine.getNrCoins_20p() * 20 + ticketMachine.getNrCoins_10p() * 10;
        int totalCashLeft = totalNotesLeft + totalCoinsLeft;
        return totalCashLeft;
    }

    /** this method calculates the correct change to be returned
     * @param paymentDifference the difference between the cash inserted and the entrance fee
     * @return the correct change, using all the correct notes available, and as few of them as possible
     */
    private ICashCount calculateChange(int paymentDifference) {
        ICashCount change = new CashCount();
        // append the correct number of denominations to the change object
        int paymentDifferenceAfter20PoundNotes = calculateChange20PoundNotes(paymentDifference, change);
        int paymentDifferenceAfter10PoundNotes = calculateChange10PoundNotes(paymentDifferenceAfter20PoundNotes, change);
        int paymentDifferenceAfter5PoundNotes = calculateChange5PoundNotes(paymentDifferenceAfter10PoundNotes, change);
        int paymentDifferenceAfter2PoundCoins = calculateChange2PoundCoins(paymentDifferenceAfter5PoundNotes, change);
        int paymentAfter1PoundCoins = calculateChange1PoundCoins(paymentDifferenceAfter2PoundCoins, change);
        int paymentAfter50PCoins = calculateChange50PCoins(paymentAfter1PoundCoins, change);
        int paymentAfter20PCoins = calculateChange20PCoins(paymentAfter50PCoins, change);
        int paymentAfter10PCoins = calculateChange10PCoins(paymentAfter20PCoins, change);
        return change;
    }


    // ---------------- THE FOLLOWING METHODS CALCULATE THE INDIVIDUAL CHANGE PER CURRENCY ----------------

    /**
     * These methods ensure the change is returned using as few notes and coins as possible
     * @param paymentDifference the difference between the entrance fee and the cash inserted
     * @param change the change to be returned by the ticket machine
     * @return an integer value of the amount of cash not accounted for per currency
     * that is still left as change to be returned
     * (the remaining change still owed by the ticket machine that requires the use of smaller increments)
     */

    // ---- ALL METHODS USE THE SAME ALGORITHM EXPLAINED IN THE calculateChange20PoundNotes() METHOD ----


    private int calculateChange20PoundNotes(int paymentDifference, ICashCount change) {
        // first check if the remainder is large enough to use the specific increment
        // and if the specific increment is available
        if (paymentDifference / 2000 > 0 && ticketMachine.has20PoundNotes())
        {
            int change20PoundNotes = paymentDifference / 2000;
            int total20PoundNotes = ticketMachine.getNrNotes_20pounds();
            // based on whether the machine has enough of the specific increment
            // it either returns the necessary amount (if it has enough)
            // or everything it has (if it doesn't have enough)
            if (total20PoundNotes > change20PoundNotes)
            {
                change.setNrNotes_20pounds(change20PoundNotes);
                paymentDifference -= 2000 * change20PoundNotes;
                ticketMachine.setNrNotes_20pounds(total20PoundNotes - change20PoundNotes);
            }
            else
            {
                change.setNrNotes_20pounds(total20PoundNotes);
                paymentDifference -= 2000 * total20PoundNotes;
                ticketMachine.setNrNotes_20pounds(0);
            }
            return paymentDifference;
        }
        // if either of the previous conditions fail, then return the original payment difference
        return paymentDifference;
    }


    private int calculateChange10PoundNotes(int paymentDifference, ICashCount change) {
        if (paymentDifference / 1000 > 0 && ticketMachine.has10PoundNotes())
        {
            int change10PoundNotes = paymentDifference / 1000;
            int total10PoundNotes = ticketMachine.getNrNotes_10pounds();
            if (total10PoundNotes > change10PoundNotes)
            {
                change.setNrNotes_10pounds(change10PoundNotes);
                paymentDifference -= 1000 * change10PoundNotes;
                ticketMachine.setNrNotes_10pounds(total10PoundNotes - change10PoundNotes);
            }
            else
            {
                change.setNrNotes_10pounds(total10PoundNotes);
                paymentDifference -= 1000 * total10PoundNotes;
                ticketMachine.setNrNotes_10pounds(0);
            }
            return paymentDifference;
        }
        return paymentDifference;
    }


    private int calculateChange5PoundNotes(int paymentDifference, ICashCount change) {
        if (paymentDifference / 500 > 0 && ticketMachine.has5PoundNotes())
        {
            int change5PoundNotes = paymentDifference / 500;
            int total5PoundNotes = ticketMachine.getNrNotes_5pounds();
            if (total5PoundNotes > change5PoundNotes)
            {
                change.setNrNotes_5pounds(change5PoundNotes);
                paymentDifference -= 500 * change5PoundNotes;
                ticketMachine.setNrNotes_5pounds(total5PoundNotes - change5PoundNotes);
            }
            else
            {
                change.setNrNotes_5pounds(total5PoundNotes);
                paymentDifference -= 500 * total5PoundNotes;
                ticketMachine.setNrNotes_5pounds(0);
            }
            return paymentDifference;
        }
        return paymentDifference;
    }


    private int calculateChange2PoundCoins(int paymentDifference, ICashCount change) {
        if (paymentDifference / 200 > 0 && ticketMachine.has2PoundCoins())
        {
            int change2PoundCoins = paymentDifference / 200;
            int total2PoundCoins = ticketMachine.getNrCoins_2pounds();
            if (total2PoundCoins > change2PoundCoins)
            {
                change.setNrCoins_2pounds(change2PoundCoins);
                paymentDifference -= 200 * change2PoundCoins;
                ticketMachine.setNrCoins_2pounds(total2PoundCoins - change2PoundCoins);
            }
            else
            {
                change.setNrCoins_2pounds(total2PoundCoins);
                paymentDifference -= 200 * total2PoundCoins;
                ticketMachine.setNrCoins_2pounds(0);
            }
            return paymentDifference;
        }
        return paymentDifference;
    }


    private int calculateChange1PoundCoins(int paymentDifference, ICashCount change) {
        if (paymentDifference / 100 > 0 && ticketMachine.has1PoundCoins())
        {
            int change1PoundCoins = paymentDifference / 100;
            int total1PoundCoins = ticketMachine.getNrCoins_1pound();
            if (total1PoundCoins > change1PoundCoins)
            {
                change.setNrCoins_1pound(change1PoundCoins);
                paymentDifference -= 100 * change1PoundCoins;
                ticketMachine.setNrCoins_1pound(total1PoundCoins - change1PoundCoins);
            }
            else
            {
                change.setNrCoins_1pound(total1PoundCoins);
                paymentDifference -= 100 * total1PoundCoins;
                ticketMachine.setNrCoins_1pound(0);
            }
            return paymentDifference;
        }
        return paymentDifference;
    }


    private int calculateChange50PCoins(int paymentDifference, ICashCount change) {
        if (paymentDifference / 50 > 0 && ticketMachine.has50PCoins())
        {
            int change50PCoins = paymentDifference / 50;
            int total50PCoins = ticketMachine.getNrCoins_50p();
            if (total50PCoins > change50PCoins)
            {
                change.setNrCoins_50p(change50PCoins);
                paymentDifference -= 50 * change50PCoins;
                ticketMachine.setNrCoins_50p(total50PCoins - change50PCoins);
            }
            else
            {
                change.setNrCoins_50p(total50PCoins);
                paymentDifference -= 50 * total50PCoins;
                ticketMachine.setNrCoins_50p(0);
            }
            return paymentDifference;
        }
        return paymentDifference;
    }


    private int calculateChange20PCoins(int paymentDifference, ICashCount change) {
        if (paymentDifference / 20 > 0 && ticketMachine.has20PCoins())
        {
            int change20PCoins = paymentDifference / 20;
            int total20PCoins = ticketMachine.getNrCoins_20p();
            if (total20PCoins > change20PCoins)
            {
                change.setNrCoins_20p(change20PCoins);
                paymentDifference -= 20 * change20PCoins;
                ticketMachine.setNrCoins_20p(total20PCoins - change20PCoins);
            }
            else
            {
                change.setNrCoins_20p(total20PCoins);
                paymentDifference -= 20 * total20PCoins;
                ticketMachine.setNrCoins_20p(0);
            }
            return paymentDifference;
        }
        return paymentDifference;
    }


    private int calculateChange10PCoins(int paymentDifference, ICashCount change) {
        if (paymentDifference / 10 > 0 && ticketMachine.has10PCoins())
        {
            int change10PCoins = paymentDifference / 10;
            int total10PCoins = ticketMachine.getNrCoins_10p();
            if (total10PCoins > change10PCoins)
            {
                change.setNrCoins_10p(change10PCoins);
                paymentDifference -= 10 * change10PCoins;
                ticketMachine.setNrCoins_10p(total10PCoins - change10PCoins);
            }
            else
            {
                change.setNrCoins_10p(total10PCoins);
                paymentDifference -= 10 * total10PCoins;
                ticketMachine.setNrCoins_10p(0);
            }
            return paymentDifference;
        }
        return paymentDifference;
    }


}

