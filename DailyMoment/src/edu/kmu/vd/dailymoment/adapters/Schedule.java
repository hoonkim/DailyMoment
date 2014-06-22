package edu.kmu.vd.dailymoment.adapters;

import android.content.Context;

//@TODO 복구.
public class Schedule {
	private String mCatetgory;
	private String mEndTime;
	private String mStartTime;
	private String mTitle;

	public Schedule(String category, String startTime, String endTime,
			String title) {
		mCatetgory = category;
		mStartTime = startTime;
		mEndTime = endTime;
		mTitle = title;
	}

	public String getDate() {
		return mStartTime + " - " + mEndTime;
	}

	public int getIcon(Context context, String time) {

		String iconNumber = "_";

		int startTimeMin = getHour(mStartTime) * 60 + getMin(mStartTime);
		int endTimeMin = getHour(mEndTime) * 6 - +getMin(mEndTime);
		int timeMin = getHour(time) * 60 + getMin(time);

		int diffStart = (startTimeMin - timeMin);

		float diffEnd = (endTimeMin - timeMin);

		if (diffStart > 60) {
			iconNumber += "01";
		} else if (diffStart > 40) {
			iconNumber += "02";
		} else if (diffStart > 20) {
			iconNumber += "03";
		} else if (diffStart > 0) {
			iconNumber += "04";
		} else if (diffEnd < 0) {
			iconNumber += "10";
		} else if ((diffEnd / (endTimeMin - startTimeMin)) > (0.8)) {
			iconNumber += "05";
		} else if ((diffEnd / (endTimeMin - startTimeMin)) > (0.6)) {
			iconNumber += "06";
		} else if ((diffEnd / (endTimeMin - startTimeMin)) > (0.4)) {
			iconNumber += "07";
		} else if ((diffEnd / (endTimeMin - startTimeMin)) > (0.2)) {
			iconNumber += "08";
		} else {
			iconNumber += "09";
		}

		int resource = context.getResources().getIdentifier(
				"edu.kmu.vd.dailymoment:drawable/icon_" + mCatetgory
						+ iconNumber, null, null);
		return resource;
	}

	public String getTitle() {
		return mTitle;
	}

	private int getHour(String time) {
		return Integer.parseInt(time.substring(0, 2));
	}

	private int getMin(String time) {
		return Integer.parseInt(time.substring(3, 5));
	}
}