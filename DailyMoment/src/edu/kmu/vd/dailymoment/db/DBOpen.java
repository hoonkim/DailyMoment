package edu.kmu.vd.dailymoment.db;
//@TODO 복구.
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpen extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "DATABASE";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_CATEGORY = "CATEGORY";
	private static final String TABLE_SCHEDULE = "SCHEDULE";
	private static final String TAG = "SQLiteOpenHelper";
	private SQLiteDatabase mDB = getWritableDatabase();

	public DBOpen(Context paramContext) {
		super(paramContext, "DATABASE", null, 1);
	}

	public SQLiteDatabase getmDB() {
		return this.mDB;
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
		paramSQLiteDatabase
				.execSQL("CREATE TABLE SCHEDULE(sid integer primary key autoincrement, category_id integer, start_time TIMESTAMP, end_time TIMESTAMP, title VARCHAR (20))");
		paramSQLiteDatabase
				.execSQL("CREATE TABLE CATEGORY(cid integer primary key autoincrement, cname VARCHAR(20))");
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
			int paramInt2) {
		Log.w("SQLiteOpenHelper", "디비를 버전 '" + paramInt1 + "'에서 '" + paramInt2
				+ "' 으로 업그레이드 합니다. 기존의 데이터가 파괴됩니다.");
		paramSQLiteDatabase.execSQL("Drop TABLE IF EXISTS DATABASE");
		onCreate(paramSQLiteDatabase);
	}
}

/*
 * Location:
 * C:\Users\KaiEn\Desktop\edu.kmu.vd.dailymoment-1\edu.kmu.vd.dailymoment
 * -1_dex2jar.jar Qualified Name: edu.kmu.vd.dailymoment.db.DBOpen JD-Core
 * Version: 0.6.0
 */