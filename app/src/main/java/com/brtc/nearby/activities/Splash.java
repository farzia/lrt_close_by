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
    EditText pin, name;
    Button done, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        pin = (EditText) findViewById(R.id.edtPin);
        done = (Button) findViewById(R.id.done);

        name = (EditText) findViewById(R.id.edtName);
        register = (Button) findViewById(R.id.register);

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
                    String userTxt = name.getText().toString();
                    if(userTxt.length() > 0 && pinTxt.length() > 0){

                        if(db.check(userTxt, pinTxt)) {
                            startActivity(new Intent(Splash.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Splash.this, "Wrong Information", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(Splash.this, "User name, password required", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i1= new Intent(Splash.this, RegisterActivity.class);
                    startActivity(i1);
                }
            });
        }

    }
}
