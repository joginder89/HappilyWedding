package com.example.happilywedding;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	int checkBoxStatus = 0;

	private ArrayList<List_Detail> packageList;
	Activity context;
	// PackageManager packageManager;
	boolean[] itemChecked;
	List_Detail list_Detail = null;
	boolean[] stt;
	String listing_id = null;

	public ListAdapter(Activity context, ArrayList<List_Detail> packageList) {
		super();
		this.context = context;
		this.packageList = packageList;
		// this.packageManager = packageManager;
		itemChecked = new boolean[packageList.size()];
		stt = new boolean[packageList.size()];
	}

	private class ViewHolder {
		RelativeLayout relativeLayout;
		LinearLayout layout_text;
		TextView tvName;
		TextView tvKm;
		TextView tvArea;
		CheckBox ck1;
		RatingBar ratingBar;
		TextView tvlisting_id;
	}

	public int getCount() {
		return packageList.size();
	}

	public List_Detail getItem(int position) {
		return packageList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
	//	listing_id=list_Detail.getListing_id();
		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.thirdpage_sublist_checkbox,
					parent, false);
			holder = new ViewHolder();

			holder.layout_text = (LinearLayout) convertView
					.findViewById(R.id.linearlayout_textview);
			holder.relativeLayout = (RelativeLayout) convertView
					.findViewById(R.id.realtive_layout_top);

			holder.tvName = (TextView) convertView
					.findViewById(R.id.textfrName);
			holder.tvKm = (TextView) convertView.findViewById(R.id.textfrKm);
			holder.tvArea = (TextView) convertView
					.findViewById(R.id.textfrArea);
			holder.tvlisting_id = (TextView) convertView
					.findViewById(R.id.textlisting_id);
			holder.ck1 = (CheckBox) convertView.findViewById(R.id.checkBox11);
			// holder.ck1.setButtonDrawable(id);
			holder.ratingBar = (RatingBar) convertView
					.findViewById(R.id.setRating);
			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		if (packageList.size() <= 0) {
			holder.tvName.setText("No Data");

		} else {
			list_Detail = null;

			list_Detail = packageList.get(position);
			listing_id=list_Detail.getListing_id();
			int deciIndex;
			if (list_Detail != null && list_Detail.getKm().contains(".")) {
				deciIndex = list_Detail.getKm().indexOf(".") + 2;

			} else {
				deciIndex = list_Detail.getKm().length();
			}

			holder.tvName.setText(list_Detail.getName().trim());
			holder.tvlisting_id.setText(list_Detail.getListing_id().trim());
			holder.tvlisting_id.setVisibility(View.INVISIBLE);
			holder.tvKm.setText(list_Detail.getKm().substring(0, deciIndex)
					.trim());
			holder.tvArea.setText(list_Detail.getArea().trim());

			holder.ratingBar.setRating(list_Detail.getRating());
			
		}
		boolean togleStatus = false;
		if (list_Detail.getListing_id().equals("1")) {
			togleStatus = true;
		} else {
			togleStatus = false;
		}
		holder.ck1.setChecked(togleStatus);
        
	//	holder.ck1.setChecked(false);

		/*if (itemChecked[position])
			holder.ck1.setChecked(true);
		else
			holder.ck1.setChecked(false);*/
	//	holder.ck1.setChecked(stt[position]);
		holder.ck1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { // TODO Auto-generated method
				if (holder.ck1.isChecked()) {
					checkBoxStatus = 1;
					stt[position] = true;
					listing_id = holder.tvlisting_id.getText().toString();
					Log.e("Listng_id", listing_id);

				} else {
					checkBoxStatus = 0;
					stt[position] = true;
					listing_id = holder.tvlisting_id.getText().toString();
					Log.e("Listng_id", listing_id);
				}

				Intent sendToggleValue = new Intent("android.checkboxValue1");

				Bundle extras = new Bundle();
				extras.putInt("CHECKBOX_VALUE", checkBoxStatus);
				extras.putString("Listing_ID", listing_id);
				sendToggleValue.putExtras(extras);
				context.sendBroadcast(sendToggleValue);
			}
		});
		// ....................click on layout of textbox.................
		// holder.layout_text.setTag(R.id.linearlayout_textview);
		OnClickListener clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				String taskName = holder.tvName.getText().toString();
				listing_id = holder.tvlisting_id.getText().toString();
				// Toast.makeText(context, "TaskNAme=" + taskName, 3000).show();
				String disatance = holder.tvKm.getText().toString();
				Intent i = new Intent(context, LastPage_map.class);
				i.putExtra("taskname", taskName);
				i.putExtra("Listing_Id", listing_id);
				i.putExtra("dis", disatance);
				context.startActivity(i);

			}
		};
		holder.relativeLayout.setOnClickListener(clickListener);
		holder.layout_text.setOnClickListener(clickListener);
		holder.tvKm.setOnClickListener(clickListener);
		holder.tvName.setOnClickListener(clickListener);
		holder.tvArea.setOnClickListener(clickListener);
		return convertView;
	}
}
