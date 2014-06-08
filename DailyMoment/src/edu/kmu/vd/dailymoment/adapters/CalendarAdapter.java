package edu.kmu.vd.dailymoment.adapters;
//@TODO 복구.
import java.util.ArrayList;

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

	public CalendarAdapter(Context paramContext, int paramInt,
			ArrayList<DayInfo> paramArrayList) {
		this.mContext = paramContext;
		this.mDayList = paramArrayList;
		this.mResource = paramInt;
		this.mLiInflater = ((LayoutInflater) this.mContext
				.getSystemService("layout_inflater"));
	}

	public int getCount() {
		return this.mDayList.size();
	}

	public Object getItem(int paramInt) {
		return this.mDayList.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return 0L;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		DayInfo localDayInfo = (DayInfo) this.mDayList.get(paramInt);
		DayViewHolde localDayViewHolde = null;
		if (paramView == null) {
			paramView = this.mLiInflater.inflate(this.mResource, null);
			localDayViewHolde = new DayViewHolde();
			localDayViewHolde.llBackground = ((LinearLayout) paramView
					.findViewById(2131099721));
			localDayViewHolde.tvDay = ((TextView) paramView
					.findViewById(2131099722));
			paramView.setTag(localDayViewHolde);
		}
		while (true) {
			if ((localDayInfo != null) && (localDayInfo.isInMonth()))
				localDayViewHolde.tvDay.setText(localDayInfo.getDay());
			return paramView;
		}
	}

	public class DayViewHolde {
		public LinearLayout llBackground;
		public TextView tvDay;

		public DayViewHolde() {
		}
	}
}

/*
 * Location:
 * C:\Users\KaiEn\Desktop\edu.kmu.vd.dailymoment-1\edu.kmu.vd.dailymoment
 * -1_dex2jar.jar Qualified Name:
 * edu.kmu.vd.dailymoment.calendar.CalendarAdapter JD-Core Version: 0.6.0
 */