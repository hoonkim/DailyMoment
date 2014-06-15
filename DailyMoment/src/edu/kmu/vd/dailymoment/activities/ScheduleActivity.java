package edu.kmu.vd.dailymoment.activities;

//@TODO 복구.
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import edu.kmu.vd.dailymoment.R;

public class ScheduleActivity extends Activity {

	private String mYear;
	private String mMonth;
	private String mDate;

	private String mAmPm;
	private int mHour;
	private int mMinute;

	private TextView mStartTime;
	private TextView mEndTime;

	private static final int START_TIME_PICKER = 0;
	private static final int END_TIME_PICKER = 1;

	private TimePickerDialog.OnTimeSetListener mStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			mHour = hourOfDay;
			if (mHour >= 12) {
				mHour -= 12;
				mAmPm = "PM";
			} else {
				mAmPm = "AM";
			}
			mMinute = minute;
			mStartTime.setText(new StringBuilder().append(mAmPm + " ")
					.append(mHour).append(":").append(pad(mMinute)));
		}
	};

	private TimePickerDialog.OnTimeSetListener mEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			if (mHour >= 12) {
				mHour -= 12;
				mAmPm = "PM";
			} else {
				mAmPm = "AM";
			}
			mMinute = minute;
			mStartTime.setText(new StringBuilder().append(mAmPm + " ")
					.append(mHour).append(":").append(pad(mMinute)));

		}
	};

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent intent = getIntent();
		mYear = intent.getExtras().getString("Year");
		mMonth = intent.getExtras().getString("Month");
		mDate = intent.getExtras().getString("Date");
		Log.d("Sibal", mMonth);

		setContentView(R.layout.activity_schedule);
		
		LinearLayout backButton = (LinearLayout) findViewById(R.id.activity_schedule_back);
		

		backButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				onBackPressed();
				return false;
			}
		});
	
		mStartTime = (TextView) findViewById(R.id.activity_schedule_start_time);
		mEndTime = (TextView) findViewById(R.id.activity_schedule_end_time);

		mStartTime.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				showDialog(START_TIME_PICKER);
				return false;
			}
		});

		mEndTime.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				showDialog(END_TIME_PICKER);
				return false;
			}
		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case START_TIME_PICKER:
			return new TimePickerDialog(this, mStartTimeSetListener, mHour,
					mMinute, false);
		case END_TIME_PICKER:
			return new TimePickerDialog(this, mEndTimeSetListener, mHour,
					mMinute, false);
		}
		return null;
	}

	/**
	 * @param c
	 * @return
	 */
	private static String pad(int c) {
		if (c >= 10) {
			return String.valueOf(c);
		} else
			return "0" + String.valueOf(c);
	}
}
