package edu.kmu.vd.dailymoment.activities;

//@TODO 복구.
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import edu.kmu.vd.dailymoment.R;
import edu.kmu.vd.dailymoment.services.LockService;

public class ConfigActivity extends Activity {
	static final String LOCK_SCREEN = "locked";
	static final String PREFERENCE_NAME = "Setting";
	private Switch mSwitch;
	private SharedPreferences preferences;

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
		setContentView(R.layout.activity_config);
		Log.d("경과보고", "setContentView");
		preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
		mSwitch = ((Switch) findViewById(R.id.config_switch));
		if (getPreference()) {
			Log.d("스위치기본", "켜짐");
			mSwitch.setChecked(true);
			startService(new Intent(this, LockService.class));
		}

		final Context ctx = this;
		mSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton paramCompoundButton,
					boolean paramBoolean) {
				if (paramBoolean) {
					Log.d("스위치", "켜짐");
					startService(new Intent(ctx, LockService.class));
					setPreference(true);
					return;
				} else {
					Log.d("스위치", "꺼짐");
					stopService(new Intent(ctx, LockService.class));
					setPreference(false);
				}
			}
		});
	}
}