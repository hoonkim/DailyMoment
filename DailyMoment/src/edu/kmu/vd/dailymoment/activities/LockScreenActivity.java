package edu.kmu.vd.dailymoment.activities;

//@TODO 복구.
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import edu.kmu.vd.dailymoment.adapters.ListAdapter;
import edu.kmu.vd.dailymoment.adapters.Schedule;
import edu.kmu.vd.dailymoment.db.DBController;

public class LockScreenActivity extends Activity {
	private TextView dateTextView;
	private final Handler handler = new Handler();
	private DBController mDBController;
	private ListAdapter mListAdapter;
	private ListView mListView;

	private void changeDateView() {
		Calendar localCalendar = Calendar.getInstance();
		String time = new SimpleDateFormat("HH:mm", Locale.US)
				.format(localCalendar.getTime());
		String date = new SimpleDateFormat(" EEE, MMM dd", Locale.US)
				.format(localCalendar.getTime());
		dateTextView.setText(Html.fromHtml("<u>" + time + "<br>" + date
				+ "</u>"));
		Runnable mRunnable = new Runnable() {
			public void run() {
				changeDateView();
			}
		};
		handler.postDelayed(mRunnable, 1000L);
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		getWindow().addFlags(6815744);
		Intent localIntent = getIntent();
		if (localIntent == null)
			Log.d("Intent null", "it's null");
		if (localIntent.getExtras() == null) {
			// @TODO 시발 이상함.
			Log.d("not null", "it's not null");
			startLauncher();
			finish();
			return;
		}
		setContentView(2130903067);
		dateTextView = ((TextView) findViewById(2131099719));
		Typeface localTypeface = Typeface.createFromAsset(getAssets(),
				"Roboto-Medium.ttf");
		dateTextView.setTypeface(localTypeface);
		changeDateView();

		mListView = ((ListView) findViewById(2131099720));
		mListAdapter = new ListAdapter(this, 2130903070);
		mListView.setAdapter(mListAdapter);

		mDBController = new DBController(this);
		for (Schedule schedule : mDBController.getSchedule()) {
			mListAdapter.add(schedule);
		}

		mListAdapter.add(new Schedule(1, "8:00", "9:00", "Breafast"));
		mListAdapter.add(new Schedule(2, "7:00", "9:00", "Wow"));
		mListAdapter.add(new Schedule(3, "8:00", "9:00", "Suyoung"));
		mListAdapter.add(new Schedule(4, "5:00", "9:00", "Samsong"));
		mListAdapter.add(new Schedule(5, "8:00", "9:00", "schedule!"));
		mListAdapter.add(new Schedule(6, "8:00", "9:00", "sleepy"));
		mListAdapter.add(new Schedule(7, "8:00", "9:00", "Test"));
		mListAdapter.add(new Schedule(8, "8:00", "9:00", "run"));
		mListAdapter.add(new Schedule(9, "8:00", "9:00", "siiiii"));
		mListAdapter.add(new Schedule(10, "8:00", "9:00", "qweqweqwe"));
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
}
