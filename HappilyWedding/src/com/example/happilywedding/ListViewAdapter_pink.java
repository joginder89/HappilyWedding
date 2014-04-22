package com.example.happilywedding;

import java.util.ArrayList;
import java.util.HashMap;

import com.annadih.wedding.dto.TaskData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ListViewAdapter_pink extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<TaskData> data;
	ImageLoader imageLoader;
	TaskData resultp;
	String task_id;
	int toggleStatus = 0;
	boolean[] toogleChecked;
	String taskname;
	boolean tooglestatuschecked = false;
	boolean[] stt;

	public ListViewAdapter_pink(Context context, ArrayList<TaskData> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
		toogleChecked = new boolean[arraylist.size()];
		stt = new boolean[arraylist.size()];
//		A2NewListview_toogle.flag_toggle = true;
				Intent sendToggleValue = new Intent("android.toggleValue1");

				Bundle extras = new Bundle();
				extras.putInt("PROGRESS_VALUE",
						A2NewListview_tooglepink.toggleCount1);
				extras.putInt("TOGGLE_STATUS", toggleStatus);
				// extras.putString("Task_idd", task_id);
				extras.putBooleanArray("Toogle_Statusboolean", stt);
				sendToggleValue.putExtras(extras);
				context.sendBroadcast(sendToggleValue);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		final TextView rank;
		final ToggleButton toggleButton;
		ImageView flag;
		LinearLayout linearLayout;
		final TextView task_idTv;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.sublist_secondpage_pink,
				parent, false);
		// itemView.setBackground(R.drawable.left_pink);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		rank = (TextView) itemView.findViewById(R.id.textswtich1);

		// Locate the ImageView in listview_item.xml
		flag = (ImageView) itemView.findViewById(R.id.imageswitch1);

		linearLayout = (LinearLayout) itemView
				.findViewById(R.id.linearlayout_image);
		toggleButton = (ToggleButton) itemView
				.findViewById(R.id.toggleButton13);
		task_idTv = (TextView) itemView.findViewById(R.id.text_toggleidpink);
		// Capture position and set results to the TextViews
		rank.setText(resultp.getTask_name());
		task_idTv.setText(resultp.getTask_id());
		task_idTv.setVisibility(View.INVISIBLE);
		toggleButton.setBackgroundResource(R.drawable.btntoggle_selector);
		boolean togleStatus = false;
		if (resultp.getMappingid().equals("1")) {
			togleStatus = true;
		} else {
			togleStatus = false;
		}
		toggleButton.setChecked(togleStatus);
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		// imageLoader.DisplayImage(resultp.get(A2NewListview_toogle.FLAG),
		// flag);
		// get text from textview.....................

		// Capture ListView item click
		OnClickListener clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Get the position
				resultp = data.get(position);
				task_id = task_idTv.getText().toString();
				taskname = rank.getText().toString();
				// Toast.makeText(context, "Taskname" + taskname, 3000).show();
				Intent intent = new Intent(context,
						ThirdPage_list_checkbox_pink.class);
				intent.putExtra("taskname", taskname);
				intent.putExtra("position", task_id);
				context.startActivity(intent);

			}
		};
		linearLayout.setOnClickListener(clickListener);
		rank.setOnClickListener(clickListener);
		// toggleButton.setTag(new Integer(position));
		toggleButton.setChecked(stt[position]);
		toggleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				task_id = task_idTv.getText().toString();
				Log.e("Task_toogle", task_id);
				if (toggleButton.isChecked()) {
					A2NewListview_tooglepink.toggleCount1++;
					toggleStatus = 1;
					// tooglestatuschecked = true;
					data.get(position).setMappingid("1");
					stt[position] = true;
					A2NewListview_toogle.flag_toggle = false;

				} else {
					A2NewListview_tooglepink.toggleCount1--;
					toggleStatus = 0;
					// tooglestatuschecked = false;
					stt[position] = false;
					data.get(position).setMappingid("0");
					A2NewListview_toogle.flag_toggle = false;

				}
				// set broadcast the value of toogle for server as 0 or
				// 1........

				Intent sendToggleValue = new Intent("android.toggleValue1");

				Bundle extras = new Bundle();
				extras.putInt("PROGRESS_VALUE1",
						A2NewListview_tooglepink.toggleCount1);
				extras.putInt("TOGGLE_STATUS1", toggleStatus);
				// ......send the toggle status for hold o
				// listview........................
				extras.putBooleanArray("Toogle_Statusboolean", stt);
				sendToggleValue.putExtras(extras);
				context.sendBroadcast(sendToggleValue);
			}
		});

		/*if (0 == position) {
		//	A2NewListview_toogle.flag_toggle = true;
			Intent sendToggleValue = new Intent("android.toggleValue1");

			Bundle extras = new Bundle();
			extras.putInt("PROGRESS_VALUE",
					A2NewListview_tooglepink.toggleCount1);
			extras.putInt("TOGGLE_STATUS", toggleStatus);
			// extras.putString("Task_idd", task_id);
			extras.putBooleanArray("Toogle_Statusboolean", stt);
			sendToggleValue.putExtras(extras);
			context.sendBroadcast(sendToggleValue);
		}
*/
		return itemView;
	}

}
