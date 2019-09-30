package infracom.abcr.sarthamicrofinance.AssignEMP;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import infracom.abcr.sarthamicrofinance.Config;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Dpost.ImageLoader;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.Profile.Admin_Launch;
import infracom.abcr.sarthamicrofinance.Profile.Manager_Approve;
import infracom.abcr.sarthamicrofinance.Profile.Manager_Launch;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.crop.imagecropper.CropIntent;
import infracom.abcr.sarthamicrofinance.material.app.BottomSheetDialog;
import infracom.abcr.sarthamicrofinance.material.app.DatePickerDialog;
import infracom.abcr.sarthamicrofinance.material.app.Dialog;
import infracom.abcr.sarthamicrofinance.material.app.DialogFragment;
import infracom.abcr.sarthamicrofinance.material.app.SimpleDialog;
import infracom.abcr.sarthamicrofinance.material.app.ThemeManager;
import infracom.abcr.sarthamicrofinance.material.drawable.ThemeDrawable;
import infracom.abcr.sarthamicrofinance.material.util.ViewUtil;
import infracom.abcr.sarthamicrofinance.material.widget.FloatingActionButton;
import infracom.abcr.sarthamicrofinance.material.widget.ProgressView;
import infracom.abcr.sarthamicrofinance.material.widget.RadioButton;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;
import mehdi.sakout.fancybuttons.FancyButton;

public class Recovery_EMP_Assign extends AppCompatActivity {


    ArrayList<HashMap<String, Object>> searchResults;


    ArrayList<String> listsubR = new ArrayList<String>();
    ArrayList<String> listsub1 = new ArrayList<String>();
    ArrayList<String> listsub2 = new ArrayList<String>();
    ArrayList<String> listsub3 = new ArrayList<String>();

    ArrayList<String> listsub4 = new ArrayList<String>();
    ArrayList<String> listsub5 = new ArrayList<String>();

    ArrayList<String> icon = new ArrayList<String>();

    ArrayList<String> ROI_Applied = new ArrayList<String>();
    ArrayList<String> LTV_Policy = new ArrayList<String>();
    ArrayList<String> LTV_Applied = new ArrayList<String>();

    ArrayList<String> listsubR1 = new ArrayList<String>();

    ArrayList<String> listsub11 = new ArrayList<String>();
    ArrayList<String> listsub21 = new ArrayList<String>();
    ArrayList<String> listsub31 = new ArrayList<String>();
    ArrayList<String> interval = new ArrayList<String>();

    ArrayList<String> icon1 = new ArrayList<String>();

    ArrayList<String> emp_id = new ArrayList<String>();
    ArrayList<String> Ename = new ArrayList<String>();

    ArrayList<String> no = new ArrayList<String>();
    ArrayList<String> city = new ArrayList<String>();
    ArrayList<String> product_name = new ArrayList<String>();
    ArrayList<String> emi = new ArrayList<String>();
    ArrayList<String> ROI_policy = new ArrayList<String>();
    ArrayList<String> installment_date3 = new ArrayList<String>();


    ArrayList<String> total_late_charge_panalty = new ArrayList<String>();


    LayoutInflater inflater;

    ArrayList<String> remarks = new ArrayList<String>();


    ImageView Ephoto;

    Double tot,log,tot1,log1;

    TextView TDD;

    ArrayList<HashMap<String, Object>> searchResults1=null;

    ListView mylistview1;

    ArrayList<HashMap<String, Object>> originalValues1=null;

    TableLayout tbl=null;
    ImageLoader imageLoader;

    private Manager_Approve mActivity;



    private static final int MSG_START_PROGRESS = 1000;
    private static final int MSG_STOP_PROGRESS = 1001;
    private static final int MSG_UPDATE_PROGRESS = 1002;
    private static final int MSG_UPDATE_QUERY_PROGRESS = 1003;
    private static final int MSG_UPDATE_BUFFER_PROGRESS = 1004;

    private static final long PROGRESS_INTERVAL = 7000;
    private static final long START_DELAY = 2000;
    private static final long PROGRESS_UPDATE_INTERVAL = PROGRESS_INTERVAL / 100;
    private static final long START_QUERY_DELAY = PROGRESS_INTERVAL / 2;
    private static final long QUERY_PROGRESS_UPDATE_INTERVAL = (PROGRESS_INTERVAL - START_QUERY_DELAY) / 100;
    private static final long BUFFER_PROGRESS_UPDATE_INTERVAL = (PROGRESS_INTERVAL - START_QUERY_DELAY) / 100;

    String status,installment_date2,emiPay,total_late_charge_panalty1,total_late_charge_panalty121;
    ProgressDialog dialog;
    public String user_id, password, email, name, lastname, mobile, fullname,sts;
    private Uri mImageCaptureUri, mImageCaptureUriTo;
    ImageView Cadd, CaddV, CaddA, CaddD,CCCCC;

    private  String Approve_Summ_LongOperation_url="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Approve_Summ_LongOperation.php";


    private  String Allot="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Allot.php";

    private Handler mHandler;


    private static Bitmap bm;
    static Bitmap bitmap;
    private String SERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/imageUp.php";

    private String Customer_uSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Disbus_customer_details.php";


    private String Customer_uSERVER_URL_one = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/CPhoto_Upload.php";


    ArrayList<HashMap<String, Object>> originalValues;

    TextView inputSearch;

    final List<TextView> allEds = new ArrayList<TextView>();
    final List<TextView> allEds1 = new ArrayList<TextView>();

    public TextView et;
    public String[] strings,strings2;
    TableRow tr;


    String paid_amount,intervaltext;
    String loan_install_no;
    String installment_date;
    String cheque_number;

    String serverURL1 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/empList.php";
    String serverURL2 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/recover_pay_panelty.php";


    private String selectedImagePath = "",lonper11,lonper12,lonper13;
    boolean GallaryPhotoSelected = false;
    TextView Titletxt, DestxtLastname;

    Controller aController = null;

    static String img1,img2,img3,img4, Name,Fname,tunMobile, Address,City,Zip,Xiemail,XPan="",lat,lon,img5;

    private ProgressDialog pDialog;
    String mypath;
    String cc;
    int Ckeck=0;
    public static String Finalmedia = "",notPayedEM;

    private static final String TAG = Manager_Approve.class.getSimpleName();

    private String empname,regid;
    Bitmap photo;
    String MSG;

    Uri selectedImageUri = null;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    Uri imageUri;
    LinearLayout appsts, cheqeLeni;
    TextView tv2;

    private BottomSheetDialog mBottomSheetDialog;

    TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal,view8,LTVP,ROIP;
    TextView MsgeditText,chequedate;
    DBAdapter db;

    static  String cid,cname,cphoto;

    public static final String CROPPED_IMAGE_FILEPATH = "/sdcard/SarthaMicroFinance.jpg";
    TextView et_pass;
    GPSTracker gps;
    double latitude;
    double longitude;
    String under_depart;
    String deviceIMEI = "";
    ProgressView prog;

    private ProgressView pv_circular_inout_colors;
    public int loopint;
    String bankName,BankBranchName,AccountNum,IFSCC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery__emp__assign);

        mHandler = new Handler();

        pv_circular_inout_colors = (ProgressView)findViewById(R.id.progress_pv_circular_inout_colors);

        BlurBehind.getInstance()
                .withAlpha(200)
                .withFilterColor(Color.parseColor("#FFFFFF"))
                .setBackground(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        appsts = (LinearLayout)findViewById(R.id.appstskii);
        cheqeLeni = (LinearLayout)findViewById(R.id.cheqeLeni);

        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        tbl=(TableLayout)findViewById(R.id.TableLayout1);
        tr = new TableRow(getApplicationContext());


        // Get Global variable instance
        aController = (Controller) getApplicationContext();

        pDialog = new ProgressDialog(this);
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();

        // Check if Internet Connection present
        if (!aController.isConnectingToInternet()) {

            // Internet Connection is not present
            aController.showAlertDialog(Recovery_EMP_Assign.this,
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


        String dddd = inte.getStringExtra("date");

        sts=sts.toString().trim();

        //if(sts.equals("CLEAR")||sts.equals("DONE")){appsts.setVisibility(View.GONE);}

        CustomerNameID = (TextView)findViewById(R.id.textViewNameID);
        CustomerNameID.setText(cname+"("+cid+")");

        // Check if GCM configuration is set
        if (Config.YOUR_SERVER_URL == null ||
                Config.GOOGLE_SENDER_ID == null ||
                Config.YOUR_SERVER_URL.length() == 0 ||
                Config.GOOGLE_SENDER_ID.length() == 0)
        {

            // GCM sernder id / server url is missing
            aController.showAlertDialog(Recovery_EMP_Assign.this,
                    "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID",
                    false);

            // stop executing code by return
            return;
        }



        pv_circular_inout_colors.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(MSG_STOP_PROGRESS, PROGRESS_INTERVAL);
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, PROGRESS_UPDATE_INTERVAL);
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_QUERY_PROGRESS, START_QUERY_DELAY);
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_BUFFER_PROGRESS, BUFFER_PROGRESS_UPDATE_INTERVAL);

        pv_circular_inout_colors.start();

        db = new DBAdapter(this);

        // db.open();
        HashMap<String, String> dataf = db.getLogininfo();
        String email = dataf.get("email");
        empname = dataf.get("name");
        regid = dataf.get("regid");
        String empdevice_imei = dataf.get("device_imei");
        deviceIMEI = empdevice_imei.toString().trim();

        under_depart = dataf.get("under_depart");
        //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
        db.close();

        Button submit=(Button) findViewById(R.id.submit);

        Button Dsubmit=(Button) findViewById(R.id.Decline);


        Button Query1=(Button) findViewById(R.id.Query);


        FloatingActionButton buttonP 	= (FloatingActionButton) findViewById(R.id.imageButtonP);
        FloatingActionButton buttonD 	= (FloatingActionButton) findViewById(R.id.imageButtonD);
        FloatingActionButton buttonA 	= (FloatingActionButton) findViewById(R.id.imageButtonA);
        FloatingActionButton buttonV 	= (FloatingActionButton) findViewById(R.id.imageButtonV);

        FloatingActionButton imageButtonDISBUS 	= (FloatingActionButton) findViewById(R.id.imageButtonDISBUS);


        buttonP.setVisibility(View.GONE);
        buttonA.setVisibility(View.GONE);
        buttonD.setVisibility(View.GONE);
        buttonV.setVisibility(View.GONE);
        if(sts.equals("CLEAR")){
            buttonP.setVisibility(View.VISIBLE);
            buttonA.setVisibility(View.VISIBLE);
            buttonD.setVisibility(View.VISIBLE);
            buttonV.setVisibility(View.VISIBLE);
        }

        if(sts.equals("Recovery")){

            FloatingActionButton CCCCC 	= (FloatingActionButton) findViewById(R.id.imageButtonDISBUS);
            CCCCC.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            Dsubmit.setVisibility(View.GONE);
            Query1.setVisibility(View.GONE);
          //  cheqeLeni.setVisibility(View.GONE);
        }

        if(under_depart.equals("Sales Person")){
            submit.setText("Update");
            Dsubmit.setVisibility(View.GONE);
            if(sts.equals("PENDING")||sts.equals("DONE")||sts.equals("FINANCIAL")||sts.equals("SANCTION")){appsts.setVisibility(View.GONE);}
        }if(under_depart.equals("Manager")){
            //  submit.setVisibility(View.GONE);
            // Dsubmit.setVisibility(View.GONE);
            //Query1.setVisibility(View.GONE);
            //if(sts.equals("DISBUS")){submit.setVisibility(View.GONE);Dsubmit.setVisibility(View.GONE);}
            //if(sts.equals("DISBUS")){submit.setVisibility(View.GONE);Dsubmit.setVisibility(View.GONE);imageButtonDISBUS.setVisibility(View.GONE);}
        }if(under_depart.equals("Admin")){
            // submit.setVisibility(View.GONE);
            // Dsubmit.setVisibility(View.GONE);
            // Query1.setVisibility(View.GONE);
            // if(sts.equals("DISBUS")){submit.setVisibility(View.GONE);Dsubmit.setVisibility(View.GONE);imageButtonDISBUS.setVisibility(View.GONE);}
            // if(sts.equals("CLEAR")||sts.equals("PENDING")||sts.equals("DONE")){appsts.setVisibility(View.GONE);}
        }

        new CustomerLongOperation().execute(Customer_uSERVER_URL,cid,cname,dddd);

        /*

*/


        new LongOperation1().execute(serverURL2,cid,"","");

        gps = new GPSTracker(Recovery_EMP_Assign.this);

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

        lat  = Double.toString(latitude);
        lon = Double.toString(longitude);

        final String[] items = new String[] { "Take from camera",
                "Select from gallery" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, items);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    //define the file-name to save photo taken by Camera activity
                    String fileName = "new-photo-name.jpg";
                    //create parameters for Intent with filename
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    values.put(MediaStore.Images.Media.DESCRIPTION,"Image captured by camera");
                    //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                    imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    //create new Intent
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, PICK_Camera_IMAGE);

                } else { // pick from file

                    try {
                        Intent gintent = new Intent();
                        gintent.setType("image/*");
                        gintent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(
                                Intent.createChooser(gintent, "Select Picture"),
                                PICK_IMAGE);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),
                                e.getMessage(),
                                Toast.LENGTH_LONG).show();
                        Log.e(e.getClass().getName(), e.getMessage(), e);
                    }


                }
            }
        });

        final android.app.AlertDialog dialog = builder.create();



/*
        final String[] items = new String[] { "Camera",
                "Gallery" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    String storageState = Environment.getExternalStorageState();

                    if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        // intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                        // mImageCaptureUri);
                        // if (hasImageCaptureBug()) {

                        String path = Environment.getExternalStorageDirectory()
                                .getName()
                                + File.separatorChar
                                + System.currentTimeMillis() + ".jpg";
                        File file = new File(path);
                        // mImageCaptureUri = Uri.fromFile(new File(
                        // Environment.getExternalStorageDirectory(),
                        // "tmp_avatar_"
                        // + String.valueOf(System
                        // .currentTimeMillis())
                        // + ".jpg"));
                        Log.i("TAG", "Final  path" + path);
                        try {
                            if (file.exists() == false) {
                                file.getParentFile().mkdirs();
                                file.createNewFile();
                            }

                        } catch (IOException e) {
                            Log.e("TAG", "Could not create file.", e);
                        }
                        mImageCaptureUri = Uri.fromFile(file);
                        intent.putExtra(
                                android.provider.MediaStore.EXTRA_OUTPUT,
                                mImageCaptureUri);
                        // } else {
                        // intent.putExtra(
                        // android.provider.MediaStore.EXTRA_OUTPUT,
                        // android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        // }

                        try {
                            // intent.putExtra("return-data", true);
                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        new AlertDialog.Builder(Manager_Approve.this)
                                .setMessage(
                                        "External Storeage (SD Card) is required.\n\nCurrent state: "
                                                + storageState)
                                .setCancelable(true).create().show();
                    }

                } else { // pick from file
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_FILE);
                }
            }
        });

        final AlertDialog dialog = builder.create();
*/


        Cadd 	= (ImageView) findViewById(R.id.imageViewP);
        //      ImageButton buttonP 	= (ImageButton) findViewById(R.id.imageButtonP);
        buttonP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=1;

                startCropImage();


            }
        });
        CaddA 	= (ImageView) findViewById(R.id.imageViewA);
//        ImageButton buttonA 	= (ImageButton) findViewById(R.id.imageButtonA);
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=2;

                startCropImage();
            }
        });
        CaddD	= (ImageView) findViewById(R.id.imageViewD);
        //ImageButton buttonD 	= (ImageButton) findViewById(R.id.imageButtonD);
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=3;

                startCropImage();

            }
        });
        CaddV 	= (ImageView) findViewById(R.id.imageViewV);
        // ImageButton buttonV 	= (ImageButton) findViewById(R.id.imageButtonV);
        buttonV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=4;

                startCropImage();

            }
        });

        CCCCC 	= (ImageView) findViewById(R.id.imageView18);
        // ImageButton buttonV 	= (ImageButton) findViewById(R.id.imageButtonV);
        imageButtonDISBUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=5;

                startCropImage();

            }
        });


    }

    private void showBottomSheet(final String appro){
        mBottomSheetDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.approve_send, null);
        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window));
        FancyButton onBack = (FancyButton)v.findViewById(R.id.TaskM2441);

        FancyButton bt_wrapMin = (FancyButton)v.findViewById(R.id.Taskmin);
        FancyButton bt_wrapMax = (FancyButton)v.findViewById(R.id.Taskmax);

        FancyButton approv = (FancyButton)v.findViewById(R.id.appro);
        FancyButton approCan = (FancyButton)v.findViewById(R.id.approCan);
        if(under_depart.equals("Sales Person")) {
            view8=(TextView)v.findViewById(R.id.view8);
            view8.setText("Send Message for Disbursement");
        } if(under_depart.equals("Manager")) {
            view8=(TextView)v.findViewById(R.id.view8);
            view8.setText("Sartha MicroFinance Manager Approval/Decline");
        } if(under_depart.equals("Admin")) {
            view8=(TextView)v.findViewById(R.id.view8);
            view8.setText("Sartha MicroFinance Admin Approval/Decline");
        }


        MsgeditText =(TextView)v.findViewById(R.id.MsgeditText);

        approv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //
                MSG = MsgeditText.getText().toString().trim();


                db = new DBAdapter(getApplicationContext());

                // db.open();
                HashMap<String, String> dataf = db.getLogininfo();
                //String email = dataf.get("email");
                empname = dataf.get("name");
                regid = dataf.get("regid");
                String empdevice_imei = dataf.get("device_imei");
                user_id = dataf.get("user_id");

                String under_depart = dataf.get("under_depart");
                deviceIMEI = empdevice_imei.toString().trim();
                //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
                db.close();




            }

        });

        approCan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onPause();
            }

        });

        bt_wrapMin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
            }

        });

        bt_wrapMax.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
            }

        });


        onBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onPause();
            }

        });

        mBottomSheetDialog.contentView(v)
                .show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mBottomSheetDialog != null){
            mBottomSheetDialog.dismissImmediately();
            mBottomSheetDialog = null;
        }
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

                if(Ckeck==5){
                    img5 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                    CCCCC.setImageBitmap(bitmap);

                    String photo11 = "Disbus_image";
                    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo11, cid, cname, deviceIMEI, img5);

                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public  void Submit(){


        TextView txtNameCusto = (TextView) findViewById(R.id.txtNameCusto);
        TextView  txtNameFather = (TextView) findViewById(R.id.txtNameFather);
        TextView  txtNameMobile = (TextView) findViewById(R.id.txtNameMobile);
        TextView  txtNameAddress = (TextView) findViewById(R.id.txtNameAddress);
        TextView  txtNameCity = (TextView) findViewById(R.id.txtNameCity);
        TextView  PinZip= (TextView) findViewById(R.id.PinZip);
        TextView  txtNameEmailP = (TextView) findViewById(R.id.txtNameEmailP);
        TextView  txtNamePANP= (TextView) findViewById(R.id.txtNamePANP);


        Name = txtNameCusto.getText().toString();
        Fname = txtNameFather.getText().toString();
        tunMobile = txtNameMobile.getText().toString();
        Address = txtNameAddress.getText().toString();
        City = txtNameCity.getText().toString();
        Zip = PinZip.getText().toString();
        Xiemail = txtNameEmailP.getText().toString();
        XPan = txtNamePANP.getText().toString();


        lat  = Double.toString(latitude);
        lon = Double.toString(longitude);



        TextView  BankName= (TextView) findViewById(R.id.BankName);
        TextView  BankBranchName1= (TextView) findViewById(R.id.BankBranchName);
        TextView  BankACC= (TextView) findViewById(R.id.BankACC);
        TextView  Bankifsc= (TextView) findViewById(R.id.Bankifsc);


        bankName=BankName.getText().toString();

        BankBranchName=BankBranchName1.getText().toString();

        AccountNum=BankACC.getText().toString();

        IFSCC=Bankifsc.getText().toString();


        strings = new String[allEds.size()];
        for(int i2=0; i2 < allEds.size(); i2++){
            strings[i2] = allEds.get(i2).getText().toString();
        }


        strings2 = new String[allEds1.size()];
        for(int i3=0; i3 < allEds1.size(); i3++) {
            strings2[i3] = allEds1.get(i3).getText().toString();
        }

        chequedate=(TextView)findViewById(R.id.chequedate);


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
                                        if(bankName.trim().length() > 2){
                                            if(BankBranchName.trim().length() > 2){
                                                if(AccountNum.trim().length() > 6){
                                                    if(IFSCC.trim().length() > 3){
                                                        if(!Cadd.equals(null)){
                                                            if(chequedate.equals("")){
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
                                                                                if(sts=="DISBUS"){if(status=="DISBUS"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                                                                                }else {

                                                                                  //  new DisbusLongOperation().execute(Disbus_uSERVER_URL,cid,cname,deviceIMEI,strings.toString(),strings2.toString());

                                                                                    new LongOperation().execute(SERVER_URL, Name, Fname, tunMobile, Address, City, Zip, Xiemail, XPan, empname, regid, lat, lon, deviceIMEI, cid,bankName,BankBranchName,AccountNum,IFSCC);

                                                                                }//Toast.makeText(getBaseContext(), "Please Make Sure Your Profile Details is Completed Correctly ", Toast.LENGTH_SHORT).show();
                                                                                //addEmployee();
                                                                                //onStartTransaction();

                                                                               // new ManagerApprove().execute(SDisbus_Link,cid,cname,deviceIMEI,empname,regid,MSG,lat,lon);
                                                                            }
                                                                        })

                                                                        .setNegativeButton("Cancel", null)						//Do nothing on no
                                                                        .show();

                                                            }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Select Date For Disbursement.", Toast.LENGTH_SHORT).show();return;}
                                                        }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Take Customer Photo", Toast.LENGTH_SHORT).show();return;}
                                                    }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter IFSC Code", Toast.LENGTH_SHORT).show();return;}
                                                }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter Bank Account Number", Toast.LENGTH_SHORT).show();return;}
                                            }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter Bank Branch Name", Toast.LENGTH_SHORT).show();return;}
                                        }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter Bank Name", Toast.LENGTH_SHORT).show();return;}


                                    }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter Valid PAN Number", Toast.LENGTH_SHORT).show();return;}

                                }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter Valid Email ID", Toast.LENGTH_SHORT).show();return;}

                            }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter Valid Zip Code", Toast.LENGTH_SHORT).show();return;}

                        }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter Valid City Name", Toast.LENGTH_SHORT).show();return;}

                    }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter Valid Address", Toast.LENGTH_SHORT).show();return;}

                }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();return;}

            }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter Father's Name", Toast.LENGTH_SHORT).show();return;}

        }else{Toast.makeText(Recovery_EMP_Assign.this, "Please Enter Name", Toast.LENGTH_SHORT).show();return;}

    }
    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Recovery_EMP_Assign.this);
        String data ="";
        int sizeData = 0;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

            // Dialog.setMessage("Loading...");
            // Dialog.show();

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

                        // Toast.makeText(getApplicationContext(), "Order Saved Successfully! Please Wait...", Toast.LENGTH_LONG).show();
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
/*
                            BlurBehind.getInstance().execute(Disbursement.this, new OnBlurCompleteListener() {
                                @Override
                                public void onBlurComplete() {
                                    Intent intent = new Intent(Disbursement.this, Launch.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);

                                    finish();
                                }
                            });
*/
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

    public void Submit1(View view){
        String appro="OK";
        showBottomSheet(appro);
    }

    public void backok(View view){
       /* BlurBehind.getInstance().execute(Manager_Approve.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(Manager_Approve.this, Launch.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                finish();
            }
        });
        */
        String appro="NOT";
        showBottomSheet(appro);
    }

    // Class with extends AsyncTask class
    public class CustomerLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Recovery_EMP_Assign.this);
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
                            cphoto=photo;
                            String aadhar_card       = jsonChildNode.optString("aadhar_card").toString();
                            String driving_licence       = jsonChildNode.optString("driving_licence").toString();
                            String voter_id       = jsonChildNode.optString("voter_id").toString();
                            String mobile       = jsonChildNode.optString("mobile").toString();
                            String customer_name       = jsonChildNode.optString("customer_name").toString();
                            String message       = jsonChildNode.optString("message").toString();
                            status       = jsonChildNode.optString("status").toString();

                            // Toast.makeText(getApplicationContext(), "Fetch!  "+status, Toast.LENGTH_LONG).show();


                            //TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal;

                            String freqency       = jsonChildNode.optString("freqency").toString();
                            String scheme       = jsonChildNode.optString("scheme").toString();
                            String Advance_EMI       = jsonChildNode.optString("Advance_EMI").toString();
                            String DownPay       = jsonChildNode.optString("DownPay").toString();
                            String Disbursement1       = jsonChildNode.optString("Disbursement").toString();


                            String Bank_name       = jsonChildNode.optString("Bank_name").toString();
                            String Bank_branch       = jsonChildNode.optString("Bank_branch").toString();
                            String Account_number       = jsonChildNode.optString("Account_number").toString();
                            String ifsc       = jsonChildNode.optString("ifsc").toString();

                            String Disbus_image       = jsonChildNode.optString("Disbus_image").toString();


                            String LTV_Policy       = jsonChildNode.optString("LTV_Policy").toString();
                            String ROI_policy       = jsonChildNode.optString("ROI_policy").toString();


                            String payedEMI       = jsonChildNode.optString("payedEMI").toString();
                            notPayedEM       = jsonChildNode.optString("notPayedEM").toString();



                             paid_amount       = jsonChildNode.optString("paid_amount").toString();
                             loan_install_no       = jsonChildNode.optString("loan_install_no").toString();
                             installment_date       = jsonChildNode.optString("installment_date").toString();
                             cheque_number       = jsonChildNode.optString("cheque_number").toString();
                            total_late_charge_panalty1       = jsonChildNode.optString("total_late_charge_panalty").toString();


                            String allot_id       = jsonChildNode.optString("allot_id").toString();

                            Button alloedd =(Button)findViewById(R.id.alloedd);

                            if(allot_id.equals("null")||allot_id.equals("")||allot_id.equals("0")){
                            }else{
                                alloedd.setText("Allotted");
                                alloedd.setEnabled(false);
                                Toast.makeText(getApplicationContext(), "Allotted Customer", Toast.LENGTH_LONG).show();
                            }

                            TextView  chequedate= (TextView) findViewById(R.id.chequedate);
                            TextView  Cdate= (TextView) findViewById(R.id.Cdate);
                            TextView  paidA= (TextView) findViewById(R.id.paidA);
                            TextView  Inst1= (TextView) findViewById(R.id.Inst1);

                            chequedate.setText(installment_date);
                            Cdate.setText(cheque_number);
                            paidA.setText(paid_amount);
                            Inst1.setText(loan_install_no);




                            TextView  emiamount= (TextView) findViewById(R.id.emiamount);

                            emiamount.setText(emiPP);




                            ROIP = (TextView)findViewById(R.id.ROIP);
                            LTVP = (TextView)findViewById(R.id.LTVP);

                            ROIP.setText(ROI_policy+"%");
                            LTVP.setText(LTV_Policy+"Rs.");

                            TextView  BankName= (TextView) findViewById(R.id.BankName);
                            TextView  BankBranchName1= (TextView) findViewById(R.id.BankBranchName);
                            TextView  BankACC= (TextView) findViewById(R.id.BankACC);
                            TextView  Bankifsc= (TextView) findViewById(R.id.Bankifsc);

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
                            TextView Disbursement = (TextView)findViewById(R.id.Disbursement);
                            Frequency.setText(freqency);
                            //SchemeType.setText(scheme);
                            AdvanceEMI.setText(Advance_EMI);
                            DownPayment.setText(DownPay);
                            Disbursement.setText(Disbursement1);



                            int emi_type= 0,emi_type1=0,emi_type2=0,emi_type3=0;
                            int E1mT1,E2mT1,E3mT1,E1mT2,E2mT2,E3mT2,Tenure=0;
                            Tenure=Integer.valueOf(tunerPP.toString().trim());

                            int ask=0;

                            E1mT1= Integer.valueOf(notPayedEM.toString().trim());
                            E1mT2 = Tenure-E1mT1;
                            SchemeType.setText(E1mT2+"|"+E1mT1+"  EMI Type");
                            ask=E1mT1;

                            loopint= Integer.valueOf(ask);
/*
                            if(scheme.equals("1")){
                                E1mT1 = (75*Tenure)/100;
                                E1mT2 =  Tenure-E1mT1;
                                emi_type1=E1mT2;
                                SchemeType.setText(E1mT2+"|"+E1mT1+"  EMI Type");

                                ask=E1mT1;

                            }
                            if(scheme.equals("2")) {

                                E2mT1 = (60 * Tenure) / 100;
                                E2mT2 = Tenure - E2mT1;

                                emi_type2 = E2mT2;

                                SchemeType.setText(E2mT2 + "|" + E2mT1 + "  EMI Type");

                                ask= E2mT1;
                            }

                            if(scheme.equals("3")) {
                                E3mT1 = (50 * Tenure) / 100;
                                E3mT2 = Tenure - E3mT1;

                                emi_type3 = E3mT2;

                                SchemeType.setText(E3mT2 + "|" + E3mT1 + "  EMI Type");
                                ask= E3mT1;
                            }

*/




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


                            TextView txtNameCusto = (TextView) findViewById(R.id.txtNameCusto);
                            TextView  txtNameFather = (TextView) findViewById(R.id.txtNameFather);
                            TextView  txtNameMobile = (TextView) findViewById(R.id.txtNameMobile);
                            TextView  txtNameAddress = (TextView) findViewById(R.id.txtNameAddress);
                            TextView  txtNameCity = (TextView) findViewById(R.id.txtNameCity);
                            TextView  PinZip= (TextView) findViewById(R.id.PinZip);

                            TextView  txtNameEmailP = (TextView) findViewById(R.id.txtNameEmailP);
                            TextView  txtNamePANP= (TextView) findViewById(R.id.txtNamePANP);



                            txtNameCusto.setText(customer_name);
                            txtNameFather.setText(father_name);
                            txtNameMobile.setText(mobile);
                            txtNameAddress.setText(addressP);
                            txtNameCity.setText(city);
                            PinZip.setText(zipP);

                            txtNameEmailP.setText(Pemail);
                            txtNamePANP.setText(pan_number);


                            Cadd 	= (ImageView) findViewById(R.id.imageViewP);
                            CaddA 	= (ImageView) findViewById(R.id.imageViewA);
                            CaddD 	= (ImageView) findViewById(R.id.imageViewD);
                            CaddV 	= (ImageView) findViewById(R.id.imageViewV);


                            android.widget.LinearLayout h1=(android.widget.LinearLayout)findViewById(R.id.h1);
                            android.widget.LinearLayout h2=(android.widget.LinearLayout)findViewById(R.id.h2);
                            android.widget.LinearLayout h3=(android.widget.LinearLayout)findViewById(R.id.h3);
                            android.widget.LinearLayout h4=(android.widget.LinearLayout)findViewById(R.id.h4);
                            android.widget.LinearLayout h5=(android.widget.LinearLayout)findViewById(R.id.h5);
                            android.widget.LinearLayout h6=(android.widget.LinearLayout)findViewById(R.id.h6);

                            if(product_type.equals("Sartha Sahayak")||product_type.equals("Sartha Sahayak Plus")||
                                    product_type.equals("Sartha Subharambh")||product_type.equals("Sartha Subharambh Plus")){

                                h1.setVisibility(View.GONE);
                                h2.setVisibility(View.GONE);
                                h3.setVisibility(View.GONE);
                                h4.setVisibility(View.GONE);
                                h5.setVisibility(View.GONE);
                                h6.setVisibility(View.GONE);
                            }else{

                            }
/*

                            Glide.with(Manager_Approve.this)
                                    .load(photo)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(Cadd);

                            //  Picasso.with(Manager_Approve.this)
                            //.(R.drawable.ic_launcher)
                            //        .load(photo)
//                                    .into(Cadd);
                        Glide.with(Manager_Approve.this)
                                    //.(R.drawable.ic_launcher)

                                    .load(aadhar_card)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(CaddA);

                            Glide.with(Manager_Approve.this)
                                    //.(R.drawable.ic_launcher)
                                    .load(driving_licence)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(CaddD);

                            Glide.with(Manager_Approve.this)
                                    //.(R.drawable.ic_launcher)
                                    .load(voter_id)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(CaddV);
*/
                            imageLoader.DisplayImage(photo, Cadd);
                            imageLoader.DisplayImage(aadhar_card, CaddA);
                            imageLoader.DisplayImage(driving_licence, CaddD);
                            imageLoader.DisplayImage(voter_id, CaddV);
                            imageLoader.DisplayImage(Disbus_image, CCCCC);


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
        private ProgressDialog Dialog = new ProgressDialog(Recovery_EMP_Assign.this);
        String data ="";
        int sizeData = 0;
        protected void onPreExecute() {
            // Dialog.setMessage("Uploading...");
            // Dialog.show();
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

                        // Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
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
    public class ManagerApprove  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Recovery_EMP_Assign.this);
        String data ="";
        int sizeData = 0;
        protected void onPreExecute() {


            Dialog.setMessage("Sending...");
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
                if(!params[4].equals(""))
                    data +="&" + URLEncoder.encode("under_emp_id", "UTF-8") + "="+params[4].toString();
                if(!params[5].equals(""))
                    data +="&" + URLEncoder.encode("fcm_id", "UTF-8") + "="+params[5].toString();
                if(!params[6].equals(""))
                    data +="&" + URLEncoder.encode("msg", "UTF-8") + "="+params[6].toString();
                if(!params[7].equals(""))
                    data +="&" + URLEncoder.encode("lat", "UTF-8") + "="+params[7].toString();
                if(!params[8].equals(""))
                    data +="&" + URLEncoder.encode("lon", "UTF-8") + "="+params[8].toString();
                if(!params[9].equals(""))
                    data +="&" + URLEncoder.encode("allot_name", "UTF-8") + "="+params[9].toString();
                if(!params[10].equals(""))
                    data +="&" + URLEncoder.encode("allot_id", "UTF-8") + "="+params[10].toString();
                if(!params[11].equals(""))
                    data +="&" + URLEncoder.encode("loan_install_no", "UTF-8") + "="+params[11].toString();
                if(!params[12].equals(""))
                    data +="&" + URLEncoder.encode("installment_date", "UTF-8") + "="+params[12].toString();


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

                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        boolean error       = jsonChildNode.getBoolean("error");
                        if (!error) {
                            // String PENDING       = jsonChildNode.optString("PENDING").toString();

                            Intent intent1 = null;

                            if(under_depart.equals("Sales Person")){
                                // intent1 = new Intent(Disbursement.this, Launch.class);
                            }if(under_depart.equals("Manager")){
                                intent1 = new Intent(Recovery_EMP_Assign.this, Manager_Launch.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent1);

                                finish();
                            }if(under_depart.equals("Admin")){
                                intent1 = new Intent(Recovery_EMP_Assign.this, Admin_Launch.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent1);

                                finish();
                            }

                            Toast.makeText(getApplicationContext(), "Sent.", Toast.LENGTH_LONG).show();



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

    public void queryDo(){
        Dialog.Builder builder = null;

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;


        builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){

            @Override
            protected void onBuildDone(Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView ccccq = (TextView)dialog.findViewById(R.id.cccc);
                TextView bbbbq = (TextView)dialog.findViewById(R.id.bbbb);

                ImageView im = (ImageView)dialog.findViewById(R.id.profile_pic11);

                ccccq.setText(lonper11);
                bbbbq.setText(lonper12);
                Glide.with(Recovery_EMP_Assign.this)
                        //.(R.drawable.ic_launcher)
                        .load(lonper13.replace("http","https"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_launcher)
                        .into(im);

            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                 et_pass = (TextView)fragment.getDialog().findViewById(R.id.QueryID);
                //Toast.makeText(mActivity, "Connected", Toast.LENGTH_SHORT).show();
                cc=et_pass.getText().toString().trim();
                if(cc.toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Please Type...", Toast.LENGTH_SHORT).show();
                }else{


                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Recovery_EMP_Assign.this);
                    builder
                            .setTitle("Recovery Allotment")
                            .setMessage("Are you Sure?")
                            //.setMessage("Customer Details!")
                            .setIcon(android.R.drawable.ic_menu_send)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // String appro="QUERY";
                                    //showBottomSheet(appro);
                                        new ManagerApprove().execute(Allot,cid,cname,deviceIMEI,empname,regid,cc,lat,lon,lonper11,lonper12,loan_install_no,installment_date);
                                }
                            })

                            .setNegativeButton("NO", null)						//Do nothing on no
                            .show();

                }

                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.title("Recovery Allotment")
                .positiveAction("SEND")
                .negativeAction("CANCEL")
                .contentView(R.layout.z_allot);


        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);

    }

    public void datapick(View view){

        Dialog.Builder builder = null;


        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;


        builder = new DatePickerDialog.Builder(isLightTheme ? R.style.Material_App_Dialog_DatePicker_Light :  R.style.Material_App_Dialog_DatePicker){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog)fragment.getDialog();
                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                int y=dialog.getYear();
                int m1=dialog.getMonth();
                m1=m1+1;
                int d=dialog.getDay();
                Toast.makeText(Recovery_EMP_Assign.this, "Date is " + date, Toast.LENGTH_SHORT).show();
                chequedate=(TextView)findViewById(R.id.chequedate);
                chequedate.setText(y+"/"+m1+"/"+d);
                // tbl=null;
                disbusLoanDate(y,m1,d);
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                // Toast.makeText(Disbursement.this, "Cancelled" , Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("OK")
                .negativeAction("CANCEL");


        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
    }

    public class DisbusLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Recovery_EMP_Assign.this);
        String data ="";
        int sizeData = 0;
        protected void onPreExecute() {
            // Dialog.setMessage("Loading...");
            // Dialog.show();
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
                if(!params[4].equals(""))
                    data +="&" + URLEncoder.encode("ldata", "UTF-8") + "="+java.util.Arrays.toString(strings);
                if(!params[5].equals(""))
                    data +="&" + URLEncoder.encode("ldata1", "UTF-8") + "="+java.util.Arrays.toString(strings2);

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

                            Intent intent = new Intent(Recovery_EMP_Assign.this, Launch.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);

                            finish();

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

    public void disbusLoanDate(int y, int m, int d){
        // Calendar cal = Calendar.getInstance();


        if(d>28){

            Toast.makeText(getApplicationContext(),"Select Day Only 1-28 Date.", Toast.LENGTH_SHORT).show();
            return;
        }
        //cal.set(Calendar.YEAR, y);
        //cal.set(Calendar.MONTH, m);
        //cal.set(Calendar.DAY_OF_MONTH, d);
        //int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // tr.removeAllViews();
//    et.requestLayout();

        //tbl.removeAllViews();
        if(!tbl.equals(null)){
            tbl.removeAllViews();
        }

        tbl=(TableLayout)findViewById(R.id.TableLayout1);


        //table row

        for (int i1 = 0; i1 < loopint; i1++) {
            tr = new TableRow(getApplicationContext());
            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            //for set margin
            tableRowParams.setMargins(0, 2, 0, 0);
            tr.setLayoutParams(tableRowParams);
            tr.setGravity(Gravity.CENTER_HORIZONTAL);
            //text view
            TextView tv=new TextView(getApplicationContext());
            tv.setText("Cq. No. "+(i1+1));
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#FF1E74FF"));
            tv.setTextSize(15);
            tv.setLayoutParams(new TableRow.LayoutParams(100, TableRow.LayoutParams.WRAP_CONTENT));

            tr.addView(tv);
            //text vi
            //

            tv2=new TextView(getApplicationContext());


            allEds1.add(tv2);
            tv2.setText(y+"-"+m+"-"+d);
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextColor(Color.parseColor("#FF1E74FF"));
            tv2.setTextSize(15);
            tv2.setLayoutParams(new TableRow.LayoutParams(50, TableRow.LayoutParams.WRAP_CONTENT));
            //add textview
            tr.addView(tv2);
            //set layout params of TextView
            TableRow.LayoutParams etParams=
                    new TableRow.LayoutParams
                            (100,50);
            etParams.setMargins(2, 0, 0, 0);


            et=new TextView(getApplicationContext());

            allEds.add(et);
            et.setLayoutParams(etParams);
            et.setBackgroundResource(R.drawable.abc_edit_text_material);
            et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            et.setHint("Enter Cheque Number");
            // et.setText("Enkjkh");
            et.setId(i1+1);
            tr.addView(et);
            et.setTag(et.getId());
            tbl.addView(tr, tableRowParams);


  /*
   int maxd = Integer.valueOf(notPayedEM.toString());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
       // System.out.print(df.format(cal.getTime()));



        for (int i = 1; i < maxd; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
          //  System.out.print(", " + df.format(cal.getTime()));
            et.setText(df.format(cal.getTime()));
        }
*/

            et.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    final String[] strings = new String[allEds.size()];
                    for(int i2=0; i2 < allEds.size(); i2++){
                        strings[i2] = allEds.get(i2).getText().toString();
                    }

                    Log.d("id of edit text", ""+strings[0].toString().trim());
                    //timePicker();
                }
            });

            m++;
            if(m>12){
                y++;
                m=1;
            }
        }




    }

    public void Allot(){
    showBottomSheet1();
}

    public void showBottomSheet1(){
        mBottomSheetDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.z_emp_popup_list, null);
        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window));


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        FancyButton onBack = (FancyButton)v.findViewById(R.id.TaskM2441);


        prog = (ProgressView)v.findViewById(R.id.progress_pv_circular_inout_colors);

        prog.start();
        FancyButton bt_wrapMin = (FancyButton)v.findViewById(R.id.Taskmin);
        FancyButton bt_wrapMax = (FancyButton)v.findViewById(R.id.Taskmax);

        bt_wrapMin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
            }

        });

        bt_wrapMax.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
            }

        });

        listsubR.clear();
        listsub1.clear();
        listsub2.clear();
        listsub3.clear();
        listsub4.clear();
        listsub5.clear();
        // listsub6.clear();
        // listsub7.clear();
        // listsub8.clear();
        // listsub9.clear();

        // originalValues1=null;
        // searchResults1=null;


        mHandler = new Handler();




        inputSearch = (TextView) v.findViewById(R.id.inputSearch);


        new LongOperationFind().execute(serverURL1,"id","id","id");


        db = new DBAdapter(getApplicationContext());

        // db.open();
        HashMap<String, String> dataf = db.getLogininfo();
        String user_id = dataf.get("user_id");
        empname = dataf.get("name");
        //regid = dataf.get("regid");
        String empdevice_imei = dataf.get("device_imei");
        //deviceIMEI =empdevice_imei;
        //Toast.makeText(getContext(),""+deviceIMEI, Toast.LENGTH_SHORT).show();
        db.close();

        // Menu menu; {
        // MenuItem mSearchAction = v.menu.findItem(R.id.action_search);


        //  inputSearch = (TextView)v.findViewById(R.id.edtSearch); //the text editor



//        aController = (Controller)v.getContext();
        //product = "Mobile";
        //  SelectCat = intent.getStringExtra("SelectCat");
        // ProPer = intent.getStringExtra("ProPer");

        //   proName = (TextView) findViewById(R.id.textPoster);
        //  proName.setText("Select " + product);
        mylistview1 = (ListView) v.findViewById(R.id.listViewtotalCC);

        // inflater=(LayoutInflater) v.getSystemService(Context.LAYOUT_INFLATER_SERVICE);








        onBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onPause();
            }

        });

        mBottomSheetDialog.contentView(v)
                .show();
    }

    public class LongOperationFind  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(getApplicationContext());
        String data ="";
        int sizeData = 0;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)
            //      Dialog.setMessage("Loading...");
//                      Dialog.show();

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

            prog.setVisibility(View.GONE);
            prog.stop();
            // Close progress dialog
            Dialog.dismiss();

            if (Error != null) {


            } else {

                // Show Response Json On Screen (activity)

                /****************** Start Parse Response JSON Data *************/

//                aController.clearUserData();

                JSONObject jsonResponse;

                try {

                    /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                    jsonResponse = new JSONObject(Content);

                    /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                    /*******  Returns null otherwise.  *******/
                 /*   JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
                    //JSONObject jObj = new JSONObject(response);
                    //JSONObject jObj = new JSONObject(response);
                    //boolean error = jObj.getBoolean("error");
                    //boolean error = jsonMainNode.getBoolean("email").toString(;
                    //boolean error       = jsonChildNode.optString("name").toString();
                    // Check for error node in json
                    //if (!error) {}
                    ********** Process each JSON Node ***********


                   // Toast.makeText(MainActivityD.this, ""+lengthJsonArr, Toast.LENGTH_SHORT).show();
                    for(int i=0; i < lengthJsonArr; i++)
                    {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                            String name       = jsonChildNode.optString("a").toString();
                        fragmentParent.addPage("aa"+ "");
                    }*/

                    //CreateDynamicTables("ABC","isa","ds");
                    // db.deleteAll();

                    if(jsonResponse != null){
                        JSONArray list = jsonResponse.getJSONArray("Android");
                        if(list != null){
                            for(int ji = 0; ji < list.length();ji++){
                                JSONObject elem = list.getJSONObject(ji);
                                if(elem != null){
                                    JSONArray prods = elem.getJSONArray("a");
                                    if(prods != null){
                                        for(int j = 0; j < prods.length();j++){
                                            JSONObject innerElem = prods.getJSONObject(j);
                                            if(innerElem != null){
                                                String sku = innerElem.getString("x");
                                                listsubR.add(prods.getJSONObject(j).getString("x"));

                                                listsub1.add(prods.getJSONObject(j).getString("sub1"));

                                                listsub2.add(prods.getJSONObject(j).getString("sub2"));

                                                listsub3.add(prods.getJSONObject(j).getString("sub3"));
                                                listsub4.add(prods.getJSONObject(j).getString("total_dis"));
                                                listsub5.add(prods.getJSONObject(j).getString("total_log"));


                                                //listsub4.add(prods.getJSONObject(j).getString("sub4"));
                                                //listsub5.add(prods.getJSONObject(j).getString("sub5"));
                                                //listsub6.add(prods.getJSONObject(j).getString("sub6"));
                                                //listsub7.add(prods.getJSONObject(j).getString("sub7"));
                                                //listsub8.add(prods.getJSONObject(j).getString("sub8"));
                                                //listsub9.add(prods.getJSONObject(j).getString("sub9"));


                                                /*
                                                JSONArray pro = innerElem.getJSONArray("y");
                                                ArrayList<String> listsub = new ArrayList<String>();

                                                if(pro != null){
                                                    for(int k = 0; k < pro.length();k++){
                                                        JSONObject innerElemN = pro.getJSONObject(k);
                                                        if(innerElemN != null){

                                                            //String sub = innerElem.getString("sub");

                                                           // listsubR.add(pro.getJSONObject(k).getString("sub"));

                                                            JSONArray proTo = innerElemN.getJSONArray("w");

                                                            if(proTo != null){
                                                                for(int l = 0; l < proTo.length();l++){
                                                                    JSONObject innerElemNTo = proTo.getJSONObject(l);
                                                                    if(innerElemNTo != null){

                                                                        //String sub = innerElem.getString("sub");

                                                                        // listsubR.add(proTo.getJSONObject(l).getString("pro"));


                                                                    }}}

                                                        }}}
                                                        */
                                                //fragmentParent.addPage(sku);
                                                //textView.setText("");
                                                // HashMap<String, String> map = new HashMap<String, String>();

                                                // jsonobject = jsonarray.getJSONObject(i);
                                                // Retrive JSON Objects
                                                // map.put("x", innerElem.getString("x"));

                                                //db.addColumn("aaa");
                                                //db.close();

                                                // fragmentParent.addPage(sku,listsub);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        addone1();




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

    public void addone1(){



        //   cler();


        String[] names = new String[listsubR.size()];
        names = listsubR.toArray(names);

        String[] teams = new String[listsub1.size()];
        teams = listsub1.toArray(teams);

        String[] teamsP = new String[listsub2.size()];
        teamsP = listsub2.toArray(teamsP);

        String[] teamsS = new String[listsub3.size()];
        teamsS = listsub3.toArray(teamsS);

        String[] Sid = new String[listsub4.size()];
        Sid = listsub4.toArray(Sid);

        String[] Sname = new String[listsub5.size()];
        Sname = listsub5.toArray(Sname);

  /*
        String[] Simage = new String[listsub6.size()];
        Simage = listsub6.toArray(Simage);

        String[] message = new String[listsub7.size()];
        message = listsub7.toArray(message);


        String[] time = new String[listsub8.size()];
        time = listsub8.toArray(time);


        String[] casec = new String[listsub9.size()];
        casec = listsub9.toArray(casec);
        */

        // String teams[]={"Real Madrid","Barcelona","Chelsea", "Barcelona","Chelsea","Liverpool", "ManU","Barcelona"};
        Integer[] photos={R.drawable.smartphone,R.drawable.bikes,
                R.drawable.home_decore,R.drawable.books_a_more,
                R.drawable.electronics,R.drawable.appliances,
                R.drawable.books_a_more,R.drawable.fixtures};

        originalValues1=new ArrayList<HashMap<String,Object>>();

        //temporary HashMap for populating the
        //Items in the ListView
        HashMap<String , Object> temp=null;

        //total number of rows in the ListView
        int noOfPlayers=names.length;

        //now populate the ArrayList players
        for(int i=0;i<noOfPlayers;i++)
        {
            temp=new HashMap<String, Object>();

            temp.put("name", names[i]);
            temp.put("team", teams[i]);
            temp.put("photo", teamsP[i]);
            temp.put("status", teamsS[i]);
            temp.put("Sid", Sid[i]);
            temp.put("Sname", Sname[i]);
            //   temp.put("Simage", Simage[i]);
            //  temp.put("message", message[i]);
            //  temp.put("time", time[i]);
            //  temp.put("casec", casec[i]);
            //add the row to the ArrayList
            originalValues1.add(temp);
        }
        //searchResults=OriginalValues initially
        searchResults1=new ArrayList<HashMap<String,Object>>(originalValues1);

        //create the adapter
        //first param-the context
        //second param-the id of the layout file
        //you will be using to fill a row
        //third param-the set of values that
        //will populate the ListView
        final CustomAdapter adapteroo=new CustomAdapter(getApplicationContext(), R.layout.z_emp_list_data,searchResults1);

        //finally,set the adapter to the default ListView
        mylistview1.setAdapter(adapteroo);

        inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the TextView
                String searchString=inputSearch.getText().toString();
                int textLength=searchString.length();
                searchResults1.clear();

                for(int i=0;i<originalValues1.size();i++)
                {

                    String playerName=originalValues1.get(i).get("name").toString();
                    String idP=originalValues1.get(i).get("team").toString();

                    if(textLength<=playerName.length()){
                        //compare the String in TextView with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
                            searchResults1.add(originalValues1.get(i));
                    }
                    if(textLength<=idP.length()){
                        //compare the String in TextView with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(idP.substring(0,textLength)))
                            searchResults1.add(originalValues1.get(i));
                    }
                }

                adapteroo.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {


            }
        });


    }

    private class CustomAdapter extends ArrayAdapter<HashMap<String, Object>>
    {

        public CustomAdapter(Context context, int textViewResourceId,
                             ArrayList<HashMap<String, Object>> Strings) {

            //let android do the initializing :)
            super(context, textViewResourceId, Strings);
        }


        //class for caching the views in a row
        private class ViewHolder
        {
            ImageView photo,Simage;
            TextView name,team,contact_type,Sname,Sid,message,time,casec1,tt,ll;

        }

        ViewHolder viewHolder=null;

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                //View view = inflater.inflate(android.R.layout.list_item_recyclerView, parent, false);

                convertView=inflater.inflate(R.layout.z_emp_list_data, null);

                viewHolder=new ViewHolder();

                //cache the views
                viewHolder.photo=(ImageView) convertView.findViewById(R.id.profile_pic);
                viewHolder.name=(TextView) convertView.findViewById(R.id.member_name);
                viewHolder.team=(TextView) convertView.findViewById(R.id.status);
                viewHolder.contact_type=(TextView) convertView.findViewById(R.id.contact_typetime);

                viewHolder.tt=(TextView) convertView.findViewById(R.id.t);
                viewHolder.ll=(TextView) convertView.findViewById(R.id.l);

                //viewHolder.casec1=(TextView) convertView.findViewById(R.id.csase);
                //viewHolder.message=(TextView) convertView.findViewById(R.id.textView37);
                //viewHolder.time=(TextView) convertView.findViewById(R.id.timrid);
                //viewHolder.Sname=(TextView) convertView.findViewById(R.id.sname);
                // viewHolder.Sid=(TextView) convertView.findViewById(R.id.sid);
                // viewHolder.Simage=(ImageView) convertView.findViewById(R.id.profile_pic1);

                //link the cached views to the convertview
                convertView.setTag(viewHolder);

            }
            else
                viewHolder=(ViewHolder) convertView.getTag();


            // int photoId=(Integer) searchResults1.get(position).get("photo");

            Glide.with(getContext())
                    //.(R.drawable.ic_launcher)
                    .load(searchResults1.get(position).get("photo").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.photo);

            //imageLoader.DisplayImage(searchResults1.get(position).get("photo").toString(), viewHolder.photo);
            //set the data to be displayed
            //viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
            viewHolder.name.setText(searchResults1.get(position).get("name").toString());

            // viewHolder.Sname.setText(searchResults1.get(position).get("Sname").toString());
            // viewHolder.Sid.setText(searchResults1.get(position).get("Sid").toString());
            // viewHolder.casec1.setText(searchResults1.get(position).get("casec").toString());
            // viewHolder.time.setText(searchResults1.get(position).get("time").toString());
            // viewHolder.message.setText(searchResults1.get(position).get("message").toString());

            // viewHolder.Sname.setText(searchResults1.get(position).get("Sname").toString());
            // viewHolder.Sid.setText(searchResults1.get(position).get("Sid").toString());

            tot= Double.valueOf(searchResults1.get(position).get("Sid").toString());
            log= Double.valueOf(searchResults1.get(position).get("Sname").toString());



            Double cal= 0.0;
            cal= tot/log;

            //trendid.setText(total_dis+"/"+total_log+" = "+cal);



            viewHolder.contact_type.setText(cal.toString());

            viewHolder.tt.setText(tot.toString());
            viewHolder.ll.setText(log.toString());

            final String lonper = searchResults1.get(position).get("team").toString();
            String prop=searchResults1.get(position).get("team").toString()+"";


            viewHolder.team.setText(prop);

            final ViewHolder finalHolder = viewHolder;
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub


                    lonper11=searchResults1.get(position).get("team").toString();
                    lonper12= searchResults1.get(position).get("name").toString();
                    lonper13=searchResults1.get(position).get("photo").toString();

                    //final String namee = finalHolder.name.getText().toString().trim();
                    //final String eid = finalHolder.team.getText().toString().trim();
                    //final String sts = finalHolder.contact_type.getText().toString().trim();
/*
                    tot1= Double.valueOf(finalHolder.tt.getText().toString().trim());
                    log1=Double.valueOf(finalHolder.ll.getText().toString().trim());

                    Double cal= 0.0;
                    cal= tot1/log1;

                    Glide.with(getContext())
                            //.(R.drawable.ic_launcher)
                            .load(searchResults1.get(position).get("photo").toString())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_launcher)
                            .into(Ephoto);



                    TDD.setText(cal.toString());
*/
                    //queryDo();
                    if(mBottomSheetDialog != null){
                        mBottomSheetDialog.dismissImmediately();
                        mBottomSheetDialog = null;
                    }
                    Intent intent = new Intent(getContext(), Panalty_Count.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    intent.putExtra("cid", cid);
                    intent.putExtra("cname", cname);
                    intent.putExtra("cphoto", cphoto);
                    intent.putExtra("eid", lonper12);
                    intent.putExtra("ename", lonper11);
                    intent.putExtra("ephoto", lonper13);
                    intent.putExtra("intervaltext", intervaltext);
                    intent.putExtra("installment_date", installment_date2);
                    intent.putExtra("loan_install_no", loan_install_no);

                    intent.putExtra("emiPay", emiPay);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lon", lon);


                    startActivity(intent);

                    // Toast.makeText(finalConvertView.getContext(), ""+namee, Toast.LENGTH_SHORT).show();
                    //MainActivityD mainD = null;
                    //mainD.privecyTerms();



//                            Intent intent = new Intent(getContext(), Manager_Approve.class);
                    //                          intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    //                        intent.putExtra("cname", namee);
                    //                      intent.putExtra("cid", eid);

                    //                    intent.putExtra("sts", sts);
                    //                  startActivity(intent);

/*
                    if(task.equals("QUERY")){
                        Intent intent = new Intent(getContext(), Manager_Approve.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        intent.putExtra("cname", namee);
                        intent.putExtra("cid", eid);
                        intent.putExtra("sts", "CLEAR");
                        startActivity(intent);
                    }else {


                        //Toast.makeText(getApplicationContext(), "else In", Toast.LENGTH_SHORT).show();
                        if (task.equals("SANCTION")) {
                            Intent intent1 = new Intent(getContext(), Disbursement.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                            intent1.putExtra("cname", namee);
                            intent1.putExtra("cid", eid);
                            intent1.putExtra("sts", "SANCTION");
                            startActivity(intent1);
                        }else{


                            // Toast.makeText(getApplicationContext(), "else Go", Toast.LENGTH_SHORT).show();

                            Intent intent2 = new Intent(getContext(), Manager_Approve.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                            intent2.putExtra("cname", namee);
                            intent2.putExtra("cid", eid);
                            intent2.putExtra("sts", sts);

                            startActivity(intent2);
                        }
                    }
*/
                    //finish();

//if(Product.toString().trim()=="Mobile"){}
                    //(MainActivityD).mobile();
                    //FragmentChild.doSubProduct(Product);
                    //  subProduct(Product,Team);

                }
            });

            //return the view to be displayed
            return convertView;
        }

    }

    public void addone(){


        pv_circular_inout_colors.stop();
        pv_circular_inout_colors.setVisibility(View.GONE);

        String[] names = new String[listsubR.size()];
        names = listsubR.toArray(names);

        String[] teams = new String[listsub1.size()];
        teams = listsub1.toArray(teams);

        String[] teamsP = new String[icon.size()];
        teamsP = icon.toArray(teamsP);

        String[] no1 = new String[icon.size()];
        no1 = no.toArray(no1);

        String[] city1 = new String[icon.size()];
        city1 = city.toArray(city1);

        String[] product_name1 = new String[icon.size()];
        product_name1 = product_name.toArray(product_name1);

        String[] emi1 = new String[icon.size()];
        emi1 = emi.toArray(emi1);

        String[] ROI_policy1 = new String[icon.size()];
        ROI_policy1 = ROI_policy.toArray(ROI_policy1);

        String[] remarks1 = new String[icon.size()];
        remarks1 = remarks.toArray(remarks1);


        String[] ROI_Applied1 = new String[icon.size()];
        ROI_Applied1 = ROI_Applied.toArray(ROI_Applied1);

        String[] LTV_Policy1 = new String[icon.size()];
        LTV_Policy1 = LTV_Policy.toArray(LTV_Policy1);

        String[] LTV_Applied1 = new String[icon.size()];
        LTV_Applied1 = LTV_Applied.toArray(LTV_Applied1);

        String[] interval1 = new String[interval.size()];
        interval1 = interval.toArray(interval1);


        String[] total_late_charge_panalty2 = new String[total_late_charge_panalty.size()];
        total_late_charge_panalty2 = total_late_charge_panalty.toArray(total_late_charge_panalty2);


        String[] installment_date1 = new String[icon.size()];
        installment_date1 = installment_date3.toArray(installment_date1);

        String[] emp_id1 = new String[emp_id.size()];
        emp_id1 = emp_id.toArray(emp_id1);


        String[] Ename1 = new String[Ename.size()];
        Ename1 = Ename.toArray(Ename1);



        // String teams[]={"Real Madrid","Barcelona","Chelsea", "Barcelona","Chelsea","Liverpool", "ManU","Barcelona"};
        // String teams[]={"Real Madrid","Barcelona","Chelsea", "Barcelona","Chelsea","Liverpool", "ManU","Barcelona"};
        Integer[] photos={R.drawable.ic_electronics_devices_other_black_24dp,R.drawable.ic_laptop_mac_black_24dp,
                R.drawable.ic_directions_car_black_24dp,R.drawable.ic_applien_local_laundry_service_black_24dp,
                R.drawable.ic_home_shopping_basket_black_24dp,R.drawable.ic_other_shopping_cart_black_24dp,
                R.drawable.books_a_more,R.drawable.fixtures};

        originalValues=new ArrayList<HashMap<String,Object>>();

        //temporary HashMap for populating the
        //Items in the ListView
        HashMap<String , Object> temp;

        //total number of rows in the ListView
        int noOfPlayers=names.length;

        //now populate the ArrayList players
        for(int i=0;i<noOfPlayers;i++)
        {
            temp=new HashMap<String, Object>();

            temp.put("name", names[i]);
            temp.put("team", teams[i]);

            temp.put("photo", teamsP[i]);

            temp.put("no", no1[i]);
            temp.put("city", city1[i]);
            temp.put("product_name", product_name1[i]);
            temp.put("emi", emi1[i]);
            temp.put("ROI_policy", ROI_policy1[i]);
            temp.put("remarks", remarks1[i]);

            temp.put("ROI_Applied", ROI_Applied1[i]);
            temp.put("LTV_Policy", LTV_Policy1[i]);
            temp.put("LTV_Applied", LTV_Applied1[i]);
            temp.put("installment_date", installment_date1[i]);

            temp.put("emp_id", emp_id1[i]);
            temp.put("Ename", Ename1[i]);
            temp.put("interval", interval1[i]);

            temp.put("total_late_charge_panalty", total_late_charge_panalty2[i]);

/*
            if(SelectCat=="Care") {
                //photos = new Integer[]{R.drawable.ic_laptop_mac_black_24dp};
                temp.put("photo", photos[2]);
            }if(SelectCat=="Appliance") {
            //photos = new Integer[]{R.drawable.ic_laptop_mac_black_24dp};
            temp.put("photo", photos[1]);
        }if(SelectCat=="Electronics") {
            //photos = new Integer[]{R.drawable.ic_laptop_mac_black_24dp};
            temp.put("photo", photos[0]);
        }if(SelectCat=="Home Decor") {
            //photos = new Integer[]{R.drawable.ic_laptop_mac_black_24dp};
            temp.put("photo", photos[3]);
        }if(SelectCat=="Bike") {
            //photos = new Integer[]{R.drawable.ic_laptop_mac_black_24dp};
            temp.put("photo", photos[2]);
        }else{
            temp.put("photo", photos[5]);
        }
  */          //add the row to the ArrayList
            originalValues.add(temp);
        }
        //searchResults=OriginalValues initially
        searchResults=new ArrayList<HashMap<String,Object>>(originalValues);

        //create the adapter
        //first param-the context
        //second param-the id of the layout file
        //you will be using to fill a row
        //third param-the set of values that
        //will populate the ListView
        final CustomAdapter1 adapter=new CustomAdapter1(this, R.layout.rcovery_list_allote,searchResults);

        mylistview1 = (ListView)findViewById(R.id.listview);

        //finally,set the adapter to the default ListView
        mylistview1.setAdapter(adapter);

        /*

        inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the TextView
                String searchString=inputSearch.getText().toString();
                int textLength=searchString.length();
                searchResults.clear();

                for(int i=0;i<originalValues.size();i++)
                {

                    String playerNameid=originalValues.get(i).get("team").toString();
                    String playerName=originalValues.get(i).get("name").toString();
                    if(textLength<=playerName.length()){
                        //compare the String in TextView with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
                            searchResults.add(originalValues.get(i));
                    }
                    if(textLength<=playerNameid.length()){
                        //compare the String in TextView with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(playerNameid.substring(0,textLength)))
                            searchResults.add(originalValues.get(i));
                    }
                }

                adapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {


            }
        });

*/
    }

    // Class with extends AsyncTask class
    public class LongOperation1  extends AsyncTask<String, Void, String> {




        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Recovery_EMP_Assign.this);
        String data ="";
        int sizeData = 0;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

            // prog.setVisibility(View.GONE);
            //Dialog.setMessage("Loading...");
            // Dialog.show();
            pv_circular_inout_colors.setVisibility(View.VISIBLE);
            pv_circular_inout_colors.start();

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
//            prog.stop();

            pv_circular_inout_colors.stop();
            pv_circular_inout_colors.setVisibility(View.GONE);


            if (Error != null) {


            } else {

                // Show Response Json On Screen (activity)

                /****************** Start Parse Response JSON Data *************/

//                aController.clearUserData();

                JSONObject jsonResponse;

                try {

                    /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                    jsonResponse = new JSONObject(Content);

                    /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                    /*******  Returns null otherwise.  *******/
                 /*   JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
                    //JSONObject jObj = new JSONObject(response);
                    //JSONObject jObj = new JSONObject(response);
                    //boolean error = jObj.getBoolean("error");
                    //boolean error = jsonMainNode.getBoolean("email").toString(;
                    //boolean error       = jsonChildNode.optString("name").toString();
                    // Check for error node in json
                    //if (!error) {}
                    ********** Process each JSON Node ***********


                   // Toast.makeText(MainActivityD.this, ""+lengthJsonArr, Toast.LENGTH_SHORT).show();
                    for(int i=0; i < lengthJsonArr; i++)
                    {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                            String name       = jsonChildNode.optString("a").toString();
                        fragmentParent.addPage("aa"+ "");
                    }*/

                    //CreateDynamicTables("ABC","isa","ds");
                    // db.deleteAll();


                    listsubR.clear();
                    listsub1.clear();
                    listsub2.clear();
                    icon.clear();

                    no.clear();
                    city.clear();
                    product_name.clear();
                    emi.clear();
                    ROI_policy.clear();
                    remarks.clear();
                    installment_date3.clear();
                    emp_id.clear();
                    Ename.clear();
                    interval.clear();
                    total_late_charge_panalty.clear();

                    if(jsonResponse != null){
                        JSONArray list = jsonResponse.getJSONArray("Android");
                        if(list != null){
                            for(int ji = 0; ji < list.length();ji++){
                                JSONObject elem = list.getJSONObject(ji);
                                if(elem != null){
                                    JSONArray prods = elem.getJSONArray("a");
                                    if(prods != null){
                                        for(int j = 0; j < prods.length();j++){
                                            JSONObject innerElem = prods.getJSONObject(j);
                                            if(innerElem != null){
                                                String sku = innerElem.getString("x");
                                                listsubR.add(prods.getJSONObject(j).getString("x"));

                                                listsub1.add(prods.getJSONObject(j).getString("sub1"));

                                                listsub2.add(prods.getJSONObject(j).getString("sub2"));

                                                icon.add(prods.getJSONObject(j).getString("icon"));

                                                no.add(prods.getJSONObject(j).getString("no"));
                                                city.add(prods.getJSONObject(j).getString("city"));
                                                product_name.add(prods.getJSONObject(j).getString("product_name"));
                                                emi.add(prods.getJSONObject(j).getString("emi"));
                                                ROI_policy.add(prods.getJSONObject(j).getString("ROI_policy"));
                                                remarks.add(prods.getJSONObject(j).getString("remarks"));


                                                ROI_Applied.add(prods.getJSONObject(j).getString("ROI_Applied"));
                                                LTV_Policy.add(prods.getJSONObject(j).getString("LTV_Policy"));
                                                LTV_Applied.add(prods.getJSONObject(j).getString("LTV_Applied"));


                                                installment_date3.add(prods.getJSONObject(j).getString("installment_date"));

                                                emp_id.add(prods.getJSONObject(j).getString("emp_id"));
                                                Ename.add(prods.getJSONObject(j).getString("Ename"));
                                                interval.add(prods.getJSONObject(j).getString("www"));

                                                total_late_charge_panalty.add(prods.getJSONObject(j).getString("total_late_charge_panalty"));


                                                /*
                                                JSONArray pro = innerElem.getJSONArray("y");
                                                ArrayList<String> listsub = new ArrayList<String>();

                                                if(pro != null){
                                                    for(int k = 0; k < pro.length();k++){
                                                        JSONObject innerElemN = pro.getJSONObject(k);
                                                        if(innerElemN != null){

                                                            //String sub = innerElem.getString("sub");

                                                           // listsubR.add(pro.getJSONObject(k).getString("sub"));

                                                            JSONArray proTo = innerElemN.getJSONArray("w");

                                                            if(proTo != null){
                                                                for(int l = 0; l < proTo.length();l++){
                                                                    JSONObject innerElemNTo = proTo.getJSONObject(l);
                                                                    if(innerElemNTo != null){

                                                                        //String sub = innerElem.getString("sub");

                                                                        // listsubR.add(proTo.getJSONObject(l).getString("pro"));


                                                                    }}}

                                                        }}}
                                                        */
                                                //fragmentParent.addPage(sku);
                                                //textView.setText("");
                                                // HashMap<String, String> map = new HashMap<String, String>();

                                                // jsonobject = jsonarray.getJSONObject(i);
                                                // Retrive JSON Objects
                                                // map.put("x", innerElem.getString("x"));

                                                //db.addColumn("aaa");
                                                //db.close();

                                                // fragmentParent.addPage(sku,listsub);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        addone();
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


    //define your custom adapter
    private class CustomAdapter1 extends ArrayAdapter<HashMap<String, Object>>
    {

        public CustomAdapter1(Context context, int textViewResourceId,
                             ArrayList<HashMap<String, Object>> Strings) {

            //let android do the initializing :)
            super(context, textViewResourceId, Strings);
        }


        //class for caching the views in a row
        private class ViewHolder1
        {
            ImageView photo;
            RadioButton rb1;
            TextView name,team,city,price,price1,price2,price3,price4,sNo,ROI_Applied,LTV_Applied,LTV_Policy,installment_date,emp_id,Ename,interval;
            infracom.abcr.sarthamicrofinance.material.widget.LinearLayout relativeLayout1;
        }

        ViewHolder1 viewHolder1;

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {

                convertView=inflater.inflate(R.layout.rcovery_list_allote, null);

                viewHolder1=new ViewHolder1();


//                relativeLayout1=(LinearLayout)convertView.findViewById(R.id.relativeLayout1);


                //cache the views

               // viewHolder1.rb1 = (RadioButton)convertView.findViewById(R.id.switches_rb1);
                viewHolder1.photo=(ImageView) convertView.findViewById(R.id.profile_pic);
                viewHolder1.name=(TextView) convertView.findViewById(R.id.customername);
                viewHolder1.team=(TextView) convertView.findViewById(R.id.city);


                viewHolder1.sNo=(TextView) convertView.findViewById(R.id.sNo);
                viewHolder1.city=(TextView) convertView.findViewById(R.id.city);
                viewHolder1.price1=(TextView) convertView.findViewById(R.id.price1);
                viewHolder1.price2=(TextView) convertView.findViewById(R.id.price2);
                viewHolder1.price3=(TextView) convertView.findViewById(R.id.price3);
                viewHolder1.price4=(TextView) convertView.findViewById(R.id.price4);


                viewHolder1.ROI_Applied=(TextView) convertView.findViewById(R.id.price5);
                viewHolder1.LTV_Policy=(TextView) convertView.findViewById(R.id.price6);
                viewHolder1.LTV_Applied=(TextView) convertView.findViewById(R.id.price7);



                viewHolder1.installment_date=(TextView) convertView.findViewById(R.id.price8);
                viewHolder1.Ename=(TextView) convertView.findViewById(R.id.price9);

               // viewHolder1.rb1.setSelected ( false );
//                prog.stop();


                //link the cached views to the convertview
                convertView.setTag(viewHolder1);

            }
            else
                viewHolder1=(ViewHolder1) convertView.getTag();

            String remarkss = searchResults.get(position).get("remarks").toString();
             total_late_charge_panalty121=searchResults.get(position).get("total_late_charge_panalty").toString();
            if(remarkss.equals("")||remarkss.equals("0")||remarkss.equals("null")){
                convertView.setBackgroundColor(Color.parseColor("#FFD7ACAC"));
            }else {
                convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            if(!total_late_charge_panalty121.equals("")||!total_late_charge_panalty121.equals("0")||!total_late_charge_panalty121.equals("null")){
                convertView.setBackgroundColor(Color.parseColor("#FFD7ACAC"));
            }else{ convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));}

            // int photoId=(Integer) searchResults.get(position).get("photo");

            Glide.with(Recovery_EMP_Assign.this)
                    //.(R.drawable.ic_launcher)
                    .load(searchResults.get(position).get("photo").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder1.photo);

            //set the data to be displayed
            // viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
            viewHolder1.name.setText(searchResults.get(position).get("name").toString()+"("+searchResults.get(position).get("team").toString()+")");
            final String lonper = searchResults.get(position).get("team").toString();
            String prop=searchResults.get(position).get("team").toString();
            viewHolder1.team.setText(prop);

            viewHolder1.sNo.setText(searchResults.get(position).get("no").toString());
            viewHolder1.city.setText(searchResults.get(position).get("city").toString());
            viewHolder1.price1.setText(searchResults.get(position).get("product_name").toString());
            viewHolder1.price2.setText(searchResults.get(position).get("emi").toString());
            viewHolder1.price3.setText(searchResults.get(position).get("ROI_policy").toString());

            viewHolder1.ROI_Applied.setText(searchResults.get(position).get("ROI_Applied").toString());
            viewHolder1.LTV_Policy.setText(searchResults.get(position).get("LTV_Policy").toString());
            viewHolder1.LTV_Applied.setText(searchResults.get(position).get("LTV_Applied").toString());

            viewHolder1.price4.setText(searchResults.get(position).get("remarks").toString());
            viewHolder1.installment_date.setText(searchResults.get(position).get("installment_date").toString());
            viewHolder1.Ename.setText(searchResults.get(position).get("Ename").toString()+":"+searchResults.get(position).get("emp_id").toString()+"");



            final ViewHolder1 finalHolder = viewHolder1;
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String Product = finalHolder.name.getText().toString().trim();
                    String Team = lonper;
                    lonper11=searchResults.get(position).get("team").toString();
                    lonper12= searchResults.get(position).get("name").toString();
                    lonper13=searchResults.get(position).get("photo").toString();
                    intervaltext=searchResults.get(position).get("interval").toString();
                    installment_date2=searchResults.get(position).get("installment_date").toString();
                    emiPay=searchResults.get(position).get("emi").toString();
                    total_late_charge_panalty121=searchResults.get(position).get("total_late_charge_panalty").toString();


                  /*
                    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                viewHolder1.rb1.setChecked(viewHolder1.rb1 == buttonView);
                            }

                        }

                    };


            viewHolder1.rb1.setChecked(true);
            if (viewHolder1.rb1.isChecked()) {
                viewHolder1.rb1.setChecked(true);
            } else {
                viewHolder1.rb1.setChecked(false);
            }


                    viewHolder1.rb1.setOnCheckedChangeListener(listener);
*/
                    //Viewuser(lonper);
                    //showAppCasece(lonper);
                    //Viewuser();
                    // Toast.makeText(finalConvertView.getContext(), ""+Team, Toast.LENGTH_SHORT).show();
                    //MainActivityD mainD = null;
                    //mainD.privecyTerms();

//if(Product.toString().trim()=="Mobile"){}
                    //(MainActivityD).mobile();
                    //FragmentChild.doSubProduct(Product);
                    // subProduct(Product,Team);
                    if(total_late_charge_panalty121.equals("0")){

                        Allot();
}else{
                        Toast.makeText(finalConvertView.getContext(), "Assigned Already", Toast.LENGTH_SHORT).show();
}

                }
            });


            //return the view to be displayed
            return convertView;
        }

    }


}
