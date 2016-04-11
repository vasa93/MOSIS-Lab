package com.example.mn.myplaces;

import java.util.ArrayList;

/**
 * Created by Milan on 11.4.2016..
 */
public class MyPlacesData {
    private ArrayList<MyPlace> myPlaces;

    public ArrayList<MyPlace> getMyPlaces() {
        return myPlaces;
    }

    public void setMyPlaces(ArrayList<MyPlace> myPlaces) {
        this.myPlaces = myPlaces;
    }

    private MyPlacesData() {
        myPlaces = new ArrayList<MyPlace>();

        myPlaces.add(new MyPlace("Place A"));
        myPlaces.add(new MyPlace("Place B"));
        myPlaces.add(new MyPlace("Place C"));
        myPlaces.add(new MyPlace("Place D"));
        myPlaces.add(new MyPlace("Place E"));
    }

    private static  class SingletonHolder {
        public static final  MyPlacesData instance = new MyPlacesData();
    }

    public static MyPlacesData getInstance() {
        return SingletonHolder.instance;
    }

    public void addNewPlace(MyPlace myPlace) {
        myPlaces.add(myPlace);
    }

    public MyPlace getPlace(int index) {
        return myPlaces.get(index);
    }

    public void deletePlace(int index) {
        myPlaces.remove(index);
    }
}
