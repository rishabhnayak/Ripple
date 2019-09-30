package infracom.abcr.sarthamicrofinance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;


public class ShowMessage extends AppCompatActivity {

	private String Customer_uSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Sms_Reset.php";
	private String Customer_uSERVER_URL1 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Sms_ResetGroup.php";


	// UI elements
	EditText txtMessage; 
	// Send Message button
	Button btnSend;
	
	// label to display gcm messages
	TextView lblMessage;
	Controller aController;
	DBAdapter db;

	String deviceIMEI = "";
	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;
	
	String name, under_depart;
	String message;
    String UserDeviceIMEI;
	
	/**************  Intialize Variables *************/
    public  ArrayList<UserData> CustomListViewValuesArr = new ArrayList<UserData>();
    TextView output = null;
    CustomAdapter adapter;
    ShowMessage activity = null;

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_message);
		/******************* Intialize Database *************/
		DBAdapter.init(this);
		
		// Get Global Controller Class object 
		// (see application tag in AndroidManifest.xml)
		aController = (Controller) getApplicationContext();
		
		
		// Check if Internet present
		if (!aController.isConnectingToInternet()) {
			
			// Internet Connection is not present
			aController.showAlertDialog(ShowMessage.this,
					"Internet Connection Error",
					"Please connect to Internet connection", false);
			// stop executing code by return
			return;
		}

		lblMessage = (TextView) findViewById(R.id.lblMessage);


		
		
		if(lblMessage.getText().equals("")){
		
		// Register custom Broadcast receiver to show messages on activity
		//registerReceiver(mHandleMessageReceiver, new IntentFilter(
			//	Config.DISPLAY_MESSAGE_ACTION));
		}
		
		List<UserData> data = DBAdapter.getAllUserData();      
		 
		
        for (UserData dt : data) {
			//dt.getName();
			//dt.getIMEI();
            
            lblMessage.append(dt.getName()+" : "+dt.getMessage()+"*****************************************************************************\n\n");
        }

        
        /*************** Spinner data Start *****************/
          
        activity  = this;
        
        
        List<UserData> SpinnerUserData = DBAdapter.getDistinctUser();

        for (UserData spinnerdt : SpinnerUserData) {
            
        	 UserData schedSpinner = new UserData();
            
            /******* Firstly take data in model object ********/
        	schedSpinner.setName(spinnerdt.getName());
        	schedSpinner.setIMEI(spinnerdt.getIMEI());
             
        	Log.i("GCMspinner", "-----"+spinnerdt.getName());
              
          /******** Take Model Object in ArrayList **********/
          CustomListViewValuesArr.add(schedSpinner);
          
        }

		// new LongOperation().execute(serverURL,"","","");
		BlurBehind.getInstance()
				.withAlpha(200)
				.withFilterColor(Color.parseColor("#222222"))
				.setBackground(this);


		Spinner  SpinnerExample = (Spinner)findViewById(R.id.spinner);
        // Resources passed to adapter to get image
        Resources res = getResources(); 
         
        // Create custom adapter object ( see below CustomAdapter.java )
        adapter = new CustomAdapter(activity, R.layout.spinner_rows, CustomListViewValuesArr,res);
         
        // Set adapter to spinner
        SpinnerExample.setAdapter(adapter);
         
        // Listener called when spinner item selected
        SpinnerExample.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here
                 
                // Get selected row data to show on screen
                String UserName       = ((TextView) v.findViewById(R.id.username)).getText().toString();
                UserDeviceIMEI        = ((TextView) v.findViewById(R.id.imei)).getText().toString();
                 
                String OutputMsg = "Selected User : \n\n"+UserName+"\n"+UserDeviceIMEI;

				// Launch Main Activity
				Intent i = new Intent(getApplicationContext(), SendPushNotification.class);

				// Registering user on our server
				// Sending registraiton details to MainActivity
				i.putExtra("name", UserName);
				i.putExtra("imei", UserDeviceIMEI);  // Send to
				//startActivity(i);
				//finish();

                Toast.makeText( getApplicationContext(),OutputMsg, Toast.LENGTH_LONG).show();
            }
 
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
 
        });
        
        
        txtMessage = (EditText) findViewById(R.id.txtMessage);
		btnSend    = (Button) findViewById(R.id.btnSend);

		db = new DBAdapter(this);

		// db.open();
		HashMap<String, String> data1 = db.getLogininfo();
		String email = data1.get("email");
		String name = data1.get("name");
		String device_imei = data1.get("device_imei");
		deviceIMEI = device_imei;
		under_depart = data1.get("under_depart");
		//Toast.makeText(getApplicationContext(),""+deviceIMEI, Toast.LENGTH_SHORT).show();
		db.close();

     // Click event on Register button
		btnSend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {  
				// Get data from EditText 
				String message = txtMessage.getText().toString(); 
				 
				// WebServer Request URL
		        String serverURL = Config.YOUR_SERVER_URL+"sendpush.php";
		        
		      if(!UserDeviceIMEI.equals(""))
		      {	  
		       /*
		        String deviceIMEI = "";
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
		        */
		        // Use AsyncTask execute Method To Prevent ANR Problem
		        new LongOperation().execute(serverURL,UserDeviceIMEI,message,deviceIMEI); 
		        
		        txtMessage.setText("");
		      }
		      else
		      {
		    	  Toast.makeText(
	                        getApplicationContext(),"Please select send to user.", Toast.LENGTH_LONG).show();
		    	  
		      }
			}
		});


		db = new DBAdapter(this);

		// db.open();
		HashMap<String, String> dataf = db.getLogininfo();
		//String email = dataf.get("email");
		//empname = dataf.get("name");
		//regid = dataf.get("regid");
		String empdevice_imei = dataf.get("device_imei");
		String user_id = dataf.get("user_id");
		String cid=user_id;
		deviceIMEI = empdevice_imei.toString().trim();
		//Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
		db.close();

		if(under_depart.equals("DSA Person")) {

			new ManagerLongOperationSMS().execute(Customer_uSERVER_URL1, cid, deviceIMEI);

		}else{
			new ManagerLongOperationSMS().execute(Customer_uSERVER_URL, cid, deviceIMEI);
		}
	}		



	/*********** Send message *****************/
	
	public class LongOperation  extends AsyncTask<String, Void, String> {
        
    	// Required initialization
    	
        //private final HttpClient Client = new DefaultHttpClient();
       // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(ShowMessage.this); 
        String data  = ""; 
        int sizeData = 0;  
        
        
        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
             
            //Start Progress Dialog (Message)
           
            Dialog.setMessage("Please wait..");
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
         
        protected void onPostExecute(String Result) {
            // NOTE: You can call UI Element here.
             
            // Close progress dialog
            Dialog.dismiss();
            
            if (Error != null) {
            	Toast.makeText(getBaseContext(), "Error: "+Error, Toast.LENGTH_LONG).show();  
                 
            } else {
              
            	// Show Response Json On Screen (activity)
            	 Toast.makeText(getBaseContext(), "Message sent."+Result, Toast.LENGTH_LONG).show();  
                 
             }
        }
         
    }
	
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}



	// Class with extends AsyncTask class
	public class ManagerLongOperationSMS  extends AsyncTask<String, Void, String> {
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(ShowMessage.this);
		String data ="";
		int sizeData = 0;
		protected void onPreExecute() {
			//Dialog.setMessage("Loading...");
			//Dialog.show();
		}
		protected String doInBackground(String... params) {
			BufferedReader reader=null;
			String Content = "";
			try{URL url = new URL(params[0]);
				if(!params[1].equals(""))
					data +="&" + URLEncoder.encode("cid", "UTF-8") + "="+params[1].toString();

				if(!params[2].equals(""))
					data +="&" + URLEncoder.encode("emp_id", "UTF-8") + "="+params[2].toString();


				Log.i("GCM",data);

				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write( data );
				wr.flush();
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while((line = reader.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				Content = sb.toString();
			}
			catch(Exception ex)
			{
				Error = ex.getMessage();
			}
			finally
			{
				try
				{   reader.close();}
				catch(Exception ex) {}
			}return Content;
		}

		protected void onPostExecute(String Content) {Dialog.dismiss();
			if (Error != null) {
			} else {
				aController.clearUserData();
				JSONObject jsonResponse;
				try {

					jsonResponse = new JSONObject(Content);
					JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");

					int lengthJsonArr = jsonMainNode.length();

					for(int i=0; i < lengthJsonArr; i++)
					{

						//Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
						JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
						boolean error       = jsonChildNode.getBoolean("error");
						if (!error) {
							String PENDING       = jsonChildNode.optString("PENDING").toString();

						}else
						{
							//Toast.makeText(getApplicationContext(), "Server Error Try Again!", Toast.LENGTH_LONG).show();
						}

					}


				} catch (JSONException e) {

					e.printStackTrace();
				}


			}
		}

	}


	@Override
	protected void onResume() {
		LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverLoadTodays, new IntentFilter("update-message"));
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private BroadcastReceiver broadcastReceiverLoadTodays = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(getApplicationContext(), intent.getExtras().getString("message"), Toast.LENGTH_SHORT).show();
			TextView textViewMessage = (TextView) findViewById(R.id.lblMessage);
			textViewMessage.setText(intent.getExtras().getString("message"));
		}
	};


}
