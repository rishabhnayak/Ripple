package infracom.abcr.sarthamicrofinance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.DynamicTab.MainActivityD;
import infracom.abcr.sarthamicrofinance.Profile.Admin_Launch;
import infracom.abcr.sarthamicrofinance.Profile.Customer_Launch;
import infracom.abcr.sarthamicrofinance.Profile.DealerLogin;
import infracom.abcr.sarthamicrofinance.Profile.Manager_Launch;
import infracom.abcr.sarthamicrofinance.Profile.Marketing;
import infracom.abcr.sarthamicrofinance.helper.SessionManager;
import infracom.abcr.sarthamicrofinance.mainact.location_Service;


public class Main extends AppCompatActivity {
	
	// label to display gcm messages
	TextView lblMessage;
	Controller aController;

	DBAdapter db;

	//RSSFeed lfflfeed;
	private static final int REQUEST_LOCATION = 1;
	String latitude;
	String longitude, branch_name, branch_id;
	LocationManager locationManager;
	// String lat,lon;

	private SessionManager session;

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_2main);
		
		/******************* Intialize Database *************/
		DBAdapter.init(this);
		
		// Get Global Controller Class object 
		// (see application tag in AndroidManifest.xml)
		aController = (Controller) getApplicationContext();
		

		// Check if Internet present
		if (!aController.isConnectingToInternet()) {
			
			// Internet Connection is not present
			aController.showAlertDialog(Main.this,
					"Internet Connection Error",
					"Please connect to Internet connection", false);
			// stop executing code by return
			return;
		}

		//Check device contains self information in sqlite database or not.

		session = new SessionManager(getApplicationContext());

		// Check if user is already logged in or not

		try{
			ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
			gps();
		}catch (Exception e){}


		//int vDevice = DBAdapter.validateDevice();
		
		//if(vDevice > 0)
		if (session.isLoggedIn())
		{

			db = new DBAdapter(this);

			// db.open();
			HashMap<String, String> dataf = db.getLogininfo();
			String under_depart = dataf.get("under_depart");

			branch_name = dataf.get("branch_name");

			branch_id = dataf.get("branch_id");


			Intent ii = new Intent(this, location_Service.class);
			ii.putExtra("latitude", latitude);
			ii.putExtra("longitude", longitude);
			ii.putExtra("under_depart", under_depart);
			startService(ii);

			db.close();

			if(under_depart.equals("Manager")){
				Intent i = new Intent(getApplicationContext(), Manager_Launch.class);
				startActivity(i);
				finish();
			}else {
				// Launch Main Activity
				if(under_depart.equals("Admin")){
					Intent i = new Intent(getApplicationContext(), Admin_Launch.class);
					startActivity(i);
					finish();
				}else {
					if(under_depart.equals("Customer")){
						Intent i = new Intent(getApplicationContext(), Customer_Launch.class);
						startActivity(i);
						finish();
					}else {
						if(under_depart.equals("Dealer")){
							Intent i = new Intent(getApplicationContext(), DealerLogin.class);
							startActivity(i);
							finish();
						}else {
							if(under_depart.equals("DSA Person")){
								Intent i = new Intent(getApplicationContext(), Marketing.class);
								startActivity(i);
								finish();
							}else {
								// Launch Main Activity
								Intent i = new Intent(getApplicationContext(), Launch.class);
								startActivity(i);
								finish();
							}
						}
					}
				}

			}

/*
			switch (under_depart) {
				case "Manager":
					Intent i1 = new Intent(getApplicationContext(), Manager_Launch.class);
					startActivity(i1);
					finish();
					break;
				case "Admin":
					Intent i2 = new Intent(getApplicationContext(), Admin_Launch.class);
					startActivity(i2);
					finish();
					break;
				case "Customer":
					Intent i3 = new Intent(getApplicationContext(), Customer_Launch.class);
					startActivity(i3);
					finish();
					break;
				case "Dealer":
					Intent i4 = new Intent(getApplicationContext(), DealerLogin.class);
					startActivity(i4);
					finish();
					break;
				case "Sales Person":
					Intent i5 = new Intent(getApplicationContext(), Launch.class);
					startActivity(i5);
					finish();
					break;
			}
						*/



		}
		else
		{
		/*	String deviceIMEI = "";
			if(Config.SECOND_SIMULATOR){
				
				//Make it true in CONFIG if you want to open second simutor
				// for testing actually we are using IMEI number to save a unique device
				
				deviceIMEI = "000000000000001";
			}	
			else
			{
			  // GET IMEI NUMBER      
			 TelephonyManager tManager = (TelephonyManager) getBaseContext()
			    .getSystemService(Context.TELEPHONY_SERVICE);
			  deviceIMEI = tManager.getDeviceId(); 
			}
			// WebServer Request URL
	        String serverURL = Config.YOUR_SERVER_URL+"validate_device.php";
	        
	        // Use AsyncTask execute Method To Prevent ANR Problem
	        LongOperation serverRequest = new LongOperation(); 
	        
	        serverRequest.execute(serverURL,deviceIMEI,"","");
			*/

			// Launch Main Activity
			Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
			startActivity(i);
			finish();
		}	
		
	}


	// Class with extends AsyncTask class
	public class LongOperation  extends AsyncTask<String, Void, String> {
	         
	        // Required initialization
	    	
	       //private final HttpClient Client = new DefaultHttpClient();
	       // private Controller aController = null;
	        private String Error = null;
	        private ProgressDialog Dialog = new ProgressDialog(Main.this); 
	        String data =""; 
	        int sizeData = 0;  
	        
	        
	        protected void onPreExecute() {
	            // NOTE: You can call UI Element here.
	             
	            //Start Progress Dialog (Message)
	           
	            Dialog.setMessage("Validating Device..");
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
		               	   data +="&" + URLEncoder.encode("data1", "UTF-8") + "="+params[1].toString();
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
	                      
	                     /*********** Process each JSON Node ************/
	  
	                     int lengthJsonArr = jsonMainNode.length();  
	  
	                     for(int i=0; i < lengthJsonArr; i++) 
	                     {
	                         /****** Get Object for each JSON node.***********/
	                         JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
	                          
	                         /******* Fetch node values **********/
	                         String Status       = jsonChildNode.optString("status").toString();
	                         
	                         Log.i("GCM","---"+Status);
	                         
	                         // IF server response status is update
	                         if(Status.equals("update")){
	                            
	                        	String RegID      = jsonChildNode.optString("regid").toString();
	                            String Name       = jsonChildNode.optString("name").toString();
	                            String Email      = jsonChildNode.optString("email").toString();
	                            String IMEI       = jsonChildNode.optString("imei").toString();

								 String user_id       = jsonChildNode.optString("user_id").toString();

								 String D_under_depart       = jsonChildNode.optString("under_depart").toString();


	                           // add device self data in sqlite database
								// DBAdapter.deleteAll();
	                            DBAdapter.addDeviceData(Name, Email,RegID, IMEI, user_id, D_under_depart,branch_name,branch_id);
	                            
	                            // Launch GridViewExample Activity
	                			Intent i1 = new Intent(getApplicationContext(), GridViewExample.class);
	                			startActivity(i1);
	                			finish();
	                           
	                            Log.i("GCM","---"+Name);
	                         }
	                         else if(Status.equals("install")){  
	                        	
	                        	 // Launch RegisterActivity Activity
		                		Intent i1 = new Intent(getApplicationContext(), RegisterActivity.class);
		                		startActivity(i1);
		                		finish();
	                        	 
	                         }
	                         
	                        
	                    }
	                     
	                 /****************** End Parse Response JSON Data *************/     
	                   
	                      
	                 } catch (JSONException e) {
	          
	                     e.printStackTrace();
	                 }
	  
	                 
	             }
	        }
	         
	    }


	public void gps() {
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();

		} else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			getLocation();
		}
	}


	private void getLocation() {
		if (ActivityCompat.checkSelfPermission(Main.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
				!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
				(Main.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

			ActivityCompat.requestPermissions(Main.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

		} else {
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

			if (location != null) {
				double latti = location.getLatitude();
				double longi = location.getLongitude();
				latitude = String.valueOf(latti);
				longitude = String.valueOf(longi);


//                Toast.makeText(this, "Your current location is"+ "\n" + "Lattitude = " + latitude + "\n" + "Longitude = " + longitude ,Toast.LENGTH_SHORT).show();


			} else  if (location1 != null) {
				double latti = location1.getLatitude();
				double longi = location1.getLongitude();
				latitude = String.valueOf(latti);
				longitude= String.valueOf(longi);

//                Toast.makeText(this, "Your current location is"+ "\n" + "Lattitude = " + latitude + "\n" + "Longitude = " + longitude ,Toast.LENGTH_SHORT).show();


			} else  if (location2 != null) {
				double latti = location2.getLatitude();
				double longi = location2.getLongitude();
				latitude = String.valueOf(latti);
				longitude= String.valueOf(longi);

//                 Toast.makeText(this,"Your current location is"+ "\n" + "Lattitude = " + latitude + "\n" + "Longitude = " + longitude,Toast.LENGTH_SHORT).show();

			}else{

				//             Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

			}
		}
	}

	protected void buildAlertMessageNoGps() {

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Please Turn ON your GPS Connection")
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int id) {
						startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog, final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}


	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

}
