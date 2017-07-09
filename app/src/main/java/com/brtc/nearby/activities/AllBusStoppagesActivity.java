package com.brtc.nearby.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.brtc.nearby.R;
import com.brtc.nearby.db.DBAdapter;
import com.brtc.nearby.listadapter.BusStopagesListItem;
import com.brtc.nearby.listadapter.BusStopagesListitemAdapter;
import com.brtc.nearby.myLogs.AppsLogs;

public class AllBusStoppagesActivity extends Activity {

	ListView listview;
	BusStopagesListitemAdapter adapter;
	private DBAdapter db;
	private Cursor cursor;
	private ArrayList<BusStopagesListItem> marketArray = new ArrayList<BusStopagesListItem>();
	BusStopagesListItem marketItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busstop_list);
		DBConnect();
		
		try {
			cursor = db.getAllBusStoppage();
		} catch (CursorIndexOutOfBoundsException e) {
			new AppsLogs(e);
			finish();
		} catch (SQLiteException e) {
			new AppsLogs(e);
			finish();
		} catch (NullPointerException e) {
			new AppsLogs(e);
			finish();
		} catch (Exception e) {
			new AppsLogs(e);
			finish();
		}

		listview = (ListView) findViewById(R.id.marketListview);
		BusStopagesListItem.setCursor(cursor);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			String marketName = cursor.getString(cursor
					.getColumnIndex(DBAdapter.NAME));
			String marketLocation = cursor.getString(cursor
					.getColumnIndex(DBAdapter.LOCATION));

			marketArray.add(new BusStopagesListItem(marketName, marketLocation,
					""));
		}

		adapter = new BusStopagesListitemAdapter(AllBusStoppagesActivity.this,
				R.layout.busstop_list_item, marketArray);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				cursor.moveToPosition(position);
				String marketName = cursor.getString(cursor
						.getColumnIndex(DBAdapter.NAME));
				Toast.makeText(AllBusStoppagesActivity.this, marketName,
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void DBConnect() {
		db = new DBAdapter(AllBusStoppagesActivity.this);
		db.open();
	}
}
