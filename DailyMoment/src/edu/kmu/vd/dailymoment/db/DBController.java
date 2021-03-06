package edu.kmu.vd.dailymoment.db;

//@TODO 복구.
import java.util.ArrayList;
import java.util.Locale;

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

			String query = "select "
					+ "sid, category, strftime('%H:%M',start_time), strftime('%H:%M',end_time), title "
					+ "FROM SCHEDULE "
					+ "WHERE DATE(SCHEDULE.start_time) = DATE('now', 'localtime')";

			Cursor cursor = mDB.rawQuery(query, null);
			if (cursor != null) {

				Log.d("After query", "start");
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					schedule.add(new Schedule(cursor.getInt(0), cursor
							.getString(1), cursor.getString(2), cursor
							.getString(3), cursor.getString(4)));
					Log.d("Sql", "curosr.getString(0)");

				}
				return schedule;
			}
		} catch (Exception localException) {
			Log.e("SQL error", localException.toString());
			return schedule;
		}
		Log.d("query", "Null");
		return schedule;
	}

	public ArrayList<Schedule> getSchedule(String date) {
		ArrayList<Schedule> schedule = new ArrayList<Schedule>();
		try {

			String query = "select "
					+ "sid, category, strftime('%H:%M',start_time), strftime('%H:%M',end_time), title "
					+ "FROM SCHEDULE WHERE DATE(start_time) = '" + date + "'";

			Log.d("SQL QUERY ", query);
			Cursor cursor = mDB.rawQuery(query, null);
			if (cursor != null) {

				Log.d("After query", "start");
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					schedule.add(new Schedule(cursor.getInt(0), cursor
							.getString(1), cursor.getString(2), cursor
							.getString(3), cursor.getString(4)));
					Log.d("Sql", cursor.getString(0));

				}
				return schedule;
			}
		} catch (Exception localException) {
			Log.e("SQL error", localException.toString());
			return schedule;
		}
		Log.d("query", "Null");
		return schedule;
	}

	public void putSchedule(final String title, final String startTime,
			String endTime, final String category) {
		String str = "INSERT INTO SCHEDULE ( category, start_time, end_time, title) VALUES('"
				+ category.toLowerCase(Locale.ENGLISH)
				+ "', '"
				+ startTime
				+ "','" + endTime + "', '" + title + "');";
		mDB.execSQL(str);
	}

	public void delete(final int sid) {
		Log.d("Deleting", sid + "");
		mDB.delete("SCHEDULE", "sid = " + sid, null);
	}

	public void update(final int sid, final String title,
			final String startTime, String endTime, final String category) {
		String sql = "UPDATE SCHEDULE " + "SET category = '"
				+ category.toLowerCase() + "', " + "title = '" + title
				+ "', start_time = '" + startTime + "', end_time = '" + endTime
				+ "' where sid = " + sid;
		Log.d("SQL", sql);
		mDB.execSQL(sql);
	}
}
