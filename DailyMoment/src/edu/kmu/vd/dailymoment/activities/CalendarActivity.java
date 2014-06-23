package edu.kmu.vd.dailymoment.activities;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import edu.kmu.vd.dailymoment.R;
import edu.kmu.vd.dailymoment.adapters.CalendarAdapter;
import edu.kmu.vd.dailymoment.adapters.DayInfo;
import edu.kmu.vd.dailymoment.services.LockService;

public class CalendarActivity extends Activity {

	static final String PREFERENCE_NAME = "Setting";
	static final String LOCK_SCREEN = "locked";

	public static final int SUNDAY = 1;
	public static final int MONDAY = 2;
	public static final int TUESDAY = 3;
	public static final int WEDNSESDAY = 4;
	public static final int THURSDAY = 5;
	public static final int FRIDAY = 6;
	public static final int SATURDAY = 7;

	private CalendarAdapter mCalendarAdapter;
	private ArrayList<DayInfo> mDayList;
	private GridView mGvCalendar;
	private Calendar mThisMonthCalendar;
	private TextView mTvCalendarTitle;
	private SharedPreferences preferences;

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		final Context ctx = this;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_calendar);
		preferences = getSharedPreferences("Setting", 0);
		if (getPreference()) {
			Log.d("스위치기본", "켜짐");
			startService(new Intent(this, LockService.class));
		}
		ImageView configButton = ((ImageView) findViewById(R.id.calendar_activity_config));
		configButton.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionEvent) {
				Intent intent = new Intent(ctx, ConfigActivity.class);
				startActivity(intent);
				return false;
			}
		});

		mGvCalendar = (GridView) findViewById(R.id.calendar_activity_grid);
		mGvCalendar.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramView, MotionEvent motionEvent) {
				int action = motionEvent.getActionMasked();
				float currentXPosition = motionEvent.getX();
				float currentYPosition = motionEvent.getY();
				int position = mGvCalendar.pointToPosition(
						(int) currentXPosition, (int) currentYPosition);

				DayInfo dayInfo = (DayInfo) mGvCalendar
						.getItemAtPosition(position);

				if (action == MotionEvent.ACTION_UP) {
					Log.d("Checking Day", dayInfo.getDay());
					Intent intent = new Intent(ctx, DayActivity.class);
					intent.putExtra("Year",
							mThisMonthCalendar.get(Calendar.YEAR) + "");
					intent.putExtra("Month",
							(mThisMonthCalendar.get(Calendar.MONTH) + 1) + "");
					intent.putExtra("Date", dayInfo.getDay());
					startActivity(intent);
				}
				return true;

			}
		});
		mTvCalendarTitle = ((TextView) findViewById(R.id.calendar_activity_this_month));
		ImageView prevMonthButton = (ImageView) findViewById(R.id.calendar_activity_prev_month);
		ImageView nextMonthButton = (ImageView) findViewById(R.id.calendar_activity_next_month);
		prevMonthButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
				mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
				getCalendar(mThisMonthCalendar);
				return false;
			}
		});
		nextMonthButton.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
				mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
				getCalendar(mThisMonthCalendar);
				return false;
			}
		});
		mDayList = new ArrayList<DayInfo>();

	}

	private void getCalendar(Calendar calendar) {
		int lastMonthStartDay;
		int dayOfMonth;
		int thisMonthLastDay;

		mDayList.clear();

		// 이번달 시작일의 요일을 구한다. 시작일이 일요일인 경우 인덱스를 1(일요일)에서 8(다음주 일요일)로 바꾼다.)
		dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
		thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		calendar.add(Calendar.MONTH, -1);
		Log.e("지난달 마지막일", calendar.get(Calendar.DAY_OF_MONTH) + "");

		// 지난달의 마지막 일자를 구한다.
		lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		calendar.add(Calendar.MONTH, 1);
		Log.e("이번달 시작일", calendar.get(Calendar.DAY_OF_MONTH) + "");

		if (dayOfMonth == SUNDAY) {
			dayOfMonth += 7;
		}

		lastMonthStartDay -= (dayOfMonth - 1) - 1;

		// 캘린더 타이틀(년월 표시)을 세팅한다.
		mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + " / "
				+ (mThisMonthCalendar.get(Calendar.MONTH) + 1));

		DayInfo day;

		Log.e("DayOfMOnth", dayOfMonth + "");

		for (int i = 0; i < dayOfMonth - 1; i++) {
			int date = lastMonthStartDay + i;
			day = new DayInfo();
			day.setDay(Integer.toString(date));
			day.setInMonth(false);

			mDayList.add(day);
		}
		for (int i = 1; i <= thisMonthLastDay; i++) {
			day = new DayInfo();
			day.setDay(Integer.toString(i));
			day.setInMonth(true);

			mDayList.add(day);
		}
		for (int i = 1; i < 42 - (thisMonthLastDay + dayOfMonth - 1) + 1; i++) {
			day = new DayInfo();
			day.setDay(Integer.toString(i));
			day.setInMonth(false);
			mDayList.add(day);
		}

		initCalendarAdapter();
	}

	private Calendar getLastMonth(Calendar paramCalendar) {
		paramCalendar.set(paramCalendar.get(1), paramCalendar.get(2), 1);
		paramCalendar.add(2, -1);
		mTvCalendarTitle.setText(mThisMonthCalendar.get(1) + "년 "
				+ (1 + mThisMonthCalendar.get(2)) + "월");
		return paramCalendar;
	}

	private Calendar getNextMonth(Calendar paramCalendar) {
		paramCalendar.set(paramCalendar.get(1), paramCalendar.get(2), 1);
		paramCalendar.add(2, 1);
		mTvCalendarTitle.setText(mThisMonthCalendar.get(1) + "년 "
				+ (1 + mThisMonthCalendar.get(2)) + "월");
		return paramCalendar;
	}

	private boolean getPreference() {
		return preferences.getBoolean("locked", true);
	}

	private void initCalendarAdapter() {
		mCalendarAdapter = new CalendarAdapter(this, R.layout.day, mDayList);
		mGvCalendar.setAdapter(mCalendarAdapter);
	}

	protected void onResume() {
		super.onResume();
		mThisMonthCalendar = Calendar.getInstance();
		mThisMonthCalendar.set(5, 1);
		getCalendar(mThisMonthCalendar);
	}
}