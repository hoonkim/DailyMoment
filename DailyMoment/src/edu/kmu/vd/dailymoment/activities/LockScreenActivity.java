package edu.kmu.vd.dailymoment.activities;

//@TODO 복구.
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import edu.kmu.vd.dailymoment.R;
import edu.kmu.vd.dailymoment.adapters.ListAdapter;
import edu.kmu.vd.dailymoment.adapters.Schedule;
import edu.kmu.vd.dailymoment.db.DBController;
import edu.kmu.vd.dailymoment.fragments.EditFragment;

public class LockScreenActivity extends Activity {
	private TextView dateTextView;
	private final Handler handler = new Handler();
	private DBController mDBController;
	private ListAdapter mListAdapter;
	private ListView mListView;

	private Context mContext;

	@Override
	public void onBackPressed() {
		return;
	};

	private void changeDateView() {
		Calendar localCalendar = Calendar.getInstance();
		String time = new SimpleDateFormat("HH:mm", Locale.US).format(
				localCalendar.getTime()).toUpperCase();

		String date = new SimpleDateFormat(" EEE, MMM dd", Locale.US).format(
				localCalendar.getTime()).toUpperCase();
		dateTextView.setText(Html.fromHtml("<u>" + time + "<br>" + date
				+ "</u>"));
		updatePhoto(time);
		Runnable mRunnable = new Runnable() {
			public void run() {
				changeDateView();
			}
		};
		handler.postDelayed(mRunnable, 1000L);
	}

	protected void onCreate(Bundle paramBundle) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(paramBundle);

		mContext = this;

		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		Intent localIntent = getIntent();
		if (localIntent == null)
			Log.d("Intent null", "it's null");
		if (localIntent.getExtras() == null) {
			// @TODO 시발 이상함.
			Log.d("not null", "it's not null");
			startLauncher();
			finish();
		}
		setContentView(R.layout.activity_lock_screen);
		dateTextView = ((TextView) findViewById(R.id.lockscreen_activity_time));
		mListView = ((ListView) findViewById(R.id.lockscreen_activity_schedule_list));

		TextView unLockButton = (TextView) findViewById(R.id.activity_lockscreen_unlock);
		unLockButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				finish();
				// 종료 애니메이션
				overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
				return false;
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				Fragment prev = getFragmentManager()
						.findFragmentByTag("dialog");
				if (prev != null) {
					ft.remove(prev);
				}
				ft.addToBackStack(null);

				Schedule schedule = (Schedule) parent
						.getItemAtPosition(position);
				
				/* 날짜 받아 옴 */
				Calendar localCalendar = Calendar.getInstance();
				String date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(
						localCalendar.getTime()).toUpperCase();

				DialogFragment newFragment = EditFragment.newInstance(
						schedule.getId(), schedule.getTitle(),
						schedule.getCategory(), schedule.getStartTime(),
						schedule.getEndTime(), date);
				newFragment.show(ft, "dialog");
				onResume();
			}
		});

		unLockButton.bringToFront();
		changeDateView();

	}

	public void startLauncher() {
		// @TODO 의도치않게 복구 과정에서 난독화 되버림.
		Iterator localIterator = getPackageManager()
				.queryIntentActivities(
						new Intent("android.intent.action.MAIN")
								.addCategory("android.intent.category.HOME"),
						65536).iterator();
		ResolveInfo localResolveInfo;
		do {
			if (!localIterator.hasNext())
				return;
			localResolveInfo = (ResolveInfo) localIterator.next();
		} while (getPackageName().equals(
				localResolveInfo.activityInfo.packageName));
		ComponentName localComponentName = new ComponentName(
				localResolveInfo.activityInfo.applicationInfo.packageName,
				localResolveInfo.activityInfo.name);
		Intent localIntent = new Intent("android.intent.action.MAIN");
		localIntent.addCategory("android.intent.category.LAUNCHER");
		localIntent.setFlags(270532608);
		localIntent.setComponent(localComponentName);
		startActivity(localIntent);
	}

	private void updatePhoto(String time) {
		int max = mListView.getLastVisiblePosition();
		for (int i = mListView.getFirstVisiblePosition(); i <= max; i++) {
			View view = mListView.getChildAt(i
					- mListView.getFirstVisiblePosition());
			Schedule schedule = (Schedule) mListView.getItemAtPosition(i);
			try {
				((ImageView) view.findViewById(R.id.day_activity_schedule_icon))
						.setImageResource(schedule.getIcon(mContext, time));
			} catch (Exception e) {

			}

		}

	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("LockScreen", "OnResume");
		mListAdapter = new ListAdapter(this, R.layout.schedule);
		mListView.setAdapter(mListAdapter);
		mDBController = new DBController(this);
		for (Schedule schedule : mDBController.getSchedule()) {
			mListAdapter.add(schedule);
		}
	}

}
