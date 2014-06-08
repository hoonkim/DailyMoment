package edu.kmu.vd.dailymoment.activities;

import android.app.Activity;
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
		TextView localTextView = (TextView) findViewById(R.id.day_activity_time);
		localTextView.setText("< " + mMonth + "/" + mDate);
		localTextView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
				onBackPressed();
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

		// @TODO 임시
		mListAdapter.add(new Schedule(1, "8:00", "9:00", "Breafast"));
		mListAdapter.add(new Schedule(2, "7:00", "9:00", "Wow"));
		mListAdapter.add(new Schedule(3, "8:00", "9:00", "Suyoung"));
		mListAdapter.add(new Schedule(4, "5:00", "9:00", "Samsong"));
		mListAdapter.add(new Schedule(5, "8:00", "9:00", "schedule!"));
		mListAdapter.add(new Schedule(6, "8:00", "9:00", "sleepy"));
		mListAdapter.add(new Schedule(7, "8:00", "9:00", "Test"));
		mListAdapter.add(new Schedule(8, "8:00", "9:00", "run"));
		mListAdapter.add(new Schedule(9, "8:00", "9:00", "siiiii"));
	}
}