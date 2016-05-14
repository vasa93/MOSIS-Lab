package com.example.mn.myplaces;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MyPlacesMapActivity extends ActionBarActivity {

    public static final int SHOW_MAP = 0;
    public static final int CENTER_PLACE_ON_MAP = 1;
    public static final int SELECT_COORDINATES = 2;

    private int state = 0;
    private boolean selCoordsEnabled = false;
    private LatLng placeLoc;

    private GoogleMap map;

    private HashMap<Marker, Integer> markerPlaceIdMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Intent mapIntent = getIntent();
            Bundle mapBundle = mapIntent.getExtras();
            if(mapBundle != null) {
                state = mapBundle.getInt("state");
                if(state == CENTER_PLACE_ON_MAP) {
                    String placeLat = mapBundle.getString("lat");
                    String placeLon = mapBundle.getString("lon");
                    placeLoc = new LatLng(Double.parseDouble(placeLat), Double.parseDouble(placeLon));
                }
            }
        }
        catch (Exception e) {
            Log.d("Error", "Error reading state");
        }

        setContentView(R.layout.activity_my_places_map);
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
       if(state == SHOW_MAP)
           map.setMyLocationEnabled(true);
        else if(state == SELECT_COORDINATES)
           map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
               @Override
               public void onMapClick(LatLng latLng) {
                  if(state == SELECT_COORDINATES && selCoordsEnabled) {
                      String lon = Double.toString(latLng.longitude);
                      String lat = Double.toString(latLng.latitude);
                      Intent locationIntent = new Intent();
                      locationIntent.putExtra("lon", lon);
                      locationIntent.putExtra("lat", lat);
                      setResult(Activity.RESULT_OK, locationIntent);
                      finish();
                  }
               }
           });
        else
           map.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLoc, 15));

        addMyPlacesMarkers();
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(MyPlacesMapActivity.this, ViewMyPlaceActivity.class);
                int i = markerPlaceIdMap.get(marker);
                intent.putExtra("position", i);
                startActivity(intent);
                return true;
            }
        });
    }

    private void addMyPlacesMarkers() {
        ArrayList<MyPlace> places = MyPlacesData.getInstance().getMyPlaces();
        markerPlaceIdMap = new HashMap<Marker, Integer>((int)((double)places.size()*1.2));
        for(int i = 0; i <  places.size(); i++) {
            MyPlace place = places.get(i);
            String lat = place.getLatitude();
            String lon = place.getLongitude();
            LatLng loc = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(loc);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.myplace));
            markerOptions.title(place.getName());
            Marker marker = map.addMarker(markerOptions);
            markerPlaceIdMap.put(marker, i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //getMenuInflater().inflate(R.menu.menu_my_places_map,menu);
        //return true;

        if(state == SELECT_COORDINATES && !selCoordsEnabled) {
            menu.add(0, 1, 1, "Select Coordinates");
            menu.add(0, 2, 2, "Cancel");
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.new_place_item) {
//            Intent i = new Intent(this, EditMyPlaceActivity.class);
//            startActivityForResult(i, MainActivity.NEW_PLACE);
//        }
//        else if (id == R.id.my_place_list_item) {
//            Intent i = new Intent(this, MyPlacesList.class);
//            startActivity(i);
//        }
//        else if (id == R.id.about_item) {
//            Intent i = new Intent(this, About.class);
//            startActivity(i);
//        }

        switch (id) {
            case 1:
                selCoordsEnabled = true;
                item.setEnabled(false);
                Toast.makeText(this, "Select coordinates", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == Activity.RESULT_OK) {
                String lon = data.getExtras().getString("lon");
                EditText lonText = (EditText) findViewById(R.id.editmyplace_lon_edit);
                lonText.setText(lon);

                String lat = data.getExtras().getString("lat");
                EditText latText = (EditText) findViewById(R.id.editmyplace_lat_edit);
                latText.setText(lat);

            }
        }
        catch (Exception e) {
            // test
        }
    }
}
