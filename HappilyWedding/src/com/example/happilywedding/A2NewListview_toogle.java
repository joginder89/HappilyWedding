package com.example.happilywedding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.annadih.wedding.dto.TaskData;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DigitalClock;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class A2NewListview_toogle extends Activity {

	private String url = "http://hrm.testserver87.com/wedding/displaytask.php?userid=";
	ShowAsynktask3 asyncTask;
	ProgressBar progressBar1;
	int progressStatus = 0;
	ProgressBar progressBar;
	static int toggleCount = 0;
	private BroadcastReceiver toggleValueReceiver;
	public int togglStatus = 0;
	public String taskid = null;
	ListView lv1;
	ArrayList<TaskData> arraylist;
	public static String[] passData;
	static String Taskname = "task_name";
	// $$$$$$$$$$$$$$$$$$$$
	static String Task_id = "task_id";
	static String FLAG = "task_image";
	public boolean[] status;
	String str = null;
static boolean flag_toggle=false;
	public static String usrid = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.secondpage_list_toogle);
		if (savedInstanceState != null) {
			status = savedInstanceState.getBooleanArray("status");
		}
		progressBar1 = (ProgressBar) findViewById(R.id.progressStatus);
		progressBar = (ProgressBar) findViewById(R.id.progressswitch1);
		TextView txtDay = (TextView) findViewById(R.id.tvDays1);
		TextView txtHr = (TextView) findViewById(R.id.tvHr1);
		TextView txtMin = (TextView) findViewById(R.id.tvMin1);

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		Date currentLocalTime = (Date) cal.getTime();
		SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyy HH:mm");
		date.setTimeZone(TimeZone.getTimeZone("GMT"));
		String localTime = date.format(currentLocalTime);
		Log.e("CurrentTime", localTime);

		String dateStart = localTime;
		String dateStop = loadSavedPreferences1();
		// Log.e("DateShared", dateStop);

		// HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		Date d1 = null;
		Date d2 = null;
		String remaingMinute = null;
		String remaingHours = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			// long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			// System.out.print(diffSeconds + " seconds.");

			str = String.valueOf(diffDays);
			remaingHours = String.valueOf(diffHours);
			remaingMinute = String.valueOf(diffMinutes);
			// Toast.makeText(getApplicationContext(), "Days rem=" + str, 3000)
			// .show();
			Log.e("DiffrTime=", str + remaingHours + remaingMinute);
			txtDay.setText(str);
			txtHr.setText(remaingHours);
			txtMin.setText(remaingMinute);
		} catch (Exception e) {

		}

		lv1 = (ListView) findViewById(R.id.list1);
		usrid = loadSavedPreferences();
		asyncTask = new ShowAsynktask3();
		asyncTask.execute();

	}

	private String loadSavedPreferences() {

		SharedPreferences sharedPreferences = PreferenceManager

		.getDefaultSharedPreferences(this);

		String name = sharedPreferences.getString("USER_ID", "YourId");

		return name;

	}

	private String loadSavedPreferences1() {

		SharedPreferences sharedPreferences = PreferenceManager

		.getDefaultSharedPreferences(this);

		// boolean checkBoxValue =
		// sharedPreferences.getBoolean("CheckBox_Value", false);

		String name = sharedPreferences.getString("storedName1", "YourName");

		// Toast.makeText(getApplicationContext(), "SharedValue=" + name, 3000)
		// .show();
		return name;
	}

	class ShowAsynktask3 extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			arraylist = new ArrayList<TaskData>();
			try {

				String response = z_CustomHttpClient
						.executeHttpGet(url + usrid);
				JSONArray arr = new JSONArray(response);
				String str = arr.toString();
				Log.e("sur", str);
				int len = arr.length();

				for (int i = 0; i < len; i++) {
					
					TaskData taskData=new TaskData();
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject jsonobject = arr.getJSONObject(i);
					// Retrive JSON Objects
					
					map.put("task_name", jsonobject.getString("task_name"));
					map.put("task_id", jsonobject.getString("task_id"));
					String maping_id = jsonobject
							.getString("user_task_mapping_status_id");
					Log.e("Mappingid", maping_id);
					taskData.setTask_id(jsonobject.getString("task_id"));
					taskData.setMappingid(maping_id);
					taskData.setTask_name(jsonobject.getString("task_name"));
					String imageurl = jsonobject.getString("task_image");
					String finalimageurl = "http://hrm.testserver87.com/wedding/"
							+ imageurl;
					taskData.setTask_image(finalimageurl);
					map.put("task_image", finalimageurl);

					// Set the JSON Objects into the array
					arraylist.add(taskData);

				}
				status = new boolean[len];

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);

			try {
				if (arraylist.size() > 0) {
					ListViewAdapter adapter = new ListViewAdapter(
							A2NewListview_toogle.this, arraylist);
					// Set the adapter to the ListView
					lv1.setAdapter(adapter);

					if (progressBar != null)
						progressBar.setVisibility(View.GONE);

					progressBar1.setMax(arraylist.size());
				} else {
					Toast.makeText(getApplicationContext(),
							"Internet Problem occur!", 3000).show();
				}

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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter intentFilter = new IntentFilter("android.toggleValue");
		toggleValueReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				if (intent.getAction().equalsIgnoreCase("android.toggleValue")) {
					Bundle bundle = intent.getExtras();
					int toogleValue = bundle.getInt("PROGRESS_VALUE");
					togglStatus = bundle.getInt("TOGGLE_STATUS");
					taskid = bundle.getString("Task_idd");

					Resources res = getResources();
					Rect bounds = progressBar1.getProgressDrawable()
							.getBounds();
					progressBar1.setProgress(toogleValue);
					progressBar1.setProgressDrawable(res
							.getDrawable(R.drawable.blue_progreesbar));
					sendServerValue();
					// ...........Hold the status of toogle button...........
				//	status = bundle.getBooleanArray("Toogle_Statusboolean");
					if(flag_toggle=true){
						sendServerValue();
					}					
					else {
						sendServerValue();
					}
				}

			}
		};
		this.registerReceiver(toggleValueReceiver, intentFilter);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.unregisterReceiver(toggleValueReceiver);
	}

	void sendServerValue() {
		new HttpAsyncTask()
				.execute("http://hrm.testserver87.com/wedding/updatetaskstatus.php?userid="
						+ usrid
						+ "&taskid="
						+ taskid
						+ "&statusid="
						+ togglStatus);

	}

	class HttpAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... urls) {

			return POST(urls[0], togglStatus);
		}

		protected void onPostExecute(String result) {
		}
	}

	public static String POST(String url, int toggleStatus) {
		InputStream inputStream = null;
		String result = "";
		try {

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			String json = "";

			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("statusid", toggleStatus);

			json = jsonObject.toString();
			StringEntity se = new StringEntity(json);
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			HttpResponse httpResponse = httpClient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();

			if (inputStream != null)
				result = convertInputStreamToString(inputStream);

			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage() + result);
		}

		return result;

	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();

		return result;

	}

}
