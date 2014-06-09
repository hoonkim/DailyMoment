package edu.kmu.vd.dailymoment.receivers;

//@TODO 복구.

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import edu.kmu.vd.dailymoment.activities.LockScreenActivity;

public class LockReceiver extends BroadcastReceiver {
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
			Intent lockScreenIntent = new Intent(context,
					LockScreenActivity.class);
			lockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			lockScreenIntent.putExtra("launch", "true");
			context.startActivity(lockScreenIntent);
		}
	}
}