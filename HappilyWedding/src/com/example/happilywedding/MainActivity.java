package com.example.happilywedding;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	ShowAsynktask asyncTaskk;
	ProgressBar bar;
	String[] passcounttry;
	// String[] passcity;
	// String[] passSuburb;
	String radioValue = null;
	Boolean flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		String radioVal = loadSavedPreferences2();
		if (!loadSavedPreferences().equals("YourId")
				&& loadSavedPreferences().length() > 0
				&& radioValue.length() > 0 && radioVal.equals("0")) {
			Intent intent = new Intent(MainActivity.this,
					A2NewListview_toogle.class);
			startActivity(intent);
			this.finish();

		} else if (!loadSavedPreferences().equals("YourId")
				&& loadSavedPreferences().length() > 0
				&& radioValue.length() > 0 && radioVal.equals("1")) {
			Intent intent1 = new Intent(MainActivity.this,
					A2NewListview_tooglepink.class);
			startActivity(intent1);
			this.finish();
		} else {
			bar = (ProgressBar) findViewById(R.id.progressR1);
			bar.setVisibility(View.INVISIBLE);
			asyncTaskk = new ShowAsynktask();
			asyncTaskk.execute();
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
	}

	private String loadSavedPreferences() {

		SharedPreferences sharedPreferences = PreferenceManager

		.getDefaultSharedPreferences(this);

		String name = sharedPreferences.getString("USER_ID", "YourId");
		Log.e("Sharedpref", name);
		return name;

	}

	private String loadSavedPreferences2() {

		SharedPreferences sharedPreferences = PreferenceManager

		.getDefaultSharedPreferences(this);

		radioValue = sharedPreferences.getString("storedRadio", "YourId");
		Log.e("Sharedprefradio", radioValue);
		return radioValue;

	}

	// .......... city country suburb fetch from server............
	class ShowAsynktask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			bar.setVisibility(View.VISIBLE);
			bar.bringToFront();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				// .........for country.....................
				String responseCountry = z_CustomHttpClient
						.executeHttpGet("http://hrm.testserver87.com/wedding/country.php");
				JSONArray arr = new JSONArray(responseCountry);
				String str = arr.toString();
				Log.e("sur", str);
				int len = arr.length();
				String[] CountryArry = new String[len];
				String[] countryid_aary = new String[len];
				for (int i = 0; i < len; i++) {
					JSONObject pr = arr.getJSONObject(i);
					String country1 = pr.getString("country_name");
					String country_id = pr.getString("country_id");
					CountryArry[i] = country1;
					countryid_aary[i] = country_id;
					Log.e("hh", country1);

				}
				passcounttry = new String[len];
				passcounttry = CountryArry;

				/*
				 * // ............passcity........................
				 * 
				 * String responseCity = z_CustomHttpClient .executeHttpGet(
				 * "http://hrm.testserver87.com/wedding/city.php?countryid=1");
				 * JSONArray arr11 = new JSONArray(responseCity); String str1 =
				 * arr11.toString(); Log.e("sur1", str1); int len1 =
				 * arr11.length(); String[] CityArry = new String[len1]; for
				 * (int i = 0; i < len1; i++) { JSONObject pr1 =
				 * arr11.getJSONObject(i); String city1 =
				 * pr1.getString("city_name"); CityArry[i] = city1;
				 * Log.e("CityName", city1); } passcity = new String[len1];
				 * passcity = CityArry;
				 * 
				 * // ............passsuburb........................
				 * 
				 * String responseSuburb = z_CustomHttpClient .executeHttpGet(
				 * "http://hrm.testserver87.com/wedding/suburb.php?cityid=1");
				 * JSONArray arr22 = new JSONArray(responseSuburb); String str2
				 * = arr22.toString(); Log.e("sur2", str2); int len21 =
				 * arr22.length(); String[] SuburbArry = new String[len21]; for
				 * (int i = 0; i < len21; i++) { JSONObject pr21 =
				 * arr22.getJSONObject(i); String suburb1 =
				 * pr21.getString("suburb_name"); SuburbArry[i] = suburb1;
				 * Log.e("Suburb", suburb1); } passSuburb = new String[len21];
				 * passSuburb = SuburbArry;
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
			if (bar != null)
				bar.setVisibility(View.GONE);
			try {
				if (passcounttry != null) {

					// if (flag = true) {
					Intent inte = new Intent(MainActivity.this,
							A1_RegistrationPage.class);
					inte.putExtra("key1", passcounttry);
					// inte.putExtra("key2", passcity);
					// inte.putExtra("key3", passSuburb);

					startActivity(inte);
					MainActivity.this.finish();
					// }
					// flag = false;
					// if (flag = false) {
					// Intent intent = new Intent(getApplicationContext(),
					// A2NewListview_toogle.class);
					// startActivity(intent);
					// }
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

}
