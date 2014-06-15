package edu.kmu.vd.dailymoment.activities;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import edu.kmu.vd.dailymoment.R;
import edu.kmu.vd.dailymoment.services.LockService;

public class ConfigActivity extends Activity {
	static final String LOCK_SCREEN = "locked";
	static final String PREFERENCE_NAME = "Setting";
	private ImageView mSwitch;
	private SharedPreferences preferences;

	private boolean mIsSwitchOn;

	private boolean getPreference() {
		return preferences.getBoolean("locked", true);
	}

	private void setPreference(boolean paramBoolean) {
		SharedPreferences.Editor localEditor = preferences.edit();
		localEditor.putBoolean("locked", paramBoolean);
		localEditor.commit();
	}

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_config);
		Log.d("경과보고", "setContentView");
		preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
		mIsSwitchOn = getPreference();
		mSwitch = ((ImageView) findViewById(R.id.config_switch));
		if (mIsSwitchOn) {
			Log.d("스위치기본", "켜짐");
			mSwitch.setImageResource(R.drawable.switch_on);
			startService(new Intent(this, LockService.class));
		} else {
			mSwitch.setImageResource(R.drawable.switch_off);
		}

		final Context ctx = this;
		mSwitch.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (mIsSwitchOn) {
					mSwitch.setImageResource(R.drawable.switch_off);
					stopService(new Intent(ctx, LockService.class));
					mIsSwitchOn = !mIsSwitchOn;
				} else {
					mSwitch.setImageResource(R.drawable.switch_on);
					startService(new Intent(ctx, LockService.class));
					mIsSwitchOn = !mIsSwitchOn;
				}
				return false;
			}
		});

		LinearLayout backButton = (LinearLayout) findViewById(R.id.activity_config_back);
		backButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				onBackPressed();
				return false;
			}
		});
	}
}