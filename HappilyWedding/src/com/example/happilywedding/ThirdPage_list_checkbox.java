package com.example.happilywedding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.happilywedding.A2NewListview_toogle.HttpAsyncTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ThirdPage_list_checkbox extends Activity {

	int checkBoxValue = 0;
	ListView apps;
	ListView check;
	ProgressBar progressBar;
	ShowAsynktask asyncTaskk;
	double latti = 0;
	double longii = 0;
	double[] latiarry;
	double[] longi;
	public static String[] passData;
	public static String[] passData1;
	public static String[] passDistanceArry;
	public static String[] passlisting_idArry;
	public static int[] passRatingArry;
	double MyLat, MyLong;
	private String url = "http://hrm.testserver87.com/wedding/displaysublisting.php?taskid=1&userid=1";
	String[] arradata = { "leaf", "leaf1" };
	Button btnSearch, btnLeft;
	EditText mtxt;
	ArrayList<List_Detail> packagelist1;
	ArrayList<List_Detail> totalpackagelist1;
	String task_idd;
	String usrid = null;
	private BroadcastReceiver checkBoxReceiver;
	int check_value = 0;
	String listing_id = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.thirdpage_list_checkbox);
		Bundle bundle = getIntent().getExtras();
		task_idd = bundle.getString("position");
		Log.e("Post", task_idd);

		progressBar = (ProgressBar) findViewById(R.id.progresscheck1);
		progressBar.bringToFront();
		apps = (ListView) findViewById(R.id.listName1);
		Bundle bunName = getIntent().getExtras();
		String sName = bunName.getString("taskname");
		TextView textViewHeading = (TextView) findViewById(R.id.texthead1);
		// set font style................
		Typeface face = Typeface.createFromAsset(getAssets(),
				"Larissa Regular.TTF");

		textViewHeading.setTypeface(face);
		textViewHeading.setText(sName);
		usrid = A2NewListview_toogle.usrid;
		asyncTaskk = new ShowAsynktask();
		asyncTaskk.execute();

		turnGPSOn(); // method to turn on the GPS if its in off state.
		getMyCurrentLocation();

		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnLeft = (Button) findViewById(R.id.btnLeft);
		mtxt = (EditText) findViewById(R.id.edSearch);

	}

	class ShowAsynktask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			try {
				String response = z_CustomHttpClient
						.executeHttpGet("http://hrm.testserver87.com/wedding/displaysublisting.php?taskid="
								+ task_idd + "&userid=" + usrid);
				String re = response.toString();
				Log.e("res", re);
				JSONArray arr = new JSONArray(response);
				arr.toString();
				// Log.e("sur", str);
				int len = arr.length();
				String[] listName_Arry = new String[len];
				String[] listArea_arry = new String[len];
				String[] listing_idArry = new String[len];
				int[] rating_arry = new int[len];
				longi = new double[len];
				latiarry = new double[len];
				String listing_id = null;
				Double[] distanceArry = new Double[len];
				for (int i = 0; i < len; i++) {
					JSONObject pr = arr.getJSONObject(i);
					listing_id = pr.getString("listing_id");
					Log.e("Listing_id", listing_id);
					String listname = pr.getString("lisitng_name");
					pr.getString("listing_id");
					String listarea = pr.getString("listing_area");
					int rating = 0;
					try {
						latti = pr.getDouble("lisitng_latitude");
						longii = pr.getDouble("lisitng_longitude");
						rating = pr.getInt("listing_rating");

					} catch (Exception e) {
						// TODO: handle exception
					}
					listing_idArry[i] = listing_id;
					listName_Arry[i] = listname;
					listArea_arry[i] = listarea;
					rating_arry[i] = rating;
					latiarry[i] = latti;
					longi[i] = longii;
					Log.e("hharealati", listarea + latti + longii);
					Double distancee = distFrom(MyLat, MyLong, latti, longii);
					distanceArry[i] = distancee;
				}
				passData = new String[len];
				passData = listName_Arry;
				passData1 = new String[len];
				passData1 = listArea_arry;
				passlisting_idArry = new String[len];
				passlisting_idArry = listing_idArry;
				passRatingArry = new int[len];
				passRatingArry = rating_arry;
				passDistanceArry = new String[len];
				for (int i = 0; i < distanceArry.length; i++)
					passDistanceArry[i] = String.valueOf(distanceArry[i]);

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
				packagelist1 = new ArrayList<List_Detail>();

				for (int i = 0; i < passData.length; i++) {
					List_Detail item = new List_Detail(passlisting_idArry[i],
							passData[i], passDistanceArry[i], passData1[i],
							passRatingArry[i]);
					packagelist1.add(item);
					// packagelist1.add(i, list_Detail.setName(strName[i]));
				}
				if (packagelist1.size() > 0) {
					totalpackagelist1 = packagelist1;
					ListAdapter Adapter = new ListAdapter(
							ThirdPage_list_checkbox.this, packagelist1);
					apps.setAdapter(Adapter);
				} else {
					Toast.makeText(getApplicationContext(),
							"Internet Problem Occur", 3000).show();
				}

				mtxt.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						if (0 != mtxt.getText().length()) {
							String spnId = mtxt.getText().toString();
							setSearchResult(spnId);
						} else {
							packagelist1 = totalpackagelist1;
							ListAdapter Adapter = new ListAdapter(
									ThirdPage_list_checkbox.this, packagelist1);
							apps.setAdapter(Adapter);
						}
					}
				});
				btnLeft.setOnClickListener(clickListener);
				btnSearch.setOnClickListener(clickListener);

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

	public void setSearchResult(String str) {
		packagelist1 = new ArrayList<List_Detail>();
		for (int x = 0; x < totalpackagelist1.size(); x++) {
			if (totalpackagelist1.get(x).getName().toLowerCase()
					.contains(str.toLowerCase())) {
				packagelist1.add(totalpackagelist1.get(x));
			}
		}
		ListAdapter Adapter = new ListAdapter(ThirdPage_list_checkbox.this,
				packagelist1);
		apps.setAdapter(Adapter);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		IntentFilter intentFilter = new IntentFilter("android.checkboxValue1");
		checkBoxReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				if (intent.getAction()
						.equalsIgnoreCase("android.checkboxValue1")) {
					Bundle bundle = intent.getExtras();
					check_value = bundle.getInt("CHECKBOX_VALUE");
					listing_id = bundle.getString("Listing_ID");
					// Toast.makeText(getApplicationContext(),
					// "Toogle status" + check_value, 3000).show();
					// taskid = bundle.getString("Task_idd");
					sendServerValue();
				}

			}
		};
		this.registerReceiver(checkBoxReceiver, intentFilter);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.unregisterReceiver(checkBoxReceiver);
	}

	void sendServerValue() {
		new HttpAsyncTask()
				.execute("http://hrm.testserver87.com/wedding/updateuserlisting.php?userid="
						+ usrid
						+ "&listingid="
						+ listing_id
						+ "&statusid="
						+ check_value);

	}

	class HttpAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... urls) {

			return POST(urls[0], check_value);
		}

		protected void onPostExecute(String result) {
		}
	}

	public static String POST(String url, int check_Status) {
		InputStream inputStream = null;
		String result = "";
		try {

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			String json = "";

			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("statusid", check_Status);

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

	// method to find distanve betwn two places............ public static
	public static double distFrom(double lat1, double lng1, double lat2,
			double lng2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		return dist;
	}

	/** Method to turn on GPS **/
	public void turnGPSOn() {
		try {

			String provider = Settings.Secure.getString(getContentResolver(),
					Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

			if (!provider.contains("gps")) { // if gps is disabled
				final Intent poke = new Intent();
				poke.setClassName("com.android.settings",
						"com.android.settings.widget.SettingsAppWidgetProvider");
				poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
				poke.setData(Uri.parse("3"));
				sendBroadcast(poke);
			}
		} catch (Exception e) {

		}
	}

	// Method to turn off the GPS
	public void turnGPSOff() {
		String provider = Settings.Secure.getString(getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

		if (provider.contains("gps")) { // if gps is enabled
			final Intent poke = new Intent();
			poke.setClassName("com.android.settings",
					"com.android.settings.widget.SettingsAppWidgetProvider");
			poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
			poke.setData(Uri.parse("3"));
			sendBroadcast(poke);
		}
	}

	// turning off the GPS if its in on state. to avoid the battery drain.
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		turnGPSOff();
	}

	/**
	 * Check the type of GPS Provider available at that instance and collect the
	 * location informations
	 * 
	 * @Output Latitude and Longitude
	 */
	void getMyCurrentLocation() {

		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener locListener = new MyLocationListener();

		try {
			gps_enabled = locManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}
		try {
			network_enabled = locManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
		}

		// don't start listeners if no provider is enabled
		// if(!gps_enabled && !network_enabled)
		// return false;

		if (gps_enabled) {
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
					0, locListener);

		}

		if (gps_enabled) {
			location = locManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		}

		if (network_enabled && location == null) {
			locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					0, 0, locListener);

		}

		if (network_enabled && location == null) {
			location = locManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		}

		if (location != null) {

			MyLat = location.getLatitude();
			MyLong = location.getLongitude();

		} else {
			Location loc = getLastKnownLocation(this);
			if (loc != null) {

				MyLat = loc.getLatitude();
				MyLong = loc.getLongitude();

			}
		}
		locManager.removeUpdates(locListener); // removes the periodic updates
												// from location listener to
												// //avoid battery drainage. If
												// you want to get location at
												// the periodic intervals call
												// this method using //pending
												// intent.

		try {
			// Getting address from found locations.
			Geocoder geocoder;

			List<Address> addresses;
			geocoder = new Geocoder(this, Locale.getDefault());
			addresses = geocoder.getFromLocation(MyLat, MyLong, 1);

			StateName = addresses.get(0).getAdminArea();
			CityName = addresses.get(0).getLocality();
			CountryName = addresses.get(0).getCountryName();
			// you can get more details other than this . like country code,
			// state code, etc.

			System.out.println(" StateName " + StateName);
			System.out.println(" CityName " + CityName);
			System.out.println(" CountryName " + CountryName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// textView2.setText("" + MyLat);
		// textView3.setText("" + MyLong);
		// textView1.setText(" StateName " + StateName + " CityName " + CityName
		// + " CountryName " + CountryName);

		// Toast.makeText(ThirdPage_list_checkbox.this,
		// "Lati=" + MyLat + "Longi=" + MyLong, 3000).show();
	}

	// Location listener class. to get location.
	public class MyLocationListener implements LocationListener {
		public void onLocationChanged(Location location) {
			if (location != null) {
			}
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
	}

	private boolean gps_enabled = false;
	private boolean network_enabled = false;
	Location location;

	String CityName = "";
	String StateName = "";
	String CountryName = "";

	// below method to get the last remembered location. because we don't get
	// locations all the times .At some instances we are unable to get the
	// location from GPS. so at that moment it will show us the last stored
	// location.

	public static Location getLastKnownLocation(Context context) {
		Location location = null;
		LocationManager locationmanager = (LocationManager) context
				.getSystemService("location");
		List list = locationmanager.getAllProviders();
		boolean i = false;
		Iterator iterator = list.iterator();
		do {
			// System.out.println("---------------------------------------------------------------------");
			if (!iterator.hasNext())
				break;
			String s = (String) iterator.next();
			// if(i != 0 && !locationmanager.isProviderEnabled(s))
			if (i != false && !locationmanager.isProviderEnabled(s))
				continue;
			// System.out.println("provider ===> "+s);
			Location location1 = locationmanager.getLastKnownLocation(s);
			if (location1 == null)
				continue;
			if (location != null) {
				// System.out.println("location ===> "+location);
				// System.out.println("location1 ===> "+location);
				float f = location.getAccuracy();
				float f1 = location1.getAccuracy();
				if (f >= f1) {
					long l = location1.getTime();
					long l1 = location.getTime();
					if (l - l1 <= 600000L)
						continue;
				}
			}
			location = location1;
			// System.out.println("location  out ===> "+location);
			// System.out.println("location1 out===> "+location);
			i = locationmanager.isProviderEnabled(s);
			// System.out.println("---------------------------------------------------------------------");
		} while (true);
		return location;
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btnSearch) {
				mtxt.setText("");
			}

		}
	};
}
