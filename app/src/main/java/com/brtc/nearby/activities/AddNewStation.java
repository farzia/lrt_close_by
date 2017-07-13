package com.brtc.nearby.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.brtc.nearby.R;
import com.brtc.nearby.db.DBAdapter;
import com.brtc.nearby.db.DBHelper;

/**
 * Created by user on 13-Jul-17.
 */

public class AddNewStation extends MainActivity{
    String name, location, latitude, longitude;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_station_layout);

        EditText inputName = (EditText)findViewById(R.id.editTextStationName);
        name = inputName.getText().toString();

        EditText inputLocation = (EditText)findViewById(R.id.editTextLocationState);
        location = inputLocation.getText().toString();

        EditText inputLatitude = (EditText)findViewById(R.id.editTextLatitude);
        latitude = inputLatitude.getText().toString();

        EditText inputLongitude = (EditText)findViewById(R.id.editTextLongitude);
        longitude = inputLongitude.getText().toString();

        Button saveBtn= (Button)findViewById(R.id.buttonSaveNew);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // enter data into database methd
                saveNewLrt(name, location, latitude, longitude);
                saveData();
            }
        });

    }

    public void saveData(){
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();;

    }

    private boolean saveNewLrt(String name, String location, String longitude, String latitude){

        //TODO : NOMAN
        //dbAdapter.execSQL("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES (" + location + ", " +  location + ", " + longitude +", " + latitude +")");
        return true;
    }

}
