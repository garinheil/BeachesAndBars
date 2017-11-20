package heil1gd.cps496.cmich.edu.finalproject;

/**
 * Created by heilg on 11/15/2017.
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

//    // Overloaded constructor, allows no user comments
//    public Favorites(String name, String date, String addr) {
//        this.name = name;
//        this.date = date;
//        this.addr = addr;
//    }

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
