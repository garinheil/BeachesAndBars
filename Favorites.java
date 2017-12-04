package heil1gd.cps496.cmich.edu.readwritetest;

/**
 * Created by heilg on 11/15/2017.
 *
 * Description: This generic class stores information related to a user's
 *              favorite beach or bar, including the location's name, the
 *              date that the user went to the beach (or added the location
 *              to their favorites), the address, and any comments the user
 *              would wish the include about the location.
 */

public class Favorites {
    private String name, date, addr, cmts;

    // Default Favorites class constructor
    public Favorites() {
        name = "";
        date = "";
        addr = "";
        cmts = "";
    }

    // Overloaded constructor
    public Favorites(String name, String date, String addr, String cmts) {
        this.name = name;
        this.date = date;
        this.addr = addr;
        this.cmts = cmts;
    }

    // Overrides the default toString method in order to place the information into an arraylist
    public String toString() {
        return name + ";" + date + ";" + addr + ";" + cmts + ";";
    }

    public String toStringForDisplay() {
        return name + "\n" + date + "\n" + addr + "\n" + cmts;
    }

    // Getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return addr;
    }

    public void setCmts(String cmts) {
        this.cmts = cmts;
    }

    public String getCmts() {
        return cmts;
    }
}
