package com.example.mn.myplaces;

/**
 * Created by Milan on 11.4.2016..
 */
public class MyPlace {
    private String name;
    private String description;
    private String latitude;
    private String longitude;
    private long id;

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
