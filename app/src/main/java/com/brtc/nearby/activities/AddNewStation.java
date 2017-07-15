package com.brtc.nearby.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brtc.nearby.R;
import com.brtc.nearby.db.DBAdapter;
import com.brtc.nearby.db.DBHelper;

/**
 * Created by user on 13-Jul-17.
 */

public class AddNewStation extends MainActivity{

    public DBAdapter db;
    String name, location, latitude, longitude;
    EditText inputName, inputLocation, inputLatitude, inputLongitude;
    Button saveBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_station_layout);

        inputName = (EditText)findViewById(R.id.editTextStationName);
        name = inputName.getText().toString();

        inputLocation = (EditText)findViewById(R.id.editTextLocationState);
        location = inputLocation.getText().toString();

        inputLatitude = (EditText)findViewById(R.id.editTextLatitude);
        latitude = inputLatitude.getText().toString();

        inputLongitude = (EditText)findViewById(R.id.editTextLongitude);
        longitude = inputLongitude.getText().toString();

        saveBtn = (Button)findViewById(R.id.buttonSaveNew);

        new AsyncLoadDB().execute();

    }

    public void saveData(){
        Intent backIntent = new Intent();
        String result= "Saved";
        if(name.matches("")|| location.matches("")|| latitude.matches("")|| longitude.matches("")){
            result= "Failed";
        }
        backIntent.putExtra("RESULT",result);
        setResult(RESULT_OK, backIntent);
        finish();;
    }

    public class AsyncLoadDB extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            db = new DBAdapter(AddNewStation.this);
            db.open();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // enter data into database method
                    if(inputName.getText().length() > 0 & inputLocation.getText().length() > 0 && inputLatitude.getText().length() > 0 && inputLongitude.getText().length() > 0){
                        db.saveNewLrt(inputName.getText().toString(), inputLocation.getText().toString(), inputLatitude.getText().toString(), inputLongitude.getText().toString());
                        startActivity(new Intent(AddNewStation.this, MainActivity.class));
                    }else{
                        Toast.makeText(AddNewStation.this,"All fields are required", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //db.rawQuery("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES ('" + name + "', '" +  location + "', '" + longitude +"', '" + latitude +")", null)

        }

    }

}
