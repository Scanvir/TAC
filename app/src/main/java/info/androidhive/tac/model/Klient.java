package info.androidhive.tac.model;

import java.util.ArrayList;

public class Klient {

    public int id;
    public String name;

    @SuppressWarnings("serial")
    public static class klients extends ArrayList<Klient> {
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
