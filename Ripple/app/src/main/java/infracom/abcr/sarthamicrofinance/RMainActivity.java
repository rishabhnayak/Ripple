package infracom.abcr.sarthamicrofinance;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gcm.GCMRegistrar;

import java.text.SimpleDateFormat;
import java.util.Date;


public class RMainActivity extends AppCompatActivity {
	// label to display gcm messages
	TextView lblMessage;
	Controller aController;

	private static final String TAG = RMainActivity.class.getSimpleName();

	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;

	public static String name;
	public static String email,eid, user_id,D_under_departKEY,branch_id,branch_name;
	public static String imei,latitude,longitude;
	public String regId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_2main);

		// Get Global Controller Class object
		// (see application tag in AndroidManifest.xml)
		aController = (Controller) getApplicationContext();


		// Check if Internet present
		if (!aController.isConnectingToInternet()) {

			// Internet Connection is not present
			aController.showAlertDialog(RMainActivity.this,
					"Internet Connection Error",
					"Please connect to Internet connection", false);
			// stop executing code by return
			return;
		}

		String deviceIMEI = "";

		// Getting name, email from intent
		Intent i = getIntent();

		name = i.getStringExtra("name");
		email = i.getStringExtra("email");
		latitude = i.getStringExtra("latitude");
		longitude = i.getStringExtra("longitude");
		branch_id = i.getStringExtra("branch_id");
		branch_name = i.getStringExtra("branch_name");
		eid = i.getStringExtra("eid");

		user_id = i.getStringExtra("user_id");
		D_under_departKEY = i.getStringExtra("under_depart");


		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		deviceIMEI = user_id;


		imei  = deviceIMEI;

		// Make sure the device has the proper dependencies.
		//GCMRegistrar.checkDevice(this);

		// Make sure the manifest permissions was properly set
		//GCMRegistrar.checkManifest(this);

		lblMessage = (TextView) findViewById(R.id.lblMessage);

		// Register custom Broadcast receiver to show messages on activity
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				Config.DISPLAY_REGISTRATION_MESSAGE_ACTION));

		// Get GCM registration id
		//final String regId = GCMRegistrar.getRegistrationId(this);
		displayFirebaseRegId();
		// Check if regid already presents

	}

	private void displayFirebaseRegId() {
		SharedPreferences pref = getApplicationContext().getSharedPreferences(infracom.abcr.sarthamicrofinance.Register.Config.SHARED_PREF, 0);
		regId = pref.getString("regId", null);

		Log.e(TAG, "Firebase reg id: " + regId);

		if (!TextUtils.isEmpty(regId)) {
			//	txtRegId.setText("Firebase Reg Id: " + regId);
			Toast.makeText(getApplicationContext(),
					"Your ID Registered with Sartha Microfinance Server",
					Toast.LENGTH_LONG).show();

			if (regId.equals("")) {

				Log.i("GCM K", "--- Regid = ''" + regId);
				// Register with GCM
				//GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);
				Toast.makeText(getApplicationContext(),
						"Your ID Not Registered with Sartha MicroFinance Server Try again!",
						Toast.LENGTH_LONG).show();
				setResult(RESULT_CANCELED);
				finish();

			} else {

				// Device is already registered on GCM Server
				//if (GCMRegistrar.isRegisteredOnServer(this)) {

				//	final Context context = this;
					// Skips registration.
				//	Toast.makeText(getApplicationContext(),"Already registered with GCM Server",Toast.LENGTH_LONG).show();
				//	Log.i("GCM K", "Already registered with GCM Server");

					//GCMRegistrar.unregister(context);

				//} else {

					Log.i("GCM K", "-- gO for registration--");

					// Try to register again, but not in the UI thread.
					// It's also necessary to cancel the thread onDestroy(),
					// hence the use of AsyncTask instead of a raw thread.
					final Context context = this;

					mRegisterTask = new AsyncTask<Void, Void, Void>() {

						@Override
						protected Void doInBackground(Void... params) {

							// Register on our server
							// On server creates a new user
							aController.register(context, name, email, regId, imei, latitude, longitude, user_id, D_under_departKEY,branch_name,branch_id);

							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							mRegisterTask = null;

							finish();
						}

					};

					// execute AsyncTask
					mRegisterTask.execute(null, null, null);

			}
		}
		else{
			//txtRegId.setText("Firebase Reg Id is not received yet!");
		Toast.makeText(getApplicationContext(),
				"Your Not ID Registered with Sartha Microfinance Server!",
				Toast.LENGTH_LONG).show();
		}
	}

	// Create a broadcast receiver to get message and show on screen
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);

			// Waking up mobile if it is sleeping
			aController.acquireWakeLock(getApplicationContext());

			// Display message on the screen
			lblMessage.append(newMessage + "\n");

			Toast.makeText(getApplicationContext(),
					"Got Message: " + newMessage,
					Toast.LENGTH_LONG).show();

			// Releasing wake lock
			aController.releaseWakeLock();
		}
	};

	@Override
	protected void onDestroy() {
		// Cancel AsyncTask
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			// Unregister Broadcast Receiver
			unregisterReceiver(mHandleMessageReceiver);

			//Clear internal resources.
			GCMRegistrar.onDestroy(this);

		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

}
