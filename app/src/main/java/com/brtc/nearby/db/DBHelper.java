package com.brtc.nearby.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brtc.nearby.staticvar.StaticVars;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, StaticVars.DB_NAME, null, StaticVars.DB_VER);
    }

    // database table-column declaration
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bus_stopages("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT,"
                + " location TEXT," + " latitude TEXT," + " longitude TEXT)");

        db.execSQL("CREATE TABLE user("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + " pin TEXT)");

        PRE_DEFINED_VALUES(db);
    }

    // pre defined values insertion
    private void PRE_DEFINED_VALUES(SQLiteDatabase db) {

        db.execSQL("INSERT INTO user(pin) VALUES ('1234')");

        db.execSQL("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES ('LRT Bukit Jalil','Kuala Lumpur','3.05879','101.69173')");
        db.execSQL("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES ('LRT Masjid Jamek','Kuala Lumpur','3.1501','101.69688')");
        db.execSQL("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES ('LRT Kelana Jaya','Kuala Lumpur','3.11281','101.60429')");
        db.execSQL("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES ('LRT Bandar Tasik','Kuala Lumpur','3.07689','101.71482')");
        db.execSQL("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES ('LRT Gombak','Kuala Lumpur','3.23096','101.72419')");
        db.execSQL("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES ('LRT Masjid Jamek','Kuala Lumpur','3.1501','101.69688')");
        db.execSQL("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES ('Sri Petaling','Kuala Lumpur','3.06154','101.68703')");
        db.execSQL("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES ('LRT KLCC','Kuala Lumpur','3.15896','101.71409')");
        db.execSQL("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES ('LRT Asia jaya','Kuala Lumpur','3.10411','101.63828')");
    }

    // update table - dropping and renewing
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bus_stopages");
        onCreate(db);

    }
}
