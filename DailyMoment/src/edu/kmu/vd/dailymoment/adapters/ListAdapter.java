package edu.kmu.vd.dailymoment.adapters;

import edu.kmu.vd.dailymoment.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<Schedule> {
	private LayoutInflater mInflater;

	public ListAdapter(Context paramContext, int paramInt) {
		super(paramContext, paramInt);
		mInflater = ((LayoutInflater) paramContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
	}

	public View getView(int location, View view, ViewGroup viewGroup) {
		Schedule localSchedule = (Schedule) getItem(location);
		ItemHolder holder;
		if (view == null) {
			view = mInflater.inflate(R.layout.schedule, viewGroup, false);
			holder = new ItemHolder();
			// holder.mIcon = (ImageView) view
			// .findViewById(R.id.day_activity_schedule_icon);
			holder.mDate = (TextView) view
					.findViewById(R.id.day_activity_schedule_title);
			holder.mTitle = (TextView) view
					.findViewById(R.id.day_activity_schedule_time);
			view.setTag(holder);
		} else {
			holder = (ItemHolder) view.getTag();
		}
	//	holder.mIcon.setImageResource(localSchedule.getIcon());
		holder.mDate.setText(localSchedule.getDate());
		holder.mTitle.setText(localSchedule.getTitle());
		return view;

	}

	private class ItemHolder {
		TextView mDate;
		ImageView mIcon;
		TextView mTitle;
	}
}
