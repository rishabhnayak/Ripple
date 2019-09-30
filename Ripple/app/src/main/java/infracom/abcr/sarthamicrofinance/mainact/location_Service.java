package infracom.abcr.sarthamicrofinance.mainact;

/**
 * Created by Narayan Singh on 15/04/2018.
 */


        import android.app.Service;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.IBinder;
        import android.support.annotation.Nullable;
        import android.support.v4.app.ActivityCompat;
        import android.util.Log;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.net.URL;
        import java.net.URLConnection;
        import java.net.URLEncoder;
        import java.util.ArrayList;
        import java.util.HashMap;

        import infracom.abcr.sarthamicrofinance.DBAdapter;
        import infracom.abcr.sarthamicrofinance.utils.GPSTracker;


public class location_Service extends Service {

    String serverURL1 = "https://www.sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/location_update.php";

    DBAdapter db;

    static  String cid,cname;

    GPSTracker gps;
    String lat;
    String lon;
    private String empname,regid;
    String deviceIMEI = "",under_depart;

    private static final int REQUEST_LOCATION = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone
        if (intent != null && intent.getExtras() != null){
            lat = intent.getStringExtra("latitude");
            lon = intent.getStringExtra("longitude");
        }

        db = new DBAdapter(this);

        try {

            // db.open();
            HashMap<String, String> dataf = db.getLogininfo();
            String email = dataf.get("email");
            empname = dataf.get("name");
            regid = dataf.get("regid");
            //  String empdevice_imei = dataf.get("device_imei");
            String user_id = dataf.get("user_id");
            under_depart = dataf.get("under_depart");
            cid = user_id;
            deviceIMEI = dataf.get("device_imei");
          //  Toast.makeText(getApplicationContext(), "" + under_depart, Toast.LENGTH_SHORT).show();
            db.close();

     //   String lat = Double.toString(latitude);
     //   String lon = Double.toString(longitude);

        new LongOperation1().execute(serverURL1,cid,lat,lon,under_depart);


    }catch (Exception e){

    }
        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed
    }

    public class LongOperation1 extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        // private ProgressDialog Dialog = new ProgressDialog(MainActivityD.this);
        String data = "";
        int sizeData = 0;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)
            //   mProgressBar1.setVisibility(View.VISIBLE);
            //   Dialog.setMessage("Loading...");
            // Dialog.show();

        }

        // Call after onPreExecute method
        protected String doInBackground(String... params) {

            /************ Make Post Call To Web Server ***********/
            BufferedReader reader = null;
            String Content = "";
            // Send data
            try {

                // Defined URL  where to send data
                URL url = new URL(params[0]);

                // Set Request parameter
                if (!params[1].equals(""))
                    data += "&" + URLEncoder.encode("cid", "UTF-8") + "=" + params[1].toString();
                if (!params[2].equals(""))
                    data += "&" + URLEncoder.encode("lat", "UTF-8") + "=" + params[2].toString();
                if (!params[3].equals(""))
                    data += "&" + URLEncoder.encode("lon", "UTF-8") + "=" + params[3].toString();
                if (!params[4].equals(""))
                    data += "&" + URLEncoder.encode("emp", "UTF-8") + "=" + params[4].toString();
                Log.i("GCM", data);

                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }

                // Append Server Response To Content String
                Content = sb.toString();
            } catch (Exception ex) {
                Error = ex.getMessage();
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }

            /*****************************************************/
            return Content;
        }

        protected void onPostExecute(String Content) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
//            Dialog.dismiss();


            //   mProgressBar1.setVisibility(View.GONE);

            if (Error != null) {


            } else {

              //  Toast.makeText(getApplicationContext(),"Location Updated", Toast.LENGTH_SHORT).show();

            }
        }

    }

}