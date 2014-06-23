package edu.kmu.vd.dailymoment.fragments;

import java.util.Locale;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import edu.kmu.vd.dailymoment.R;
import edu.kmu.vd.dailymoment.activities.EditActivity;
import edu.kmu.vd.dailymoment.db.DBController;

public class EditFragment extends DialogFragment {

	private int mSid;
	private String mTitle;
	private String mCategory;
	private String mStartTime;
	private String mEndTime;

	private String mDate;

	public static EditFragment newInstance(int sid, String title,
			String category, String startTime, String endTime, String date) {
		EditFragment f = new EditFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putInt("sid", sid);
		args.putString("title", title);
		args.putString("category", category);
		args.putString("startTime", startTime);
		args.putString("EndTime", endTime);
		args.putString("date", date);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
		mSid = getArguments().getInt("sid");
		mTitle = getArguments().getString("title");
		mCategory = getArguments().getString("category");
		mStartTime = getArguments().getString("startTime");
		mEndTime = getArguments().getString("EndTime");
		mDate = getArguments().getString("date");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_edit, container, false);

		getDialog().getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));

		/* 아이콘 설정 */
		ImageView imageView = (ImageView) v
				.findViewById(R.id.fragment_edit_icon);

		int icon = getResources().getIdentifier(
				"edu.kmu.vd.dailymoment:drawable/icon_" + mCategory + "_05",
				null, null);

		imageView.setImageResource(icon);

		/* 타이틀 */
		TextView title = (TextView) v.findViewById(R.id.fragment_edit_title);
		title.setText(mTitle);

		/* 카테고리 */
		TextView category = (TextView) v
				.findViewById(R.id.fragment_edit_subcategory);
		category.setText(mCategory.split("_")[1]);

		/* 시간 */
		TextView time = (TextView) v.findViewById(R.id.fragment_edit_time);
		time.setText(mStartTime + " - " + mEndTime);

		/* 수정 버튼 리스너 */
		TextView editButton = (TextView) v
				.findViewById(R.id.fragment_edit_button_edit);
		editButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Intent intent = new Intent(getActivity(), EditActivity.class);
				dismiss();
				intent.putExtra("date", mDate);
				intent.putExtra("sid", mSid);

				String category = mCategory.split("_")[0];
				category = category.substring(0, 1).toUpperCase(Locale.ENGLISH)
						+ category.substring(1);
				intent.putExtra("category", category);

				String subCategory = mCategory.split("_")[1];
				subCategory = subCategory.substring(0, 1).toUpperCase(
						Locale.ENGLISH)
						+ subCategory.substring(1);
				intent.putExtra("subCategory", subCategory);
				
				intent.putExtra("title", mTitle);
				intent.putExtra("start", mStartTime);
				intent.putExtra("end", mEndTime);

				startActivity(intent);

				return false;
			}
		});

		/* 삭제 버튼 리스너 */
		TextView deleteButton = (TextView) v
				.findViewById(R.id.fragment_edit_button_delete);
		deleteButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				new DBController(getActivity()).delete(mSid);

				// ((Activity) getActivity()).onResume();
				dismiss();
				return false;
			}
		});

		/* 종료 버튼 리스너 */
		ImageView closeButton = (ImageView) v
				.findViewById(R.id.fragment_edit_button_close);
		closeButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				dismiss();

				return false;
			}
		});

		return v;
	}
}
