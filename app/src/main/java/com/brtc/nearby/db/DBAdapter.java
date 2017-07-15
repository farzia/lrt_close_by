package com.brtc.nearby.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {

    private Context context;
    private DBHelper dbHelper;
    public SQLiteDatabase db;

    public static final String ID = "_id", NAME = "name",
            LOCATION = "location", LATITUDE = "latitude",
            LONGITUDE = "longitude", TABLE_BUS = "bus_stopages";

    // set Context to access the database
    public DBAdapter(Context context) {
        this.context = context;
    }

    // open the database
    public DBAdapter open() throws SQLException {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // close the database
    public void close() {
        dbHelper.close();
    }

    public Cursor getAllBusStoppage() {
        Cursor cursor = db.query(DBAdapter.TABLE_BUS, new String[]{
                        DBAdapter.ID, DBAdapter.NAME, DBAdapter.LOCATION,
                        DBAdapter.LATITUDE, DBAdapter.LONGITUDE,}, null, null, null,
                null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor getAllBusStoppageByID(long id) {
        String where = DBAdapter.ID + "=" + id;
        Cursor cursor = db.query(DBAdapter.TABLE_BUS, new String[]{
                        DBAdapter.ID, DBAdapter.NAME, DBAdapter.LOCATION,
                        DBAdapter.LATITUDE, DBAdapter.LONGITUDE,}, where, null, null,
                null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public boolean check(String userName, String pass) {
        Cursor cursor = db.rawQuery("SELECT name FROM user WHERE password='" + pass + "' AND username='" + userName + "'", null);
        if (cursor.getCount() > 0)
            return true;

        return false;
    }

    public boolean saveNewLrt(String name, String location, String longitude, String latitude){
        db.rawQuery("INSERT INTO bus_stopages(name, location, latitude, longitude) VALUES ('" + name + "', '" +  location + "', '" + longitude +"', '" + latitude +"')", null);
        return true;
    }

    public boolean saveNewUser(String name, String username, String password){
        db.rawQuery("INSERT INTO user(name, username, password) VALUES ('" + name + "', '" +  username + "', '" + password +"')", null);
        return true;
    }


}
