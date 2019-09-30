package infracom.abcr.sarthamicrofinance.Order;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import infracom.abcr.sarthamicrofinance.Config;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Dpost.ImageLoader;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.DynamicTab.MainActivityTask;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.crop.CropOption;
import infracom.abcr.sarthamicrofinance.crop.CropOptionAdapter;
import infracom.abcr.sarthamicrofinance.crop.imagecropper.CropIntent;
import infracom.abcr.sarthamicrofinance.material.widget.FloatingActionButton;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;

public class Task_Process extends AppCompatActivity {

    //ConnectionDetector cd;

    ImageLoader imageLoader;

    String status;
    ProgressDialog dialog;
    public String user_id, password, email, name, lastname, mobile, fullname,sts;
    private Uri mImageCaptureUri, mImageCaptureUriTo;
    ImageView Cadd, CaddV, CaddA, CaddD;

    private static Bitmap bm;
    static Bitmap bitmap;
    private String SERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/imageUp.php";

    private String Customer_uSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/customer_details.php";


    private String Customer_uSERVER_URL_one = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/CPhoto_Upload.php";

    private String selectedImagePath = "";
    boolean GallaryPhotoSelected = false;
    TextView Titletxt, DestxtLastname,LTVP,ROIP;

    Controller aController = null;

    static String img1,img2,img3,img4, Name,Fname,tunMobile, Address,City,Zip,Xiemail,XPan="";

    private ProgressDialog pDialog;
    String mypath;

int Ckeck=0;
    public static String Finalmedia = "";

    private static final String TAG = Task_Process.class.getSimpleName();

    private String empname,regid;
    Bitmap photo;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    Uri selectedImageUri = null;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    Uri imageUri;


    public static final String CROPPED_IMAGE_FILEPATH = "/sdcard/SarthaMicroFinance.jpg";

    private ImageView mImageView,ImageView14;


    TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal;

    DBAdapter db;

    static  String cid,cname;

    GPSTracker gps;
    double latitude;
    double longitude;

    String bankName,BankBranchName,AccountNum,IFSCC;
    String deviceIMEI = "";

    String Chk="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__process);

        BlurBehind.getInstance()
                .withAlpha(200)
                .withFilterColor(Color.parseColor("#222222"))
                .setBackground(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        //ImageView14 = (ImageView)findViewById(R.id.ImageView14);

       // mImageView = (ImageView)findViewById(R.id.CroppedImageView);
        // Get Global variable instance
        aController = (Controller) getApplicationContext();

        pDialog = new ProgressDialog(this);
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();

        // Check if Internet Connection present
        if (!aController.isConnectingToInternet()) {

            // Internet Connection is not present
            aController.showAlertDialog(Task_Process.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);

            // stop executing code by return
            return;
        }


        imageLoader = new ImageLoader(this);

        Intent inte = getIntent();
        String[] data = new String[0];


        cid = inte.getStringExtra("cid");
        cname = inte.getStringExtra("cname");

        sts = inte.getStringExtra("sts");

        sts=sts.toString().trim();

        CustomerNameID = (TextView)findViewById(R.id.textViewNameID);
        CustomerNameID.setText(cname+"("+cid+")");

        // Check if GCM configuration is set
        if (Config.YOUR_SERVER_URL == null ||
                Config.GOOGLE_SENDER_ID == null ||
                Config.YOUR_SERVER_URL.length() == 0 ||
                Config.GOOGLE_SENDER_ID.length() == 0)
        {

            // GCM sernder id / server url is missing
            aController.showAlertDialog(Task_Process.this,
                    "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID",
                    false);

            // stop executing code by return
            return;
        }


        db = new DBAdapter(this);

        // db.open();
        HashMap<String, String> dataf = db.getLogininfo();
        String email = dataf.get("email");
        empname = dataf.get("name");
        regid = dataf.get("regid");
        String empdevice_imei = dataf.get("device_imei");
        deviceIMEI = empdevice_imei.toString().trim();
        Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
        db.close();

        new CustomerLongOperation().execute(Customer_uSERVER_URL,cid,cname,deviceIMEI);

        Button submitB 	= (Button) findViewById(R.id.submit);
        FloatingActionButton buttonP 	= (FloatingActionButton) findViewById(R.id.imageButtonP);
        FloatingActionButton buttonD 	= (FloatingActionButton) findViewById(R.id.imageButtonD);
        FloatingActionButton buttonA 	= (FloatingActionButton) findViewById(R.id.imageButtonA);
        FloatingActionButton buttonV 	= (FloatingActionButton) findViewById(R.id.imageButtonV);

        if(sts.equals("DONE")){
            buttonP.setVisibility(View.GONE);
            buttonA.setVisibility(View.GONE);
            buttonD.setVisibility(View.GONE);
            buttonV.setVisibility(View.GONE);
            submitB.setVisibility(View.GONE);
        }


        gps = new GPSTracker(Task_Process.this);

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



        Cadd 	= (ImageView) findViewById(R.id.imageViewP1);
  //      ImageButton buttonP 	= (ImageButton) findViewById(R.id.imageButtonP);
        buttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=1;
                startCropImage();


            }
        });
        CaddA 	= (ImageView) findViewById(R.id.imageViewA1);
//        ImageButton buttonA 	= (ImageButton) findViewById(R.id.imageButtonA);
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=2;
                startCropImage();


            }
        });
        CaddD	= (ImageView) findViewById(R.id.imageViewD1);
        //ImageButton buttonD 	= (ImageButton) findViewById(R.id.imageButtonD);
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=3;
                startCropImage();


            }
        });
        CaddV 	= (ImageView) findViewById(R.id.imageViewV1);
       // ImageButton buttonV 	= (ImageButton) findViewById(R.id.imageButtonV);
        buttonV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=4;
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




                // Cadd.setImageBitmap(b);
                if(Ckeck==1){

                    img1 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                    Cadd.setImageBitmap(bitmap);

                    String photo1="photo";
                    if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    }else {
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img1);
                    }
                }
                if(Ckeck==2){
                    img2 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                    CaddA.setImageBitmap(bitmap);
                    String photo1="aadhar_card";
                    if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    }else {
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img2);
                    }
                }
                if(Ckeck==3){
                    img3 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                    CaddD.setImageBitmap(bitmap);
                    String photo1="driving_licence";
                    if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    }else {
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img3);
                    }
                }
                if(Ckeck==4){
                    img4 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                    CaddV.setImageBitmap(bitmap);
                    if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    }else {
                        String photo1 = "voter_id";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img4);
                    }
                }

            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public static Bitmap decodeFile(String path) {
        int orientation;
        try {
            if (path == null) {
                return null;
            }
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            // while (true) {
            // if (width_tmp / 2 < REQUIRED_SIZE
            // || height_tmp / 2 < REQUIRED_SIZE)
            // break;
            // width_tmp /= 2;
            // height_tmp /= 2;
            // scale++;
            // }
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            bm = BitmapFactory.decodeFile(path, o2);
            bitmap = bm;

            ExifInterface exif = new ExifInterface(path);

            orientation = exif
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            Log.e("ExifInteface .........", "rotation =" + orientation);

            // exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);

            Log.e("orientation", "" + orientation);
            Matrix m = new Matrix();

            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                m.postRotate(180);
                // m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                // if(m.preRotate(90)){
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                m.postRotate(90);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                m.postRotate(270);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }

    }

 /*
    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        ByteArrayOutputStream bos =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();
        //img = Base64.encodeToString(bb, 0);
        final String img = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

       // String photo1="image";
        // if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
        // }else {
       // new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, deviceIMEI, img);

       // Cadd.setImageBitmap(bitmap);
       // Cadd1.setImageBitmap(bitmap);

        Cadd 	= (ImageView) findViewById(R.id.imageViewP);
        CaddA 	= (ImageView) findViewById(R.id.imageViewA);
        CaddD 	= (ImageView) findViewById(R.id.imageViewD);
        CaddV 	= (ImageView) findViewById(R.id.imageViewV);


        if(Ckeck==1){

            img1 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
            Cadd.setImageBitmap(bitmap);

            String photo1="photo";
            if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
            }else {
                new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img1);
            }
        }
        if(Ckeck==2){


            img2 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
            CaddA.setImageBitmap(bitmap);
            String photo1="aadhar_card";
            if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
            }else {
                new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img2);
            }
        }
        if(Ckeck==3){

            img3 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
            CaddD.setImageBitmap(bitmap);
            String photo1="driving_licence";
            if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
            }else {
                new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img3);
            }
        }
        if(Ckeck==4){
            if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
            }else {
                img4 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                CaddV.setImageBitmap(bitmap);
                String photo1 = "voter_id";
                new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img4);
            }
        }


    }
*/



    public  void Submit(View view){


        EditText txtNameCusto = (EditText) findViewById(R.id.txtNameCusto);
        EditText  txtNameFather = (EditText) findViewById(R.id.txtNameFather);
        EditText  txtNameMobile = (EditText) findViewById(R.id.txtNameMobile);
        EditText  txtNameAddress = (EditText) findViewById(R.id.txtNameAddress);
        EditText  txtNameCity = (EditText) findViewById(R.id.txtNameCity);
        EditText  PinZip= (EditText) findViewById(R.id.PinZip);
        EditText  txtNameEmailP = (EditText) findViewById(R.id.txtNameEmailP);
        EditText  txtNamePANP= (EditText) findViewById(R.id.txtNamePANP);



        Name = txtNameCusto.getText().toString();
        Fname = txtNameFather.getText().toString();
        tunMobile = txtNameMobile.getText().toString();
        Address = txtNameAddress.getText().toString();
        City = txtNameCity.getText().toString();
        Zip = PinZip.getText().toString();
        Xiemail = txtNameEmailP.getText().toString();
        XPan = txtNamePANP.getText().toString();


        final String lat = Double.toString(latitude);
        final String lon = Double.toString(longitude);


       EditText  BankName= (EditText) findViewById(R.id.BankName);
        EditText  BankBranchName1= (EditText) findViewById(R.id.BankBranchName);
        EditText  BankACC= (EditText) findViewById(R.id.BankACC);
        EditText  Bankifsc= (EditText) findViewById(R.id.Bankifsc);


        bankName=BankName.getText().toString();

        BankBranchName=BankBranchName1.getText().toString();

        AccountNum=BankACC.getText().toString();

        IFSCC=Bankifsc.getText().toString();



        if(Name.trim().length() > 2){
            if(Fname.trim().length() > 2 && !Fname.equals("null")){
                if(tunMobile.trim().length() > 9){
                    if(Address.trim().length() > 2 && !Address.equals("null")){
                        if(City.trim().length() > 3 && !City.equals("null")){
                            if(Zip.trim().length() > 5){
                                //addEmployee();

                                if(Xiemail.trim().length() > 3){
                                    //addEmployee();
                                    if(XPan.trim().length() > 3){
                                     //   if(bankName.trim().length() > 2){
                                       //     if(BankBranchName.trim().length() > 2){
                                         //       if(AccountNum.trim().length() > 6){
                                           //         if(IFSCC.trim().length() > 3){

                                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                                        builder
                                                //.setTitle("............Pay")
                                                .setMessage("Are you Sure to Change Customer Information?")
                                                //.setMessage("Customer Details!")
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //Yes button clicked, do something
                                                        //Toast.makeText(Tahsil_data_entry.this, "Yes button pressed",Toast.LENGTH_SHORT).show();
                                                        if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                                                        }else {
                                                            new LongOperation().execute(SERVER_URL, Name, Fname, tunMobile, Address, City, Zip, Xiemail, XPan, empname, regid, lat, lon, deviceIMEI, cid,bankName,BankBranchName,AccountNum,IFSCC);
                                                        }//Toast.makeText(getBaseContext(), "Please Make Sure Your Profile Details is Completed Correctly ", Toast.LENGTH_SHORT).show();
                                                        //addEmployee();
                                                        //onStartTransaction();
                                                    }
                                                })

                                                .setNegativeButton("Cancel", null)						//Do nothing on no
                                                .show();


                                          //          }else{Toast.makeText(Task_Process.this, "Please Enter IFSC Code", Toast.LENGTH_SHORT).show();return;}
                                        //        }else{Toast.makeText(Task_Process.this, "Please Enter Bank Account Number", Toast.LENGTH_SHORT).show();return;}
                                      //      }else{Toast.makeText(Task_Process.this, "Please Enter Bank Branch Name", Toast.LENGTH_SHORT).show();return;}
                                    //    }else{Toast.makeText(Task_Process.this, "Please Enter Bank Name", Toast.LENGTH_SHORT).show();return;}

                                            }else{Toast.makeText(Task_Process.this, "Please Enter PAN Number", Toast.LENGTH_SHORT).show();return;}

                                   }else{Toast.makeText(Task_Process.this, "Please Enter Email ID", Toast.LENGTH_SHORT).show();return;}

                            }else{Toast.makeText(Task_Process.this, "Please Enter Zip Code", Toast.LENGTH_SHORT).show();return;}

                        }else{Toast.makeText(Task_Process.this, "Please Enter City Name", Toast.LENGTH_SHORT).show();return;}

                    }else{Toast.makeText(Task_Process.this, "Please Enter Address", Toast.LENGTH_SHORT).show();return;}

                }else{Toast.makeText(Task_Process.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();return;}

            }else{Toast.makeText(Task_Process.this, "Please Enter Father's Name", Toast.LENGTH_SHORT).show();return;}

        }else{Toast.makeText(Task_Process.this, "Please Enter Name", Toast.LENGTH_SHORT).show();return;}

    }


    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Task_Process.this);
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
                    data +="&" + URLEncoder.encode("Fname", "UTF-8") + "="+params[2].toString();
                if(!params[3].equals(""))
                    data +="&" + URLEncoder.encode("tunMobile", "UTF-8") + "="+params[3].toString();
                if(!params[4].equals(""))
                    data +="&" + URLEncoder.encode("Address", "UTF-8") + "="+params[4].toString();
                if(!params[5].equals(""))
                    data +="&" + URLEncoder.encode("City", "UTF-8") + "="+params[5].toString();
                if(!params[6].equals(""))
                    data +="&" + URLEncoder.encode("Zip", "UTF-8") + "="+params[6].toString();
                if(!params[7].equals(""))
                    data +="&" + URLEncoder.encode("email", "UTF-8") + "="+params[7].toString();
                if(!params[8].equals(""))
                    data +="&" + URLEncoder.encode("pan", "UTF-8") + "="+params[8].toString();
                if(!params[9].equals(""))
                    data +="&" + URLEncoder.encode("empname", "UTF-8") + "="+params[9].toString();
                if(!params[10].equals(""))
                    data +="&" + URLEncoder.encode("regid", "UTF-8") + "="+params[10].toString();
                if(!params[11].equals(""))
                    data +="&" + URLEncoder.encode("lat", "UTF-8") + "="+params[11].toString();
                if(!params[12].equals(""))
                    data +="&" + URLEncoder.encode("lon", "UTF-8") + "="+params[12].toString();
                if(!params[13].equals(""))
                    data +="&" + URLEncoder.encode("emp_id", "UTF-8") + "="+params[13].toString();
                if(!params[14].equals(""))
                    data +="&" + URLEncoder.encode("cid", "UTF-8") + "="+params[14].toString();

                if(!params[15].equals(""))
                    data +="&" + URLEncoder.encode("BankName", "UTF-8") + "="+params[15].toString();
                if(!params[16].equals(""))
                    data +="&" + URLEncoder.encode("BranchName", "UTF-8") + "="+params[16].toString();
                if(!params[17].equals(""))
                    data +="&" + URLEncoder.encode("AccountNum", "UTF-8") + "="+params[17].toString();
                if(!params[18].equals(""))
                    data +="&" + URLEncoder.encode("IFSC", "UTF-8") + "="+params[18].toString();

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


                Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_LONG).show();

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

                        Toast.makeText(getApplicationContext(), "Please Fill All Details Correctly!", Toast.LENGTH_LONG).show();
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

                           // Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_LONG).show();
                           // BlurBehind.getInstance().execute(Task_Process.this, new OnBlurCompleteListener() {
                           //     @Override
                            //    public void onBlurComplete() {
                             //       Intent intent = new Intent(Task_Process.this, Launch.class);
                              //      intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                              //      startActivity(intent);

                                //    finish();
                               // }
                           // });
                            // startActivity(intent);
                            // finish();

                            Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_LONG).show();

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
/*
    public void ditact(String mypath1) {

        int index3 = mypath1.lastIndexOf("/");
        String fileName3 = mypath1.substring(index3 + 1);


        String extension = "";

        int i = fileName3.lastIndexOf('.');
        int p = Math.max(fileName3.lastIndexOf('/'), fileName3.lastIndexOf('\\'));

        if (i > p) {
            extension = fileName3.substring(i+1);
        }

        try {
            String Nfilename3 = fileName3.substring(0, fileName3.lastIndexOf("."));

            File f3 = new File(mypath1);

            String pathPP3 = f3.getParent();


            mypath = pathPP3 + "/" + Nfilename3 + "-1."+extension;
            photo = decodeFile(mypath);

            ByteArrayOutputStream bos =new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
            byte[] bb = bos.toByteArray();

            if(Ckeck==1){

                img1 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                Cadd.setImageBitmap(photo);

                String photo1="photo";
                if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                }else {
                    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img1);
                }
            }
            if(Ckeck==2){


                img2 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                CaddA.setImageBitmap(photo);
                String photo1="aadhar_card";
                if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                }else {
                    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img2);
                }
            }
            if(Ckeck==3){

                img3 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                CaddD.setImageBitmap(photo);
                String photo1="driving_licence";
                if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                }else {
                    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img3);
                }
            }
            if(Ckeck==4){
                if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                }else {
                    img4 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                    CaddV.setImageBitmap(photo);
                    String photo1 = "voter_id";
                    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, cid, cname, deviceIMEI, img4);
                }
            }



        } catch (Exception e) {

        }
    }

*/


    public void Submit1(View view){
        BlurBehind.getInstance().execute(Task_Process.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(Task_Process.this, MainActivityTask.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                finish();
            }
        });

    }

    public void backok(View view){
        BlurBehind.getInstance().execute(Task_Process.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(Task_Process.this, Launch.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                finish();
            }
        });
    }


    // Class with extends AsyncTask class
    public class CustomerLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Task_Process.this);
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
                    data +="&" + URLEncoder.encode("cid", "UTF-8") + "="+params[1].toString();
                if(!params[2].equals(""))
                    data +="&" + URLEncoder.encode("cname", "UTF-8") + "="+params[2].toString();
                if(!params[3].equals(""))
                    data +="&" + URLEncoder.encode("emp_id", "UTF-8") + "="+params[3].toString();

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
                            String product_type       = jsonChildNode.optString("product_type").toString();
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
                            String mobile       = jsonChildNode.optString("mobile").toString();
                            String customer_name       = jsonChildNode.optString("customer_name").toString();
                            String message       = jsonChildNode.optString("message").toString();
                            status       = jsonChildNode.optString("status").toString();

                            String freqency       = jsonChildNode.optString("freqency").toString();
                            String scheme       = jsonChildNode.optString("scheme").toString();
                            String Advance_EMI       = jsonChildNode.optString("Advance_EMI").toString();
                            String DownPay       = jsonChildNode.optString("DownPay").toString();
                            String Disbursement       = jsonChildNode.optString("Disbursement").toString();

                            String Bank_name       = jsonChildNode.optString("Bank_name").toString();
                            String Bank_branch       = jsonChildNode.optString("Bank_branch").toString();
                            String Account_number       = jsonChildNode.optString("Account_number").toString();
                            String ifsc       = jsonChildNode.optString("ifsc").toString();

                            String LTV_Policy       = jsonChildNode.optString("LTV_Policy").toString();
                            String ROI_policy       = jsonChildNode.optString("ROI_policy").toString();



                             ROIP = (TextView)findViewById(R.id.ROIP);
                             LTVP = (TextView)findViewById(R.id.LTVP);

                            ROIP.setText(ROI_policy+"%");
                            LTVP.setText(LTV_Policy+"Rs.");



                            EditText  BankName= (EditText) findViewById(R.id.BankName);
                            EditText  BankBranchName1= (EditText) findViewById(R.id.BankBranchName);
                            EditText  BankACC= (EditText) findViewById(R.id.BankACC);
                            EditText  Bankifsc= (EditText) findViewById(R.id.Bankifsc);

                            BankName.setText(Bank_name);
                            BankBranchName1.setText(Bank_branch);
                            BankACC.setText(Account_number);
                            Bankifsc.setText(ifsc);
                            // Toast.makeText(getApplicationContext(), "Fetch!  "+status, Toast.LENGTH_LONG).show();


                            //TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal;


                            TextView Frequency = (TextView)findViewById(R.id.Frequency);
                            TextView SchemeType = (TextView)findViewById(R.id.SchemeType);
                            TextView AdvanceEMI = (TextView)findViewById(R.id.AdvanceEMI);
                            TextView DownPayment = (TextView)findViewById(R.id.DownPayment);
                            TextView Disbursement2 = (TextView)findViewById(R.id.Disbursement);
                            Frequency.setText(freqency);
                            SchemeType.setText(scheme);
                            AdvanceEMI.setText(Advance_EMI);
                            DownPayment.setText(DownPay);
                            Disbursement2.setText(Disbursement);


                            int emi_type= 0,emi_type1=0,emi_type2=0,emi_type3=0;
                            int E1mT1,E2mT1,E3mT1,E1mT2,E2mT2,E3mT2,Tenure=0;
                            Tenure=Integer.valueOf(tunerPP.toString().trim());


                            if(scheme.equals("1")){
                                E1mT1 = (75*Tenure)/100;
                                E1mT2 =  Tenure-E1mT1;
                                emi_type1=E1mT2;
                                SchemeType.setText(E1mT2+"|"+E1mT1+"  EMI Type");

                            }
                            if(scheme.equals("2")) {

                                E2mT1 = (60 * Tenure) / 100;
                                E2mT2 = Tenure - E2mT1;

                                emi_type2 = E2mT2;

                                SchemeType.setText(E2mT2 + "|" + E2mT1 + "  EMI Type");
                            }

                            if(scheme.equals("3")) {
                                E3mT1 = (50 * Tenure) / 100;
                                E3mT2 = Tenure - E3mT1;

                                emi_type3 = E3mT2;

                                SchemeType.setText(E3mT2 + "|" + E3mT1 + "  EMI Type");
                            }


                            TextView textViewOrder = (TextView)findViewById(R.id.textViewOrder);
                            textViewOrder.setText("Costomer Order ID is "+order_id+"("+product_type+") Of Product Item "+product_compny+"("+product_name+")"+", The Loan Percent is "+product_poan_percent+"% Of Product Price and Status is "+status);

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
                            PRate.setText(percent_rate+"% Of Product Price");
                            PTunner.setText(tunerPP);
                            Pemi.setText(emiPP+"Rs.");
                            Ptotal.setText(total_amountPP+"Rs.");


                            EditText txtNameCusto = (EditText) findViewById(R.id.txtNameCusto);
                            EditText  txtNameFather = (EditText) findViewById(R.id.txtNameFather);
                            EditText  txtNameMobile = (EditText) findViewById(R.id.txtNameMobile);
                            EditText  txtNameAddress = (EditText) findViewById(R.id.txtNameAddress);
                            EditText  txtNameCity = (EditText) findViewById(R.id.txtNameCity);
                            EditText  PinZip= (EditText) findViewById(R.id.PinZip);

                            EditText  txtNameEmailP = (EditText) findViewById(R.id.txtNameEmailP);
                            EditText  txtNamePANP= (EditText) findViewById(R.id.txtNamePANP);



                            txtNameCusto.setText(customer_name);
                            txtNameFather.setText(father_name);
                             txtNameMobile.setText(mobile);
                             txtNameAddress.setText(addressP);
                            txtNameCity.setText(city);
                            PinZip.setText(zipP);

                            txtNameEmailP.setText(Pemail);
                            txtNamePANP.setText(pan_number);

/*
                            Cadd 	= (ImageView) findViewById(R.id.imageViewP1);
                            CaddA 	= (ImageView) findViewById(R.id.imageViewA1);
                            CaddD 	= (ImageView) findViewById(R.id.imageViewD1);
                            CaddV 	= (ImageView) findViewById(R.id.imageViewV1);

                            Glide.with(Task_Process.this)
                                    .load(photo)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(Cadd);

                          //  Picasso.with(Task_Process.this)
                                    //.(R.drawable.ic_launcher)
                            //        .load(photo)
//                                    .into(Cadd);

                            Glide.with(Task_Process.this)
                                    //.(R.drawable.ic_launcher)

                                    .load(aadhar_card)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                   // .error(R.drawable.ic_launcher)
                                    .into(CaddA);

                            Glide.with(Task_Process.this)
                                    //.(R.drawable.ic_launcher)
                                    .load(driving_licence)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                  //  .error(R.drawable.ic_launcher)
                                    .into(CaddD);

                            Glide.with(Task_Process.this)
                                    //.(R.drawable.ic_launcher)
                                    .load(voter_id)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                  //  .error(R.drawable.ic_launcher)
                                    .into(CaddV);



*/
                            imageLoader = new ImageLoader(getApplicationContext());

                            imageLoader.DisplayImage(photo, Cadd);
                            imageLoader.DisplayImage(aadhar_card, CaddA);
                            imageLoader.DisplayImage(driving_licence, CaddD);
                            imageLoader.DisplayImage(voter_id, CaddV);
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
    public class CustomerPhotoLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Task_Process.this);
        String data ="";
        int sizeData = 0;
        protected void onPreExecute() {
            //Dialog.setMessage("Uploading...");
            //Dialog.show();
        }
        protected String doInBackground(String... params) {
            BufferedReader reader=null;
            String Content = "";
            try{URL url = new URL(params[0]);
                if(!params[1].equals(""))
                    data +="&" + URLEncoder.encode("photo", "UTF-8") + "="+params[1].toString();
                if(!params[2].equals(""))
                    data +="&" + URLEncoder.encode("cid", "UTF-8") + "="+params[2].toString();
                if(!params[3].equals(""))
                    data +="&" + URLEncoder.encode("cname", "UTF-8") + "="+params[3].toString();
                if(!params[4].equals(""))
                    data +="&" + URLEncoder.encode("emp_id", "UTF-8") + "="+params[4].toString();
                if(!params[5].equals(""))
                    data +="&" + URLEncoder.encode("img", "UTF-8") + "="+params[5].toString();

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

}
