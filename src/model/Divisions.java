package model;

/**
 * divisions class
 * @author Erik Scovin
 */
public class Divisions {


    private int divId;
    private String divName;
    private int countryId;

    public Divisions( int divId, String divName, int countryId) {

        this.divId = divId;
        this.divName = divName;
        this.countryId = countryId;

    }

    public String getDivName() { return divName; }

    public int getDivId() { return divId; }

    public int getCountryId() { return countryId; }



    //Corrects combo box display by overriding toString
    @Override
    public String toString() {
        return (divId + " " + divName);
    }
}
