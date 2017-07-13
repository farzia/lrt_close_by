package com.brtc.nearby.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.brtc.nearby.R;
import com.brtc.nearby.db.DBAdapter;
import com.brtc.nearby.map.NearByMapActivity;

public class MainActivity extends ActionBarActivity {
	private static final int ACTIVITY_RESULT_CODE = 1;

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
			startActivityForResult(addStationIntent3, ACTIVITY_RESULT_CODE);

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

	// returned result from AddNewLocation
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		// super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ACTIVITY_RESULT_CODE) {
			if(resultCode==1){
				Toast.makeText(MainActivity.this,"Location Saved", Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(MainActivity.this,"Failed to Save", Toast.LENGTH_SHORT).show();
			}
		}
	}



}
