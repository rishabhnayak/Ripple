package infracom.abcr.sarthamicrofinance.GroupLoan;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.io.FileNotFoundException;
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
import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.Main;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.crop.imagecropper.CropIntent;
import infracom.abcr.sarthamicrofinance.material.widget.FloatingActionButton;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;


public class AddDSA extends AppCompatActivity {


    ImageLoader imageLoader;

    private String empname,regid;
    Bitmap photo;

    //private Uri mImageCaptureUri;
    //private static final int PICK_FROM_CAMERA = 1;
    // private static final int CROP_FROM_CAMERA = 2;
    // private static final int PICK_FROM_FILE = 3;


    public static final String CROPPED_IMAGE_FILEPATH = "/sdcard/SarthaMicroFinance.jpg";
    Uri selectedImageUri = null;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    Uri imageUri;

    DBAdapter db;
    String deviceIMEI = "",user_id,depart;

    public static String Finalmedia = "";

    private String SERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Add_App_Manager.php";
    private String Add_post = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Add_App_emp.php";
  //  private String FartchLink = "http://sarthamicrofinance.co/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/AddFatch_id_pass.php";
   private String FartchLink = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/AddFatch_id_pass.php";

    private String Customer_uSERVER_URL_one = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/EmpPhoto_Upload.php";
    CollapsingToolbarLayout collapsingToolbar;

    GPSTracker gps;
    double latitude;
    double longitude;


    ImageView Cadd;
    CircleImageView Cadd1;
    static String img1,img2,img3,img4, Name,Fname,tunMobile, Address,City,Zip,Xiemail,XPan="",XPassword;

    Controller aController = null;
    private ProgressDialog pDialog;
    String mypath;
    String Newint_id;

    private static Bitmap bm;
    static Bitmap bitmap;

    String branch_name,branch_id, under_depart;


    private String selectedImagePath = "";
    boolean GallaryPhotoSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dsa);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText etPassword = (EditText)findViewById(R.id.Password);
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

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsingToolbar.setTitle("Fine Hi...");
        //collapsingToolbar.setBackgroundResource(R.drawable.pavanb_pic);
        collapsingToolbar.setBackgroundColor(Color.DKGRAY);

        Intent inte = getIntent();
        String[] data = new String[0];


        depart = inte.getStringExtra("depart");
        // Get Global variable instance
        aController = (Controller) getApplicationContext();

        pDialog = new ProgressDialog(this);
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();

        // Check if Internet Connection present
        if (!aController.isConnectingToInternet()) {

            // Internet Connection is not present
            aController.showAlertDialog(AddDSA.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);

            // stop executing code by return
            return;
        }

        gps = new GPSTracker(AddDSA.this);

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

        imageLoader = new ImageLoader(this);


        db = new DBAdapter(this);

        // db.open();
        HashMap<String, String> dataf = db.getLogininfo();
        String email = dataf.get("email");
        empname = dataf.get("name");
        regid = dataf.get("regid");

        user_id = dataf.get("user_id");
        String empdevice_imei = dataf.get("device_imei");
        deviceIMEI = empdevice_imei.toString().trim();
        under_depart = dataf.get("under_depart");

        branch_name =  dataf.get("branch_name");
        branch_id =  dataf.get("branch_id");
        //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
        db.close();


        new CustomerLongOperation().execute(FartchLink,user_id);




        FloatingActionButton submitB 	= (FloatingActionButton) findViewById(R.id.empphoto);




        Cadd 	= (ImageView) findViewById(R.id.backdrop);
        Cadd1 = (CircleImageView) findViewById(R.id.imageShow1l);
        //      ImageButton buttonP 	= (ImageButton) findViewById(R.id.imageButtonP);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ckeck=1;
                // dialog.show();

                startCropImage();

            }
        });
    }


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


        TextView EMPIDtextView = (TextView) findViewById(R.id.EMPIDtextView);

        final String emp_NewID= EMPIDtextView.getText().toString().trim();

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
                                    .setMessage("Are you Sure?")
                                    //.setMessage("Customer Details!")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Yes button clicked, do something
                                            //Toast.makeText(Tahsil_data_entry.this, "Yes button pressed",Toast.LENGTH_SHORT).show();
                                            //if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                                            //}else {
                                            new LongOperation().execute(Add_post, Name, tunMobile, Address, Xiemail, lat, lon, deviceIMEI,XPassword,Newint_id,depart,branch_name,branch_id);
                                            //}//Toast.makeText(getBaseContext(), "Please Make Sure Your Profile Details is Completed Correctly ", Toast.LENGTH_SHORT).show();
                                            //addEmployee();
                                            //onStartTransaction();
                                        }
                                    })

                                    .setNegativeButton("Cancel", null)						//Do nothing on no
                                    .show();



                        }else{
                            Toast.makeText(AddDSA.this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();return;}
                    }else{
                        Toast.makeText(AddDSA.this, "Please Enter Email ID", Toast.LENGTH_SHORT).show();return;}
                }else{Toast.makeText(AddDSA.this, "Please Enter Address", Toast.LENGTH_SHORT).show();return;}
            }else{Toast.makeText(AddDSA.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();return;}
        }else{Toast.makeText(AddDSA.this, "Please Enter Name", Toast.LENGTH_SHORT).show();return;}

    }


    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(AddDSA.this);
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
                if(!params[9].equals(""))
                    data +="&" + URLEncoder.encode("emp_new_id", "UTF-8") + "="+params[9].toString();
                if(!params[10].equals(""))
                    data +="&" + URLEncoder.encode("depart", "UTF-8") + "="+params[10].toString();
                if(!params[11].equals(""))
                    data +="&" + URLEncoder.encode("branch_name", "UTF-8") + "="+params[11].toString();
                if(!params[12].equals(""))
                    data +="&" + URLEncoder.encode("branch_id", "UTF-8") + "="+params[12].toString();



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

//            Toast.makeText(getApplicationContext(), "Saved Successfully!", Toast.LENGTH_LONG).show();

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


                            String alert       = jsonChildNode.optString("alert").toString();
                            //String email       = jsonChildNode.optString("email").toString();

                            //Log.i("GCM","---"+name);
                            Toast.makeText(getApplicationContext(), "Added Successfully!", Toast.LENGTH_LONG).show();

                            //Intent intent = new Intent(getApplicationContext(), Main.class);

                            // Registering user on our server
                            // Sending registraiton details to MainActivity
                            //String lat = Double.toString(latitude);
                            //String lon = Double.toString(longitude);

                            //intent.putExtra("name", name);
                            //intent.putExtra("email", email);
                            //intent.putExtra("latitude", lat);
                            //intent.putExtra("longitude", lon)
                            // ;
                            // BlurBehind.getInstance().execute(AddDSA.this, new OnBlurCompleteListener() {
                            //    @Override
                            //   public void onBlurComplete() {
                            Intent intent = new Intent(AddDSA.this, Main.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);

                            finish();
                            // }
                            // });
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

                    Dialog.dismiss();
                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }

    }




    // Class with extends AsyncTask class
    public class CustomerPhotoLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(AddDSA.this);
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
        private ProgressDialog Dialog = new ProgressDialog(AddDSA.this);
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
                            String int_id       = jsonChildNode.optString("int_id").toString();

                            // Toast.makeText(getApplicationContext(), "Fetch!  "+status, Toast.LENGTH_LONG).show();


                            //TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal;

                            //collapsingToolbar.setTitle(emp_name);

                            TextView tadsilnamCe = (TextView)findViewById(R.id.tadsilnamCe);

                            TextView  EMPIDtextView = (TextView) findViewById(R.id.EMPIDtextView);
                            tadsilnamCe.setText("Post- "+depart);

                            EMPIDtextView.setText("ID - SMF"+int_id+"DSA");
                            Newint_id="SMF"+int_id+"DSA";
                            // ProductName = (TextView)findViewByI
                            // ProductName = (TextView)findViewById(R.id.Product_Name);
                            //PPprice = (TextView)findViewById(R.id.Product_Price);
                            //PLoanAmount = (TextView)findViewById(R.id.Loan_Amount);
                            // PRate = (TextView)findViewById(R.id.Percenr_Rate);
                            // PTunner = (TextView)findViewById(R.id.Ptuner_y);
                            // Pemi = (TextView)findViewById(R.id.textViewPemi);
                            // Ptotal = (TextView)findViewById(R.id.textViewPtotal);

                            //ProductName.setText(product_compny+"("+product_name+")");
                            //PPprice.setText(product_price+"Rs.");
                            //PLoanAmount.setText(loan_amount+"Rs.");
                            //PRate.setText(percent_rate+"% Of Product Price");
                            //  PTunner.setText(tunerPP);
                            // Pemi.setText(emiPP+"Rs.");
                            // Ptotal.setText(total_amountPP+"Rs.");


                            EditText Password = (EditText) findViewById(R.id.Password);
                            // EditText  Emobile = (EditText) findViewById(R.id.Emobile);
                            // EditText  Eemail = (EditText) findViewById(R.id.Eemail);
                            // EditText  Eaddress = (EditText) findViewById(R.id.Eaddress);
                            //EditText  PinZip= (EditText) findViewById(R.id.PinZip);

                            //EditText  txtNameEmailP = (EditText) findViewById(R.id.txtNameEmailP);
                            //EditText  txtNamePANP= (EditText) findViewById(R.id.txtNamePANP);



                            Password.setText(int_id);
                            // Eemail.setText(Pemail);
                            // Emobile.setText(mobile);

                            // Eaddress.setText(addressP);
                            // txtNameCity.setText(city);
                            // PinZip.setText(zipP);

                            //txtNameEmailP.setText(Pemail);
                            //txtNamePANP.setText(pan_number);


                            //Cadd 	= (ImageView) findViewById(R.id.backdrop);
                            //Cadd1 	= (CircleImageView) findViewById(R.id.imageShow1l);
                            // CaddD 	= (ImageView) findViewById(R.id.imageViewD);
                            // CaddV 	= (ImageView) findViewById(R.id.imageViewV);


                            //imageLoader.DisplayImage(photo, Cadd);
                            //imageLoader.DisplayImage(photo, Cadd);
                            //imageLoader.DisplayImage(photo, Cadd1);
                            //imageLoader.DisplayImage(voter_id, CaddV);

                            // Drawable drawable = LoadImageFromWebOperations(photo);
                            // Cadd.setImageDrawable(drawable);
                            // Cadd1.setImageDrawable(drawable);
/*
                            Glide.with(EmpProfile.this)
                                    //.(R.drawable.ic_launcher)
                                    .load(photo)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(Cadd);
                            Glide.with(EmpProfile.this)
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





}
