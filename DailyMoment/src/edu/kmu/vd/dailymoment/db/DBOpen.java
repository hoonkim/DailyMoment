package edu.kmu.vd.dailymoment.db;

//@TODO 복구.
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpen extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 3;

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

		String categories[] = { "Eat", "Celebrate", "Work", "Rest", "Outdoor",
				"Indoor" };

		putCategory(categories);

		/* 서브 카테고리 */

		ArrayList<String[]> subCategories = new ArrayList<String[]>();

		/* Eat */
		subCategories.add(new String[] { "Breakfast", "Drinking", "Cooking",
				"Snack", "Coffee", "Dinner", "Dessert", "Chocolate fountain" });
		/* Celebrate */
		subCategories.add(new String[] { "Birthday", "Anniversary",
				"Graduation", "Baby", "Loving" });
		/* Work */
		subCategories.add(new String[] { "Working", "Presentation", "Exam",
				"School", "Study", "buisness trip", "Save the city" });
		/* Rest */
		subCategories
				.add(new String[] { "Sleep", "Nap", "Nothing1", "Nothing2" });
		/* Outdoor */
		subCategories.add(new String[] { "Workout", "Walk", "Picnic", "Travel",
				"Drive", "Sports", "Camping", "Space travel" });
		/* Indoor */
		subCategories.add(new String[] { "Movies", "Reading", "Exhibition",
				"Shopping", "Show", "Loving", "Gaming" });

		for (int i = 0; i < subCategories.size(); i++) {
			putSubCategory(i + 1, subCategories.get(i));
		}

		mDB = getWritableDatabase();

	}

	public SQLiteDatabase getmDB() {
		return mDB;
	}

	public void onCreate(final SQLiteDatabase db) {

		db.execSQL("CREATE TABLE " + TABLE_CATEGORY
				+ "(cid integer primary key autoincrement, "
				+ "cname VARCHAR(20))");

		db.execSQL("CREATE TABLE " + TABLE_SCHEDULE
				+ "(sid integer primary key autoincrement, "
				+ "category_id integer, start_time TIMESTAMP, "
				+ "end_time TIMESTAMP, " + "title VARCHAR (20), "
				+ "FOREIGN KEY(category_id) REFERENCES " + TABLE_CATEGORY
				+ "(cid))");

		db.execSQL("CREATE TABLE " + TABLE_SUBCATEGORY + "(c_id integer, "
				+ "scid integer primary key autoincrement, "
				+ "cname VARCHAR(20), " + "FOREIGN KEY(c_id) REFERENCES "
				+ TABLE_CATEGORY + "(cid))");

		for (ContentValues category : mCategories) {
			db.insert(TABLE_CATEGORY, null, category);
		}

		for (ContentValues subCategory : mSubCategories) {
			db.insert(TABLE_SUBCATEGORY, null, subCategory);
		}

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

	private void putCategory(String categories[]) {
		for (String category : categories) {
			ContentValues temp = new ContentValues();
			temp.put("cname", category);
			mCategories.add(temp);
		}
	}

	private void putSubCategory(int categoryId, String subCategories[]) {

		for (String category : subCategories) {
			ContentValues temp = new ContentValues();
			temp.put("c_id", categoryId);
			temp.put("cname", category);
			mSubCategories.add(temp);
		}

	}
}