package edu.kmu.vd.dailymoment.activities;
//@TODO 복구.
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import edu.kmu.vd.dailymoment.adapters.ListAdapter;
import edu.kmu.vd.dailymoment.adapters.Schedule;
import edu.kmu.vd.dailymoment.db.DBController;

public class DayActivity extends Activity
{
  private DBController mDBController;
  private String mDate;
  private ListAdapter mListAdapter;
  private ListView mListView;
  private String mMonth;
  private String mYear;

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903066);
    Intent localIntent = getIntent();
    this.mYear = localIntent.getExtras().getString("Year");
    this.mMonth = localIntent.getExtras().getString("Month");
    this.mDate = localIntent.getExtras().getString("Date");
    TextView localTextView = (TextView)findViewById(2131099717);
    localTextView.setText("< " + this.mMonth + "/" + this.mDate);
    localTextView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
      {
        DayActivity.this.onBackPressed();
        return false;
      }
    });
    this.mListView = ((ListView)findViewById(2131099716));
    this.mListAdapter = new ListAdapter(this, 2130903070);
    this.mListView.setAdapter(this.mListAdapter);
    this.mDBController = new DBController(this);
    Iterator localIterator = this.mDBController.getSchedule().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.mListAdapter.add(new Schedule(1, "8:00", "9:00", "Breafast"));
        this.mListAdapter.add(new Schedule(2, "7:00", "9:00", "Wow"));
        this.mListAdapter.add(new Schedule(3, "8:00", "9:00", "Suyoung"));
        this.mListAdapter.add(new Schedule(4, "5:00", "9:00", "Samsong"));
        this.mListAdapter.add(new Schedule(5, "8:00", "9:00", "schedule!"));
        this.mListAdapter.add(new Schedule(6, "8:00", "9:00", "sleepy"));
        this.mListAdapter.add(new Schedule(7, "8:00", "9:00", "Test"));
        this.mListAdapter.add(new Schedule(8, "8:00", "9:00", "run"));
        this.mListAdapter.add(new Schedule(9, "8:00", "9:00", "siiiii"));
        return;
      }
      Schedule localSchedule = (Schedule)localIterator.next();
      this.mListAdapter.add(localSchedule);
    }
  }
}

/* Location:           C:\Users\KaiEn\Desktop\edu.kmu.vd.dailymoment-1\edu.kmu.vd.dailymoment-1_dex2jar.jar
 * Qualified Name:     edu.kmu.vd.dailymoment.calendar.DayActivity
 * JD-Core Version:    0.6.0
 */