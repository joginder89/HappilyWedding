package com.example.happilywedding;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class LastPage_map_pink extends Activity {

	ProgressBar progressBar;
	TextView tvaddrs, tvphone, tvdistance;
	RatingBar ratingBar;
	int ss;
	ShowAsynktask asyncTaskk;
	private String url = "http://hrm.testserver87.com/wedding/display_listing_web.php?listingid=1";
	String address;
	String number;
	String email;
	String distnce;
	String listing_id=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lastpage_map_pink);
		Button email = (Button) findViewById(R.id.butemail);
		Bundle bundle1=getIntent().getExtras();
		listing_id=bundle1.getString("Listing_Id");
		email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent ii = new Intent(getApplicationContext(), Emaill.class);
				startActivity(ii);
			}
		});
		TextView tv = (TextView) findViewById(R.id.text4Name);
		Bundle bundle = getIntent().getExtras();
		String taskname = bundle.getString("taskname");
		tv.setText(taskname);
		Bundle bun = getIntent().getExtras();
		distnce = bun.getString("dis");

		progressBar = (ProgressBar) findViewById(R.id.progressdetail1);
		progressBar.bringToFront();
		tvaddrs = (TextView) findViewById(R.id.textaddress4);
		tvphone = (TextView) findViewById(R.id.textPhoneNo);
		tvdistance = (TextView) findViewById(R.id.textDistance);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar11);

		WebView webView = (WebView) findViewById(R.id.webV);
		webView.loadUrl("file:///android_asset/map.html");
		webView.getSettings().setJavaScriptEnabled(true);

		asyncTaskk = new ShowAsynktask();
		asyncTaskk.execute();
	}

	class ShowAsynktask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			try {
				String response = z_CustomHttpClient.executeHttpGet("http://hrm.testserver87.com/wedding/display_listing_web.php?listingid="+listing_id);
				String re = response.toString();
				Log.e("res", re);
				JSONArray arr = new JSONArray(response);
				String str = arr.toString();

				int len = arr.length();
				// listName_Arry = new String[len];
				// listArea_arry = new String[len];
				for (int i = 0; i < len; i++) {
					JSONObject pr = arr.getJSONObject(i);
					address = pr.getString("listing_address");
					number = pr.getString("listing_number");
					email = pr.getString("listing_email");
					String rating = pr.getString("listing_rating");
					int s = Integer.parseInt(rating);
					ss = s;
					Log.e("hh", address + number + email + rating
							+ "ratingjson" + ss);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (progressBar != null)
				progressBar.setVisibility(View.GONE);

			try {
				tvaddrs.setText(address);
				tvphone.setText(number);
				ratingBar.setRating(ss);
				ratingBar.setNumStars(5);
				tvdistance.setText(distnce + "Km");
			}

			catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

		}
	}

}
