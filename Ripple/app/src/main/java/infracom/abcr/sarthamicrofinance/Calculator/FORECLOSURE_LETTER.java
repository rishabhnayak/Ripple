package infracom.abcr.sarthamicrofinance.Calculator;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Dpost.ImageLoader;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.Main;
import infracom.abcr.sarthamicrofinance.Profile.Search_All_Customer;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.Utility;
import infracom.abcr.sarthamicrofinance.material.app.Dialog;
import infracom.abcr.sarthamicrofinance.material.app.DialogFragment;
import infracom.abcr.sarthamicrofinance.material.app.SimpleDialog;
import infracom.abcr.sarthamicrofinance.material.app.ThemeManager;
import infracom.abcr.sarthamicrofinance.material.widget.FloatingActionButton;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;

public class FORECLOSURE_LETTER extends AppCompatActivity {

     //imageLoader;

    private String empname,regid,under_depart;
    Bitmap photo;

    //private Uri mImageCaptureUri;
    //private static final int PICK_FROM_CAMERA = 1;
    // private static final int CROP_FROM_CAMERA = 2;
    // private static final int PICK_FROM_FILE = 3;

    ImageView imageView;
    Button buttonCamera, buttonGallery;
    File file;
    Uri uri;
    Intent CamIntent, GalIntent, CropIntent;
    public static final int RequestPermissionCode = 1;
    DisplayMetrics displayMetrics;
    int width, height;

    TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal,view8,ROIP,LTVP;



    public static final String CROPPED_IMAGE_FILEPATH = "/sdcard/SarthaMicroFinance.jpg";
    Uri selectedImageUri = null;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    Uri imageUri;

    DBAdapter db;
    String deviceIMEI = "",user_id;

    private String userChoosenTask;

    public static String Finalmedia = "";

   // private String SERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Emp_Detail_Up.php";
    private String Customer_uSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/FORECLOSURE_Calculator.php";
//    private String Customer_uSERVER_URL_one = "http://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/EmpPhoto_Upload.php";


  //  private String DSERVER_URL = "http://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Emp_Detail_Up.php";
   // private String DCustomer_uSERVER_URL = "http://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Emp_details_app.php";
//    private String DCustomer_uSERVER_URL_one = "http://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/EmpPhoto_Upload.php";

    CollapsingToolbarLayout collapsingToolbar;

    GPSTracker gps;
    double latitude;
    double longitude;

    TextView Product_Cost,Sanction_Amount,ROI,Tenor,Principal,pwr,Total_Principal,Total_Interest,EMI_paid,Waiver,Principal_Outstanding
            ,Interest_Outstanding,Other_dues,Foreclosure_Amount,tadsilnamCe,EMPIDtextView;

    ImageView Cadd;
    CircleImageView Cadd1;
    static String img1,img2,img3,img4, Name,Fname,tunMobile, Address,City,Zip,Xiemail,XPan="",XPassword;

    Controller aController = null;
    private ProgressDialog pDialog;
    String mypath;

    private static Bitmap bm;
    static Bitmap bitmap;


    private String selectedImagePath = "";
    boolean GallaryPhotoSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foreclosure__letter);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsingToolbar.setTitle("Fine Hi...");
        //collapsingToolbar.setBackgroundResource(R.drawable.pavanb_pic);
        collapsingToolbar.setBackgroundColor(Color.DKGRAY);

        // Get Global variable instance
        aController = (Controller) getApplicationContext();

        pDialog = new ProgressDialog(this);
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();

        // Check if Internet Connection present
        if (!aController.isConnectingToInternet()) {

            // Internet Connection is not present
            aController.showAlertDialog(FORECLOSURE_LETTER.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);

            // stop executing code by return
            return;
        }
/*
        gps = new GPSTracker(FORECLOSURE_LETTER.this);

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
*/
       // imageLoader = new ImageLoader(this);

        //Cadd 	= (TextView)findViewById(R.id.Product_Cost);
        Product_Cost= (TextView)findViewById(R.id.Product_Cost);
        Sanction_Amount= (TextView)findViewById(R.id.Sanction_Amount);
        ROI= (TextView)findViewById(R.id.ROI);
        Tenor= (TextView)findViewById(R.id.Tenor);
        Principal= (TextView)findViewById(R.id.Principal);
        pwr= (TextView)findViewById(R.id.pwr);
        Total_Principal= (TextView)findViewById(R.id.Total_Principal);
        Total_Interest= (TextView)findViewById(R.id.Total_Interest);
        EMI_paid= (TextView)findViewById(R.id.EMI_paid);
        Waiver= (TextView)findViewById(R.id.Waiver);
        Principal_Outstanding= (TextView)findViewById(R.id.Principal_Outstanding);
        Interest_Outstanding= (TextView)findViewById(R.id.Interest_Outstanding);
        Other_dues= (TextView)findViewById(R.id.Other_dues);
        Foreclosure_Amount= (TextView)findViewById(R.id.Foreclosure_Amount);

        EMPIDtextView= (TextView)findViewById(R.id.EMPIDtextView);
        tadsilnamCe= (TextView)findViewById(R.id.tadsilnamCe);

        Cadd 	= (ImageView) findViewById(R.id.backdrop);
        Cadd1 = (CircleImageView) findViewById(R.id.imageShow1l);
        //      ImageButton buttonP 	= (ImageButton) findViewById(R.id.imageButtonP);
        sear();
    }



    /*
        private void startCropImage() {
    
            // Create a CropIntent
            CropIntent intent = new CropIntent();
    
            // Set the source image filepath/URL and output filepath/URL (Optional)
            //intent.setImagePath("/sdcard/source.jpg");
            intent.setOutputPath(CROPPED_IMAGE_FILEPATH);
    
            // Set a fixed crop window size (Optional)
            //intent.setOutputSize(640,480);
    
            // set the max crop window size (Optional)
            //intent.setMaxOutputSize(800,600);
    
            // Set a fixed crop window's width/height aspect (Optional)
            //intent.setAspect(3,2);
    
            // start ImageCropper activity with certain request code and listen for result
            startActivityForResult(intent.getIntent(this), 0);
        }
    
    
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode != RESULT_OK) {
                return;
            }
            if (requestCode == 0) {
                Uri croppedUri = data.getExtras().getParcelable(MediaStore.EXTRA_OUTPUT);
                try {
                    InputStream in = getContentResolver().openInputStream(croppedUri);
                    Bitmap b = BitmapFactory.decodeStream(in);
                    // ImageView14.setImageBitmap(b);
                    selectedImagePath = croppedUri.getPath();
                    // Bitmap bm = decodeFile(selectedImagePath);
    
                    int scale = 1;
    
    
                    //Cadd 	= (ImageView) findViewById(R.id.imageViewP1);
                    // mImageView.setImageBitmap(b);
                    //Toast.makeText(this,"Crop success，saved at"+CROPPED_IMAGE_FILEPATH,Toast.LENGTH_LONG).show();
    
    
                    BitmapFactory.Options o2 = new BitmapFactory.Options();
                    o2.inSampleSize = scale;
                    bitmap = BitmapFactory.decodeFile(selectedImagePath, o2);
    
                    ByteArrayOutputStream bos =new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                    byte[] bb = bos.toByteArray();
    
    
    
                        img1 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                        Cadd1.setImageBitmap(bitmap);
                        Cadd.setImageBitmap(bitmap);
    
                    String photo1="image";
                    // if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    // }else {
                    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, deviceIMEI, img1);
    
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    
    */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_view_icon_text_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, Main.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public  void SubmitPRo(View view){


        EditText Ename = (EditText) findViewById(R.id.Ename);
        EditText  Eemail = (EditText) findViewById(R.id.Eemail);
        EditText  Emobile = (EditText) findViewById(R.id.Emobile);
        EditText  Eaddress = (EditText) findViewById(R.id.Eaddress);

        final EditText  Password = (EditText) findViewById(R.id.Password);

        XPassword = Password.getText().toString();

        Name = Ename.getText().toString();
        tunMobile = Emobile.getText().toString();
        Address = Eaddress.getText().toString();
        Xiemail = Eemail.getText().toString();


        final String lat = Double.toString(latitude);
        final String lon = Double.toString(longitude);



        if(Name.trim().length() > 2){
            if(tunMobile.trim().length() > 2){
                if(Address.trim().length() > 2){
                    if(Xiemail.trim().length() > 2){
                        if(XPassword.trim().length() > 2){

                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                            builder
                                    //.setTitle("............Pay")
                                    .setMessage("Are you Sure to Change Information?")
                                    //.setMessage("Customer Details!")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Yes button clicked, do something
                                            //Toast.makeText(Tahsil_data_entry.this, "Yes button pressed",Toast.LENGTH_SHORT).show();
                                            //if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                                            //}else {
                                            if(under_depart.equals("DSA Person")) {
                                               // new LongOperation().execute(DSERVER_URL, Name, tunMobile, Address, Xiemail, lat, lon, deviceIMEI, XPassword);
                                            }else{
                                               // new LongOperation().execute(SERVER_URL, Name, tunMobile, Address, Xiemail, lat, lon, deviceIMEI, XPassword);

                                            }

                                            //}//Toast.makeText(getBaseContext(), "Please Make Sure Your Profile Details is Completed Correctly ", Toast.LENGTH_SHORT).show();
                                            //addEmployee();
                                            //onStartTransaction();
                                        }
                                    })

                                    .setNegativeButton("Cancel", null)						//Do nothing on no
                                    .show();



                        }else{
                            Toast.makeText(FORECLOSURE_LETTER.this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();return;}
                    }else{Toast.makeText(FORECLOSURE_LETTER.this, "Please Enter Email ID", Toast.LENGTH_SHORT).show();return;}
                }else{Toast.makeText(FORECLOSURE_LETTER.this, "Please Enter Address", Toast.LENGTH_SHORT).show();return;}
            }else{Toast.makeText(FORECLOSURE_LETTER.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();return;}
        }else{Toast.makeText(FORECLOSURE_LETTER.this, "Please Enter Name", Toast.LENGTH_SHORT).show();return;}

    }


    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(FORECLOSURE_LETTER.this);
        String data ="";
        int sizeData = 0;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

            Dialog.setMessage("Loading...");
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
                    data +="&" + URLEncoder.encode("Name", "UTF-8") + "="+params[1].toString();
                if(!params[2].equals(""))
                    data +="&" + URLEncoder.encode("tunMobile", "UTF-8") + "="+params[2].toString();
                if(!params[3].equals(""))
                    data +="&" + URLEncoder.encode("Address", "UTF-8") + "="+params[3].toString();
                if(!params[4].equals(""))
                    data +="&" + URLEncoder.encode("email", "UTF-8") + "="+params[4].toString();
                if(!params[5].equals(""))
                    data +="&" + URLEncoder.encode("lat", "UTF-8") + "="+params[5].toString();
                if(!params[6].equals(""))
                    data +="&" + URLEncoder.encode("lon", "UTF-8") + "="+params[6].toString();
                if(!params[7].equals(""))
                    data +="&" + URLEncoder.encode("emp_id", "UTF-8") + "="+params[7].toString();
                if(!params[8].equals(""))
                    data +="&" + URLEncoder.encode("Password", "UTF-8") + "="+params[8].toString();

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
            Toast.makeText(getApplicationContext(), "Saved Successfully!", Toast.LENGTH_LONG).show();

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

                        //Toast.makeText(getApplicationContext(), "Saved Successfully! Please Wait...", Toast.LENGTH_LONG).show();
                        /****** Get Object for each JSON node.***********/
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                        /******* Fetch node values **********/

                        boolean error       = jsonChildNode.getBoolean("error");
                        if (!error) {
                            //String name       = jsonChildNode.optString("name").toString();
                            //String email       = jsonChildNode.optString("email").toString();

                            //Log.i("GCM","---"+name);
                            //  Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();

                            //Intent intent = new Intent(getApplicationContext(), MainActivityD.class);

                            // Registering user on our server
                            // Sending registraiton details to MainActivity
                            //String lat = Double.toString(latitude);
                            //String lon = Double.toString(longitude);

                            //intent.putExtra("name", name);
                            //intent.putExtra("email", email);
                            //intent.putExtra("latitude", lat);
                            //intent.putExtra("longitude", lon)
                            // ;
                            BlurBehind.getInstance().execute(FORECLOSURE_LETTER.this, new OnBlurCompleteListener() {
                                @Override
                                public void onBlurComplete() {
                                    Intent intent = new Intent(FORECLOSURE_LETTER.this, Main.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);

                                    finish();
                                }
                            });
                            // startActivity(intent);
                            // finish();
                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Server Problem Try Again!", Toast.LENGTH_LONG).show();
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




    // Class with extends AsyncTask class
    public class CustomerPhotoLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(FORECLOSURE_LETTER.this);
        String data ="";
        int sizeData = 0;
        protected void onPreExecute() {
            Dialog.setMessage("Uploading...");
            Dialog.show();
        }
        protected String doInBackground(String... params) {
            BufferedReader reader=null;
            String Content = "";
            try{URL url = new URL(params[0]);
                if(!params[1].equals(""))
                    data +="&" + URLEncoder.encode("photo", "UTF-8") + "="+params[1].toString();
                if(!params[2].equals(""))
                    data +="&" + URLEncoder.encode("emp_id", "UTF-8") + "="+params[2].toString();
                if(!params[3].equals(""))
                    data +="&" + URLEncoder.encode("img", "UTF-8") + "="+params[3].toString();

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

                        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        boolean error       = jsonChildNode.getBoolean("error");
                        if (!error) {


                            Toast.makeText(getApplicationContext(), "Uploaded Successfully!", Toast.LENGTH_LONG).show();

                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Server Error Try Again!", Toast.LENGTH_LONG).show();
                        }

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }

    }







    // Class with extends AsyncTask class
    public class CustomerLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(FORECLOSURE_LETTER.this);
        String data ="";
        int sizeData = 0;
        protected void onPreExecute() {
            Dialog.setMessage("Loading...");
            Dialog.show();
        }
        protected String doInBackground(String... params) {
            BufferedReader reader=null;
            String Content = "";
            try{URL url = new URL(params[0]);
                if(!params[1].equals(""))
                    data +="&" + URLEncoder.encode("emp_id", "UTF-8") + "="+params[1].toString();

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

                        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        boolean error       = jsonChildNode.getBoolean("error");
                        if (!error) {

                            String customer_id       = jsonChildNode.optString("customer_id").toString();
                            String order_id       = jsonChildNode.optString("order_id").toString();
                            String pan_number       = jsonChildNode.optString("pan_number").toString();
                            String emiPP       = jsonChildNode.optString("emi").toString();
                            String product_compny       = jsonChildNode.optString("product_compny").toString();
                            String product_name       = jsonChildNode.optString("product_name").toString();
                            String product_price       = jsonChildNode.optString("product_price").toString();
                            String loan_amount       = jsonChildNode.optString("loan_amount").toString();
                            String product_poan_percent       = jsonChildNode.optString("product_poan_percent").toString();
                            String total_amountPP       = jsonChildNode.optString("total_amount").toString();
                            String percent_rate       = jsonChildNode.optString("percent_rate").toString();
                            String tunerPP       = jsonChildNode.optString("tuner").toString();
                            String emp_id       = jsonChildNode.optString("emp_id").toString();
                            String emp_name       = jsonChildNode.optString("emp_name").toString();

                            String Pemail       = jsonChildNode.optString("email").toString();
                            String father_name       = jsonChildNode.optString("father_name").toString();
                            String addressP       = jsonChildNode.optString("address").toString();
                            String city       = jsonChildNode.optString("city").toString();
                            String zipP       = jsonChildNode.optString("zip").toString();
                            String photo       = jsonChildNode.optString("photo").toString();
                            String aadhar_card       = jsonChildNode.optString("aadhar_card").toString();
                            String driving_licence       = jsonChildNode.optString("driving_licence").toString();
                            String voter_id       = jsonChildNode.optString("voter_id").toString();

                            String product_id_photo       = jsonChildNode.optString("product_id_photo").toString();
                            String bank_statment_1       = jsonChildNode.optString("bank_statment_1").toString();
                            String bank_statment_2       = jsonChildNode.optString("bank_statment_2").toString();






                            String mobile       = jsonChildNode.optString("mobile").toString();
                            String customer_name       = jsonChildNode.optString("customer_name").toString();
                            String message       = jsonChildNode.optString("message").toString();

                            String freqency       = jsonChildNode.optString("freqency").toString();
                            String scheme       = jsonChildNode.optString("scheme").toString();
                            String Advance_EMI       = jsonChildNode.optString("Advance_EMI").toString();
                            String DownPay       = jsonChildNode.optString("DownPay").toString();
                            String Disbursement1       = jsonChildNode.optString("Disbursement").toString();

                            String Bank_name       = jsonChildNode.optString("Bank_name").toString();
                            String Bank_branch       = jsonChildNode.optString("Bank_branch").toString();
                            String Account_number       = jsonChildNode.optString("Account_number").toString();
                            String ifsc       = jsonChildNode.optString("ifsc").toString();

                            String LTV_Policy       = jsonChildNode.optString("LTV_Policy").toString();
                            String ROI_policy       = jsonChildNode.optString("ROI_policy").toString();

                            String Mother_Name5       = jsonChildNode.optString("Mother_Name").toString();
                            String DOB5       = jsonChildNode.optString("DOB").toString();
                            String No_of_Dependents5       = jsonChildNode.optString("No_of_Dependents").toString();
                            String ADHAR_Number5       = jsonChildNode.optString("ADHAR_Number").toString();
                            String DL5       = jsonChildNode.optString("DL").toString();
                            String land_mark5       = jsonChildNode.optString("land_mark").toString();
                            String land_mark25       = jsonChildNode.optString("land_mark2").toString();
                            String land_mark35      = jsonChildNode.optString("land_mark3").toString();
                            String zip5       = jsonChildNode.optString("zip").toString();
                            String PIN5       = jsonChildNode.optString("PIN").toString();
                            String PIN35       = jsonChildNode.optString("PIN3").toString();
                            String City5       = jsonChildNode.optString("City").toString();
                            String City25       = jsonChildNode.optString("City2").toString();
                            String City35       = jsonChildNode.optString("City3").toString();
                            String State5       = jsonChildNode.optString("State").toString();
                            String State25       = jsonChildNode.optString("State2").toString();
                            String State35       = jsonChildNode.optString("State3").toString();
                            String Residence5       = jsonChildNode.optString("Residence").toString();
                            String Office5       = jsonChildNode.optString("Office").toString();
                            String Nationality5       = jsonChildNode.optString("Nationality").toString();
                            String Annual_Income5       = jsonChildNode.optString("Annual_Income").toString();
                            String no_year_current_reci5       = jsonChildNode.optString("no_year_current_reci").toString();
                            String product5       = jsonChildNode.optString("product").toString();
                            String product_imei5       = jsonChildNode.optString("product_imei").toString();
                            String Gender5       = jsonChildNode.optString("Gender").toString();
                            String Marital_status5       = jsonChildNode.optString("Marital_status").toString();
                            String Category5       = jsonChildNode.optString("Category").toString();
                            String house_type5       = jsonChildNode.optString("house_type").toString();

                            String Address11       = jsonChildNode.optString("Address1").toString();
                            String Address12       = jsonChildNode.optString("Address2").toString();
                            String Address13       = jsonChildNode.optString("Address3").toString();

                            String QualificationS       = jsonChildNode.optString("Qualification").toString();
                            String OccupationS       = jsonChildNode.optString("Occupation").toString();

                            String query_sts       = jsonChildNode.optString("query_sts").toString();



                            new DownloadImageFromInternet(Cadd).execute(photo);
                            new DownloadImageFromInternet(Cadd1).execute(photo);

                            TextView Frequency = (TextView)findViewById(R.id.Frequency);
                            TextView SchemeType = (TextView)findViewById(R.id.SchemeType);
                            TextView AdvanceEMI = (TextView)findViewById(R.id.AdvanceEMI);
                            TextView DownPayment = (TextView)findViewById(R.id.DownPayment);
                            TextView Disbursement = (TextView)findViewById(R.id.Disbursement);
                            Frequency.setText(freqency);
                            SchemeType.setText(scheme);
                            AdvanceEMI.setText(Advance_EMI);
                            DownPayment.setText(DownPay);
                            Disbursement.setText(Disbursement1);


                            String payedEMI       = jsonChildNode.optString("payedEMI").toString();
                            String notPayedEM       = jsonChildNode.optString("notPayedEM").toString();

                            SchemeType.setText(payedEMI + "|" + notPayedEM + "  EMI Type");

                            ProductName = (TextView)findViewById(R.id.Product_Name);
                            PPprice = (TextView)findViewById(R.id.Product_Price);
                            PLoanAmount = (TextView)findViewById(R.id.Loan_Amount);
                            PRate = (TextView)findViewById(R.id.Percenr_Rate);
                            PTunner = (TextView)findViewById(R.id.Ptuner_y);
                            Pemi = (TextView)findViewById(R.id.textViewPemi);
                            Ptotal = (TextView)findViewById(R.id.textViewPtotal);


                            ProductName.setText(product_compny+"("+product_name+")");
                            PPprice.setText(product_price+"Rs.");
                            PLoanAmount.setText(loan_amount+"Rs.");
                            PRate.setText(percent_rate+"%");
                            PTunner.setText(tunerPP);
                            Pemi.setText(emiPP+"Rs.");
                            Ptotal.setText(total_amountPP+"Rs.");

                            ROIP = (TextView)findViewById(R.id.ROIP);
                            LTVP = (TextView)findViewById(R.id.LTVP);

                            ROIP.setText(ROI_policy+"%");
                            LTVP.setText(LTV_Policy+"Rs.");

                            //  TextView Product_Cost,Sanction_Amount,ROI,Tenor,Principal,pwr,Total_Principal,Total_Interest,EMI_paid,Waiver,Principal_Outstanding
                            //        ,Interest_Outstanding,Other_dues,Foreclosure_Amount,tadsilnamCe,EMPIDtextView;

                            Product_Cost.setText(product_price);
                            Sanction_Amount.setText(loan_amount);
                            ROI.setText(percent_rate);
                            Tenor.setText(tunerPP);


                            EMPIDtextView.setText(customer_id);
                            tadsilnamCe.setText(customer_name);

                            Double b = Double.valueOf(loan_amount.toString().trim());
                            Double d = Double.valueOf(tunerPP.toString().trim());
                            Double c = Double.valueOf(percent_rate.toString().trim());

                            Double e, f, h;

                            e=b/d;

                            f =(b*c)/100;



                            Principal.setText(e.toString());
                            pwr.setText(f.toString());

                            Total_Principal.setText(b.toString());

                            h = f*d;
                            Total_Interest.setText(h.toString());

                            Double k,l,n;

                            Double ii = Double.valueOf(payedEMI.toString().trim());
                            Double j = Double.valueOf(notPayedEM.toString().trim());

                            k = b - (e*ii);

                            //.setText(notPayedEM);
                            EMI_paid.setText(payedEMI);
                            Waiver.setText(notPayedEM);



                                    Principal_Outstanding.setText(k.toString());
                            l =(d-j-ii)-f;
                                    Interest_Outstanding.setText(l.toString());


                            n=k+l;

                            Other_dues.setText("");
                            Foreclosure_Amount.setText(n.toString());

                            // Drawable drawable = LoadImageFromWebOperations(photo);
                            // Cadd.setImageDrawable(drawable);
                            // Cadd1.setImageDrawable(drawable);
/*
                            Glide.with(FORECLOSURE_LETTER.this)
                                    //.(R.drawable.ic_launcher)
                                    .load(photo)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(Cadd);
                            Glide.with(FORECLOSURE_LETTER.this)
                                    .load(photo)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(Cadd1);
*/
                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Server Error Try Again!", Toast.LENGTH_LONG).show();
                        }

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }

    }


    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            //Toast.makeText(getApplicationContext(), "Loading Images...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }


    public void selectCustomerF(View view){
        sear();

    }

public void sear(){


    Dialog.Builder builder = null;

    boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;


    builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){

        @Override
        protected void onBuildDone(Dialog dialog) {
            dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        @Override
        public void onPositiveActionClicked(DialogFragment fragment) {
            EditText et_pass = (EditText)fragment.getDialog().findViewById(R.id.SSCID);
            //Toast.makeText(mActivity, "Connected", Toast.LENGTH_SHORT).show();
            String  cc=et_pass.getText().toString().trim();
            if(cc.toString().trim().equals("")){
                Toast.makeText(getApplicationContext(), "Please Enter Customer ID.", Toast.LENGTH_SHORT).show();
            }else{


                new CustomerLongOperation().execute(Customer_uSERVER_URL, cc);

                // Intent intent = new Intent(FORECLOSURE_LETTER.this, Search_All_Customer.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                //intent.putExtra("cname", "KJJHFDID");
                // intent.putExtra("cid", cc);
                //intent.putExtra("sts", "QUERY");
                // startActivity(intent);

/*
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Search_All_Customer.this);
                    builder
                            .setTitle("Search")
                            .setMessage("Are you Sure?")
                            //.setMessage("Customer Details!")
                            .setIcon(android.R.drawable.ic_menu_send)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // String appro="QUERY";
                                    //showBottomSheet(appro);



                                }
                            })

                            .setNegativeButton("NO", null)						//Do nothing on no
                            .show();

                    */

            }

            super.onPositiveActionClicked(fragment);
        }

        @Override
        public void onNegativeActionClicked(DialogFragment fragment) {
            //Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_SHORT).show();
            super.onNegativeActionClicked(fragment);
        }
    };

    builder.title("Customer Details")
            .positiveAction("Search")
            .negativeAction("CANCEL")
            .contentView(R.layout.search_customer_popup);


    DialogFragment fragment = DialogFragment.newInstance(builder);
    fragment.show(getSupportFragmentManager(), null);


}

}
