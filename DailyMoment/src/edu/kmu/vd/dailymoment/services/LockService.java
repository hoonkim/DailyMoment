package edu.kmu.vd.dailymoment.services;
//@TODO 복구.
import edu.kmu.vd.dailymoment.receivers.LockReceiver;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class LockService extends Service
{
  private LockReceiver mReceiver = null;

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
    this.mReceiver = new LockReceiver();
    IntentFilter localIntentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
    registerReceiver(this.mReceiver, localIntentFilter);
  }

  public void onDestroy()
  {
    super.onDestroy();
    if (this.mReceiver != null)
      unregisterReceiver(this.mReceiver);
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    super.onStartCommand(paramIntent, paramInt1, paramInt2);
    if ((paramIntent != null) && (paramIntent.getAction() == null) && (this.mReceiver == null))
    {
      this.mReceiver = new LockReceiver();
      IntentFilter localIntentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
      registerReceiver(this.mReceiver, localIntentFilter);
    }
    return 3;
  }
}

/* Location:           C:\Users\KaiEn\Desktop\edu.kmu.vd.dailymoment-1\edu.kmu.vd.dailymoment-1_dex2jar.jar
 * Qualified Name:     edu.kmu.vd.dailymoment.LockService
 * JD-Core Version:    0.6.0
 */