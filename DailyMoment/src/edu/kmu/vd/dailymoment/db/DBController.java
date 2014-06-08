package edu.kmu.vd.dailymoment.db;
//@TODO 복구.
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import edu.kmu.vd.dailymoment.adapters.Schedule;

public class DBController {
	private SQLiteDatabase mDB;
	private DBOpen mDBOpen;

	public DBController(Context paramContext) {
		this.mDBOpen = new DBOpen(paramContext);
		this.mDB = this.mDBOpen.getmDB();
	}

	public ArrayList<Schedule> getSchedule() {
		ArrayList<Schedule> localArrayList = new ArrayList<Schedule>();
		try {
			Cursor localCursor = this.mDB
					.query(true,
							"schedule",
							new String[] { "category_id", "start_time",
									"end_time", "title" },
							"DATE(SCHEDULE.start_time) >= DATE('now', 'localtime') AND DATE(SCHEDULE.end_time) <= DATE('now', 'localtime')",
							null, null, null, null, null, null);
			if (localCursor != null) {
				localCursor.moveToFirst();
				Log.d("After query", "start");
				while (true) {
					if (localCursor.isAfterLast())
						return localArrayList;
					Log.d("Query Good", localCursor.getString(3));
					localArrayList.add(new Schedule(localCursor.getInt(0),
							localCursor.getString(1), localCursor.getString(2),
							localCursor.getString(3)));
					localCursor.moveToNext();
				}
			}
		} catch (Exception localException) {
			Log.e("SQL error", localException.toString());
			return null;
		}
		Log.d("query", "Null");
		return null;
	}

	public void putSchedule(String paramString1, String paramString2,
			String paramString3, int paramInt) {
		String str = "INSERT INTO SCHEDULE ( category_id, start_time, end_time, title) VALUES("
				+ paramInt
				+ ", '"
				+ paramString2
				+ "','"
				+ paramString3
				+ "', '" + paramString1 + "');";
		this.mDB.execSQL(str);
	}
}
