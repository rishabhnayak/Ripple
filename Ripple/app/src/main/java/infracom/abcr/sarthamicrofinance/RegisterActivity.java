package infracom.abcr.sarthamicrofinance;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import infracom.abcr.sarthamicrofinance.helper.AppController;
import infracom.abcr.sarthamicrofinance.helper.SessionManager;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;

public class RegisterActivity extends AppCompatActivity {
	
	// UI elements
	EditText txtName; 
	EditText txtEmail;

	private SessionManager session;

	Controller aController = null;
	private ProgressDialog pDialog;

	private static final String TAG = RegisterActivity.class.getSimpleName();	// Register button
	Button btnRegister;
	//private SQLiteHandler db;
	public static String URL_LOGIN = "https://sarthamicrofinance.com/admin//gcm_Device_to/gcm_server_files/login.php";
	// WebServer Request URL to get All registered devices
	String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/login.php";

	GPSTracker gps;
	double latitude;
	double longitude;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.register_activity);
/******************* Intialize Database *************/


		gps = new GPSTracker(RegisterActivity.this);

		// check if GPS enabled
		if(gps.canGetLocation()){

			 latitude = gps.getLatitude();
			 longitude = gps.getLongitude();

			// \n is for new line
			//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
		}else{
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
			//return;
		}

		final EditText etPassword = (EditText)findViewById(R.id.txtEmail);
		final CheckBox cbShowPassword = (CheckBox)findViewById(R.id.cb_show_password);

		cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// to encode password in dots
					etPassword.setTransformationMethod(null);
				} else {
					// to display the password in normal text
					etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}
			}
		});

		DBAdapter.init(this);

		// Get Global variable instance
		aController = (Controller) getApplicationContext();

		pDialog = new ProgressDialog(this);
		//Get Global Controller Class object (see application tag in AndroidManifest.xml)
		final Controller aController = (Controller) getApplicationContext();



		// Check if Internet Connection present
		if (!aController.isConnectingToInternet()) {
			
			// Internet Connection is not present
			aController.showAlertDialog(RegisterActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			
			// stop executing code by return
			return;
		}

		session = new SessionManager(getApplicationContext());

		// Check if user is already logged in or not
		if (session.isLoggedIn()) {
			// User is already logged in. Take him to main activity
			Intent intent = new Intent(RegisterActivity.this, Main.class);
			startActivity(intent);
			finish();
		}
		// Check if GCM configuration is set
		if (Config.YOUR_SERVER_URL == null || 
			Config.GOOGLE_SENDER_ID == null || 
			Config.YOUR_SERVER_URL.length() == 0 || 
			Config.GOOGLE_SENDER_ID.length() == 0) 
		{
			
			// GCM sernder id / server url is missing
			aController.showAlertDialog(RegisterActivity.this,
					"Configuration Error!",
					"Please set your Server URL and GCM Sender ID",
					false);
			
			// stop executing code by return
			 return;
		}
		
		txtName = (EditText) findViewById(R.id.txtName);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		
		// Click event on Register button
		btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {  
				// Get data from EditText 
				String name = txtName.getText().toString(); 
				String email = txtEmail.getText().toString();
				// Check if user filled the form
				if(name.trim().length() > 0 && email.trim().length() > 0){

					//LongOperation serverRequest = new LongOperation();

					new LongOperation().execute(serverURL,name,email,"");
					//serverRequest.execute(URL_LOGIN,"name","email","");

				}else{
					
					
					// user doen't filled that data
					aController.showAlertDialog(RegisterActivity.this,
							"Registration Error!",
							"Please enter your details",
							false);
				}
			}
		});
	}



	// Class with extends AsyncTask class
	public class LongOperation  extends AsyncTask<String, Void, String> {

		// Required initialization

		//private final HttpClient Client = new DefaultHttpClient();
		// private Controller aController = null;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(RegisterActivity.this);
		String data ="";
		int sizeData = 0;


		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			//Start Progress Dialog (Message)

			Dialog.setMessage("Logging...");
			Dialog.show();

		}

		// Call after onPreExecute method
		protected String doInBackground(String... params) {

			/************ Make Post Call To Web Server ***********/
			BufferedReader reader=null;
			String Content = "";
			// Send data
			try{

				// Defined URL  where to send data
				URL url = new URL(params[0]);

				// Set Request parameter
				if(!params[1].equals(""))
					data +="&" + URLEncoder.encode("data", "UTF-8") + "="+params[1].toString();
				if(!params[2].equals(""))
					data +="&" + URLEncoder.encode("data2", "UTF-8") + "="+params[2].toString();
				if(!params[3].equals(""))
					data +="&" + URLEncoder.encode("data3", "UTF-8") + "="+params[3].toString();
				Log.i("GCM",data);

				// Send POST data request

				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write( data );
				wr.flush();

				// Get the server response

				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Read Server Response
				while((line = reader.readLine()) != null)
				{
					// Append server response in string
					sb.append(line + "\n");
				}

				// Append Server Response To Content String
				Content = sb.toString();
			}
			catch(Exception ex)
			{
				Error = ex.getMessage();
			}
			finally
			{
				try
				{

					reader.close();
				}

				catch(Exception ex) {}
			}

			/*****************************************************/
			return Content;
		}

		protected void onPostExecute(String Content) {
			// NOTE: You can call UI Element here.

			// Close progress dialog
			Dialog.dismiss();

			if (Error != null) {


			} else {

				// Show Response Json On Screen (activity)

				/****************** Start Parse Response JSON Data *************/
				aController.clearUserData();

				JSONObject jsonResponse;

				try {

					/****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
					jsonResponse = new JSONObject(Content);

					/***** Returns the value mapped by name if it exists and is a JSONArray. ***/
					/*******  Returns null otherwise.  *******/
					JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
					//JSONObject jObj = new JSONObject(response);
					//JSONObject jObj = new JSONObject(response);
					//boolean error = jObj.getBoolean("error");
					//boolean error = jsonMainNode.getBoolean("email").toString(;
					//boolean error       = jsonChildNode.optString("name").toString();
					// Check for error node in json
					//if (!error) {}
					/*********** Process each JSON Node ************/

					int lengthJsonArr = jsonMainNode.length();

					for(int i=0; i < lengthJsonArr; i++)
					{
						/****** Get Object for each JSON node.***********/
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

						/******* Fetch node values **********/

						boolean error       = jsonChildNode.getBoolean("error");
						if (!error) {
						String name       = jsonChildNode.optString("name").toString();
						String email       = jsonChildNode.optString("email").toString();
							String eid       = jsonChildNode.optString("eid").toString();
							String user_id       = jsonChildNode.optString("user_id").toString();


							String under_depart       = jsonChildNode.optString("under_depart").toString();
							String branch_name       = jsonChildNode.optString("branch_name").toString();
							String branch_id       = jsonChildNode.optString("branch_id").toString();
						//Log.i("GCM","---"+name);
						//	Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();

						Intent intent = new Intent(getApplicationContext(), RMainActivity.class);

						// Registering user on our server
						// Sending registraiton details to MainActivity
							String lat = Double.toString(latitude);
							String lon = Double.toString(longitude);

						intent.putExtra("name", name);
						intent.putExtra("email", email);
							intent.putExtra("latitude", lat);
							intent.putExtra("longitude", lon);
							intent.putExtra("eid", eid);
							intent.putExtra("user_id", user_id);
							intent.putExtra("under_depart", under_depart);
							intent.putExtra("branch_name", branch_name);
							intent.putExtra("branch_id", branch_id);
							startActivity(intent);
						finish();
						}else
						{
							Toast.makeText(getApplicationContext(), "Wrong User ID or Password!", Toast.LENGTH_LONG).show();
						}

						//UserData userdata = new UserData();
						//userdata.setIMEI(IMEI);
						//userdata.setName(Name);

						//Add user data to controller class UserDataArr arraylist
						//aController.setUserData(userdata);

					}

					/****************** End Parse Response JSON Data *************/

					//Add user data to controller class UserDataArr arraylist
					//gridView.setAdapter(new CustomGridAdapter(getBaseContext(), aController));


				} catch (JSONException e) {

					e.printStackTrace();
				}


			}
		}

	}

}
