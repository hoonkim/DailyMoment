package edu.kmu.vd.dailymoment.activities;

//@TODO 복구.
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
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
		mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
				+ (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

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
		this.mTvCalendarTitle.setText(this.mThisMonthCalendar.get(1) + "년 "
				+ (1 + this.mThisMonthCalendar.get(2)) + "월");
		return paramCalendar;
	}

	private Calendar getNextMonth(Calendar paramCalendar) {
		paramCalendar.set(paramCalendar.get(1), paramCalendar.get(2), 1);
		paramCalendar.add(2, 1);
		this.mTvCalendarTitle.setText(this.mThisMonthCalendar.get(1) + "년 "
				+ (1 + this.mThisMonthCalendar.get(2)) + "월");
		return paramCalendar;
	}

	private boolean getPreference() {
		return this.preferences.getBoolean("locked", true);
	}

	private void initCalendarAdapter() {
		this.mCalendarAdapter = new CalendarAdapter(this, 2130903069,
				this.mDayList);
		this.mGvCalendar.setAdapter(this.mCalendarAdapter);
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(2130903064);
		this.preferences = getSharedPreferences("Setting", 0);
		if (getPreference()) {
			Log.d("스위치기본", "켜짐");
			startService(new Intent(this, LockService.class));
		}
		((ImageView) findViewById(2131099709))
				.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View paramView,
							MotionEvent paramMotionEvent) {
						Intent localIntent = new Intent(CalendarActivity.this,
								ConfigActivity.class);
						CalendarActivity.this.startActivity(localIntent);
						return false;
					}
				});
		this.mGvCalendar = ((GridView) findViewById(2131099713));
		this.mGvCalendar.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
				int i = paramMotionEvent.getActionMasked();
				float f1 = paramMotionEvent.getX();
				float f2 = paramMotionEvent.getY();
				int j = CalendarActivity.this.mGvCalendar.pointToPosition(
						(int) f1, (int) f2);
				DayInfo localDayInfo;
				if (i == 1) {
					localDayInfo = (DayInfo) CalendarActivity.this.mGvCalendar
							.getItemAtPosition(j);
					if (localDayInfo != null)
						;
				} else {
					return true;
				}
				Log.d("Checking Day", localDayInfo.getDay());
				Intent localIntent = new Intent(CalendarActivity.this,
						DayActivity.class);
				localIntent.putExtra("Date", localDayInfo.getDay());
				localIntent.putExtra("Month",
						CalendarActivity.this.mThisMonthCalendar.get(2));
				localIntent.putExtra("Month",
						CalendarActivity.this.mThisMonthCalendar.get(1));
				CalendarActivity.this.startActivity(localIntent);
				return true;
			}
		});
		this.mTvCalendarTitle = ((TextView) findViewById(2131099711));
		ImageView localImageView1 = (ImageView) findViewById(2131099710);
		ImageView localImageView2 = (ImageView) findViewById(2131099712);
		localImageView1.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
				CalendarActivity.this.mThisMonthCalendar = CalendarActivity.this
						.getLastMonth(CalendarActivity.this.mThisMonthCalendar);
				CalendarActivity.this
						.getCalendar(CalendarActivity.this.mThisMonthCalendar);
				return false;
			}
		});
		localImageView2.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
				CalendarActivity.this.mThisMonthCalendar = CalendarActivity.this
						.getNextMonth(CalendarActivity.this.mThisMonthCalendar);
				CalendarActivity.this
						.getCalendar(CalendarActivity.this.mThisMonthCalendar);
				return false;
			}
		});
		this.mDayList = new ArrayList();

	}

	protected void onResume() {
		super.onResume();
		super.onResume();
		this.mThisMonthCalendar = Calendar.getInstance();
		this.mThisMonthCalendar.set(5, 1);
		getCalendar(this.mThisMonthCalendar);
	}
}