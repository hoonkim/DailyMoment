package edu.kmu.vd.dailymoment.adapters;

//@TODO 복구.
import java.util.ArrayList;

import edu.kmu.vd.dailymoment.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CalendarAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<DayInfo> mDayList;
	private LayoutInflater mLiInflater;
	private int mResource;

	public CalendarAdapter(Context context, int textResource,
			ArrayList<DayInfo> dayList) {
		mContext = context;
		mDayList = dayList;
		mResource = textResource;
		mLiInflater = ((LayoutInflater) mContext
				.getSystemService("layout_inflater"));
	}

	public int getCount() {
		return mDayList.size();
	}

	public Object getItem(int paramInt) {
		return mDayList.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return 0L;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		DayInfo localDayInfo = (DayInfo) mDayList.get(position);
		DayViewHolder dayViewHolder = null;
		if (convertView == null) {
			convertView = mLiInflater.inflate(mResource, null);
			dayViewHolder = new DayViewHolder();

			dayViewHolder.tvDay = ((TextView) convertView
					.findViewById(R.id.day_cell));
			convertView.setTag(dayViewHolder);
		}
		while (true) {
			if ((localDayInfo != null) && (localDayInfo.isInMonth()))
				dayViewHolder.tvDay.setText(localDayInfo.getDay());
			return convertView;
		}
	}

	public class DayViewHolder {
		public LinearLayout llBackground;
		public TextView tvDay;

		public DayViewHolder() {
		}
	}
}

/*
 * Location:
 * C:\Users\KaiEn\Desktop\edu.kmu.vd.dailymoment-1\edu.kmu.vd.dailymoment
 * -1_dex2jar.jar Qualified Name:
 * edu.kmu.vd.dailymoment.calendar.CalendarAdapter JD-Core Version: 0.6.0
 */