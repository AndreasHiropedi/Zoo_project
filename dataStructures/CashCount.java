package dataStructures;

public class CashCount implements ICashCount {

    // all the given variables keep track of the individual notes and coins supply
    private int nrNotes20Pounds;
    private int nrNotes10Pounds;
    private int nrNotes5Pounds;
    private int nrCoins2Pounds;
    private int nrCoins1Pound;
    private int nrCoins50P;
    private int nrCoins20P;
    private int nrCoins10P;

    // Constructor
    public CashCount() {

    }

    // ---------------- SETTERS ----------------

    /**
     * @param noteCount Sets the number of 20 pound notes available in this pool of cash.
     */
    public void setNrNotes_20pounds(int noteCount) {
        nrNotes20Pounds = noteCount;
    }

    /**
     * @param noteCount Sets the number of 10 pound notes available in this pool of cash.
     */
    public void setNrNotes_10pounds(int noteCount) {
        nrNotes10Pounds = noteCount;
    }

    /**
     * @param noteCount Sets the number of 5 pound notes available in this pool of cash.
     */
    public void setNrNotes_5pounds(int noteCount) {
        nrNotes5Pounds = noteCount;
    }

    /**
     * @param coinCount Sets the number of 2 pound coins available in this pool of cash.
     */
    public void setNrCoins_2pounds(int coinCount) {
        nrCoins2Pounds = coinCount;
    }

    /**
     * @param coinCount Sets the number of 1 pound coins available in this pool of cash.
     */
    public void setNrCoins_1pound(int coinCount) {
        nrCoins1Pound = coinCount;
    }

    /**
     * @param coinCount Sets the number of 50 pence coins available in this pool of cash.
     */
    public void setNrCoins_50p(int coinCount) {
        nrCoins50P = coinCount;
    }

    /**
     * @param coinCount Sets the number of 20 pence coins available in this pool of cash.
     */
    public void setNrCoins_20p(int coinCount) {
        nrCoins20P = coinCount;
    }

    /**
     * @param coinCount Sets the number of 10 pence coins available in this pool of cash.
     */
    public void setNrCoins_10p(int coinCount) {
        nrCoins10P = coinCount;
    }

    // ---------------- GETTERS ----------------

    /**
     * @return Returns the number of 20 pound notes available in this pool of cash.
     */
    public int getNrNotes_20pounds() {
        return nrNotes20Pounds;
    }

    /**
     * @return Returns the number of 10 pound notes available in this pool of cash.
     */
    public int getNrNotes_10pounds() {
        return nrNotes10Pounds;
    }

    /**
     * @return Returns the number of 5 pound notes available in this pool of cash.
     */
    public int getNrNotes_5pounds() {
        return nrNotes5Pounds;
    }

    /**
     * @return Returns the number of 2 pound coins available in this pool of cash.
     */
    public int getNrCoins_2pounds() {
        return nrCoins2Pounds;
    }

    /**
     * @return Returns the number of 1 pound coins available in this pool of cash.
     */
    public int getNrCoins_1pound() {
        return nrCoins1Pound;
    }

    /**
     * @return Returns the number of 50 pence coins available in this pool of cash.
     */
    public int getNrCoins_50p() {
        return nrCoins50P;
    }

    /**
     * @return Returns the number of 20 pence coins available in this pool of cash.
     */
    public int getNrCoins_20p() {
        return nrCoins20P;
    }

    /**
     * @return Returns the number of 10 pence coins available in this pool of cash.
     */
    public int getNrCoins_10p() {
        return nrCoins10P;
    }


    public ICashCount setEmptyCashCount(ICashCount coins) {
        coins.setNrNotes_20pounds(0);
        coins.setNrNotes_10pounds(0);
        coins.setNrNotes_5pounds(0);
        coins.setNrCoins_2pounds(0);
        coins.setNrCoins_1pound(0);
        coins.setNrCoins_50p(0);
        coins.setNrCoins_20p(0);
        coins.setNrCoins_10p(0);
        return coins;
    }

    // ---------------- CHECK MACHINE STOCK ----------------

    /** all the following methods check whether every individual type of coins and notes
     * are currently in stock, which is used in returning the change for the payEntrance method
     * @return true if the specific type of currency is in the ticket machine's stock
     */

    public boolean has20PoundNotes() {
        if (getNrNotes_20pounds() > 0)
        {
            return true;
        }
        return false;
    }

    public boolean has10PoundNotes() {
        if (getNrNotes_10pounds() > 0)
        {
            return true;
        }
        return false;
    }

    public boolean has5PoundNotes() {
        if (getNrNotes_5pounds() > 0)
        {
            return true;
        }
        return false;
    }

    public boolean has2PoundCoins() {
        if (getNrCoins_2pounds() > 0)
        {
            return true;
        }
        return false;
    }

    public boolean has1PoundCoins() {
        if (getNrCoins_1pound() > 0)
        {
            return true;
        }
        return false;
    }

    public boolean has50PCoins() {
        if (getNrCoins_50p() > 0) {
            return true;
        }
        return false;
    }

    public boolean has20PCoins() {
        if (getNrCoins_20p() > 0) {
            return true;
        }
        return false;
    }

    public boolean has10PCoins() {
        if (getNrCoins_10p() > 0) {
            return true;
        }
        return false;
    }

}
