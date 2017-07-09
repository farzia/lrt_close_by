package com.brtc.nearby.listadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.brtc.nearby.R;
import com.brtc.nearby.db.DBAdapter;
import com.brtc.nearby.map.MapActivity;
import com.brtc.nearby.staticvar.StaticVars;

import java.util.ArrayList;

public class BusStopagesListitemAdapter extends
		ArrayAdapter<BusStopagesListItem> {

	Context context;
	int layoutResourceId;
	ArrayList<BusStopagesListItem> marketLists = new ArrayList<BusStopagesListItem>();
	Cursor cursor;
	private DBAdapter dbAdapter;
	BusStopagesListItem marketItem;

	public BusStopagesListitemAdapter(Context context, int layoutResourceId,
			ArrayList<BusStopagesListItem> marketLists) {
		super(context, layoutResourceId, marketLists);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.marketLists = marketLists;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		ViewWrapper viewWrapper = null;

		if (item == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			item = inflater.inflate(layoutResourceId, parent, false);
			viewWrapper = new ViewWrapper();
			viewWrapper.marketName = (TextView) item
					.findViewById(R.id.marketName);
			viewWrapper.marketLocation = (TextView) item
					.findViewById(R.id.market_location);

			viewWrapper.map = (Button) item
					.findViewById(R.id.marketLocationBtn);
			item.setTag(viewWrapper);
		} else {
			viewWrapper = (ViewWrapper) item.getTag();
		}

		BusStopagesListItem marketItems = marketLists.get(position);
		viewWrapper.marketName.setText(marketItems.getMarketName());
		viewWrapper.marketLocation.setText(marketItems.getMarketLocation());

		final int pos = position;
		dbCreate();

		viewWrapper.map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				cursor.moveToPosition(pos);
				String latitude = cursor.getString(cursor
						.getColumnIndex(DBAdapter.LATITUDE));
				String longitude = cursor.getString(cursor
						.getColumnIndex(DBAdapter.LONGITUDE));
				String name = cursor.getString(cursor
						.getColumnIndex(DBAdapter.NAME));
				Intent i = new Intent(context.getApplicationContext(),
						MapActivity.class);
				i.putExtra(StaticVars.BUS_STOPPAGE, name);
				i.putExtra(StaticVars.LAT, Double.parseDouble(latitude));
				i.putExtra(StaticVars.LONG, Double.parseDouble(longitude));
				context.startActivity(i);
			}
		});

		return item;

	}

	private void dbCreate() {

		dbAdapter = new DBAdapter(context);
		dbAdapter.open();
		cursor = BusStopagesListItem.getCursor();

	}

	static class ViewWrapper {
		TextView marketName;
		TextView marketLocation;

		Button map;

	}

}
