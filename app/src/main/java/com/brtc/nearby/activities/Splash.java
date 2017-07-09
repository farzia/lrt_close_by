package com.brtc.nearby.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brtc.nearby.R;
import com.brtc.nearby.db.DBAdapter;

public class Splash extends Activity {

    public DBAdapter db;
    EditText pin;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        pin = (EditText) findViewById(R.id.edtPin);
        done = (Button) findViewById(R.id.done);

        new AsyncLoadDB().execute();
    }

    public class AsyncLoadDB extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            db = new DBAdapter(Splash.this);
            db.open();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pinTxt = pin.getText().toString();
                    if (db.check(pinTxt)) {
                        startActivity(new Intent(Splash.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(Splash.this, "Wrong PIN", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
