package com.brtc.nearby.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.brtc.nearby.R;
import com.brtc.nearby.db.DBAdapter;
import com.brtc.nearby.map.NearByMapActivity;

public class MainActivity extends ActionBarActivity {
	private DBAdapter dbAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		dbCreate();

		if (isNetAvailable(MainActivity.this) == false
				|| isGpsEnabled(MainActivity.this) == false) {
			final Dialog d = new Dialog(MainActivity.this);
			d.setContentView(R.layout.demo_dialog);
			d.setTitle("Attention Please!!!");
			d.setCancelable(false);
			Button demo = (Button) d.findViewById(R.id.okDemo);
			demo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					d.dismiss();
					finish();
				}
			});
			d.show();
		}

	}

	public void click(View v) {

		switch (v.getId()) {
		case R.id.allMarketsListBtn:

			Intent marketlistIntent1 = new Intent(MainActivity.this,
					AllBusStoppagesActivity.class);
			startActivity(marketlistIntent1);

			break;

		case R.id.todayMarketBtn:

			Intent marketlistIntent2 = new Intent(MainActivity.this,
					NearByMapActivity.class);
			startActivity(marketlistIntent2);

			break;

			case R.id.addNewMarketBtn:
			Intent addStationIntent3 = new Intent(MainActivity.this,
					AddNewStation.class);
			startActivity(addStationIntent3);

			break;


		}
	}

	private void dbCreate() {

		dbAdapter = new DBAdapter(MainActivity.this);
		dbAdapter.open();

	}

	private boolean isNetAvailable(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// mobile
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();

		// wifi
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();

		if (mobile == State.CONNECTED
				|| mobile == State.CONNECTING
				|| wifi == State.CONNECTED
				|| wifi == State.CONNECTING) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isGpsEnabled(Context context) {

		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			return true;
		} else {
			return false;
		}
	}

	// Initiating Menu XML file (menu.xml)
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected
	 * Identify single menu item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
			case R.id.action_showLRT_list:
				// Single menu item is selected do something
				// Ex: launching new activity/screen or show alert message
				Intent marketlistIntent1 = new Intent(MainActivity.this,
						AllBusStoppagesActivity.class);
				startActivity(marketlistIntent1);
				return true;

			case R.id.action_show_map:
				Intent marketlistIntent2 = new Intent(MainActivity.this,
						NearByMapActivity.class);
				startActivity(marketlistIntent2);
				return true;

			case R.id.action_add_new:
				Intent addStationIntent3 = new Intent(MainActivity.this,
						AddNewStation.class);
				startActivity(addStationIntent3);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
