package edu.kmu.vd.dailymoment.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import edu.kmu.vd.dailymoment.R;
import edu.kmu.vd.dailymoment.adapters.ListAdapter;
import edu.kmu.vd.dailymoment.adapters.Schedule;
import edu.kmu.vd.dailymoment.db.DBController;

public class DayActivity extends Activity {
	private DBController mDBController;
	private String mDate;
	private ListAdapter mListAdapter;
	private ListView mListView;
	private String mMonth;
	private String mYear;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_day);
		Intent intent = getIntent();
		mYear = intent.getExtras().getString("Year");
		mMonth = intent.getExtras().getString("Month");
		mDate = intent.getExtras().getString("Date");

		Log.d("DayActivity Entered", mYear + "");
		TextView backButton = (TextView) findViewById(R.id.day_activity_time);
		backButton.setText("< " + mMonth + "/" + mDate);
		backButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
				onBackPressed();
				return false;
			}
		});
		TextView addButton = (TextView) findViewById(R.id.day_activity_add);

		final Context context = this;
		addButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
				Intent intent = new Intent(context, ScheduleActivity.class);
				intent.putExtra("Year", mYear);
				intent.putExtra("Month", mMonth);
				intent.putExtra("Date", mDate);
				
				startActivity(intent);
				return false;
			}
		});

		mListView = ((ListView) findViewById(R.id.day_activity_schedule_list));
		mListAdapter = new ListAdapter(this, R.layout.schedule);
		mListView.setAdapter(mListAdapter);
		mDBController = new DBController(this);

		for (Schedule schedule : mDBController.getSchedule()) {
			mListAdapter.add(schedule);
		}
	}
}