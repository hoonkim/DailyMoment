package edu.kmu.vd.dailymoment.adapters;

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
		this.mInflater = ((LayoutInflater) paramContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
	}

	public View getView(int paramInt, View convertView, ViewGroup paramViewGroup) {
		Schedule localSchedule = (Schedule) getItem(paramInt);
		ItemHolder holder;
		if (convertView == null) {
			convertView = this.mInflater.inflate(2130903070, paramViewGroup,
					false);
			holder = new ItemHolder();
			holder.mIcon = ((ImageView) convertView.findViewById(2131099723));
			holder.mDate = ((TextView) convertView.findViewById(2131099725));
			holder.mTitle = ((TextView) convertView.findViewById(2131099724));
			convertView.setTag(holder);
		} else {
			holder = (ItemHolder) convertView.getTag();
		}
		holder.mIcon.setImageResource(localSchedule.getIcon());
		holder.mDate.setText(localSchedule.getDate());
		holder.mTitle.setText(localSchedule.getTitle());
		return convertView;

	}

	private class ItemHolder {
		TextView mDate;
		ImageView mIcon;
		TextView mTitle;
	}
}
