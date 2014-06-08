package edu.kmu.vd.dailymoment.receivers;
//@TODO 복구.

import edu.kmu.vd.dailymoment.activities.LockScreenActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LockReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction().equals("android.intent.action.SCREEN_OFF"))
    {
      Intent localIntent = new Intent(paramContext, LockScreenActivity.class);
      localIntent.addFlags(268435456);
      localIntent.putExtra("launch", "true");
      paramContext.startActivity(localIntent);
    }
  }
}

/* Location:           C:\Users\KaiEn\Desktop\edu.kmu.vd.dailymoment-1\edu.kmu.vd.dailymoment-1_dex2jar.jar
 * Qualified Name:     edu.kmu.vd.dailymoment.LockReceiver
 * JD-Core Version:    0.6.0
 */