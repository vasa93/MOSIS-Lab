package com.example.mn.myplaces;

import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Milan on 11.4.2016..
 */
public class MyPlacesData {
    private ArrayList<MyPlace> myPlaces;

    MyPlacesDBAdapter dbAdapter;

    public ArrayList<MyPlace> getMyPlaces() {
        return myPlaces;
    }

    public void setMyPlaces(ArrayList<MyPlace> myPlaces) {
        this.myPlaces = myPlaces;
    }

    private MyPlacesData() {
        myPlaces = new ArrayList<MyPlace>();
        dbAdapter = new MyPlacesDBAdapter(MyPlacesApplication.getContext());
        try {
            dbAdapter.open();
            this.myPlaces = dbAdapter.getAllEntries();
        } catch (SQLException e) {
            Log.v("MyPlacesData", e.getMessage());
        } finally {
            dbAdapter.close();
        }
    }

    private static  class SingletonHolder {
        public static final  MyPlacesData instance = new MyPlacesData();
    }

    public static MyPlacesData getInstance() {
        return SingletonHolder.instance;
    }

    public void addNewPlace(MyPlace myPlace) {
        myPlaces.add(myPlace);

        try {
            dbAdapter.open();
            long id = dbAdapter.insertEntry(myPlace);
            myPlace.setId(id);
        } catch (SQLException e) {
            Log.v("MyPlacesData", e.getMessage());
        } finally {
            dbAdapter.close();
        }
    }

    public MyPlace getPlace(int index) {
        return myPlaces.get(index);
    }

    public void deletePlace(int index) {
        MyPlace place = myPlaces.remove(index);

        try {
            dbAdapter.open();
            boolean success = dbAdapter.removeEntry(place.getId());
        } catch (SQLException e) {
            Log.v("MyPlacesData", e.getMessage());
        } finally {
            dbAdapter.close();
        }
    }

    public void updatePlace(MyPlace place) {
        try {
            dbAdapter.open();
            dbAdapter.updateEntry(place.getId(), place);
        } catch (SQLException e) {
            Log.v("MyPlacesData", e.getMessage());
        } finally {
            dbAdapter.close();
        }
    }
}
