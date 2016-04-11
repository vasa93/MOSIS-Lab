package com.example.mn.myplaces;

/**
 * Created by Milan on 11.4.2016..
 */
public class MyPlace {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MyPlace(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public MyPlace(String name)
    {
        this(name,"");
    }

    @Override
    public String toString() {
        return this.name;
    }
}
