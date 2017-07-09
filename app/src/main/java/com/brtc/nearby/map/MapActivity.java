package com.brtc.nearby.map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.brtc.nearby.R;
import com.brtc.nearby.staticvar.StaticVars;
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

public class MapActivity extends FragmentActivity {

    //	public GoogleMap googleMap;
    public Double lat = 0.0, lng = 0.0, busLat, busLng;
    public ArrayList<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();
    public HashMap<String, String> mapLatLong = new HashMap<String, String>();
    ;
    public String busStoppage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getBaseContext());

        busLat = getIntent().getDoubleExtra(StaticVars.LAT, 0.0);
        busLng = getIntent().getDoubleExtra(StaticVars.LONG, 0.0);

        busStoppage = getIntent().getStringExtra(StaticVars.BUS_STOPPAGE);

        // Showing status
        if (status != ConnectionResult.SUCCESS) { // Google Play Services are
            // not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
                    requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);

            // Getting GoogleMap object from the fragment
            /*googleMap =*/
            fm.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.setMyLocationEnabled(true);
                    LatLng latLng = new LatLng(busLat, busLng);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

                    drawMarker(googleMap);
                }
            });

        }
    }

    @SuppressLint("ShowToast")
    private void drawMarker(GoogleMap googleMap) {
        googleMap.clear();

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(busLat, busLng)).title(busStoppage));

        if (lat != 0 && lng != 0) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,
                    lng)));


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


}