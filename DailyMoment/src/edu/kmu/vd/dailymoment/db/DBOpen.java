package edu.kmu.vd.dailymoment.db;

//@TODO 복구.
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpen extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 5;

	private static final String DATABASE_NAME = "DATABASE";
	private static final String TABLE_CATEGORY = "CATEGORY";
	private static final String TABLE_SCHEDULE = "SCHEDULE";
	private static final String TABLE_SUBCATEGORY = "SUBCATEGORY";
	private static final String TAG = "SQLiteOpenHelper";
	private SQLiteDatabase mDB;

	private ArrayList<ContentValues> mCategories;
	private ArrayList<ContentValues> mSubCategories;

	public DBOpen(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		/* ArrayList 초기화 */
		mCategories = new ArrayList<ContentValues>();
		mSubCategories = new ArrayList<ContentValues>();
		/* 카테고리 목록. */

		mDB = getWritableDatabase();
	}

	public SQLiteDatabase getmDB() {
		return mDB;
	}

	public void onCreate(final SQLiteDatabase db) {

		db.execSQL("CREATE TABLE " + TABLE_SCHEDULE
				+ "(sid integer primary key autoincrement, "
				+ "category text, start_time TIMESTAMP, "
				+ "end_time TIMESTAMP, " + "title VARCHAR (20))");

	}

	public void onUpgrade(final SQLiteDatabase db, final int olderVersion,
			final int newVersion) {
		Log.w("SQLiteOpenHelper", "디비를 버전 '" + olderVersion + "'에서 '"
				+ newVersion + "' 으로 업그레이드 합니다. 기존의 데이터가 파괴됩니다.");
		db.execSQL("Drop TABLE " + TABLE_CATEGORY);
		db.execSQL("Drop TABLE " + TABLE_SCHEDULE);
		db.execSQL("Drop TABLE " + TABLE_SUBCATEGORY);

		onCreate(db);
	}

}