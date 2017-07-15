package com.brtc.nearby.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brtc.nearby.R;
import com.brtc.nearby.db.DBAdapter;

/**
 * Created by user on 15-Jul-17.
 */

public class RegisterActivity extends Splash {

    public DBAdapter db;
    Button register;

    EditText name, userName, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        name = (EditText) findViewById(R.id.registerName);
        userName = (EditText) findViewById(R.id.registerUserName);
        password = (EditText) findViewById(R.id.registerPassword);

        register = (Button) findViewById(R.id.buttonRegister);

        new AsyncLoadDB().execute();


    }

    private void saveNewUser(String name, String userName, String password) {
            //TODO : NOMAN : save
        Toast.makeText(RegisterActivity.this,"DONE", Toast.LENGTH_SHORT).show();

    }


    public class AsyncLoadDB extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            db = new DBAdapter(RegisterActivity.this);
            db.open();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // enter data into database method
                    if(name != null && name.getText().length() > 0 && userName != null && userName.getText().length() > 0 && password != null && password.getText().length() > 0){
                        db.saveNewUser(name.getText().toString(), userName.getText().toString(), password.getText().toString());
                        startActivity(new Intent(RegisterActivity.this, Splash.class));
                    }else{
                        Toast.makeText(RegisterActivity.this,"All fields are required", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }


}
