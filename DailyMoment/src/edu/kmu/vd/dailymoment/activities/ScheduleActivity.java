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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import edu.kmu.vd.dailymoment.R;
import edu.kmu.vd.dailymoment.db.DBController;

public class ScheduleActivity extends Activity {

	private String mYear;
	private String mMonth;
	private String mDate;

	private String mFullDate;

	private String mAmPm;
	private int mHour;
	private int mMinute;

	/**
	 * 시작시간.
	 */
	private String mStart = "";

	/**
	 * 끝나는 시간.
	 */
	private String mEnd = "";

	private EditText mTitle;

	private TextView mOKButton;

	private TextView mStartTime;
	private TextView mEndTime;

	private static final int START_TIME_PICKER = 0;
	private static final int END_TIME_PICKER = 1;

	private final String packageName = "edu.kmu.vd.dailymoment";

	private DBController mDBController;

	private Spinner mCategory;
	private Spinner mSubCategory;

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
			mStart = mFullDate + " " + pad(hourOfDay) + ":" + pad(mMinute);

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
			mEndTime.setText(new StringBuilder().append(mAmPm + " ")
					.append(mHour).append(":").append(pad(mMinute)));
			mEnd = mFullDate + " " + pad(hourOfDay) + ":" + pad(mMinute);

		}
	};

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent intent = getIntent();

		/* 달력에서 넘어올때 선택된 날짜를 넘겨받음. */
		mYear = intent.getExtras().getString("Year");
		mMonth = intent.getExtras().getString("Month");
		mDate = intent.getExtras().getString("Date");

		/* Timestamp로 바꾸기 쉽게 하기 위해. */
		mFullDate = mYear + "-";
		mFullDate += (mMonth.length() == 1) ? "0" + mMonth : mMonth;
		mFullDate += "-";
		mFullDate += (mDate.length() == 1) ? "0" + mDate : mDate;
		
		setContentView(R.layout.activity_schedule);

		final Context context = this; // 리스너들에서 사용하기 위해 context final로 선언.

		mDBController = new DBController(this); // DB 컨트롤러 초기화.

		/* 위에 날짜 버튼 누르면 뒤로 백 */
		LinearLayout backButton = (LinearLayout) findViewById(R.id.activity_schedule_back);
		backButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				onBackPressed();
				return false;
			}
		});

		mTitle = (EditText) findViewById(R.id.activity_schedule_title_edittext);

		/* 스케줄 시작 시간 끝나는 시간 */
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

		/* OK 버튼 눌리면 디비에 저장. */
		mOKButton = (TextView) findViewById(R.id.activity_schedule_ok);
		mOKButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				Log.d("Title", mTitle + "");
				/* 제목을 가져오고 비어있으면 종료. */
				String title = mTitle.getText().toString();
				if (title == null) {
					return false;
				}

				/* 카테고리를 가져와 합침. */
				String category = mCategory.getSelectedItem().toString();
				category += "_" + mSubCategory.getSelectedItem().toString();

				/* 시작 시간 체크 되어 있는지 체크. */
				if ((mStart == null) || (mEnd == null)) {
					return false;
				}

				/* 디비에 저장. */
				mDBController.putSchedule(title, mStart, mEnd, category);

				onBackPressed();

				return false;
			}
		});

		/* 카테고리 선택하는 스피너들. */
		mCategory = (Spinner) findViewById(R.id.activity_schedule_category_spinner);
		mSubCategory = (Spinner) findViewById(R.id.activity_schedule_subcategory_spinner);

		ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter
				.createFromResource(this, R.array.Category,
						android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> subCategoryAdapter = ArrayAdapter
				.createFromResource(this, R.array.Eat,
						android.R.layout.simple_spinner_item);

		categoryAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mCategory.setAdapter(categoryAdapter);
		mSubCategory.setAdapter(subCategoryAdapter);

		/* 카테고리 고르면 서브 카테고리 바뀜 */
		mCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String selected = parent.getItemAtPosition(position).toString();

				ArrayAdapter<CharSequence> subCategoryAdapter = ArrayAdapter
						.createFromResource(
								context,
								context.getResources().getIdentifier(
										packageName + ":array/" + selected,
										null, null),
								android.R.layout.simple_spinner_item);
				subCategoryAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				mSubCategory.setAdapter(subCategoryAdapter); // 서브카테고리 바꿈.

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
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
	 * 1 -> 01 한자리수면 0을 앞에 붙여주는 함수.
	 * 
	 * @param c
	 *            한자리 수
	 * @return 두글자
	 */
	private static String pad(int c) {
		if (c >= 10) {
			return String.valueOf(c);
		} else
			return "0" + String.valueOf(c);
	}
}
