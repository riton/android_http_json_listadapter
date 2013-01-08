package fr.riton.twitterjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	//private static final String TWITTER_SEARCH_URL = "http://search.twitter.com/search.json?q=freebox";
	private static final String TWITTER_SEARCH_URL = "http://remi.ferrand.free.fr/test.json";
	
	private CookieManager cookie_manager;
	
	private final int NETWORK_READ_TIMEOUT = 15000; /* Milliseconds */
	private final int NETWORK_CONNECT_TIMEOUT = 15000; /* Milliseconds */
	
	public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11";
	
	private ListView display_list;
	
	// test GIT
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		List<Event> results = null;
		
		try {
			try {
				results = connect(TWITTER_SEARCH_URL);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (results == null) {
			Toast.makeText(getApplicationContext(), "Error: NULL results", Toast.LENGTH_LONG).show();
			return;
		}
		
		TestAdapater adapter = new TestAdapater(this, R.layout.listview_item_row, results);
		
		display_list = (ListView) findViewById(R.id.listView1);
		display_list.setTextFilterEnabled(false);
		
		display_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
			
				AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
				adb.setTitle("TEST");
				adb.setMessage("Selected item is = " + display_list.getItemAtPosition(arg2).toString());
				adb.setPositiveButton("OK", null);
				adb.show();
			}
			
		});
		
		View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
		
		display_list.addHeaderView(header);
		display_list.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private List<Event> connect(String the_url) throws IOException, JSONException {
		
		URL url = new URL(the_url);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		
		try {
			 
			urlConnection.setReadTimeout(NETWORK_READ_TIMEOUT);
			urlConnection.setConnectTimeout(NETWORK_CONNECT_TIMEOUT);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestProperty("User-Agent", USER_AGENT);
			urlConnection.setInstanceFollowRedirects(false);
			
			urlConnection.setRequestMethod("GET");	
						
			if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				Toast.makeText(getApplicationContext(), "HTTP failed with status " + urlConnection.getResponseCode(), Toast.LENGTH_LONG).show();
				return null;
			}
			
			/**
			 * BEWARE !
			 * The next getInputStream() could raise a FileNotFoundException
			 * when the ResponseCode is >= 400
			 */
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));			
			// http://digitallibraryworld.com/?p=189
			// WARNING: Scanner is NOT thread safe !

			JSONObject jsonResponse = new JSONObject(readData(reader));
			
			float c = Float.parseFloat(jsonResponse.getString("completed_in"));
			
			
			List<Event> results = parseResponse(jsonResponse);
			
			return results;			
			
		}
		finally {
			urlConnection.disconnect();
		}

	}

	private ArrayList<Event> parseResponse(JSONObject response) throws JSONException {
		
		ArrayList<Event> results = new ArrayList<Event>();
		
		JSONArray r = response.getJSONArray("array"); 
		
		for (int i = 0; i < r.length(); i++) {
			
			JSONObject o = r.getJSONObject(i);

			@SuppressWarnings("unchecked")
			Iterator<String> k_iter = o.keys();
			
			while (k_iter.hasNext()) {
				String key = k_iter.next();
				String value = o.getString(key);
				results.add(new Event(key, value));				
			}
			
			
			//results.add(twitter_results.getJSONObject(i).getString("created_at"));
		}
		
		return results;
	
	}
	
	private String readData(BufferedReader reader) throws IOException {
		
		String s;
		String jsonData = "";
		
		while (true) {
			s = reader.readLine();
			if (s == null) {
				break;
			}
			jsonData += s;
		}
		
		return jsonData;
	}
	
}
