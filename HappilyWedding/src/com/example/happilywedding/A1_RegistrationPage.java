package com.example.happilywedding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class A1_RegistrationPage extends Activity implements OnClickListener {

	Boolean flag = true;
	ProgressBar progressBar;
	static UserPersonalDetails user;
	Button btnDatePicker;
	Button btnTimePicker;
	Button submit;
	EditText txtTimerPicker;
	EditText txtDatePicker;
	EditText edName;
	RadioGroup rg1;
	private int mYear, mMonth, mDay, mHour, mMinute, mSecond;
	String messag = "";
	static String name, phoneno, city, country, sex, partnr, suburbb, datepic,
			timepic;
	RadioButton radioSexButton;
	int id = 0;
	int index1 = 0, index2 = 0, index3 = 0;
	String[] passcity;
	String[] passSuburb;
	String[] bundleCountry;
	Spinner sCity;
	Spinner sSuburb;
	ShowAsynktask asyncTaskk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regestration_page);

		progressBar = (ProgressBar) findViewById(R.id.progressReg1);
		progressBar.setVisibility(View.INVISIBLE);
		edName = (EditText) findViewById(R.id.ednamee);

		Bundle b1 = getIntent().getExtras();
		bundleCountry = b1.getStringArray("key1");

		// Bundle b2 = getIntent().getExtras();
		// String[] bundleCity = b2.getStringArray("key2");
		//
		// Bundle b3 = getIntent().getExtras();
		// String[] bundleSubUrb = b3.getStringArray("key3");

		btnDatePicker = (Button) findViewById(R.id.btnDatePicker);
		btnTimePicker = (Button) findViewById(R.id.btnTimePicker);

		txtDatePicker = (EditText) findViewById(R.id.txtDatePicker);
		txtTimerPicker = (EditText) findViewById(R.id.txtTimePicker);

		Spinner sCountry = (Spinner) findViewById(R.id.spcountry);
		sCity = (Spinner) findViewById(R.id.spinnercity);
		sSuburb = (Spinner) findViewById(R.id.spinsuburb);
		rg1 = (RadioGroup) findViewById(R.id.radioGroup1);
		submit = (Button) findViewById(R.id.butSubmitted);

		btnDatePicker.setOnClickListener(this);
		btnTimePicker.setOnClickListener(this);

		submit.setOnClickListener(this);

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				A1_RegistrationPage.this, android.R.layout.simple_spinner_item,
				bundleCountry);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sCountry.setAdapter(dataAdapter);

		sCountry.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				index1 = arg0.getSelectedItemPosition();
				asyncTaskk = new ShowAsynktask();
				asyncTaskk.execute();

				// passcity = null;
				// passSuburb = null;

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		((BaseAdapter) sCountry.getAdapter()).notifyDataSetChanged();
		// dateComparison();
	}

	class HttpAsyncTask extends AsyncTask<String, Void, String> {

		// private static final String String = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
			progressBar.bringToFront();
		}

		@Override
		protected String doInBackground(String... urls) {

			user = new UserPersonalDetails();

			user.setName(name);
			try {

				// http://hrm.testserver87.com/wedding/user_registration.php?name=rahul&sex=1&country=1&city=2&suburb=2&date=24/03/45&time=02:01&user_partner_helping=7
				String response = z_CustomHttpClient
						.executeHttpGet("http://hrm.testserver87.com/wedding/user_registration.php?name="
								+ name
								+ "&sex="
								+ id
								+ "&country="
								+ index1
								+ "&city="
								+ index2
								+ "&suburb="
								+ index3
								+ "&date="
								+ datepic
								+ "&time="
								+ timepic
								+ "&user_partner_helping=7");

				String res = response.toString();
				Log.e("msg", res);

				JSONArray ob = new JSONArray(response);
				// String str = ob.toString();
				int ln = ob.length();
				// String[] descr = new String[ln];
				for (int ii = 0; ii < ln; ii++) {
					JSONObject pr = ob.getJSONObject(ii);
					messag = pr.getString("message");
					String idd = "";
					if (messag.equals("1")) {
						idd = pr.getString("userid");
						savePreferences("USER_ID", idd);

					}

					Log.e("kk", messag + idd);

				}

			} catch (Exception e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}

			return POST(urls[0], user);
		}

		protected void onPostExecute(String result) {

			if (progressBar != null)
				progressBar.setVisibility(View.GONE);

			try {
				if (messag.equals("0")) {
					Toast.makeText(getApplicationContext(),
							"Information Entry Dulplicate", Toast.LENGTH_LONG)
							.show();
				} else if (messag.equals("1")) {
					Toast toasst = Toast.makeText(getApplicationContext(),
							"Data Insert Succesfully", 5000);
					toasst.show();
					toasst.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
					if (id == 1) {
						Intent ii = new Intent(getApplicationContext(),
								A2NewListview_tooglepink.class);
						startActivity(ii);
					} else {
						Intent i = new Intent(getApplicationContext(),
								A2NewListview_toogle.class);
						startActivity(i);

					}

				}
				String dateTime = datepic + " " + timepic;
				DateFnd(dateTime);
				// save dateand time in preferences......
				savePreferences1("storedName1", dateTime);
				String radioValue = String.valueOf(id);
				savePreferences2("storedRadio", radioValue);

			} catch (Exception e) {

			}

		}
	}

	private void savePreferences1(String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager

		.getDefaultSharedPreferences(this);

		Editor editor = sharedPreferences.edit();

		editor.putString(key, value);

		editor.commit();

	}

	private void savePreferences2(String key, String id2) {
		SharedPreferences sharedPreferences = PreferenceManager

		.getDefaultSharedPreferences(this);

		Editor editor = sharedPreferences.edit();

		editor.putString(key, id2);

		editor.commit();

	}

	private void savePreferences(String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		// editor.putString(key, value1);

		editor.commit();

	}

	// now post data to server................................................
	public static String POST(String url, UserPersonalDetails user) {
		InputStream inputStream = null;
		String result = "";
		try {

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			String json = "";

			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("name", user.getName());

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

	// check validation..............................

	public boolean validate() {
		boolean valid = true;

		if (name.trim().length() == 0) {

			valid = false;
		}

		if (datepic.trim().length() == 0) {
			valid = false;
		}

		if (timepic.trim().length() == 0) {
			valid = false;
		}

		return valid;

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

	public void onClick(View v) {

		if (v == btnDatePicker) {
			// Process to get Current Date
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			// Launch Date Picker Dialog
			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// Display Selected date in textbox
							if (year >= mYear && monthOfYear >= mMonth) {
								// dateComparison();
								txtDatePicker.setText(dayOfMonth + "-"
										+ (monthOfYear + 1) + "-" + year);

							} else {
								Toast.makeText(getApplicationContext(),
										"Set current and future date:", 3000)
										.show();
							}
						}
					}, mYear, mMonth, mDay);
			dpd.show();
		}

		if (v == btnTimePicker) {

			// Process to get Current Time
			final Calendar c = Calendar.getInstance();
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);
			// mSecond=c.get(Calendar.SECOND);
			// Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							if (hourOfDay >= mHour && minute >= mHour) {
								txtTimerPicker
										.setText(hourOfDay + ":" + minute);
							} else {
								Toast.makeText(getApplicationContext(),
										"Set Current or future time", 3000)
										.show();
							}

						}

					}, mHour, mMinute, false);
			tpd.show();

		}

		if (v == submit) {

			name = edName.getText().toString();
			timepic = txtTimerPicker.getText().toString();
			datepic = txtDatePicker.getText().toString();

			// get selected radio button from radioGroup
			int selectedId = rg1.getCheckedRadioButtonId();

			// find the radiobutton by returned id
			radioSexButton = (RadioButton) findViewById(selectedId);

			if (radioSexButton.getText().equals("Groom")) {
				id = 0;
			} else {
				id = 1;
			}

			flag = validate();
			if (flag == true) {
				// new HttpAsyncTask()
				// .execute("http://hmkcode.appspot.com/jsonservlet");
				new HttpAsyncTask()
						.execute("http://hrm.testserver87.com/wedding/user_registration.php?name="
								+ name
								+ "&sex="
								+ id
								+ "&country="
								+ index1
								+ "&city="
								+ index2
								+ "&suburb="
								+ index3
								+ "&date="
								+ datepic
								+ "&time="
								+ timepic
								+ "&user_partner_helping=7");

			} else {
				Toast.makeText(getApplicationContext(),
						"Please insert all fields", Toast.LENGTH_LONG).show();
			}

		}

	}

	/*
	 * @SuppressWarnings("unused") private void dateComparison() { try {
	 * SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); Calendar cal =
	 * Calendar.getInstance();
	 * 
	 * String date = sdf.format(cal.getTime());// current date
	 * 
	 * DatePicker datePicker = null; String _date =
	 * sdf.format(sdf.parse(datePicker.getDayOfMonth() + "-" +
	 * (datePicker.getMonth() + 1) + "-" + datePicker.getYear()));
	 * 
	 * Date date1 = sdf.parse(date); Date date2 = sdf.parse(_date);// date
	 * picker selected date
	 * 
	 * if (date2.before(date1)) { // do whatever you want to do
	 * Toast.makeText(getApplicationContext(), "ITS cORRECT DATE", 3000).show();
	 * 
	 * } else { Toast.makeText(getApplicationContext(),
	 * "ITS not a CORRECT DATE", 3000).show(); } } catch (ParseException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (java.text.ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	// .......... city fetch from server............
	class ShowAsynktask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			// bar.setVisibility(View.VISIBLE);
			// bar.bringToFront();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {

				// ............passcity........................

				String responseCity = z_CustomHttpClient
						.executeHttpGet("http://hrm.testserver87.com/wedding/city.php?countryid="
								+ index1);
				JSONArray arr11 = new JSONArray(responseCity);
				String str1 = arr11.toString();
				Log.e("sur1", str1);
				int len1 = arr11.length();
				String[] CityArry = new String[len1];
				for (int i = 0; i < len1; i++) {
					JSONObject pr1 = arr11.getJSONObject(i);
					String city1 = pr1.getString("city_name");
					CityArry[i] = city1;
					Log.e("CityName", city1);
				}
				passcity = new String[len1];
				passcity = CityArry;

				/*
				 * // ............passsuburb........................
				 */

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {
				// set spinner value of country and city............
				if (passcity != null) {

					ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(
							A1_RegistrationPage.this,
							android.R.layout.simple_spinner_item, passcity);
					dataAdapter1
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sCity.setAdapter(dataAdapter1);
					sCity.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							index2 = arg0.getSelectedItemPosition();
							ShowAsynktask1 asynktask1 = new ShowAsynktask1();
							asynktask1.execute();
							// passSuburb = null;
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});
				} else {
					Toast.makeText(A1_RegistrationPage.this,
							"No City for this country", 3000).show();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

		}
	}

	// .......... suburb fetch from server............
	class ShowAsynktask1 extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			// bar.setVisibility(View.VISIBLE);
			// bar.bringToFront();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {

				// ............passsuburb........................

				String responseSuburb = z_CustomHttpClient
						.executeHttpGet("http://hrm.testserver87.com/wedding/suburb.php?cityid="
								+ index2);
				JSONArray arr22 = new JSONArray(responseSuburb);
				String str2 = arr22.toString();
				Log.e("sur2", str2);
				int len21 = arr22.length();
				String[] SuburbArry = new String[len21];
				for (int i = 0; i < len21; i++) {
					JSONObject pr21 = arr22.getJSONObject(i);
					String suburb1 = pr21.getString("suburb_name");
					SuburbArry[i] = suburb1;
					Log.e("Suburb", suburb1);
				}
				passSuburb = new String[len21];
				passSuburb = SuburbArry;

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

				if (passSuburb != null) {
					// set suburb array adapter...............
					ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(
							A1_RegistrationPage.this,
							android.R.layout.simple_spinner_item, passSuburb);
					dataAdapter2
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sSuburb.setAdapter(dataAdapter2);
					sSuburb.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							index3 = arg0.getSelectedItemPosition();
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});
					((BaseAdapter) sSuburb.getAdapter()).notifyDataSetChanged();
				} else {
					/*
					 * Toast.makeText(getApplicationContext(),
					 * "No Suburb on server for this city", 3000).show();
					 */
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

		}
	}

	public void DateFnd(String dateTime) {
		String dateStart = "01/14/2012 09:29:58";
		String dateStop = dateTime;
		Log.e("DateShared", dateStop);

		// HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
			Log.e("Date & Time", "Days=" + diffDays + "Hr=" + diffHours
					+ "min=" + diffMinutes + "Sec=" + diffSeconds);
		} catch (Exception e) {

		}
	}

}
