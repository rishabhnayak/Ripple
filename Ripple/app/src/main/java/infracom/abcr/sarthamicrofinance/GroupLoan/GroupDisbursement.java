package infracom.abcr.sarthamicrofinance.GroupLoan;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import infracom.abcr.sarthamicrofinance.Config;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Dpost.ImageLoader;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.Main;
import infracom.abcr.sarthamicrofinance.Profile.Admin_Launch;
import infracom.abcr.sarthamicrofinance.Profile.Manager_Approve;
import infracom.abcr.sarthamicrofinance.Profile.Manager_Launch;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.material.app.BottomSheetDialog;
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


public class GroupDisbursement extends AppCompatActivity {

    TextView Bname,Bcode,Sname,Damount,Ddate,Nname,Naddress,Ncontact,NIDIMAGE,NSIGNIMAGE,NTHumg,
            Branch_Name,Branch_code,Customer_Sapouse_name,Purpose;

    RadioButton RP1,RP2,UB1,UB2,UB3,UB4,RG1,RG2,RG3,RG4,RG5,CH1,CH2;
    String TRP1,TRP2,TUB1,TUB2,TUB3,TUB4,TRG1,TRG2,TRG3,TRG4,TRG5,TCH1,TCH2,only_save;

    private int year, month, day;

    ImageView imageView;
    Button buttonCamera, buttonGallery;
    File file;
    Uri uri;
    Intent CamIntent, GalIntent, CropIntent;
    public static final int RequestPermissionCode = 1;
    DisplayMetrics displayMetrics;
    int width, height;

    RadioButton SB;
    RadioButton CA;
    RadioButton CC;

    String SB1,CA1,CC1;

    TableLayout tbl=null;
    ImageLoader imageLoader;

    private Manager_Approve mActivity;

    String status,freqency;
    ProgressDialog dialog;
    public String user_id, password, email, name, lastname, mobile, fullname,sts;
    private Uri mImageCaptureUri, mImageCaptureUriTo;
    ImageView Cadd, CaddV, CaddA, CaddD,CCCCC,DDDD, NNNN,SSSS, TTTT;

    private  String Sales_Profile_Query_Send_Link="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/Sales_Quary_Profile.php";

    private  String Manager_Profile_Query_Send_Link="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/Manager_Quary_Profile.php";
    private  String Manager_Finance_Query_Send_Link="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/Manager_Quary_Finance.php";
    private  String Admin_Finance_Query_Send_Link="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/Admin_Quary_Finance.php";


    private static Bitmap bm;
    static Bitmap bitmap;
    private String Customer_uSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Group_customer_details.php";


    private String Customer_uSERVER_URL_one = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/CPhoto_UploadNew.php";

    private  String ApproveSend_Link="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/ManagerApprove.php";

    private  String ApproveSend_Link2="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/ManagerDecline.php";


    private  String SApproveSend_Link="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/SalesApprove.php";

    private  String SApproveSend_Link2="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/SalesDecline.php";

    private  String FINANCeApproveSend_Link="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/Finance_ManagerApprove.php";

    private  String FINANCeApproveSend_Link2="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/Finance_ManagerDecline.php";

    private  String Admin_AApproveSend_Link="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/Admin_Approve.php";

    private  String Admin_AApproveSend_Link2="https://globecorpmf.com/admin/gcm_Device_to/gcm_server_files/Admin_Decline.php";

    ArrayList<HashMap<String, Object>> searchResults;

    //ArrayList that will hold the original Data
    ArrayList<HashMap<String, Object>> originalValues;
    LayoutInflater inflater;
    //String[] names;
    //String names[]={"Ronaldo","Messi","Torres","Iniesta", "Drogba","Gerrard","Rooney","Xavi"};

    ArrayList<String> listsubR = new ArrayList<String>();

    ArrayList<String> listsub1 = new ArrayList<String>();
    ArrayList<String> listsub2 = new ArrayList<String>();
    ArrayList<String> listsub3 = new ArrayList<String>();
    ArrayList<String> icon = new ArrayList<String>();


    ListView mylistview1;
    LinearLayout list,productDetail;
    String[] member_names1;
    String product,SelectCat,ProPer;
    TextView proName;

    EditText inputSearch;

    final List<EditText> allEds = new ArrayList<EditText>();
    final List<TextView> allEds1 = new ArrayList<TextView>();

    public EditText et;
    public String[] strings,strings2;
    TableRow tr;


    private String Disbus_uSERVER_URL  = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/loan_success_new.php";

    private String SERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/imageUp.php";


    private  String SDisbus_Link="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/GroupDisbursement.php";


    private  String Approve_Summ_LongOperation_url="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Approve_Summ_LongOperationGroup.php";


    boolean GallaryPhotoSelected = false;
    TextView Titletxt, DestxtLastname;

    Controller aController = null;

    static String img1,img2,img3,img4, Name,Fname,tunMobile, Address,City,Zip,Xiemail,XPan="",lat,lon,img5,img6,img7,img8,img9;

    private ProgressDialog pDialog;
    String mypath;
    String cc;
    int Ckeck=0;
    public static String Finalmedia = "",notPayedEM;

    private static final String TAG = Manager_Approve.class.getSimpleName();

    private String empname,regid;
    Bitmap photo;
    String MSG;
    //private static final int PICK_FROM_CAMERA = 1;
    //private static final int CROP_FROM_CAMERA = 2;
    //private static final int PICK_FROM_FILE = 3;

    Uri selectedImageUri = null;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    Uri imageUri;
    LinearLayout appsts, cheqeLeni;
    TextView tv2;

    private BottomSheetDialog mBottomSheetDialog;

    TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal,view8,LTVP,ROIP;
    EditText MsgeditText,chequedate;
    DBAdapter db;

    static  String cid,cname;

    public static final String CROPPED_IMAGE_FILEPATH = "/sdcard/GlobeCorpMicroFinance.jpg";

    CheckBox Application_Form_Filled,Picture_of_customer_with_product_with_GPS_location,Picture_of_original_invoice,Confirmation_physical_copy,Confirmation_on_docket,Copy_Saction_obtained,Invoice_Obtained, Address_Proof,PAN_Copy,Aadhar_Copy;
    String c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
    GPSTracker gps;
    double latitude;
    double longitude;
    String under_depart;
    String deviceIMEI = "";
    ProgressView prog;
    public int loopint;
    String bankName,BankBranchName,AccountNum,IFSCC;

    FloatingActionButton NNNN1, SSSS1, TTTT1;
    private Calendar calendar, calendar1;






    File sourceFile;
    int totalSize = 0;
    String FILE_UPLOAD_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/UploadToServer.php";
    String Customer_uSERVER_URL_oneGG = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/UploadToServerDataGG.php";
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
    private String selectedImagePath = "", imagepath="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_disbursement);

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

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        mylistview1 = (ListView)findViewById(R.id.appp);

        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //EnableRuntimePermission();

        BlurBehind.getInstance()
                .withAlpha(200)
                .withFilterColor(Color.parseColor("#FFFFFF"))
                .setBackground(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        appsts = (LinearLayout)findViewById(R.id.appstskii);
        cheqeLeni = (LinearLayout)findViewById(R.id.cheqeLeni);



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
            aController.showAlertDialog(GroupDisbursement.this,
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
            aController.showAlertDialog(GroupDisbursement.this,
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

        under_depart = dataf.get("under_depart");
        //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
        db.close();


        Branch_Name = (TextView)findViewById(R.id.Branch_Name);
        Branch_code = (TextView)findViewById(R.id.Branch_code);
        Customer_Sapouse_name = (TextView)findViewById(R.id.Customer_Sapouse_name);
        Damount = (TextView)findViewById(R.id.Damount);
        Ddate = (TextView)findViewById(R.id.Ddate);
        Nname = (TextView)findViewById(R.id.Nname);
        Naddress = (TextView)findViewById(R.id.Naddress);
        Ncontact = (TextView)findViewById(R.id.Ncontact);
        Purpose =(TextView)findViewById(R.id.Purpose);

        CH1 = (RadioButton)findViewById(R.id.CH1);
        CH2 = (RadioButton)findViewById(R.id.CH2);

        TCH1="Cheque";
        CompoundButton.OnCheckedChangeListener listener2RC = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    CH1.setChecked(CH1 == buttonView);
                    CH2.setChecked(CH2 == buttonView);
                }
                if(CH1.isChecked()){//checkTun="Month";
                    TCH1="Cheque";
                }
                if(CH2.isChecked()){// checkTun="Fortnight";
                    TCH1="Cash";
                }

            }
        };
        CH1.setOnCheckedChangeListener(listener2RC);
        CH2.setOnCheckedChangeListener(listener2RC);



        RP1 = (RadioButton)findViewById(R.id.RP1);
        RP2 = (RadioButton)findViewById(R.id.RP2);


        TRP1="No";
        CompoundButton.OnCheckedChangeListener listener2R = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    RP1.setChecked(RP1 == buttonView);
                    RP2.setChecked(RP2 == buttonView);
                }
                if(RP1.isChecked()){//checkTun="Month";
                    TRP1="Yes";
                }
                if(RP2.isChecked()){// checkTun="Fortnight";
                    TRP1="No";
                }

            }
        };
        RP1.setOnCheckedChangeListener(listener2R);
        RP2.setOnCheckedChangeListener(listener2R);


        RG1 = (RadioButton)findViewById(R.id.RG1);
        RG2 = (RadioButton)findViewById(R.id.RG2);
        RG3 = (RadioButton)findViewById(R.id.RG3);
        RG4 = (RadioButton)findViewById(R.id.RG4);
        RG5 = (RadioButton)findViewById(R.id.RG5);


        TRG1="H";
        CompoundButton.OnCheckedChangeListener listenerU = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    RG1.setChecked(RG1 == buttonView);
                    RG2.setChecked(RG2 == buttonView);
                    RG3.setChecked(RG3 == buttonView);
                    RG4.setChecked(RG4 == buttonView);
                    RG5.setChecked(RG5 == buttonView);
                }
                if(RG1.isChecked()){//checkTun="Month";
                    TRG1="H";
                }
                if(RG2.isChecked()){// checkTun="Fortnight";
                    TRG1="M";
                }
                if(RG3.isChecked()){//checkTun="Month";
                    TRG1="S";
                }
                if(RG4.isChecked()){// checkTun="Fortnight";
                    TRG1="C";
                }
                if(RG5.isChecked()){// checkTun="Fortnight";
                    TRG1="B";
                }

            }
        };


        RG1.setOnCheckedChangeListener(listenerU);
        RG2.setOnCheckedChangeListener(listenerU);
        RG3.setOnCheckedChangeListener(listenerU);
        RG4.setOnCheckedChangeListener(listenerU);
        RG5.setOnCheckedChangeListener(listenerU);


        UB1 = (RadioButton)findViewById(R.id.UB1);
        UB2 = (RadioButton)findViewById(R.id.UB2);
        UB3 = (RadioButton)findViewById(R.id.UB3);
        UB4 = (RadioButton)findViewById(R.id.UB4);


        TUB1="Urban";
        CompoundButton.OnCheckedChangeListener listenerUB = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UB1.setChecked(UB1 == buttonView);
                    UB2.setChecked(UB2 == buttonView);
                    UB3.setChecked(UB3 == buttonView);
                    UB4.setChecked(UB4 == buttonView);
                }
                if(UB1.isChecked()){//checkTun="Month";
                    TUB1="Urban";
                }
                if(UB2.isChecked()){// checkTun="Fortnight";
                    TUB1="Semi Urban";
                }
                if(UB3.isChecked()){//checkTun="Month";
                    TUB1="Rural";
                }
                if(UB4.isChecked()){// checkTun="Fortnight";
                    TUB1="Village";
                }

            }
        };
        UB1.setOnCheckedChangeListener(listenerUB);
        UB2.setOnCheckedChangeListener(listenerUB);
        UB4.setOnCheckedChangeListener(listenerUB);
        UB3.setOnCheckedChangeListener(listenerUB);



        SB = (RadioButton)findViewById(R.id.SB);
        CA = (RadioButton)findViewById(R.id.CA);
        CC = (RadioButton)findViewById(R.id.CC);


        SB1="SB";
        CompoundButton.OnCheckedChangeListener listener2 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SB.setChecked(SB == buttonView);
                    CA.setChecked(CA == buttonView);
                    CC.setChecked(CC == buttonView);
                }
                if(SB.isChecked()){//checkTun="Month";
                    SB1="SB";
                }
                if(CA.isChecked()){// checkTun="Fortnight";
                    SB1="CA";
                }if(CC.isChecked()){//checkTun="Month";
                    SB1="CC";
                }

            }
        };
        SB.setOnCheckedChangeListener(listener2);
        CA.setOnCheckedChangeListener(listener2);
        CC.setOnCheckedChangeListener(listener2);


        final Button submit=(Button) findViewById(R.id.submit);

        Button Dsubmit=(Button) findViewById(R.id.Decline);

        Button buttonRE_Save=(Button) findViewById(R.id.buttonRE_Save);


        buttonRE_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                only_save="1";

                Submit();
            }
        });
        // Button Query1=(Button) findViewById(R.id.Query);


        FloatingActionButton buttonP 	= (FloatingActionButton) findViewById(R.id.imageButtonP);
        FloatingActionButton buttonD 	= (FloatingActionButton) findViewById(R.id.imageButtonD);
        FloatingActionButton buttonA 	= (FloatingActionButton) findViewById(R.id.imageButtonA);
        FloatingActionButton buttonV 	= (FloatingActionButton) findViewById(R.id.imageButtonV);

        FloatingActionButton imageButtonDISBUS 	= (FloatingActionButton) findViewById(R.id.imageButtonDISBUS);

        Application_Form_Filled=(CheckBox)findViewById(R.id.Application_Form_Filled);
        Picture_of_customer_with_product_with_GPS_location=(CheckBox)findViewById(R.id.Picture_of_customer_with_product_with_GPS_location);
        Picture_of_original_invoice=(CheckBox)findViewById(R.id.Picture_of_original_invoice);
        Confirmation_physical_copy=(CheckBox)findViewById(R.id.Confirmation_physical_copy);
        Confirmation_on_docket=(CheckBox)findViewById(R.id.Confirmation_on_docket);
        Copy_Saction_obtained=(CheckBox)findViewById(R.id.Copy_Saction_obtained);
        Invoice_Obtained=(CheckBox)findViewById(R.id.Invoice_Obtained);
        Address_Proof=(CheckBox)findViewById(R.id.Address_Proof);
        PAN_Copy=(CheckBox)findViewById(R.id.PAN_Copy);
        Aadhar_Copy=(CheckBox)findViewById(R.id.Aadhar_Copy);

        FloatingActionButton CCCCC1 	= (FloatingActionButton) findViewById(R.id.imageButtonDISBUS);
        FloatingActionButton DDDD1 	= (FloatingActionButton) findViewById(R.id.imageButtonB);


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

        if(sts.equals("SANCTION")){

            // Application_Form_Filled,
            // Picture_of_customer_with_product_with_GPS_location,
            // Picture_of_original_invoice,
            // Confirmation_physical_copy,
            // Confirmation_on_docket,
            // Copy_Saction_obtained,
            // Invoice_Obtained,
            // Address_Proof,
            // PAN_Copy,
            //Aadhar_Copy


            //DDDD 	= (ImageView) findViewById(R.id.backokLLLBB);

            Application_Form_Filled.setEnabled(false);
            Picture_of_customer_with_product_with_GPS_location.setEnabled(false);
            Picture_of_original_invoice.setEnabled(false);
            Confirmation_physical_copy.setEnabled(false);
            Confirmation_on_docket.setEnabled(false);
            Copy_Saction_obtained.setEnabled(false);
            Invoice_Obtained.setEnabled(false);
            Address_Proof.setEnabled(false);
            PAN_Copy.setEnabled(false);
            Aadhar_Copy.setEnabled(false);

            DDDD1.setVisibility(View.GONE);
            CCCCC1.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            Dsubmit.setVisibility(View.GONE);


            // Query1.setVisibility(View.GONE);
            cheqeLeni.setVisibility(View.GONE);
        }

        if(under_depart.equals("dsa Person")){
            // Query1.setVisibility(View.GONE);
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



        new CustomerLongOperation().execute(Customer_uSERVER_URL,cid,cname,deviceIMEI);

        /*

*/



        gps = new GPSTracker(GroupDisbursement.this);

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
        */

        //FloatingActionButton DDDD1 	= (FloatingActionButton) findViewById(R.id.imageButtonB);
        inputSearch = (EditText)findViewById(R.id.inputSearch);

        listsubR.clear();
        listsub1.clear();
        listsub2.clear();
        listsub3.clear();
        icon.clear();


        new Approve_Summ_LongOperation().execute(Approve_Summ_LongOperation_url,cid,"","");


        CCCCC 	= (ImageView) findViewById(R.id.imageView18);
        // ImageButton buttonV 	= (ImageButton) findViewById(R.id.imageButtonV);
        imageButtonDISBUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=5;

                // startPickImage();
//                startCropImage();

                com.theartofdev.edmodo.cropper.CropImage.activity(null)
                        .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                        .start(GroupDisbursement.this);
            }
        });

        DDDD 	= (ImageView) findViewById(R.id.backokLLLBB);
        // ImageButton buttonV 	= (ImageButton) findViewById(R.id.imageButtonV);
        DDDD1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=6;

                // startPickImage();
//                startCropImage();
                com.theartofdev.edmodo.cropper.CropImage.activity(null)
                        .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                        .start(GroupDisbursement.this);

            }
        });

        NNNN 	= (ImageView) findViewById(R.id.imageViewNID);
        SSSS 	= (ImageView) findViewById(R.id.imageViewNSI);
        TTTT 	= (ImageView) findViewById(R.id.imageViewNTH);

        NNNN1 	= (FloatingActionButton) findViewById(R.id.imageButtonNN);
        SSSS1 	= (FloatingActionButton) findViewById(R.id.imageButtonNSC);
        TTTT1 	= (FloatingActionButton) findViewById(R.id.imageButtonNTHC);

        NNNN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=7;

                // startPickImage();
//                startCropImage();
                com.theartofdev.edmodo.cropper.CropImage.activity(null)
                        .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                        .start(GroupDisbursement.this);

            }
        });
        SSSS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=8;

                // startPickImage();
//                startCropImage();
                com.theartofdev.edmodo.cropper.CropImage.activity(null)
                        .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                        .start(GroupDisbursement.this);

            }
        });
        TTTT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=9;

                // startPickImage();
//                startCropImage();
                com.theartofdev.edmodo.cropper.CropImage.activity(null)
                        .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                        .start(GroupDisbursement.this);

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            com.theartofdev.edmodo.cropper.CropImage.ActivityResult result = com.theartofdev.edmodo.cropper.CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //((ImageView) findViewById(R.id.backdrop)).setImageURI(result.getUri());
                //((ImageView) findViewById(R.id.imageShow1l)).setImageURI(result.getUri());

                Bitmap  mBitmap=null;
                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // InputStream in = getContentResolver().openInputStream(croppedUri);


                ByteArrayOutputStream bos =new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                byte[] bb = bos.toByteArray();

                // InputStream in = getContentResolver().openInputStream(croppedUri);
                SaveImage(mBitmap);

                // img1 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                // String photo1="image";
                // if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                // }else {
                //  new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_one, photo1, deviceIMEI, img1);


                File file1 = new File(imagepath);
                long length = file1.length() / 1024;

                if(length >= 3072){
                    Toast.makeText(getApplicationContext(), "File Size is should be less then 2MB..", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    //  new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneAA, "image", deviceIMEI, fname);


                    //  Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
                    if (Ckeck == 5) {
                        img5 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                        //CCCCC.setImageBitmap(bitmap);
                        ((ImageView) findViewById(R.id.imageView18)).setImageURI(result.getUri());

                        String photo11 = "Disbus_image";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo11, cid, cname, deviceIMEI, fname);

                    }
                    if (Ckeck == 6) {
                        img6 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                        // DDDD.setImageBitmap(bitmap);
                        ((ImageView) findViewById(R.id.backokLLLBB)).setImageURI(result.getUri());

                        String photo11 = "invoice_image";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo11, cid, cname, deviceIMEI, fname);

                    }
                    if (Ckeck == 7) {
                        img7 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                        // DDDD.setImageBitmap(bitmap);
                        ((ImageView) findViewById(R.id.imageViewNID)).setImageURI(result.getUri());

                        String photo11 = "nominee_image";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo11, cid, cname, deviceIMEI, fname);

                    }
                    if (Ckeck == 8) {
                        img8 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                        // DDDD.setImageBitmap(bitmap);
                        ((ImageView) findViewById(R.id.imageViewNSI)).setImageURI(result.getUri());

                        String photo11 = "nominee_sign";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo11, cid, cname, deviceIMEI, fname);

                    }
                    if (Ckeck == 9) {
                        img9 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                        // DDDD.setImageBitmap(bitmap);
                        ((ImageView) findViewById(R.id.imageViewNTH)).setImageURI(result.getUri());

                        String photo11 = "nominee_sign";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo11, cid, cname, deviceIMEI, fname);

                    }
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }








    public void DialogShow(){

        final android.app.Dialog dialog = new android.app.Dialog(GroupDisbursement.this);
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
                connection = (HttpURLConnection) new URL(FILE_UPLOAD_URL).openConnection();
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

                if (item == 0) {
                    ClickImageFromCamera();

                }else{
                    GetImageFromGallery();

                }

            }

        });

        final AlertDialog dialog = builder.create();

        dialog.show();

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
        if(under_depart.equals("DSA Person")) {
            view8=(TextView)v.findViewById(R.id.view8);
            view8.setText("Send Message for Disbursement");
        } if(under_depart.equals("Manager")) {
            view8=(TextView)v.findViewById(R.id.view8);
            view8.setText("Globe Corp Micro Finance Manager Approval/Decline");
        } if(under_depart.equals("Admin")) {
            view8=(TextView)v.findViewById(R.id.view8);
            view8.setText("Globe Corp Micro Finance Admin Approval/Decline");
        }


        MsgeditText =(EditText)v.findViewById(R.id.MsgeditText);

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


                if(appro.equals("OK")){


                    if(under_depart.equals("DSA Person")){


                        Submit();

                    }if(under_depart.equals("Manager")){
                        if(sts.equals("PENDING")){
                            new ManagerApprove().execute(ApproveSend_Link,cid,cname,user_id,empname,regid,MSG,lat,lon);
                        }if(sts.equals("DONE")){
                            new ManagerApprove().execute(FINANCeApproveSend_Link,cid,cname,user_id,empname,regid,MSG,lat,lon);
                        }
                    }if(under_depart.equals("Admin")){
                        new ManagerApprove().execute(Admin_AApproveSend_Link,cid,cname,user_id,empname,regid,MSG,lat,lon);
                    }


                    Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_SHORT).show();
                }if(appro.equals("NO")){

                    if(under_depart.equals("DSA Person")){
                        new ManagerApprove().execute(SApproveSend_Link2,cid,cname,deviceIMEI,empname,regid,MSG,lat,lon);
                    }if(under_depart.equals("Manager")){

                        if(sts.equals("PENDING")){
                            new ManagerApprove().execute(ApproveSend_Link2,cid,cname,user_id,empname,regid,MSG,lat,lon);
                        }if(sts.equals("DONE")){
                            new ManagerApprove().execute(FINANCeApproveSend_Link2,cid,cname,user_id,empname,regid,MSG,lat,lon);
                        }
                        //  new ManagerApprove().execute(ApproveSend_Link2,cid,cname,user_id,empname,regid,MSG,lat,lon);
                    }if(under_depart.equals("Admin")){
                        new ManagerApprove().execute(Admin_AApproveSend_Link2,cid,cname,user_id,empname,regid,MSG,lat,lon);
                    }
                    Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_SHORT).show();
                    //
                }if(appro.equals("QUERY")){

                    if(under_depart.equals("Manager")){

                        if(sts.equals("PENDING")){
                            new ManagerApprove().execute(ApproveSend_Link2,cid,cname,user_id,empname,regid,MSG,lat,lon);
                        }if(sts.equals("DONE")){
                            new ManagerApprove().execute(FINANCeApproveSend_Link2,cid,cname,user_id,empname,regid,MSG,lat,lon);
                        }
                        //  new ManagerApprove().execute(ApproveSend_Link2,cid,cname,user_id,empname,regid,MSG,lat,lon);
                    }if(under_depart.equals("Admin")){
                        new ManagerApprove().execute(Admin_AApproveSend_Link2,cid,cname,user_id,empname,regid,MSG,lat,lon);
                    }
                    Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_SHORT).show();
                    //
                }

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
    */
    public  void Submit(){


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


        lat  = Double.toString(latitude);
        lon = Double.toString(longitude);



        EditText  BankName= (EditText) findViewById(R.id.BankName);
        EditText  BankBranchName1= (EditText) findViewById(R.id.BankBranchName);
        EditText  BankACC= (EditText) findViewById(R.id.BankACC);
        EditText  Bankifsc= (EditText) findViewById(R.id.Bankifsc);


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



        if(strings2.equals(null)){
            // Toast.makeText(GroupDisbursement.this, "Please Enter Cheque Number.", Toast.LENGTH_SHORT).show();return;
        }
        // Application_Form_Filled,
        // Picture_of_customer_with_product_with_GPS_location,
        // Picture_of_original_invoice,
        // Confirmation_physical_copy,
        // Confirmation_on_docket,
        // Copy_Saction_obtained,
        // Invoice_Obtained,
        // Address_Proof,
        // PAN_Copy,
        //Aadhar_Copy

        Application_Form_Filled=(CheckBox)findViewById(R.id.Application_Form_Filled);
        Picture_of_customer_with_product_with_GPS_location=(CheckBox)findViewById(R.id.Picture_of_customer_with_product_with_GPS_location);
        Picture_of_original_invoice=(CheckBox)findViewById(R.id.Picture_of_original_invoice);
        Confirmation_physical_copy=(CheckBox)findViewById(R.id.Confirmation_physical_copy);
        Confirmation_on_docket=(CheckBox)findViewById(R.id.Confirmation_on_docket);
        Copy_Saction_obtained=(CheckBox)findViewById(R.id.Copy_Saction_obtained);
        Invoice_Obtained=(CheckBox)findViewById(R.id.Invoice_Obtained);
        Address_Proof=(CheckBox)findViewById(R.id.Address_Proof);
        PAN_Copy=(CheckBox)findViewById(R.id.PAN_Copy);
        Aadhar_Copy=(CheckBox)findViewById(R.id.Aadhar_Copy);

        if(Application_Form_Filled.isChecked()){
            c1="Yes";
        }else{
            c1="No";
        }
        if(Picture_of_customer_with_product_with_GPS_location.isChecked()){
            c2="Yes";
        }else{
            c2="No";
        }
        if(Picture_of_original_invoice.isChecked()){
            c3="Yes";
        }else{
            c3="No";
        }
        if(Confirmation_physical_copy.isChecked()){
            c4="Yes";
        }else{
            c4="No";
        }
        if(Confirmation_on_docket.isChecked()){
            c5="Yes";
        }else{
            c5="No";
        }
        if(Copy_Saction_obtained.isChecked()){
            c6="Yes";
        }else{
            c6="No";
        }
        if(Invoice_Obtained.isChecked()){
            c7="Yes";
        }else{
            c7="No";
        }
        if(Address_Proof.isChecked()){
            c8="Yes";
        }else{
            c8="No";
        }
        if(PAN_Copy.isChecked()){
            c9="Yes";
        }else{
            c9="No";
        }
        if(Aadhar_Copy.isChecked()){
            c10="Yes";
        }else{
            c10="No";
        }

        final String  Branch_Name1 = Branch_Name.getText().toString();
        final String  Branch_code1 = Branch_code.getText().toString();
        final String  Customer_Sapouse_name1 = Customer_Sapouse_name.getText().toString();
        final String Purpose1= Purpose.getText().toString();

        final String  Damount1 = Damount.getText().toString();
        final String Ddate1 = Ddate.getText().toString();

        final String  Naddress1 = Naddress.getText().toString();

        final String Nname1= Nname.getText().toString();
        final String Ncontact1= Ncontact.getText().toString();

        if(Name.trim().length() > 2){
            if(Fname.trim().length() > 2 && !Fname.equals("null")){
                if(tunMobile.trim().length() > 9){
                    if(Address.trim().length() > 2 && !Address.equals("null")){
                        if(City.trim().length() > 3 && !City.equals("null")){
                            if(Zip.trim().length() > 5){
                                //addEmployee();

                                if(Xiemail.trim().length() > 3){
                                    //addEmployee();
                                    //   if(XPan.trim().length() > 3){
                                    //     if(bankName.trim().length() > 2){
                                    //       if(BankBranchName.trim().length() > 2){
                                    //         if(AccountNum.trim().length() > 6){
                                    //           if(IFSCC.trim().length() > 3){
                                    //             if(!Cadd.equals(null)){
                                    //               if(!chequedate.equals("")){
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
                                                    // if(sts=="DISBUS"){if(status=="DISBUS"){
                                                    // Toast.makeText(getBaseContext(), "Good!", Toast.LENGTH_SHORT).show();
                                                    // }
                                                    //   }if(status=="DONE"){
                                                    //    }else {

                                                    if(only_save.equals("2")) {

                                                        chequedate=(EditText)findViewById(R.id.chequedate);

                                                        String dateQ=chequedate.getText().toString().trim();
                                                        if(dateQ.equals("")){
                                                            Toast.makeText(GroupDisbursement.this, "Please Select Date For Disbursement.", Toast.LENGTH_SHORT).show();return;
                                                        }


                                                        DateFormat srcDf = new SimpleDateFormat("dd-mm-yyyy");
                                                        Date date = null;
                                                        try {
                                                            date = srcDf.parse(dateQ);
                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                        }
                                                        DateFormat destDf = new SimpleDateFormat("yyyy-mm-dd");
                                                        dateQ = destDf.format(date);
                                                        // Toast.makeText(this,""+dateQ, Toast.LENGTH_SHORT).show();



                                                        new LongOperation().execute(SERVER_URL, Name, Fname, tunMobile, Address, City, Zip, Xiemail, XPan, empname, regid, lat, lon, deviceIMEI, cid, bankName, BankBranchName, AccountNum, IFSCC, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, SB1,
                                                                Damount1,Ddate1,Nname1,Naddress1,Ncontact1,Branch_Name1,Branch_code1,Customer_Sapouse_name1,Purpose1,TRP1,TUB1,TRG1,TCH1);

                                                        //  }//Toast.makeText(getBaseContext(), "Please Make Sure Your Profile Details is Completed Correctly ", Toast.LENGTH_SHORT).show();
                                                        //addEmployee();
                                                        //onStartTransaction();
                                                        new DisbusLongOperation().execute(Disbus_uSERVER_URL, cid, cname, deviceIMEI, strings.toString(), strings2.toString());


                                                        new ManagerApprove().execute(SDisbus_Link, cid, cname, deviceIMEI, empname, regid, MSG, lat, lon);
                                                    }else{
                                                        new LongOperation().execute(SERVER_URL, Name, Fname, tunMobile, Address, City, Zip, Xiemail, XPan, empname, regid, lat, lon, deviceIMEI, cid, bankName, BankBranchName, AccountNum, IFSCC, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, SB1,
                                                                Damount1,Ddate1,Nname1,Naddress1,Ncontact1,Branch_Name1,Branch_code1,Customer_Sapouse_name1,Purpose1,TRP1,TUB1,TRG1,TCH1);

                                                    }
                                                }
                                            })

                                            .setNegativeButton("Cancel", null)						//Do nothing on no
                                            .show();

                                    //            }else{Toast.makeText(GroupDisbursement.this, "Please Select Date For Disbursement.", Toast.LENGTH_SHORT).show();return;}
                                    //          }else{Toast.makeText(GroupDisbursement.this, "Please Take Customer Photo", Toast.LENGTH_SHORT).show();return;}
                                    //        }else{Toast.makeText(GroupDisbursement.this, "Please Enter IFSC Code", Toast.LENGTH_SHORT).show();return;}
                                    //      }else{Toast.makeText(GroupDisbursement.this, "Please Enter Bank Account Number", Toast.LENGTH_SHORT).show();return;}
                                    //    }else{Toast.makeText(GroupDisbursement.this, "Please Enter Bank Branch Name", Toast.LENGTH_SHORT).show();return;}
                                    //  }else{Toast.makeText(GroupDisbursement.this, "Please Enter Bank Name", Toast.LENGTH_SHORT).show();return;}


                                    //}else{Toast.makeText(GroupDisbursement.this, "Please Enter Valid PAN Number", Toast.LENGTH_SHORT).show();return;}

                                }else{Toast.makeText(GroupDisbursement.this, "Please Enter Valid Email ID", Toast.LENGTH_SHORT).show();return;}

                            }else{Toast.makeText(GroupDisbursement.this, "Please Enter Valid Zip Code", Toast.LENGTH_SHORT).show();return;}

                        }else{Toast.makeText(GroupDisbursement.this, "Please Enter Valid City Name", Toast.LENGTH_SHORT).show();return;}

                    }else{Toast.makeText(GroupDisbursement.this, "Please Enter Valid Address", Toast.LENGTH_SHORT).show();return;}

                }else{Toast.makeText(GroupDisbursement.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();return;}

            }else{Toast.makeText(GroupDisbursement.this, "Please Enter Father's Name", Toast.LENGTH_SHORT).show();return;}

        }else{Toast.makeText(GroupDisbursement.this, "Please Enter Name", Toast.LENGTH_SHORT).show();return;}

    }


    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(GroupDisbursement.this);
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

                if(!params[19].equals(""))
                    data +="&" + URLEncoder.encode("Application_Form_Filled", "UTF-8") + "="+params[19].toString();
                if(!params[20].equals(""))
                    data +="&" + URLEncoder.encode("GPS_location", "UTF-8") + "="+params[20].toString();
                if(!params[21].equals(""))
                    data +="&" + URLEncoder.encode("Picture_of_original_invoice", "UTF-8") + "="+params[21].toString();
                if(!params[22].equals(""))
                    data +="&" + URLEncoder.encode("Confirmation_physical_copy", "UTF-8") + "="+params[22].toString();
                if(!params[23].equals(""))
                    data +="&" + URLEncoder.encode("Confirmation_on_docket", "UTF-8") + "="+params[23].toString();
                if(!params[24].equals(""))
                    data +="&" + URLEncoder.encode("Copy_Saction_obtained", "UTF-8") + "="+params[24].toString();
                if(!params[25].equals(""))
                    data +="&" + URLEncoder.encode("Invoice_Obtained", "UTF-8") + "="+params[25].toString();
                if(!params[26].equals(""))
                    data +="&" + URLEncoder.encode("Address_Proof", "UTF-8") + "="+params[26].toString();
                if(!params[27].equals(""))
                    data +="&" + URLEncoder.encode("PAN_Copy", "UTF-8") + "="+params[27].toString();
                if(!params[28].equals(""))
                    data +="&" + URLEncoder.encode("Aadhar_Copy", "UTF-8") + "="+params[28].toString();
                if(!params[29].equals(""))
                    data +="&" + URLEncoder.encode("ACC_type", "UTF-8") + "="+params[29].toString();

                //   Damount1,Ddate1,Nname1,Naddress1,Ncontact1,Branch_Name1,Branch_code1,Customer_Sapouse_name1,Purpose1,TRP1,TUB1,TRG1,TCH1

                if(!params[30].equals(""))
                    data +="&" + URLEncoder.encode("C_amount", "UTF-8") + "="+params[30].toString();
                if(!params[31].equals(""))
                    data +="&" + URLEncoder.encode("C_date", "UTF-8") + "="+params[31].toString();
                if(!params[32].equals(""))
                    data +="&" + URLEncoder.encode("nominee_name", "UTF-8") + "="+params[32].toString();
                if(!params[33].equals(""))
                    data +="&" + URLEncoder.encode("nominee_address", "UTF-8") + "="+params[33].toString();
                if(!params[34].equals(""))
                    data +="&" + URLEncoder.encode("nominee_contact", "UTF-8") + "="+params[34].toString();
                if(!params[35].equals(""))
                    data +="&" + URLEncoder.encode("branch_name", "UTF-8") + "="+params[35].toString();
                if(!params[36].equals(""))
                    data +="&" + URLEncoder.encode("branch_code", "UTF-8") + "="+params[36].toString();
                if(!params[37].equals(""))
                    data +="&" + URLEncoder.encode("spouse_name", "UTF-8") + "="+params[37].toString();
                if(!params[38].equals(""))
                    data +="&" + URLEncoder.encode("obf_purpose3", "UTF-8") + "="+params[38].toString();
                if(!params[39].equals(""))
                    data +="&" + URLEncoder.encode("repeat_customer", "UTF-8") + "="+params[39].toString();
                if(!params[40].equals(""))
                    data +="&" + URLEncoder.encode("area", "UTF-8") + "="+params[40].toString();
                if(!params[41].equals(""))
                    data +="&" + URLEncoder.encode("religion", "UTF-8") + "="+params[41].toString();
                if(!params[42].equals(""))
                    data +="&" + URLEncoder.encode("cheque_type", "UTF-8") + "="+params[42].toString();

                // Application_Form_Filled,
                // GPS_location,
                // Picture_of_original_invoice,
                // Confirmation_physical_copy,
                // Confirmation_on_docket,
                // Copy_Saction_obtained,
                // Invoice_Obtained,
                // Address_Proof,
                // PAN_Copy,
                //Aadhar_Copy,

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

                        Toast.makeText(getApplicationContext(), "Saved Successfully!", Toast.LENGTH_LONG).show();
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
                            BlurBehind.getInstance().execute(GroupDisbursement.this, new OnBlurCompleteListener() {
                                @Override
                                public void onBlurComplete() {
                                    Intent intent = new Intent(GroupDisbursement.this, Launch.class);
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

    public void ditactExis(String mypath1) {

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
            File file = new File(mypath);

            //Path fileToDeletePath = Paths.get("fileToDelete_jdk7.txt");
            //Files.delete(fileToDeletePath);

            if (file.exists()){
                Log.i("TAG","File Exist   "+mypath);
                file.delete();
            }
            // photo = decodeFile(mypath);
           // Cadd.setImageBitmap(photo);

        } catch (Exception e) {

        }
    }

  */


    public void Submit1(View view){

        chequedate=(EditText)findViewById(R.id.chequedate);

        String dateQ=chequedate.getText().toString().trim();
        if(dateQ.equals("")){
            Toast.makeText(GroupDisbursement.this, "Please Select Date For Disbursement!", Toast.LENGTH_SHORT).show();return;
        }

        String stss= et.getText().toString();
        if(stss.equals("")){
            //  Toast.makeText(GroupDisbursement.this, "Please Enter all Cheque Number.", Toast.LENGTH_SHORT).show();return;
        }
        strings = new String[allEds.size()];
        for(int i2=0; i2 < allEds.size(); i2++){
            strings[i2] = allEds.get(i2).getText().toString();

        }

        strings2 = new String[allEds1.size()];
        for(int i3=0; i3 < allEds1.size(); i3++) {
            strings2[i3] = allEds1.get(i3).getText().toString();
        }


        String appro="OK";
        showBottomSheet(appro);

        only_save="2";
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
        private ProgressDialog Dialog = new ProgressDialog(GroupDisbursement.this);
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

                            // Toast.makeText(getApplicationContext(), "Fetch!  "+status, Toast.LENGTH_LONG).show();


                            //TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal;

                            freqency       = jsonChildNode.optString("freqency").toString();
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

                            String Application_Form_Filled1      = jsonChildNode.optString("Application_Form_Filled").toString();
                            String Picture_of_customer_with_product_with_GPS_location1     = jsonChildNode.optString("Picture_of_customer_with_product_with_GPS_location").toString();
                            String Picture_of_original_invoice1    = jsonChildNode.optString("Picture_of_original_invoice").toString();
                            String Confirmation_physical_copy1     = jsonChildNode.optString("Confirmation_physical_copy").toString();
                            String Confirmation_on_docket1    = jsonChildNode.optString("Confirmation_on_docket").toString();
                            String Copy_Saction_obtained1     = jsonChildNode.optString("Copy_Saction_obtained").toString();
                            String Invoice_Obtained1     = jsonChildNode.optString("Invoice_Obtained").toString();
                            String Address_Proof1     = jsonChildNode.optString("Address_Proof").toString();
                            String PAN_Copy1      = jsonChildNode.optString("PAN_Copy").toString();
                            String Aadhar_Copy1     = jsonChildNode.optString("Aadhar_Copy").toString();
                            String invoice_image1     = jsonChildNode.optString("invoice_image").toString();
                            String ACC_type1  = jsonChildNode.optString("ACC_type").toString();


                            String C_amount     = jsonChildNode.optString("C_amount").toString();
                            String C_date     = jsonChildNode.optString("C_date").toString();
                            String nominee_name      = jsonChildNode.optString("nominee_name").toString();
                            String nominee_address     = jsonChildNode.optString("nominee_address").toString();
                            String nominee_contact     = jsonChildNode.optString("nominee_contact").toString();
                            String branch_name  = jsonChildNode.optString("branch_name").toString();
                            String branch_code     = jsonChildNode.optString("branch_code").toString();
                            String spouse_name     = jsonChildNode.optString("spouse_name").toString();
                            String obf_purpose3      = jsonChildNode.optString("obf_purpose3").toString();
                            String repeat_customer     = jsonChildNode.optString("repeat_customer").toString();
                            String area     = jsonChildNode.optString("area").toString();
                            String religion  = jsonChildNode.optString("religion").toString();
                            String cheque_type  = jsonChildNode.optString("cheque_type").toString();

                            String nominee_image     = jsonChildNode.optString("nominee_image").toString();
                            String nominee_sign  = jsonChildNode.optString("nominee_sign").toString();
                            String nominee_thumb  = jsonChildNode.optString("nominee_thumb").toString();


                            Branch_Name.setText(branch_name);
                            Branch_code.setText(branch_code);
                            Customer_Sapouse_name.setText(spouse_name);
                            Damount.setText(C_amount);
                            Ddate.setText(C_date);
                            Nname.setText(nominee_name);
                            Naddress.setText(nominee_address);
                            Ncontact.setText(nominee_contact);
                            Purpose.setText(obf_purpose3);

                            switch (cheque_type) {
                                case "Cheque":
                                    CH1.setChecked(true);
                                    break;
                                case "Cash":
                                    CH2.setChecked(true);
                                    break;
                                default:
                                    break;
                            }

                            switch (repeat_customer) {
                                case "Yes":
                                    RP1.setChecked(true);
                                    break;
                                case "No":
                                    RP2.setChecked(true);
                                    break;
                                default:
                                    break;
                            }

                            switch (area) {
                                case "Yes":
                                    UB1.setChecked(true);
                                    break;
                                case "Semi Urban":
                                    UB2.setChecked(true);
                                    break;
                                case "Rural":
                                    UB3.setChecked(true);
                                    break;
                                case "Village":
                                    UB4.setChecked(true);
                                    break;
                                default:
                                    break;
                            }

                            switch (religion) {
                                case "H":
                                    RG1.setChecked(true);
                                    break;
                                case "M":
                                    RG2.setChecked(true);
                                    break;
                                case "S":
                                    RG3.setChecked(true);
                                    break;
                                case "C":
                                    RG4.setChecked(true);
                                    break;
                                case "B":
                                    RG5.setChecked(true);
                                    break;
                                default:
                                    break;
                            }


                            switch (ACC_type1) {
                                case "SB":
                                    SB.setChecked(true);
                                    break;
                                case "CA":
                                    CA.setChecked(true);
                                    break;
                                case "CC":
                                    CC.setChecked(true);
                                    break;
                                default:
                                    break;
                            }

                            if(Application_Form_Filled1.equals("Yes")){
                                Application_Form_Filled.setChecked(true);
                            }
                            if(Picture_of_customer_with_product_with_GPS_location1.equals("Yes")){
                                Picture_of_customer_with_product_with_GPS_location.setChecked(true);
                            }
                            if(Picture_of_original_invoice1.equals("Yes")){
                                Picture_of_original_invoice.setChecked(true);
                            }
                            if(Copy_Saction_obtained1.equals("Yes")){
                                Copy_Saction_obtained.setChecked(true);
                            }
                            if(Confirmation_physical_copy1.equals("Yes")){
                                Confirmation_physical_copy.setChecked(true);
                            }
                            if(Confirmation_on_docket1.equals("Yes")){
                                Confirmation_on_docket.setChecked(true);
                            }
                            if(Invoice_Obtained1.equals("Yes")){
                                Invoice_Obtained.setChecked(true);
                            }
                            if(Address_Proof1.equals("Yes")){
                                Address_Proof.setChecked(true);
                            }
                            if(PAN_Copy1.equals("Yes")){
                                PAN_Copy.setChecked(true);
                            }
                            if(Aadhar_Copy1.equals("Yes")){
                                Aadhar_Copy.setChecked(true);
                            }

                            EditText  emiamount= (EditText) findViewById(R.id.emiamount);

                            emiamount.setText(emiPP);




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
                            textViewOrder.setText("Customer Disbursement Details.");

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


                            ImagePopup.enablePopUpOnClick(GroupDisbursement.this, Cadd);
                            ImagePopup.enablePopUpOnClick(GroupDisbursement.this, CaddA);
                            ImagePopup.enablePopUpOnClick(GroupDisbursement.this, CaddD);
                            ImagePopup.enablePopUpOnClick(GroupDisbursement.this, CaddV);
                            ImagePopup.enablePopUpOnClick(GroupDisbursement.this, CCCCC);
                            ImagePopup.enablePopUpOnClick(GroupDisbursement.this, DDDD);
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
                            //imageLoader.DisplayImage(photo, Cadd);
                            //imageLoader.DisplayImage(aadhar_card, CaddA);
                            //imageLoader.DisplayImage(driving_licence, CaddD);
                            //imageLoader.DisplayImage(voter_id, CaddV);
                            //imageLoader.DisplayImage(Disbus_image, CCCCC);

                            new DownloadImageFromInternet(Cadd).execute(photo.replace("http","https"));
                            new DownloadImageFromInternet(CaddA).execute(aadhar_card.replace("http","https"));
                            new DownloadImageFromInternet(CaddD).execute(driving_licence.replace("http","https"));
                            new DownloadImageFromInternet(CaddV).execute(voter_id.replace("http","https"));
                            new DownloadImageFromInternet(CCCCC).execute(Disbus_image.replace("http","https"));
                            new DownloadImageFromInternet(DDDD).execute(invoice_image1.replace("http","https"));



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
        private ProgressDialog Dialog = new ProgressDialog(GroupDisbursement.this);
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
        private ProgressDialog Dialog = new ProgressDialog(GroupDisbursement.this);
        String data ="";
        int sizeData = 0;
        protected void onPreExecute() {


            // Dialog.setMessage("Sending...");
            //Dialog.show();
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

                            if(under_depart.equals("dsa Person")){
                                // intent1 = new Intent(GroupDisbursement.this, Launch.class);
                            }if(under_depart.equals("Manager")){
                                intent1 = new Intent(GroupDisbursement.this, Manager_Launch.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent1);

                                finish();
                            }if(under_depart.equals("Admin")){
                                intent1 = new Intent(GroupDisbursement.this, Admin_Launch.class);
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


    public void queryDo(View view){
        Dialog.Builder builder = null;

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;


        builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){

            @Override
            protected void onBuildDone(Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                EditText et_pass = (EditText)fragment.getDialog().findViewById(R.id.QueryID);
                //Toast.makeText(mActivity, "Connected", Toast.LENGTH_SHORT).show();
                cc=et_pass.getText().toString().trim();
                if(cc.toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Please Type Query...", Toast.LENGTH_SHORT).show();
                }else{


                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(GroupDisbursement.this);
                    builder
                            .setTitle("Query")
                            .setMessage("Are you Sure?")
                            //.setMessage("Customer Details!")
                            .setIcon(android.R.drawable.ic_menu_send)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // String appro="QUERY";
                                    //showBottomSheet(appro);
                                    if(under_depart.equals("DSA Person")){

                                        new ManagerApprove().execute(Sales_Profile_Query_Send_Link,cid,cname,deviceIMEI,empname,regid,cc,lat,lon);



                                    }
                                    if(under_depart.equals("Manager")){

                                        new ManagerApprove().execute(Manager_Profile_Query_Send_Link,cid,cname,deviceIMEI,empname,regid,cc,lat,lon);


                                        //  new ManagerApprove().execute(ApproveSend_Link2,cid,cname,user_id,empname,regid,MSG,lat,lon);
                                    }if(under_depart.equals("Admin")){

                                        new ManagerApprove().execute(Admin_Finance_Query_Send_Link,cid,cname,deviceIMEI,empname,regid,cc,lat,lon);
                                    }



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

        builder.title("Query")
                .positiveAction("SEND")
                .negativeAction("CANCEL")
                .contentView(R.layout.custom_layout);


        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);

    }

    public void DoDate(View view){
        Ckeck=66;

        showDialog(999);
    }

    public void datapick(View view){

        Ckeck=55;

        showDialog(999);
/*
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
                Toast.makeText(GroupDisbursement.this, "Date is " + date, Toast.LENGTH_SHORT).show();
                chequedate=(EditText)findViewById(R.id.chequedate);
                chequedate.setText(y+"/"+m1+"/"+d);
                // tbl=null;
                disbusLoanDate(y,m1,d);
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                // Toast.makeText(GroupDisbursement.this, "Cancelled" , Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("OK")
                .negativeAction("CANCEL");


        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);
        */
    }


    @Override
    protected android.app.Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new android.app.DatePickerDialog(this,myDateListener, year, month, day);
        }
        return null;
    }

    private android.app.DatePickerDialog.OnDateSetListener myDateListener = new
            android.app.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    // if(dd==1) {
                    showDate(arg1, arg2 + 1, arg3);

                }
            };

    private void showDate(int year, int month, int day) {
        // dateView.setText(new StringBuilder().append(year).append("/").append(month).append("/").append(day));

        //currentD=dateView.getText().toString().trim();
        //dateView1.setText("");
        //currentDTo=dateView1.getText().toString().trim();
        //fatch(currentD,currentDTo);
        // EditText dob=(EditText)findViewById(R.id.dob);

        if(Ckeck==55){
            chequedate=(EditText)findViewById(R.id.chequedate);
            chequedate.setText(new StringBuilder().append(day).append("-").append(month).append("-").append(year));
            disbusLoanDate(year,month,day);
        }

        if(Ckeck==66){
            Ddate=(EditText)findViewById(R.id.Ddate);
            Ddate.setText(new StringBuilder().append(day).append("-").append(month).append("-").append(year));

        }


        /*
          String dateStr = day+"-"+month+"-"+year;

        DateFormat srcDf = new SimpleDateFormat("dd-mm-yyyy");

        // parse the date string into Date object
        Date date = null;
        try {
            date = srcDf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat destDf = new SimpleDateFormat("yyyy-mm-dd");

       // format the date into another format
        dateStr = destDf.format(date);
*/
        //  Toast.makeText(this,""+dateStr, Toast.LENGTH_SHORT).show();
    }

    // Class with extends AsyncTask class
    public class DisbusLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(GroupDisbursement.this);
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

                            Intent intent = new Intent(GroupDisbursement.this, Main.class);
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


/*
        if(d>28){

            Toast.makeText(getApplicationContext(),"Select Day Only 1-28 Date.", Toast.LENGTH_SHORT).show();
            return;
        }
        */
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


        SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date m1 = null;
        try {
            m1 = formatter.parse(y+"-"+m+"-"+d);

            cal.setTime(m1);




        } catch (ParseException e) {
            e.printStackTrace();
        }


        //table row
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String Ddate = null;

        //table row

        for (int i1 = 0; i1 < loopint; i1++) {

            System.out.println(m1);
            Ddate = sdf.format(m1);


            tr = new TableRow(getApplicationContext());
            TableLayout.LayoutParams tableRowParams=
                    new TableLayout.LayoutParams
                            (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            //for set margin
            tableRowParams.setMargins(5, 5, 5, 5);
            tr.setLayoutParams(tableRowParams);
            tr.setGravity(Gravity.CENTER_HORIZONTAL);
            //text view
            TextView tv=new TextView(getApplicationContext());
            tv.setText("Cq. No. "+(i1+1));
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(Color.parseColor("#FF1E74FF"));
            tv.setTextSize(15);
            tv.setLayoutParams(new TableRow.LayoutParams(200, TableRow.LayoutParams.MATCH_PARENT));

            tr.addView(tv);
            //text vi
            //

            tv2=new TextView(getApplicationContext());


            allEds1.add(tv2);
            tv2.setText(Ddate.toString());
            tv2.setGravity(Gravity.CENTER);
            tv2.setTextColor(Color.parseColor("#FF1E74FF"));
            tv2.setTextSize(15);
            tv2.setLayoutParams(new TableRow.LayoutParams(200, TableRow.LayoutParams.MATCH_PARENT));
            //add textview
            tr.addView(tv2);
            //set layout params of edittext
            TableRow.LayoutParams etParams=
                    new TableRow.LayoutParams
                            (200,120);
            etParams.setMargins(5, 5, 5, 5);


            et=new EditText(getApplicationContext());

            allEds.add(et);
            et.setLayoutParams(etParams);
            et.setTextColor(Color.parseColor("#FF1E74FF"));
            et.setBackgroundResource(R.drawable.abc_edit_text_material);
            et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            et.setHint("Enter Cheque Number"+(i1+1));
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



            if(freqency.equals("Month")){
                cal.add(Calendar.DATE, 30); // 10 is the days you want to add or subtract
                m1 = cal.getTime();
            }
            if(freqency.equals("Fortnight")){

                cal.add(Calendar.DATE, 15); // 10 is the days you want to add or subtract
                m1 = cal.getTime();
            }
            if(freqency.equals("Week")){

                cal.add(Calendar.DATE, 7); // 10 is the days you want to add or subtract
                m1 = cal.getTime();
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


    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");


            CropIntent.putExtra("outputX", 320);
            CropIntent.putExtra("outputY", 320);
            CropIntent.putExtra("aspectX", 4);
            CropIntent.putExtra("aspectY", 5);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);


            CropIntent.putExtra("scaleUpIfNeeded", true);
            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(GroupDisbursement.this, "Camera Crop Option not Support!", Toast.LENGTH_LONG).show();
        }
    }
    //Image Crop Code End Here

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(GroupDisbursement.this,
                Manifest.permission.CAMERA)) {

            Toast.makeText(GroupDisbursement.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(GroupDisbursement.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

//                    Toast.makeText(EmpProfile.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    //                  Toast.makeText(EmpProfile.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


    public void addone(){

        String[] names = new String[listsubR.size()];
        names = listsubR.toArray(names);

        String[] teams = new String[listsub1.size()];
        teams = listsub1.toArray(teams);

        String[] message = new String[listsub2.size()];
        message = listsub2.toArray(message);

        String[] depart = new String[listsub3.size()];
        depart = listsub3.toArray(depart);

        String[] teamsP = new String[icon.size()];
        teamsP = icon.toArray(teamsP);

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
            temp.put("message", message[i]);
            temp.put("depart", depart[i]);
            temp.put("photo", teamsP[i]);
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
        final CustomAdapter adapter=new CustomAdapter(this, R.layout.z_summry,searchResults);


        //finally,set the adapter to the default ListView
        //list.setAd
        //mylistview1.setAdapter(adapter);
        LinearLayout listViewReplacement = (LinearLayout) findViewById(R.id.pppp);
        // NamesRowItemAdapter adapter = new NamesRowItemAdapter(this, namesInList);
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, listViewReplacement);
            listViewReplacement.addView(view);
        }

        //  setListViewHeightBasedOnChildren(mylistview1);
        inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString=inputSearch.getText().toString();
                int textLength=searchString.length();
                searchResults.clear();

                for(int i=0;i<originalValues.size();i++)
                {
                    String playerName=originalValues.get(i).get("name").toString();
                    if(textLength<=playerName.length()){
                        //compare the String in EditText with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
                            searchResults.add(originalValues.get(i));
                    }
                }

                adapter.notifyDataSetChanged();
                //mylistview1.setExpanded(true);
                //ListUtils.setDynamicHeight(mylistview1);
                //Utility.setListViewHeightBasedOnChildren(mylistview1);
//                UIUtils.setListViewHeightBasedOnItems(mylistview1);

//whenever the data changes
                //               UIUtils.setListViewHeightBasedOnItems(mylistview1);
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {


            }
        });


        // Utility.setListViewHeightBasedOnChildren(mylistview1);
    }



    // Class with extends AsyncTask class
    public class Approve_Summ_LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(GroupDisbursement.this);
        String data ="";
        int sizeData = 0;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

            //Dialog.setMessage("Loading...");
            //Dialog.show();

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

                                                icon.add(prods.getJSONObject(j).getString("icon"));
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
            ImageView photo;
            TextView name,team,message,depart;

        }

        ViewHolder viewHolder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.z_summry, null);
                viewHolder=new ViewHolder();

                //cache the views
                viewHolder.photo=(ImageView) convertView.findViewById(R.id.profile_pic);
                viewHolder.name=(TextView) convertView.findViewById(R.id.member_name);
                viewHolder.team=(TextView) convertView.findViewById(R.id.status);
                viewHolder.message=(TextView) convertView.findViewById(R.id.contact_typetime);
                viewHolder.depart=(TextView) convertView.findViewById(R.id.depart);

                //link the cached views to the convertview
                convertView.setTag(viewHolder);

            }
            else
                viewHolder=(ViewHolder) convertView.getTag();


            // int photoId=(Integer) searchResults.get(position).get("photo");

//            Glide.with(GroupDisbursement.this)
            //.(R.drawable.ic_launcher)
            //                  .load(searchResults.get(position).get("photo").toString())
            //                .diskCacheStrategy(DiskCacheStrategy.ALL)
            //              .error(R.drawable.ic_launcher)
            //            .into(viewHolder.photo);

            new DownloadImageFromInternet(viewHolder.photo).execute(searchResults.get(position).get("photo").toString().replace("http","https"));

            //set the data to be displayed
            // viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
            viewHolder.depart.setText(searchResults.get(position).get("depart").toString());
            viewHolder.message.setText(searchResults.get(position).get("message").toString());
            viewHolder.name.setText(searchResults.get(position).get("name").toString());
            final String lonper = searchResults.get(position).get("team").toString();
            String prop=searchResults.get(position).get("team").toString()+"";
            viewHolder.team.setText(prop);

            final ViewHolder finalHolder = viewHolder;
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String Product = finalHolder.name.getText().toString().trim();
                    String Team = lonper;
                    Toast.makeText(finalConvertView.getContext(), ""+Product, Toast.LENGTH_SHORT).show();
                    //MainActivityD mainD = null;
                    //mainD.privecyTerms();

//if(Product.toString().trim()=="Mobile"){}
                    //(MainActivityD).mobile();
                    //FragmentChild.doSubProduct(Product);
                    // subProduct(Product,Team);

                }
            });

            //return the view to be displayed
            return convertView;
        }

    }
}
