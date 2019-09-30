package infracom.abcr.sarthamicrofinance;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.google.android.gcm.GCMRegistrar;
import com.squareup.leakcanary.RefWatcher;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.util.Log;

import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.DynamicTab.MainActivityD;
import infracom.abcr.sarthamicrofinance.Profile.Admin_Launch;
import infracom.abcr.sarthamicrofinance.Profile.Customer_Launch;
import infracom.abcr.sarthamicrofinance.Profile.DealerLogin;
import infracom.abcr.sarthamicrofinance.Profile.Manager_Launch;
import infracom.abcr.sarthamicrofinance.Profile.Marketing;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.helper.SessionManager;
import infracom.abcr.sarthamicrofinance.material.app.ThemeManager;

public class Controller extends Application{

    private  final int MAX_ATTEMPTS = 5;
    private  final int BACKOFF_MILLI_SECONDS = 2000;
    private  final Random random = new Random();

    private SessionManager session;
    String serverUrl;
    private  ArrayList<UserData> UserDataArr = new ArrayList<UserData>();


    // Register this account with the server.
    void register(final Context context, String name, String email, final String regId,final String IMEI, final String latitude, final String longitude,final String user_id, final String D_under_departKEY, String branch_name, String branch_id) {

        Log.i(Config.TAG, "registering device (regId = " + regId + ")");

        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        params.put("name", name);
        params.put("email", email);
        params.put("imei", IMEI);
        params.put("latitude", latitude);
        params.put("longitude", longitude);
        params.put("user_id", user_id);
        params.put("dept", D_under_departKEY);


        params.put("branch_name", branch_name);
        params.put("branch_id", branch_id);


        session = new SessionManager(getApplicationContext());

        if(D_under_departKEY.equals("Customer")){


            serverUrl = Config.YOUR_SERVER_URL+"Login_register.php";

        }else{
            if(D_under_departKEY.equals("DSA Person")){


                serverUrl = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Login_New_register.php";

            }else{
                // Server url to post gcm registration data
                serverUrl = Config.YOUR_SERVER_URL+"register.php";

            }

        }

        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);

        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {

            Log.d(Config.TAG, "Attempt #" + i + " to register");

            try {
                //Send Broadcast to sho message on screen
                displayRegistrationMessageOnScreen(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));

                // Post registration values to web server
                post(serverUrl, params);

                GCMRegistrar.setRegisteredOnServer(context, true);
                //Send Broadcast to Show message on screen
                String message = context.getString(R.string.server_registered);
                displayRegistrationMessageOnScreen(context, message);

                DBAdapter.addDeviceData(name, email, regId, IMEI, user_id,D_under_departKEY, branch_name, branch_id);

                // Launch Main Activity
                //Intent i1 = new Intent(getApplicationContext(), Launch.class);
                //i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(i1);



                session.setLogin(true);

/*
                switch (D_under_departKEY) {
                    case "Manager":
                        Intent i1 = new Intent(getApplicationContext(), Manager_Launch.class);
                        startActivity(i1);
                 //       finish();
                        break;
                    case "Admin":
                        Intent i2 = new Intent(getApplicationContext(), Admin_Launch.class);
                        startActivity(i2);
                  //      finish();
                        break;
                    case "Customer":
                        Intent i3 = new Intent(getApplicationContext(), Customer_Launch.class);
                        startActivity(i3);
                   //     finish();
                        break;
                    case "Dealer":
                        Intent i4 = new Intent(getApplicationContext(), DealerLogin.class);
                        startActivity(i4);
                     //   finish();
                        break;
                    case "Sales Person":
                        Intent i5 = new Intent(getApplicationContext(), Launch.class);
                        startActivity(i5);
                       // finish();
                        break;
                }

*/
                if(D_under_departKEY.equals("Manager")){
                    Intent i2 = new Intent(getApplicationContext(), Manager_Launch.class);
                    i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i2);
                    //finish();
                }else {
                    // Launch Main Activity
                    if(D_under_departKEY.equals("Admin")){
                        Intent i65= new Intent(getApplicationContext(), Admin_Launch.class);
                        i65.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i65);
                    }else {
                        if (D_under_departKEY.equals("Customer")) {
                            Intent i6 = new Intent(getApplicationContext(), Customer_Launch.class);
                            i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i6);
                            //finish();
                        } else {
                            if (D_under_departKEY.equals("Dealer")) {
                                Intent i6 = new Intent(getApplicationContext(), DealerLogin.class);
                                i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i6);
                                //finish();
                            } else {
                                if (D_under_departKEY.equals("DSA Person")) {
                                    Intent i6 = new Intent(getApplicationContext(), Marketing.class);
                                    i6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i6);
                                    //finish();
                                } else {
                                    // Launch Main Activity
                                    Intent i4 = new Intent(getApplicationContext(), Launch.class);
                                    i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i4);
                                    //finish();
                                }
                            }
                        }
                        // finish();
                    }

                }





                return;
            } catch (IOException e) {

                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).

                Log.e(Config.TAG, "Failed to register on attempt " + i + ":" + e);

                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {

                    Log.d(Config.TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);

                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(Config.TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return;
                }

                // increase backoff exponentially
                backoff *= 2;
            }
        }

        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);

        //Send Broadcast to Show message on screen
        displayRegistrationMessageOnScreen(context, message);
    }

    // Unregister this account/device pair within the server.
    void unregister(final Context context, final String regId,final String IMEI) {

        Log.i(Config.TAG, "unregistering device (regId = " + regId + ")");

        String serverUrl = Config.YOUR_SERVER_URL+"unregister.php";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        params.put("imei", IMEI);

        try {
            post(serverUrl, params);
            GCMRegistrar.setRegisteredOnServer(context, false);

            String message = context.getString(R.string.server_unregistered);
            displayRegistrationMessageOnScreen(context, message);
        } catch (IOException e) {

            // At this point the device is unregistered from GCM, but still
            // registered in the our server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.

            String message = context.getString(R.string.server_unregister_error,
                    e.getMessage());
            Log.i("GCM K", message);

            displayRegistrationMessageOnScreen(context, message);
        }
    }

    // Issue a POST request to the server.
    private static void post(String endpoint, Map<String, String> params)
            throws IOException {

        URL url;
        try {

            url = new URL(endpoint);

        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }

        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();

        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }

        String body = bodyBuilder.toString();

        Log.v(Config.TAG, "Posting '" + body + "' to " + url);

        byte[] bytes = body.getBytes();

        HttpURLConnection conn = null;
        try {

            Log.e("URL", "> " + url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();

            // handle the response
            int status = conn.getResponseCode();

            // If response is not success
            if (status != 200) {

                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }



    // Checking for all possible internet providers
    public boolean isConnectingToInternet(){

        ConnectivityManager connectivity =
                (ConnectivityManager) getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

    // Notifies UI to display a message.
    void displayRegistrationMessageOnScreen(Context context, String message) {

        Intent intent = new Intent(context,ShowMessage.class);
        intent.putExtra(Config.EXTRA_MESSAGE, message);

        // Send Broadcast to Broadcast receiver with message
        context.sendBroadcast(intent);

    }

    // Notifies UI to display a message.
    void displayMessageOnScreen(Context context, String title,String message,String imei) {

        Intent intent = new Intent(context,ShowMessage.class);
        intent.putExtra(Config.EXTRA_MESSAGE, message);
        intent.putExtra("name", title);
        intent.putExtra("imei", imei);
        // Send Broadcast to Broadcast receiver with message
        context.sendBroadcast(intent);

    }


    //Function to display simple Alert Dialog
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Set Dialog Title
        alertDialog.setTitle(title);

        // Set Dialog Message
        alertDialog.setMessage(message);

        if(status != null)
            // Set alert dialog icon
            alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Set OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Show Alert Message
        alertDialog.show();
    }

    private PowerManager.WakeLock wakeLock;

    public  void acquireWakeLock(Context context) {
        if (wakeLock != null) wakeLock.release();

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "WakeLock");

        wakeLock.acquire();
    }

    public  void releaseWakeLock() {
        if (wakeLock != null) wakeLock.release(); wakeLock = null;
    }

    // Get UserData model object from UserDataArrlist at specified position
    public UserData getUserData(int pPosition) {

        return UserDataArr.get(pPosition);
    }

    // Add UserData model object to UserDataArrlist
    public void setUserData(UserData Products) {

        UserDataArr.add(Products);

    }

    //Get Number of UserData model object contains by UserDataArrlist
    public int getUserDataSize() {

        return UserDataArr.size();
    }

    // Clear all user data from arraylist
    public void clearUserData() {

        UserDataArr.clear();
    }

    public static RefWatcher getRefWatcher(Context context) {
        Controller application = (Controller) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG)
            //refWatcher = LeakCanary.install(this);
            ThemeManager.init(this, 2, 0, null);
    }
}
