package model;

/**
 * countries class
 * @author Erik Scovin
 */
public class Countries {
    private int id;
    private String name;

    public Countries(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    //Corrects combo box display by overriding toString
    @Override
    public String toString() {
        return (id + " " + name);
    }
}
