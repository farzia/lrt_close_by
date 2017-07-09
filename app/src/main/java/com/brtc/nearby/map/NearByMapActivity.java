package com.brtc.nearby.map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.brtc.nearby.R;
import com.brtc.nearby.db.DBAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class NearByMapActivity extends FragmentActivity {

    public Double lat = 0.0, lng = 0.0;
    public ArrayList<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();
    public HashMap<String, String> mapLatLong = new HashMap<String, String>();
    ;
    private DBAdapter db;
    private Cursor mapCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        DBConnect();
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are
            // not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
                    requestCode);
            dialog.show();

        } else {
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);

            fm.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

                    drawMarker(googleMap);
                }
            });

        }
    }

    @SuppressLint("ShowToast")
    private void drawMarker(GoogleMap googleMap) {
        googleMap.clear();

        if (lat != 0 && lng != 0) {
            LatLng latlng = new LatLng(lat, lng);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
            googleMap.addMarker(new MarkerOptions().position(latlng));
            new FindNearestPlaceAlgorithm(googleMap).execute(lat, lng);
        }
    }

    public void myClick(View v) {

        if (lat != 0 && lng != 0) {
            Toast.makeText(getApplicationContext(), "" + lat + ", " + lng,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Nothing",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void DBConnect() {
        db = new DBAdapter(NearByMapActivity.this);
        db.open();
        mapCursor = db.getAllBusStoppage();
    }

    public class FindNearestPlaceAlgorithm extends
            AsyncTask<Double, Void, Cursor> {
        GoogleMap googleMap;

        public FindNearestPlaceAlgorithm(GoogleMap googleMap) {
            this.googleMap = googleMap;
        }

        @Override
        protected Cursor doInBackground(Double... arguments) {

            HashMap<Integer, Integer> hashMap = new HashMap<>();
            double venueLat, venueLng;
            for (mapCursor.moveToFirst(); !mapCursor.isAfterLast(); mapCursor
                    .moveToNext()) {
                venueLat = mapCursor.getFloat(mapCursor
                        .getColumnIndex(DBAdapter.LATITUDE));
                venueLng = mapCursor.getFloat(mapCursor
                        .getColumnIndex(DBAdapter.LONGITUDE));

                double latDistance = Math.toRadians(arguments[0] - venueLat);
                double lngDistance = Math.toRadians(arguments[1] - venueLng);

                double a = Math.sin(latDistance / 2)
                        * Math.sin(latDistance / 2)
                        + Math.cos(Math.toRadians(arguments[0]))
                        * Math.cos(Math.toRadians(venueLat))
                        * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

                int distance = (int) (Math.round(6371 * c));
                long id = mapCursor.getInt(mapCursor
                        .getColumnIndex(DBAdapter.ID));

                hashMap.put((int) id, distance);
            }

            Entry<Integer, Integer> min = null;
            List<Integer> minKeyList = new ArrayList<Integer>();

            for (Entry<Integer, Integer> entry : hashMap.entrySet()) {

                if (min == null || min.getValue() >= entry.getValue()) {
                    if (min == null || min.getValue() > entry.getValue()) {
                        min = entry;
                        minKeyList.clear();
                    }
                    minKeyList.add(entry.getKey());
                }
            }

            int closestPairId = minKeyList.get(0);
            Cursor c = db.getAllBusStoppageByID(closestPairId);

            return c;
        }

        @Override
        protected void onPostExecute(Cursor c) {

            double busLat = c.getFloat(c.getColumnIndex(DBAdapter.LATITUDE));
            double busLng = c.getFloat(c.getColumnIndex(DBAdapter.LONGITUDE));
            String name = c.getString(c.getColumnIndex(DBAdapter.NAME));
            googleMap.addMarker(new MarkerOptions().position(
                    new LatLng(busLat, busLng)).title(name));
        }
    }

}