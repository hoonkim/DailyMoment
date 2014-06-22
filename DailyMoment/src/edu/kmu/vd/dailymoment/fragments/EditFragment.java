package edu.kmu.vd.dailymoment.fragments;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import edu.kmu.vd.dailymoment.R;

public class EditFragment extends DialogFragment {

	private String mTitle;
	private String mCategory;
	private String mStartTime;
	private String mEndTime;

	public static EditFragment newInstance(String title, String category,
			String startTime, String endTime) {
		EditFragment f = new EditFragment();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putString("category", category);
		args.putString("startTime", startTime);
		args.putString("EndTime", endTime);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);
		mTitle = getArguments().getString("title");
		mCategory = getArguments().getString("category");
		mStartTime = getArguments().getString("startTime");
		mEndTime = getArguments().getString("EndTime");

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
				// TODO Auto-generated method stub
				return false;
			}
		});

		/* 삭제 버튼 리스너 */
		TextView deleteButton = (TextView) v
				.findViewById(R.id.fragment_edit_button_delete);
		deleteButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
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
