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
		mDBOpen = new DBOpen(paramContext);
		mDB = mDBOpen.getmDB();
	}

	public ArrayList<Schedule> getSchedule() {
		ArrayList<Schedule> schedule = new ArrayList<Schedule>();
		try {
			Cursor cursor = mDB
					.query(true,
							"schedule",
							new String[] { "category_id", "start_time",
									"end_time", "title" },
							"DATE(SCHEDULE.start_time) >= DATE('now', 'localtime') AND DATE(SCHEDULE.end_time) <= DATE('now', 'localtime')",
							null, null, null, null, null, null);
			if (cursor != null) {

				Log.d("After query", "start");
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					schedule.add(new Schedule(cursor.getInt(0), cursor
							.getString(1), cursor.getString(2), cursor
							.getString(3)));

				}
				return schedule;
			}
		} catch (Exception localException) {
			Log.e("SQL error", localException.toString());
			return null;
		}
		Log.d("query", "Null");
		return null;
	}

	public void putSchedule(final String title, final String startTime,
			String end_time, int categoryId) {
		String str = "INSERT INTO SCHEDULE ( category_id, start_time, end_time, title) VALUES("
				+ categoryId
				+ ", '"
				+ startTime
				+ "','"
				+ end_time
				+ "', '"
				+ title + "');";
		mDB.execSQL(str);
	}
}
