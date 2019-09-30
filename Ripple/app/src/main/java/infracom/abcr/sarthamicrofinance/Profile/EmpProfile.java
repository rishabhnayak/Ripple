package infracom.abcr.sarthamicrofinance.Profile;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.lzyzsd.circleprogress.DonutProgress;

import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Dpost.ImageLoader;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.Main;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.Utility;
import infracom.abcr.sarthamicrofinance.crop.CropOption;
import infracom.abcr.sarthamicrofinance.crop.CropOptionAdapter;
import infracom.abcr.sarthamicrofinance.crop.imagecropper.CropIntent;
import infracom.abcr.sarthamicrofinance.material.widget.FloatingActionButton;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;

public class EmpProfile extends AppCompatActivity {


    String branch_name,branch_id;

    ImageLoader imageLoader;

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


    public static final String CROPPED_IMAGE_FILEPATH = "/sdcard/SarthaMicroFinance.jpg";
    Uri selectedImageUri = null;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    Uri imageUri;

    DBAdapter db;
    String deviceIMEI = "",user_id;

    private String userChoosenTask;

    public static String Finalmedia = "";

    private String SERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Emp_Detail_Up.php";
    private String Customer_uSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Emp_details_app.php";
    private String Customer_uSERVER_URL_one = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/EmpPhoto_Upload.php";


    private String DSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Emp_Detail_Up.php";
    private String DCustomer_uSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Emp_details_app.php";
    private String DCustomer_uSERVER_URL_one = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/EmpPhoto_Upload.php";

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

    private static Bitmap bm;
    static Bitmap bitmap;


    private String selectedImagePath = "", imagepath="";
    boolean GallaryPhotoSelected = false;





    File sourceFile;
    int totalSize = 0;
    String FILE_UPLOAD_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/UploadToServer.php";
    String FILE_UPLOAD_URLD = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/UploadToServer.php";


    String Customer_uSERVER_URL_oneAA = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/UploadToServerData.php";
    String Customer_uSERVER_URL_oneAD = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/UploadToServerDataDSA.php";
    LinearLayout uploader_area;
    TextView text;
    String fname;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;
    int id = 1;
    private NotificationCompat.Builder build;

    LinearLayout progress_area;
    public DonutProgress donut_progress;
    private static final int REQUEST_WRITE_STORAGE = 112;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emp_profile_activity);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent resultIntent = new Intent(this, Main.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT

                );


        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(
                getApplicationContext()).setSmallIcon(R.drawable.ic_file_upload_black_24dp)
                .setContentTitle("SMF Uploading Image")
                .setContentText("Loading...")
                .setContentIntent(resultPendingIntent);
       // EnableRuntimePermission();

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsingToolbar.setTitle("Fine Hi...");
        //collapsingToolbar.setBackgroundResource(R.drawable.pavanb_pic);
        collapsingToolbar.setBackgroundColor(Color.DKGRAY);

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


        // Get Global variable instance
        aController = (Controller) getApplicationContext();

        pDialog = new ProgressDialog(this);
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();

        // Check if Internet Connection present
        if (!aController.isConnectingToInternet()) {

            // Internet Connection is not present
            aController.showAlertDialog(EmpProfile.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);

            // stop executing code by return
            return;
        }

        gps = new GPSTracker(EmpProfile.this);

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
        //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();

        under_depart = dataf.get("under_depart");

        db.close();

if(under_depart.equals("DSA Person")) {
    new CustomerLongOperation().execute(DCustomer_uSERVER_URL, user_id);
}else{
    new CustomerLongOperation().execute(Customer_uSERVER_URL, user_id);

}



        FloatingActionButton submitB 	= (FloatingActionButton) findViewById(R.id.empphoto);




        Cadd 	= (ImageView) findViewById(R.id.backdrop);
        Cadd1 = (CircleImageView) findViewById(R.id.imageShow1l);
        //      ImageButton buttonP 	= (ImageButton) findViewById(R.id.imageButtonP);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ckeck=1;
               // dialog.show();
                com.theartofdev.edmodo.cropper.CropImage.activity(null)
                        .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                        .start(EmpProfile.this);
               // startPickImage();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            com.theartofdev.edmodo.cropper.CropImage.ActivityResult result = com.theartofdev.edmodo.cropper.CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ((ImageView) findViewById(R.id.backdrop)).setImageURI(result.getUri());
                ((ImageView) findViewById(R.id.imageShow1l)).setImageURI(result.getUri());

                Bitmap  mBitmap=null;
                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // InputStream in = getContentResolver().openInputStream(croppedUri);
                SaveImage(mBitmap);

                ByteArrayOutputStream bos =new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                byte[] bb = bos.toByteArray();


                img1 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                String photo1="image";
                // if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                // }else {
            //    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, deviceIMEI, img1);

                File file1 = new File(imagepath);
                long length = file1.length() / 1024;

                if(length >= 3072){
                    Toast.makeText(getApplicationContext(), "File Size is should be less then 2MB..", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(under_depart.equals("DSA Person")) {
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneAD, "image", deviceIMEI, fname);
                    }else{
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneAA, "image", deviceIMEI, fname);
                    }
                }




              //  Toast.makeText(this, "Cropping successful " + result, Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void DialogShow(){

        final Dialog dialog = new Dialog(EmpProfile.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.progressdialog);
        // Set dialog title
        dialog.setTitle("Image Upload");

        // set values for custom dialog components - text, image and button
        text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText("Please Wait File Uploading...");
        //ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);

        donut_progress = (DonutProgress) dialog.findViewById(R.id.donut_progress);

        donut_progress.setProgress(0);
        dialog.show();

        Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
        // if decline button is clicked, close the custom dialog


        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });

    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath() + "/saved_images");

        myDir.mkdirs();
        //Random generator = new Random();
        //int n = 10000;
        //n = generator.nextInt(n);
        fname = "Image-"+ String.valueOf(System.currentTimeMillis())  +".jpg";
        File file = new File (myDir, fname);

        String pathh = null;


        URI uri = file.toURI();

        pathh = uri.toString();

        imagepath =  myDir+"/"+fname;



        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private class UploadFileToServer extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            donut_progress.setProgress(0);

            mBuilder.setContentText("Please Wait File Uploading...")
                    .setProgress(0,0,false);
            mNotifyManager.notify(0, mBuilder.build());
            // uploader_area.setVisibility(View.GONE); // Making the uploader area screen invisible
            // progress_area.setVisibility(View.VISIBLE); // Showing the stylish material progressbar
            sourceFile = new File(imagepath);
            totalSize = (int)sourceFile.length();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... progress) {
             Log.d("PROG", progress[0]);
            donut_progress.setProgress(Integer.parseInt(progress[0])); //Updating progress
           // donut_progress.setProgress(50); //Updating progress //Updating progress

            //  mBuilder.setContentText(progress[0]+"% Download complete");
            //  mBuilder.setProgress(100, Integer.parseInt(progress[0]), false);
            // Displays the progress bar for the first time.
            //  mNotifyManager.notify(0, mBuilder.build());
        }


        @Override
        protected String doInBackground(String... args) {
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection connection = null;
            String fileName = sourceFile.getName();

            try {

                if(under_depart.equals("DSA Person")) {
                    connection = (HttpURLConnection) new URL(FILE_UPLOAD_URLD).openConnection();
                }else{
                    connection = (HttpURLConnection) new URL(FILE_UPLOAD_URL).openConnection();

                }
                //connection = (HttpURLConnection) new URL(FILE_UPLOAD_URL).openConnection();
                connection.setRequestMethod("POST");
                String boundary = "---------------------------boundary";

                String tail = "\r\n--" + boundary + "--\r\n";
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                connection.setDoOutput(true);

                String metadataPart = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
                        + "" + "\r\n";

                String fileHeader1 = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"fileToUpload\"; filename=\""
                        + fileName + "\"\r\n"
                        + "Content-Type: application/octet-stream\r\n"
                        + "Content-Transfer-Encoding: binary\r\n";

                long fileLength = sourceFile.length() + tail.length();
                String fileHeader2 = "Content-length: " + fileLength + "\r\n";
                String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
                String stringData = metadataPart + fileHeader;

                long requestLength = stringData.length() + fileLength;
                connection.setRequestProperty("Content-length", "" + requestLength);
                connection.setFixedLengthStreamingMode((int) requestLength);
                connection.connect();

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(stringData);
                out.flush();

                int progress = 0;
                int bytesRead = 0;
                byte buf[] = new byte[1024];
                BufferedInputStream bufInput = new BufferedInputStream(new FileInputStream(sourceFile));
                while ((bytesRead = bufInput.read(buf)) != -1) {
                    // write output
                    out.write(buf, 0, bytesRead);
                    out.flush();
                    progress += bytesRead; // Here progress is total uploaded bytes

                    publishProgress(""+(int)((progress*100)/totalSize)); // sending progress percent to publishProgress

                }


                // Write closing boundary and close stream
                out.writeBytes(tail);
                out.flush();
                out.close();

                // Get server response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder builder = new StringBuilder();
                while((line = reader.readLine()) != null) {
                    builder.append(line);
                }

            } catch (Exception e) {
                // Exception
            } finally {
                if (connection != null) connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            mBuilder.setContentText("Uploading completed")
                    // Removes the progress bar
                    .setProgress(0,0,false);
            mNotifyManager.notify(0, mBuilder.build());
            donut_progress.setProgress(100);

            text.setText("Uploading completed");
            Toast.makeText(getApplicationContext(), "Uploading completed", Toast.LENGTH_SHORT).show();
            //Log.e("Response", "Response from server: " + result);
            super.onPostExecute(result);
        }

    }



    /*
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if(userChoosenTask.equals("Take Photo"))
                           // cameraIntent();
                        ClickImageFromCamera();
                        else if(userChoosenTask.equals("Choose from Library"))
                            GetImageFromGallery();
                            //galleryIntent();
                    } else {
                        //code for deny
                    }
                    break;
            }
        }


        protected void startPickImage() {
            //Intent intent = new Intent(Intent.ACTION_PICK);
            //intent.setType("image/*");
            //startActivityForResult(intent,0);

            final String[] items = new String[] { "Camera",
                    "Gallery" };
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.select_dialog_item, items);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Select Image");
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) { // pick from
                    // camera

                    boolean result=Utility.checkPermission(EmpProfile.this);
                        if (item == 0) {
                            userChoosenTask ="Take Photo";
                            ClickImageFromCamera();

                        }else{
                            userChoosenTask ="Choose from Library";
                          GetImageFromGallery();

                        }

                    }

            });

            final AlertDialog dialog = builder.create();

            dialog.show();

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
                                                            new LongOperation().execute(DSERVER_URL, Name, tunMobile, Address, Xiemail, lat, lon, deviceIMEI, XPassword);
                                                        }else{
                                                            new LongOperation().execute(SERVER_URL, Name, tunMobile, Address, Xiemail, lat, lon, deviceIMEI, XPassword);

                                                        }

                                                            //}//Toast.makeText(getBaseContext(), "Please Make Sure Your Profile Details is Completed Correctly ", Toast.LENGTH_SHORT).show();
                                                        //addEmployee();
                                                        //onStartTransaction();
                                                    }
                                                })

                                                .setNegativeButton("Cancel", null)						//Do nothing on no
                                                .show();



                                    }else{
                                        Toast.makeText(EmpProfile.this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();return;}
                                }else{Toast.makeText(EmpProfile.this, "Please Enter Email ID", Toast.LENGTH_SHORT).show();return;}
                    }else{Toast.makeText(EmpProfile.this, "Please Enter Address", Toast.LENGTH_SHORT).show();return;}
                }else{Toast.makeText(EmpProfile.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();return;}
        }else{Toast.makeText(EmpProfile.this, "Please Enter Name", Toast.LENGTH_SHORT).show();return;}

    }


    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(EmpProfile.this);
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
                            BlurBehind.getInstance().execute(EmpProfile.this, new OnBlurCompleteListener() {
                                @Override
                                public void onBlurComplete() {
                                    Intent intent = new Intent(EmpProfile.this, Main.class);
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
        private ProgressDialog Dialog = new ProgressDialog(EmpProfile.this);
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

        protected void onPostExecute(String Content) {

            Dialog.dismiss();

            DialogShow();
            new UploadFileToServer().execute();

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



                            //   Toast.makeText(getApplicationContext(), "Uploaded Successfully!", Toast.LENGTH_LONG).show();

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
        private ProgressDialog Dialog = new ProgressDialog(EmpProfile.this);
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
                             String emp_id       = jsonChildNode.optString("emp_id").toString();
                            String emp_name       = jsonChildNode.optString("emp_name").toString();

                            String Pemail       = jsonChildNode.optString("email").toString();
                            String addressP       = jsonChildNode.optString("address").toString();
                            String photo       = jsonChildNode.optString("photo").toString();
                            String mobile       = jsonChildNode.optString("mobile").toString();
                            String depart       = jsonChildNode.optString("depart").toString();
                            String password       = jsonChildNode.optString("password").toString();

                            // Toast.makeText(getApplicationContext(), "Fetch!  "+status, Toast.LENGTH_LONG).show();


                            //TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal;

                            collapsingToolbar.setTitle(emp_name);

                            TextView tadsilnamCe = (TextView)findViewById(R.id.tadsilnamCe);

                            TextView  EMPIDtextView = (TextView) findViewById(R.id.EMPIDtextView);
                            tadsilnamCe.setText(depart);
                            EMPIDtextView.setText("ID - "+emp_id);
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


                            EditText Ename = (EditText) findViewById(R.id.Ename);
                            EditText  Emobile = (EditText) findViewById(R.id.Emobile);
                            EditText  Eemail = (EditText) findViewById(R.id.Eemail);
                            EditText  Eaddress = (EditText) findViewById(R.id.Eaddress);
                            EditText  Password = (EditText) findViewById(R.id.Password);
                            //EditText  PinZip= (EditText) findViewById(R.id.PinZip);

                            //EditText  txtNameEmailP = (EditText) findViewById(R.id.txtNameEmailP);
                            //EditText  txtNamePANP= (EditText) findViewById(R.id.txtNamePANP);



                            Ename.setText(emp_name);
                            Eemail.setText(Pemail);
                            Emobile.setText(mobile);
                            Eaddress.setText(addressP);
                            Password.setText(password);
                           // txtNameCity.setText(city);
                           // PinZip.setText(zipP);

                            //txtNameEmailP.setText(Pemail);
                            //txtNamePANP.setText(pan_number);


                            Cadd 	= (ImageView) findViewById(R.id.backdrop);
                            Cadd1 	= (CircleImageView) findViewById(R.id.imageShow1l);
                           // CaddD 	= (ImageView) findViewById(R.id.imageViewD);
                           // CaddV 	= (ImageView) findViewById(R.id.imageViewV);


                            //imageLoader.DisplayImage(photo, Cadd);
                           //imageLoader.DisplayImage(photo, Cadd);
                           // imageLoader.DisplayImage(photo, Cadd1);
                            //imageLoader.DisplayImage(voter_id, CaddV);

                            new DownloadImageFromInternet(Cadd).execute(photo.replace("http","https"));
                            new DownloadImageFromInternet(Cadd1).execute(photo.replace("http","https"));
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

    /*

    public void ClickImageFromCamera() {


        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String fileName = System.currentTimeMillis() + ".jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 0);
        }else {
            CamIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            file = new File(Environment.getExternalStorageDirectory(),
                    "file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
            uri = Uri.fromFile(file);

            CamIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);

            CamIntent.putExtra("return-data", true);

            startActivityForResult(CamIntent, 0);

        }

    }

    public void GetImageFromGallery() {

        GalIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(Intent.createChooser(GalIntent, "Select Image From Gallery"), 2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {

            ImageCropFunction();

        } else if (requestCode == 2) {

            if (data != null) {

                uri = data.getData();

                ImageCropFunction();

            }
        } else if (requestCode == 1) {

            if (data != null) {

                Bitmap bitmap = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    Bundle bundle = data.getExtras();
                    bitmap = bundle.getParcelable("data");
                }
                else{
                    Uri uri = data.getData();
                    try {
                        bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //imageView.setImageBitmap(bitmap);
                Cadd1.setImageBitmap(bitmap);
                Cadd.setImageBitmap(bitmap);

      //          InputStream in = getContentResolver().openInputStream(croppedUri);
    //            Bitmap b = BitmapFactory.decodeStream(in);
                // ImageView14.setImageBitmap(b);
  //              selectedImagePath = croppedUri.getPath();
                // Bitmap bm = decodeFile(selectedImagePath);

//                int scale = 1;


                //Cadd 	= (ImageView) findViewById(R.id.imageViewP1);
                // mImageView.setImageBitmap(b);
                //Toast.makeText(this,"Crop success，saved at"+CROPPED_IMAGE_FILEPATH,Toast.LENGTH_LONG).show();


    //            BitmapFactory.Options o2 = new BitmapFactory.Options();
  //              o2.inSampleSize = scale;
//                bitmap = BitmapFactory.decodeFile(selectedImagePath, o2);

                ByteArrayOutputStream bos =new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                byte[] bb = bos.toByteArray();


                img1 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                String photo1="image";
                // if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                // }else {
                if(under_depart.equals("DSA Person")) {
                    new CustomerPhotoLongOperation().execute(DCustomer_uSERVER_URL_one, photo1, deviceIMEI, img1);
                }else{

                    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, deviceIMEI, img1);
                }


            }
        }
    }

    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 320);
            CropIntent.putExtra("outputY", 320);
            CropIntent.putExtra("aspectX", 4);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);

            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

            Toast.makeText(EmpProfile.this, "Camera Crop Option not Support!", Toast.LENGTH_LONG).show();
        }
    }
    //Image Crop Code End Here

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(EmpProfile.this,
                Manifest.permission.CAMERA)) {

            Toast.makeText(EmpProfile.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(EmpProfile.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }
*/

}
