package infracom.abcr.sarthamicrofinance.GroupLoan;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
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
import java.util.Calendar;
import java.util.HashMap;

import infracom.abcr.sarthamicrofinance.Config;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Dpost.ImageLoader;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.Main;
import infracom.abcr.sarthamicrofinance.Profile.Admin_Launch;
import infracom.abcr.sarthamicrofinance.Profile.Manager_Launch;
import infracom.abcr.sarthamicrofinance.Profile.Marketing;
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

public class Approve_Group_Details extends AppCompatActivity {

    TextView Occupation;
    TextView Qua;

    ImageView imageView;
    Button buttonCamera, buttonGallery;
    File file;
    Uri uri;
    Intent CamIntent, GalIntent, CropIntent;
    public static final int RequestPermissionCode = 1;
    DisplayMetrics displayMetrics;
    int width, height;

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

    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Product_db.php";


    ListView mylistview1;
    LinearLayout list,productDetail;
    String[] member_names1;
    String product,SelectCat,ProPer;
    TextView proName;

    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;
    //ConnectionDetector cd;

    ImageLoader imageLoader;

    private Approve_Group_Details mActivity;

    String status,product_type;
    ProgressDialog dialog;
    public String user_id, password, email, name, lastname, mobile, fullname,sts;
    private Uri mImageCaptureUri, mImageCaptureUriTo;
    ImageView Cadd, CaddV, CaddA, CaddD;

    private  String Sales_Profile_Query_Send_Link="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Sales_Quary_Profile.php";

    private  String Manager_Profile_Query_Send_Link="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Manager_Quary_Profile.php";
    private  String Manager_Finance_Query_Send_Link="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Manager_Quary_Finance.php";
    private  String Admin_Finance_Query_Send_Link="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Admin_Quary_Finance.php";

    private  String Approve_Summ_LongOperation_url="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Approve_Summ_LongOperation.php";

    private static Bitmap bm;
    static Bitmap bitmap;
    private String SERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/updateProfile.php";

    private String Customer_uSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Group_customer_details.php";


    private String Customer_uSERVER_URL_one = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/CPhoto_Upload.php";

    private  String ApproveSend_Link="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/ManagerApprove.php";

    private  String ApproveSend_Link2="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/ManagerDecline.php";


    private  String SApproveSend_Link="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/SalesApprove.php";

    private  String SApproveSend_LinkANS="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/SalesApproveANS.php";

    private  String FinancialDetailANS="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Financial_SalesApproveANS.php";

    private  String SApproveSend_Link2="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/SalesDecline.php";

    private  String FINANCeApproveSend_Link="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Finance_ManagerApprove.php";

    private  String FINANCeApproveSend_Link2ANS="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Finance_ManagerQANS.php";

    private  String FINANCeApproveSend_Link2="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Finance_ManagerDecline.php";

    private  String Admin_AApproveSend_Link="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Admin_Approve.php";

    private  String Admin_AApproveSend_Link2="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Admin_Decline.php";

    boolean GallaryPhotoSelected = false;
    TextView Titletxt, DestxtLastname;

    String checkPhoto="",checkPhoto2="",checkPhoto3="",checkPhoto4="";

    Controller aController = null;

    static String img1,img2,img3,img4, Name,Fname,tunMobile, Address,City,Zip,Xiemail,XPan="",lat,lon;
    // String c1="0",c2="0",c3="0",c4="0";

    private ProgressDialog pDialog;
    String mypath;
    String cc;
    int Ckeck=0;
    public static String Finalmedia = "";

    private static final String TAG = Approve_Group_Details.class.getSimpleName();

    private String empname,regid;
    Bitmap photo;
    String MSG;
    //private static final int PICK_FROM_CAMERA = 1;
    //private static final int CROP_FROM_CAMERA = 2;
    //private static final int PICK_FROM_FILE = 3;


    private int year, month, day;

    Uri selectedImageUri = null;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    Uri imageUri;
    LinearLayout appsts,photoframe;

    private BottomSheetDialog mBottomSheetDialog;

    TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal,view8,ROIP,LTVP;
    EditText MsgeditText;
    DBAdapter db;

    static  String cid,cname;

    public static final String CROPPED_IMAGE_FILEPATH = "/sdcard/SarthaMicroFinance.jpg";

    GPSTracker gps;
    double latitude;
    double longitude;
    String under_depart;
    String deviceIMEI = "";
    ProgressView prog;

    String bankName,BankBranchName,AccountNum,IFSCC;

    RadioButton Male;
    RadioButton Female;

    RadioButton MYES;
    RadioButton MNO;

    ScrollView scrollView2,scrollView6;
    RadioButton switches_rb2ST;
    RadioButton switches_rb2SC;
    RadioButton switches_rb2OBC;
    RadioButton switches_rb2General;
    RadioButton switches_rb2Other;

    RadioButton ParentalC;
    RadioButton OwenedC;
    RadioButton RentedC;

    String gender,married,categury,house;


    TextView txtNameMother ;
    TextView dob ;
    TextView No_of_Dependents ;
    TextView aadhar_num ;
    TextView DL;
    TextView LandMark1;
    TextView LandMark2;
    TextView LandMark3;
    TextView PIN1;
    TextView PIN2;
    TextView PIN3;
    TextView City1;
    TextView City2;
    TextView City3;
    TextView State1;
    TextView State2;
    TextView State3;

    TextView recidentcontact;
    TextView contactOffice;
    TextView Nationality,Address1,Address2,Address3;
    TextView AnnualIncome,txtNameFather,no_year_current,productD,imei;


    String txtNameMother1 ;
    String dob1 ;
    String No_of_Dependents1 ;
    String aadhar_num1 ;
    String DL1;
    String LandMark11;
    String LandMark21;
    String LandMark31;
    String PIN11;
    String PIN21;
    String PIN31;
    String City11;
    String City21;
    String City31;
    String State11;
    String State21;
    String State31;

    String OuaS;
    String OccupationS;


    String recidentcontact1;
    String contactOffice1;
    String Nationality1;
    String AnnualIncome1,txtNameFather1,no_year_current1,productD1,imei1,Address11,Address12,Address13;
    private Calendar calendar, calendar1;

    Button button11ANS;




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
        setContentView(R.layout.approve__group__details);


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

        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


       // EnableRuntimePermission();

        BlurBehind.getInstance()
                .withAlpha(200)
                .withFilterColor(Color.parseColor("#222222"))
                .setBackground(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        appsts = (LinearLayout)findViewById(R.id.appstskii);
        photoframe= (LinearLayout)findViewById(R.id.photoframe);
        productDetail = (LinearLayout)findViewById(R.id.productDetail);
        // Get Global variable instance
        aController = (Controller) getApplicationContext();

        button11ANS=(Button)findViewById(R.id.button11ANS);

        button11ANS.setVisibility(View.GONE);

        pDialog = new ProgressDialog(this);
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();

        // Check if Internet Connection present
        if (!aController.isConnectingToInternet()) {

            // Internet Connection is not present
            aController.showAlertDialog(Approve_Group_Details.this,
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
            aController.showAlertDialog(Approve_Group_Details.this,
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

        Button submit=(Button) findViewById(R.id.submit);


        Button Dsubmit=(Button) findViewById(R.id.Decline);


        Button save=(Button) findViewById(R.id.save);

        Button Query1=(Button) findViewById(R.id.Query);


        Button buttondob=(Button) findViewById(R.id.buttondob);
        Button qualiB=(Button) findViewById(R.id.quali);
        Button OccupationSlectB=(Button) findViewById(R.id.OccupationSlect);

        Occupation = (TextView)findViewById(R.id.Occupation);
        Qua = (TextView)findViewById(R.id.Qua);


        FloatingActionButton buttonP 	= (FloatingActionButton) findViewById(R.id.imageButtonP);
        FloatingActionButton buttonD 	= (FloatingActionButton) findViewById(R.id.imageButtonD);
        FloatingActionButton buttonA 	= (FloatingActionButton) findViewById(R.id.imageButtonA);
        FloatingActionButton buttonV 	= (FloatingActionButton) findViewById(R.id.imageButtonV);

        LinearLayout linerBank = (LinearLayout)findViewById(R.id.linerBank);
        linerBank.setVerticalGravity(View.GONE);
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

        buttonP.setVisibility(View.VISIBLE);
        buttonA.setVisibility(View.VISIBLE);
        buttonD.setVisibility(View.VISIBLE);
        buttonV.setVisibility(View.VISIBLE);


        txtNameMother = (TextView)findViewById(R.id.txtNameMother);
        txtNameFather= (TextView)findViewById(R.id.txtNameFather);
        dob = (TextView)findViewById(R.id.dob);

        No_of_Dependents = (TextView)findViewById(R.id.No_of_Dependents);
        no_year_current =(TextView)findViewById(R.id.no_year_current);
        aadhar_num = (TextView)findViewById(R.id.aadhar_num);

        DL = (TextView)findViewById(R.id.DL);
        LandMark1 = (TextView)findViewById(R.id.LandMark1);
        LandMark2 = (TextView)findViewById(R.id.LandMark2);
        LandMark3 = (TextView)findViewById(R.id.LandMark3);
        PIN1 = (TextView)findViewById(R.id.PIN1);
        PIN2 = (TextView)findViewById(R.id.PIN2);
        PIN3 = (TextView)findViewById(R.id.PIN3);
        City1 = (TextView)findViewById(R.id.City1);
        City2 = (TextView)findViewById(R.id.City2);
        City3 = (TextView)findViewById(R.id.City3);
        State1 = (TextView)findViewById(R.id.State1);
        State2 = (TextView)findViewById(R.id.State2);
        State3 = (TextView)findViewById(R.id.State3);

        productD = (TextView)findViewById(R.id.productD);
        imei = (TextView)findViewById(R.id.imei);

        recidentcontact = (TextView)findViewById(R.id.recidentcontact);
        contactOffice = (TextView)findViewById(R.id.contactOffice);
        Nationality = (TextView)findViewById(R.id.Nationality);
        AnnualIncome = (TextView)findViewById(R.id.AnnualIncome);

        Male = (RadioButton)findViewById(R.id.Male);
        Female = (RadioButton)findViewById(R.id.Female);

        MYES = (RadioButton)findViewById(R.id.MYES);
        MNO = (RadioButton)findViewById(R.id.MNO);

        switches_rb2ST = (RadioButton)findViewById(R.id.switches_rb2ST);
        switches_rb2SC = (RadioButton)findViewById(R.id.switches_rb2SC);
        switches_rb2OBC = (RadioButton)findViewById(R.id.switches_rb2OBC);
        switches_rb2General = (RadioButton)findViewById(R.id.switches_rb2General);
        switches_rb2Other = (RadioButton)findViewById(R.id.switches_rb2Other);

        ParentalC = (RadioButton)findViewById(R.id.ParentalC);
        OwenedC = (RadioButton)findViewById(R.id.OwenedC);
        RentedC = (RadioButton)findViewById(R.id.RentedC);


        Address1= (EditText) findViewById(R.id.Address1);
        Address2= (EditText) findViewById(R.id.Address2);
        Address3= (EditText) findViewById(R.id.Address3);



//        TextView AnnualIncome,txtNameFather,no_year_current,productD,imei;



        if(under_depart.equals("DSA Person")){
            if(sts.equals("QUERY")){
                button11ANS.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);
                appsts.setVisibility(View.GONE);
            }else{
                button11ANS.setVisibility(View.GONE);
                save.setVisibility(View.VISIBLE);
            }

            Query1.setVisibility(View.GONE);
            submit.setText("Update");
            Dsubmit.setVisibility(View.GONE);
            if(sts.equals("PENDING")||sts.equals("DONE")||sts.equals("FINANCIAL")||sts.equals("SANCTION")){
//                linerBank.setVerticalGravity(View.GONE);
                appsts.setVisibility(View.GONE);
            }
        }if(under_depart.equals("Manager")){
            save.setVisibility(View.GONE);

            buttondob.setVisibility(View.GONE);
            qualiB.setVisibility(View.GONE);
            OccupationSlectB.setVisibility(View.GONE);


            Male.setEnabled(false);
            Female.setEnabled(false);

            MYES.setEnabled(false);
            MNO.setEnabled(false);

            switches_rb2ST.setEnabled(false);
            switches_rb2SC.setEnabled(false);
            switches_rb2OBC.setEnabled(false);
            switches_rb2General.setEnabled(false);
            switches_rb2Other.setEnabled(false);

            ParentalC.setEnabled(false);
            OwenedC.setEnabled(false);
            RentedC.setEnabled(false);

            txtNameMother.setEnabled(false);
            dob.setEnabled(false);
            No_of_Dependents.setEnabled(false);
            aadhar_num.setEnabled(false);
            DL.setEnabled(false);
            LandMark1.setEnabled(false);
            LandMark2.setEnabled(false);
            LandMark3.setEnabled(false);
            PIN1.setEnabled(false);
            PIN2.setEnabled(false);
            PIN3.setEnabled(false);
            City1.setEnabled(false);
            City2.setEnabled(false);
            City3.setEnabled(false);
            State1.setEnabled(false);
            State2.setEnabled(false);
            State3.setEnabled(false);

            recidentcontact.setEnabled(false);
            contactOffice.setEnabled(false);
            Nationality.setEnabled(false);

            Address1.setEnabled(false);Address2.setEnabled(false);Address3.setEnabled(false);


            if(sts.equals("PENDING")){
                photoframe.setVisibility(View.GONE);}
            submit.setText("Approve");
            Dsubmit.setText("Decline");

            if(sts.equals("QUERY")){
                button11ANS.setVisibility(View.VISIBLE);
                appsts.setVisibility(View.GONE);
            }

            if(sts.equals("DISBUS")){submit.setVisibility(View.GONE);Dsubmit.setVisibility(View.GONE);}
            if(sts.equals("CLEAR")||sts.equals("FINANCIAL")||sts.equals("SANCTION")){appsts.setVisibility(View.GONE);}
        }if(under_depart.equals("Admin")){
            save.setVisibility(View.GONE);
            buttondob.setVisibility(View.GONE);
            qualiB.setVisibility(View.GONE);
            OccupationSlectB.setVisibility(View.GONE);

            Male.setEnabled(false);
            Female.setEnabled(false);

            MYES.setEnabled(false);
            MNO.setEnabled(false);

            switches_rb2ST.setEnabled(false);
            switches_rb2SC.setEnabled(false);
            switches_rb2OBC.setEnabled(false);
            switches_rb2General.setEnabled(false);
            switches_rb2Other.setEnabled(false);

            ParentalC.setEnabled(false);
            OwenedC.setEnabled(false);
            RentedC.setEnabled(false);

            txtNameMother.setEnabled(false);
            dob.setEnabled(false);
            No_of_Dependents.setEnabled(false);
            aadhar_num.setEnabled(false);
            DL.setEnabled(false);
            LandMark1.setEnabled(false);
            LandMark2.setEnabled(false);
            LandMark3.setEnabled(false);
            PIN1.setEnabled(false);
            PIN2.setEnabled(false);
            PIN3.setEnabled(false);
            City1.setEnabled(false);
            City2.setEnabled(false);
            City3.setEnabled(false);
            State1.setEnabled(false);
            State2.setEnabled(false);
            State3.setEnabled(false);

            recidentcontact.setEnabled(false);
            contactOffice.setEnabled(false);
            Nationality.setEnabled(false);
            Address1.setEnabled(false);Address2.setEnabled(false);Address3.setEnabled(false);

            submit.setText("Approve");
            Dsubmit.setText("Decline");
            if(sts.equals("DISBUS")){submit.setVisibility(View.GONE);Dsubmit.setVisibility(View.GONE);}
            if(sts.equals("CLEAR")||sts.equals("PENDING")||sts.equals("DONE")){appsts.setVisibility(View.GONE);}
        }


        new CustomerLongOperation().execute(Customer_uSERVER_URL,cid,cname,deviceIMEI);


        // new Approve_Summ_LongOperation().execute(Approve_Summ_LongOperation_url,cid,"","");

        /*

*/



        gps = new GPSTracker(Approve_Group_Details.this);

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

        /*
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
        */



        Male = (RadioButton)findViewById(R.id.Male);
        Female = (RadioButton)findViewById(R.id.Female);

        MYES = (RadioButton)findViewById(R.id.MYES);
        MNO = (RadioButton)findViewById(R.id.MNO);

        switches_rb2ST = (RadioButton)findViewById(R.id.switches_rb2ST);
        switches_rb2SC = (RadioButton)findViewById(R.id.switches_rb2SC);
        switches_rb2OBC = (RadioButton)findViewById(R.id.switches_rb2OBC);
        switches_rb2General = (RadioButton)findViewById(R.id.switches_rb2General);
        switches_rb2Other = (RadioButton)findViewById(R.id.switches_rb2Other);

        ParentalC = (RadioButton)findViewById(R.id.ParentalC);
        OwenedC = (RadioButton)findViewById(R.id.OwenedC);
        RentedC = (RadioButton)findViewById(R.id.RentedC);


        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Male.setChecked(Male == buttonView);
                    Female.setChecked(Female == buttonView);
                }
                if(Male.isChecked()){//checkTun="Month";
                    gender = "Male";
                }
                if(Female.isChecked()){// checkTun="Fortnight";
                    gender="Female";
                }
            }
        };
        Male.setOnCheckedChangeListener(listener);
        Female.setOnCheckedChangeListener(listener);


        CompoundButton.OnCheckedChangeListener listener1 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MYES.setChecked(MYES == buttonView);
                    MNO.setChecked(MNO == buttonView);
                }
                if(MYES.isChecked()){//checkTun="Month";
                    married="Yes";
                }
                if(MNO.isChecked()){// checkTun="Fortnight";
                    married="No";
                }
            }
        };
        MYES.setOnCheckedChangeListener(listener1);
        MNO.setOnCheckedChangeListener(listener1);


        CompoundButton.OnCheckedChangeListener listener2 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    switches_rb2ST.setChecked(switches_rb2ST == buttonView);
                    switches_rb2SC.setChecked(switches_rb2SC == buttonView);
                    switches_rb2OBC.setChecked(switches_rb2OBC == buttonView);
                    switches_rb2General.setChecked(switches_rb2General == buttonView);
                    switches_rb2Other.setChecked(switches_rb2Other == buttonView);
                }
                if(switches_rb2ST.isChecked()){//checkTun="Month";
                    categury="ST";
                }
                if(switches_rb2SC.isChecked()){// checkTun="Fortnight";
                    categury="SC";
                }if(switches_rb2OBC.isChecked()){//checkTun="Month";
                    categury="OBC";
                }
                if(switches_rb2General.isChecked()){// checkTun="Fortnight";
                    categury="General";
                }if(switches_rb2Other.isChecked()){//checkTun="Month";
                    categury="Other";
                }
            }
        };
        switches_rb2ST.setOnCheckedChangeListener(listener2);
        switches_rb2SC.setOnCheckedChangeListener(listener2);
        switches_rb2OBC.setOnCheckedChangeListener(listener2);
        switches_rb2General.setOnCheckedChangeListener(listener2);
        switches_rb2Other.setOnCheckedChangeListener(listener2);
        final LinearLayout padd=(LinearLayout)findViewById(R.id.padd);
        padd.setVisibility(View.GONE);


        CompoundButton.OnCheckedChangeListener listener3 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ParentalC.setChecked(ParentalC == buttonView);
                    OwenedC.setChecked(OwenedC == buttonView);
                    RentedC.setChecked(RentedC == buttonView);
                }
                if(ParentalC.isChecked()){//checkTun="Month";
                    padd.setVisibility(View.GONE);
                    house="Parental";
                }
                if(OwenedC.isChecked()){
                    padd.setVisibility(View.GONE);
                    house="Owned";
                    // checkTun="Fortnight";

                }if(RentedC.isChecked()){//checkTun="Month";
                    padd.setVisibility(View.VISIBLE);
                    house="Rented";
                }
            }
        };
        ParentalC.setOnCheckedChangeListener(listener3);
        OwenedC.setOnCheckedChangeListener(listener3);
        RentedC.setOnCheckedChangeListener(listener3);

        mylistview1 = (ListView)findViewById(R.id.appp);

        list = (LinearLayout)findViewById(R.id.pppp);
        //Utility.setListViewHeightBasedOnChildren(mylistview1);
        //Utility.setListViewHeightBasedOnChildren(mylistview1);
        //setListViewHeightBasedOnChildren(mylistview1);
        // ListUtils.setDynamicHeight(mylistview1);
        /*
        scrollView2 = (ScrollView)findViewById(R.id.scrollView2);
        scrollView6 = (ScrollView)findViewById(R.id.scrollView6);

        scrollView2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v(TAG, "PARENT TOUCH");

                findViewById(R.id.scrollView6).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        scrollView6.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v(TAG, "CHILD TOUCH");

                // Disallow the touch request for parent scroll on touch of  child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

*/

        inputSearch = (EditText)findViewById(R.id.inputSearch);

        listsubR.clear();
        listsub1.clear();
        listsub2.clear();
        listsub3.clear();
        icon.clear();


        new Approve_Summ_LongOperation().execute(Approve_Summ_LongOperation_url,cid,"","");


        /*
  mylistview1.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        mylistview1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });


final ScrollView scrollView = (ScrollView)findViewById(R.id.idsck);


        mylistview1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);

                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_UP:
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        break;
                }

                return false;
            }
        });


        mylistview1.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

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
                        new AlertDialog.Builder(Approve_Group_Details.this)
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

                //  startCropImage();
                //startPickImage();


                com.theartofdev.edmodo.cropper.CropImage.activity(null)
                        .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                        .start(Approve_Group_Details.this);
            }
        });
        CaddA 	= (ImageView) findViewById(R.id.imageViewA);
//        ImageButton buttonA 	= (ImageButton) findViewById(R.id.imageButtonA);
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=2;

                com.theartofdev.edmodo.cropper.CropImage.activity(null)
                        .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                        .start(Approve_Group_Details.this);
                // startPickImage();
                // startCropImage();
            }
        });
        CaddD	= (ImageView) findViewById(R.id.imageViewD);
        //ImageButton buttonD 	= (ImageButton) findViewById(R.id.imageButtonD);
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=3;

                com.theartofdev.edmodo.cropper.CropImage.activity(null)
                        .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                        .start(Approve_Group_Details.this);
                //startPickImage();
                // startCropImage();

            }
        });
        CaddV 	= (ImageView) findViewById(R.id.imageViewV);
        // ImageButton buttonV 	= (ImageButton) findViewById(R.id.imageButtonV);
        buttonV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ckeck=4;
                //startPickImage();
                //startCropImage();

                com.theartofdev.edmodo.cropper.CropImage.activity(null)
                        .setGuidelines(com.theartofdev.edmodo.cropper.CropImageView.Guidelines.ON)
                        .start(Approve_Group_Details.this);

            }
        });

        ImagePopup.enablePopUpOnClick(this, Cadd);
        ImagePopup.enablePopUpOnClick(this, CaddA);
        ImagePopup.enablePopUpOnClick(this, CaddD);
        ImagePopup.enablePopUpOnClick(this, CaddV);


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

                // InputStream in = getContentResolver().openInputStream(croppedUri);
                SaveImage(mBitmap);

                ByteArrayOutputStream bos =new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                byte[] bb = bos.toByteArray();


                // img1 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                // String photo1="image";
                // if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                // }else {
                //  new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, deviceIMEI, img1);




                //  Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();

                File file1 = new File(imagepath);
                long length = file1.length() / 1024;

                if(length >= 3072){
                    Toast.makeText(getApplicationContext(), "File Size is should be less then 2MB..", Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    if (Ckeck == 1) {

                        img1 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                        //Cadd.setImageBitmap(bitmap);
                        ((ImageView) findViewById(R.id.imageViewP)).setImageURI(result.getUri());
                        String photo1 = "image";
                        if (sts == "DONE") {
                            if (status == "DONE") {
                                Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        if (status == "DONE") {
                        } else {
                            //
                            //c1="1";
                            checkPhoto = "CustomerDetailDone";
                            new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, fname);
                        }
                    }
                    if (Ckeck == 2) {
                        img2 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                        ((ImageView) findViewById(R.id.imageViewA)).setImageURI(result.getUri());
                        // CaddA.setImageBitmap(bitmap);
                        String photo1 = "aadhar_card";
                        if (sts == "DONE") {
                            if (status == "DONE") {
                                Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        if (status == "DONE") {
                        } else {
                            /// c2="1"; //
                            // checkPhoto="CustomerDetailDone";
                            checkPhoto2 = "CustomerDetailDone";
                            new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, fname);
                        }
                    }
                    if (Ckeck == 3) {
                        img3 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                        ((ImageView) findViewById(R.id.imageViewD)).setImageURI(result.getUri());
                        //CaddD.setImageBitmap(bitmap);
                        String photo1 = "driving_licence";
                        if (sts == "DONE") {
                            if (status == "DONE") {
                                Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        if (status == "DONE") {
                        } else {
                            //  c3="1";
                            checkPhoto3 = "CustomerDetailDone";
                            new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, fname);
                        }
                    }
                    if (Ckeck == 4) {
                        img4 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                        ((ImageView) findViewById(R.id.imageViewV)).setImageURI(result.getUri());
                        // CaddV.setImageBitmap(bitmap);
                        if (sts == "DONE") {
                            if (status == "DONE") {
                                Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        if (status == "DONE") {
                        } else {
                            String photo1 = "voter_id";
                            // c4="1";
                            checkPhoto4 = "CustomerDetailDone";
                            new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, fname);
                        }
                    }


                }






            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }




    public void DialogShow(){

        final android.app.Dialog dialog = new android.app.Dialog(Approve_Group_Details.this);
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
            view8.setText("Send Message for Approve ");
            if(sts.equals("QUERY")){
                view8=(TextView)v.findViewById(R.id.view8);
                view8.setText("Send Message");
            }
        } if(under_depart.equals("Manager")) {
            view8=(TextView)v.findViewById(R.id.view8);
            view8.setText("Sartha MicroFinance Manager Approval/Decline");
            if(sts.equals("QUERY")){
                view8=(TextView)v.findViewById(R.id.view8);
                view8.setText("Send Message");
            }
        } if(under_depart.equals("Admin")) {
            view8=(TextView)v.findViewById(R.id.view8);
            view8.setText("Sartha MicroFinance Admin Approval/Decline");
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

                if(appro.equals("ANS")){
                    new ManagerApprove().execute(SApproveSend_LinkANS,cid,cname,deviceIMEI,empname,regid,MSG,lat,lon);

                }
                if(appro.equals("ANS1")){
                    new ManagerApprove().execute(FinancialDetailANS,cid,cname,deviceIMEI,empname,regid,MSG,lat,lon);

                }
                if(appro.equals("ANS2")){
                    new ManagerApprove().execute(FINANCeApproveSend_Link2ANS,cid,cname,deviceIMEI,empname,regid,MSG,lat,lon);

                }
                if(appro.equals("OK")){


                    if(under_depart.equals("DSA Person")){


                        //   Submit();
                        new ManagerApprove().execute(SApproveSend_Link,cid,cname,deviceIMEI,empname,regid,MSG,lat,lon);


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
                      //
                        //  c1="1";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img1);
                    }
                }
                if(Ckeck==2){
                    img2 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                    CaddA.setImageBitmap(bitmap);
                    String photo1="aadhar_card";
                    if(sts=="DONE"){
                    if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    }else {
                       // c2="1";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img2);
                    }
                }
                if(Ckeck==3){
                    img3 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                    CaddD.setImageBitmap(bitmap);
                    String photo1="driving_licence";
                    if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    }else {
                       // c3="1";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img3);
                    }
                }
                if(Ckeck==4){
                    img4 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                    CaddV.setImageBitmap(bitmap);
                    if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    }else {
                        String photo1 = "voter_id";
                        //c4="1";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img4);
                    }
                }

            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    */

//    new ManagerApprove().execute(SApproveSend_Link,cid,cname,deviceIMEI,empname,regid,MSG,lat,lon);


    public  void Submit(){


        EditText txtNameCusto = (EditText) findViewById(R.id.txtNameCusto);
        EditText  txtNameFather = (EditText) findViewById(R.id.txtNameFather);
        EditText  txtNameMobile = (EditText) findViewById(R.id.txtNameMobile);
        EditText  txtNameAddress = (EditText) findViewById(R.id.LandMark1);
        EditText  txtNameCity = (EditText) findViewById(R.id.City1);
        EditText  PinZip= (EditText) findViewById(R.id.PIN1);
        EditText  txtNameEmailP = (EditText) findViewById(R.id.txtNameEmail);
        EditText  txtNamePANP= (EditText) findViewById(R.id.txtNamePAN);



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


        txtNameMother1=txtNameMother.getText().toString().trim() ;
        dob1=dob.getText().toString().trim() ;

        if(dob1.trim().length() < 1){
            Toast.makeText(Approve_Group_Details.this, "Please Select DOB", Toast.LENGTH_SHORT).show();
            return;
        }

       /*
        //  String dateStr = "21/20/2011";
        DateFormat srcDf = new SimpleDateFormat("dd-mm-yyyy");
        Date date = null;
        try {
            date = srcDf.parse(dob1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat destDf = new SimpleDateFormat("yyyy-mm-dd");
        dob1 = destDf.format(date);
        //Toast.makeText(this,""+dob1, Toast.LENGTH_SHORT).show();

*/
        No_of_Dependents1=No_of_Dependents.getText().toString().trim() ;
        aadhar_num1 =aadhar_num.getText().toString().trim();
        DL1=DL.getText().toString().trim();
        LandMark11=LandMark1.getText().toString().trim();
        LandMark21=LandMark2.getText().toString().trim();
        LandMark31=LandMark3.getText().toString().trim();
        PIN11=PIN1.getText().toString().trim();
        PIN21=PIN2.getText().toString().trim();
        PIN31=PIN3.getText().toString().trim();
        City11=City1.getText().toString().trim();
        City21=City2.getText().toString().trim();
        City31=City3.getText().toString().trim();
        State11=State1.getText().toString().trim();
        State21=State2.getText().toString().trim();
        State31=State3.getText().toString().trim();


        OccupationS= Occupation.getText().toString().trim();
        OuaS= Qua.getText().toString().trim();

        recidentcontact1=recidentcontact.getText().toString().trim();
        contactOffice1=contactOffice.getText().toString().trim();
        Nationality1=Nationality.getText().toString().trim();
        AnnualIncome1=AnnualIncome.getText().toString().trim();
        txtNameFather1=txtNameFather.getText().toString().trim();
        no_year_current1=no_year_current.getText().toString().trim();
        productD1=productD.getText().toString().trim();
        imei1=imei.getText().toString().trim();



        Address1= (EditText) findViewById(R.id.Address1);
        Address2= (EditText) findViewById(R.id.Address2);
        Address3= (EditText) findViewById(R.id.Address3);


        Address11=Address1.getText().toString();

        Address12=Address2.getText().toString();

        Address13=Address3.getText().toString();



        if(Name.trim().length() > 2){
            if(Fname.trim().length() > 2 && !Fname.equals("null")){
                if(tunMobile.trim().length() > 9){
                    if(Address.trim().length() > 2 && !Address.equals("null")){
                        if(City.trim().length() > 3 && !City.equals("null")){
                            if(Zip.trim().length() > 5){
                                //addEmployee();

                                if(Xiemail.trim().length() > 3){
                                    //addEmployee();
                                    if(XPan.trim().length() > 3) {


                                        if(txtNameMother1.trim().length() > 2){
                                            if(dob1.trim().length() > 2){
                                                if(No_of_Dependents1.trim().length() > 0){
                                                    // if(aadhar_num1.trim().length() > 2){
                                                    // if(DL1.trim().length() > 0){
                                                    if(PIN11.trim().length() > 2){
                                                        if(City11.trim().length() > 2){
                                                            if(State11.trim().length() > 2){
                                                                if(LandMark11.trim().length() > 2){
                                                                    if(Address11.trim().length() > 2){

                                                                        // if(recidentcontact1.trim().length() > 9){
                                                                        if(Nationality1.trim().length() > 2){
                                                                            if(AnnualIncome1.trim().length() > 2){
                                                                                if(txtNameFather1.trim().length() > 2){
                                                                                    if(no_year_current1.trim().length() > 0){
                                                                                        if(productD1.trim().length() > 2){


                                                                                            if(OccupationS.trim().length() < 2){
                                                                                                Toast.makeText(Approve_Group_Details.this, "Please Select Qualification", Toast.LENGTH_SHORT).show();
                                                                                                return;
                                                                                            }
                                                                                            if(OuaS.trim().length() < 2){
                                                                                                Toast.makeText(Approve_Group_Details.this, "Please Select Occupation", Toast.LENGTH_SHORT).show();
                                                                                                return;
                                                                                            }


                                                                                            // if(c1.equals("1")){
                                                                                            //  if(c2.equals("1")){
                                                                                            //    if(c3.equals("1")){
                                                                                            //      if(c4.equals("1")){

                                                                                            if(under_depart.equals("DSA Person")) {
                                                                                                if (checkPhoto.equals("null") || checkPhoto.equals("")) {
                                                                                                    Toast.makeText(Approve_Group_Details.this, "Please Take Photo", Toast.LENGTH_SHORT).show();return;
                                                                                                }
                                                                                                if (checkPhoto2.equals("null") || checkPhoto2.equals("")) {
                                                                                                    Toast.makeText(Approve_Group_Details.this, "Please Take ID Proof Image", Toast.LENGTH_SHORT).show();return;

                                                                                                }
                                                                                                if (checkPhoto3.equals("null") || checkPhoto3.equals("")) {
                                                                                                    Toast.makeText(Approve_Group_Details.this, "Please Take Financial ID Image", Toast.LENGTH_SHORT).show();return;

                                                                                                }
                                                                                                if (checkPhoto4.equals("null") || checkPhoto4.equals("")) {
                                                                                                    Toast.makeText(Approve_Group_Details.this, "Please Take Address Proof Image", Toast.LENGTH_SHORT).show();return;

                                                                                                }


                                                                                            }


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
                                                                                                            if (sts == "DONE") {
                                                                                                                if (status == "DONE") {
                                                                                                                    Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();
                                                                                                                    return;
                                                                                                                }
                                                                                                            }
                                                                                                            if (status == "DONE") {
                                                                                                            } else {
                                                                                                                new LongOperation().execute(SERVER_URL, Name, Fname, tunMobile, Address, City, Zip, Xiemail, XPan, empname, regid, lat, lon, deviceIMEI, cid, bankName, BankBranchName, AccountNum, IFSCC,
                                                                                                                        txtNameMother1,
                                                                                                                        dob1 ,
                                                                                                                        No_of_Dependents1 ,
                                                                                                                        aadhar_num1 ,
                                                                                                                        DL1,
                                                                                                                        LandMark11,
                                                                                                                        LandMark21,
                                                                                                                        LandMark31,
                                                                                                                        PIN11,
                                                                                                                        PIN21,
                                                                                                                        PIN31,
                                                                                                                        City11,
                                                                                                                        City21,
                                                                                                                        City31,
                                                                                                                        State11,
                                                                                                                        State21,
                                                                                                                        State31,

                                                                                                                        recidentcontact1,
                                                                                                                        contactOffice1,
                                                                                                                        Nationality1,
                                                                                                                        AnnualIncome1,
                                                                                                                        txtNameFather1,
                                                                                                                        no_year_current1,
                                                                                                                        productD1,
                                                                                                                        imei1,
                                                                                                                        gender,
                                                                                                                        married,
                                                                                                                        categury,
                                                                                                                        house,
                                                                                                                        Address11,
                                                                                                                        Address12,
                                                                                                                        Address13,
                                                                                                                        OccupationS,
                                                                                                                        OuaS

                                                                                                                );
                                                                                                            }//Toast.makeText(getBaseContext(), "Please Make Sure Your Profile Details is Completed Correctly ", Toast.LENGTH_SHORT).show();
                                                                                                            //addEmployee();

                                                                                                            String appro = "OK";
                                                                                                            showBottomSheet(appro);

                                                                                                            //onStartTransaction();
                                                                                                        }
                                                                                                    })

                                                                                                    .setNegativeButton("Cancel", null)                        //Do nothing on no
                                                                                                    .show();


                                                                                            //           }else{Toast.makeText(Approve_Group_Details.this, "Please Take Address Prof Image", Toast.LENGTH_SHORT).show();return;}
                                                                                            //           }else{Toast.makeText(Approve_Group_Details.this, "Please Take Pan Card Image", Toast.LENGTH_SHORT).show();return;}
                                                                                            //        }else{Toast.makeText(Approve_Group_Details.this, "Please Take Aadhar Card Image", Toast.LENGTH_SHORT).show();return;}
                                                                                            //        }else{Toast.makeText(Approve_Group_Details.this, "Please Take Customer Photo", Toast.LENGTH_SHORT).show();return;}


                                                                                        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Product Details", Toast.LENGTH_SHORT).show();return;}

                                                                                    }else{Toast.makeText(Approve_Group_Details.this, "Please Enter No of year current Residence", Toast.LENGTH_SHORT).show();return;}
                                                                                }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Father Name", Toast.LENGTH_SHORT).show();return;}

                                                                            }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Annual Income ", Toast.LENGTH_SHORT).show();return;}

                                                                        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Nationality", Toast.LENGTH_SHORT).show();return;}
                                                                        // }else{Toast.makeText(Approve_Group_Details.this, "Please Residence Contact", Toast.LENGTH_SHORT).show();return;}

                                                                    }else{Toast.makeText(Approve_Group_Details.this, "Please Residence Address", Toast.LENGTH_SHORT).show();return;}

                                                                }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Residence Address Land Mark", Toast.LENGTH_SHORT).show();return;}
                                                            }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Residence Address State", Toast.LENGTH_SHORT).show();return;}
                                                        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Residence Address City", Toast.LENGTH_SHORT).show();return;}
                                                    }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Residence Address PIN", Toast.LENGTH_SHORT).show();return;}
                                                    //   }else{Toast.makeText(Approve_Group_Details.this, "Please Enter DL", Toast.LENGTH_SHORT).show();return;}
                                                    //  }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Aadhar Number", Toast.LENGTH_SHORT).show();return;}
                                                }else{Toast.makeText(Approve_Group_Details.this, "Please Enter No of Dependents", Toast.LENGTH_SHORT).show();return;}
                                            }else{Toast.makeText(Approve_Group_Details.this, "Please Select DOB", Toast.LENGTH_SHORT).show();return;}
                                        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Mother Name", Toast.LENGTH_SHORT).show();return;}


                                    }else{Toast.makeText(Approve_Group_Details.this, "Please Enter PAN Number", Toast.LENGTH_SHORT).show();return;}

                                }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Email ID", Toast.LENGTH_SHORT).show();return;}

                            }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Zip Code", Toast.LENGTH_SHORT).show();return;}

                        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter City Name", Toast.LENGTH_SHORT).show();return;}

                    }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Address", Toast.LENGTH_SHORT).show();return;}

                }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();return;}

            }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Father's Name", Toast.LENGTH_SHORT).show();return;}

        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Name", Toast.LENGTH_SHORT).show();return;}

    }


    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Approve_Group_Details.this);
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
                    data +="&" + URLEncoder.encode("txtNameMother", "UTF-8") + "="+params[19].toString();
                if(!params[20].equals(""))
                    data +="&" + URLEncoder.encode("dob1", "UTF-8") + "="+params[20].toString();
                if(!params[21].equals(""))
                    data +="&" + URLEncoder.encode("No_of_Dependents1", "UTF-8") + "="+params[21].toString();
                if(!params[22].equals(""))
                    data +="&" + URLEncoder.encode("aadhar_num1", "UTF-8") + "="+params[22].toString();
                if(!params[23].equals(""))
                    data +="&" + URLEncoder.encode("DL", "UTF-8") + "="+params[23].toString();
                if(!params[24].equals(""))
                    data +="&" + URLEncoder.encode("LandMark11", "UTF-8") + "="+params[24].toString();
                if(!params[25].equals(""))
                    data +="&" + URLEncoder.encode("LandMark21", "UTF-8") + "="+params[25].toString();
                if(!params[26].equals(""))
                    data +="&" + URLEncoder.encode("LandMark31", "UTF-8") + "="+params[26].toString();
                if(!params[27].equals(""))
                    data +="&" + URLEncoder.encode("PIN11", "UTF-8") + "="+params[27].toString();
                if(!params[28].equals(""))
                    data +="&" + URLEncoder.encode("PIN21", "UTF-8") + "="+params[28].toString();
                if(!params[29].equals(""))
                    data +="&" + URLEncoder.encode("PIN31", "UTF-8") + "="+params[29].toString();
                if(!params[30].equals(""))
                    data +="&" + URLEncoder.encode("City11", "UTF-8") + "="+params[30].toString();
                if(!params[31].equals(""))
                    data +="&" + URLEncoder.encode("City21", "UTF-8") + "="+params[31].toString();
                if(!params[32].equals(""))
                    data +="&" + URLEncoder.encode("City31", "UTF-8") + "="+params[32].toString();
                if(!params[33].equals(""))
                    data +="&" + URLEncoder.encode("State11", "UTF-8") + "="+params[33].toString();
                if(!params[34].equals(""))
                    data +="&" + URLEncoder.encode("State21", "UTF-8") + "="+params[34].toString();
                if(!params[35].equals(""))
                    data +="&" + URLEncoder.encode("State31", "UTF-8") + "="+params[35].toString();
                if(!params[36].equals(""))
                    data +="&" + URLEncoder.encode("recidentcontact", "UTF-8") + "="+params[36].toString();
                if(!params[37].equals(""))
                    data +="&" + URLEncoder.encode("contactOffice", "UTF-8") + "="+params[37].toString();
                if(!params[38].equals(""))
                    data +="&" + URLEncoder.encode("Nationality", "UTF-8") + "="+params[38].toString();
                if(!params[39].equals(""))
                    data +="&" + URLEncoder.encode("AnnualIncome", "UTF-8") + "="+params[39].toString();
                if(!params[40].equals(""))
                    data +="&" + URLEncoder.encode("txtNameFather", "UTF-8") + "="+params[40].toString();
                if(!params[41].equals(""))
                    data +="&" + URLEncoder.encode("no_year_current", "UTF-8") + "="+params[41].toString();
                if(!params[42].equals(""))
                    data +="&" + URLEncoder.encode("productD1", "UTF-8") + "="+params[42].toString();
                if(!params[43].equals(""))
                    data +="&" + URLEncoder.encode("imei", "UTF-8") + "="+params[43].toString();
                if(!params[44].equals(""))
                    data +="&" + URLEncoder.encode("gender", "UTF-8") + "="+params[44].toString();
                if(!params[45].equals(""))
                    data +="&" + URLEncoder.encode("married", "UTF-8") + "="+params[45].toString();
                if(!params[46].equals(""))
                    data +="&" + URLEncoder.encode("categury", "UTF-8") + "="+params[46].toString();
                if(!params[47].equals(""))
                    data +="&" + URLEncoder.encode("house", "UTF-8") + "="+params[47].toString();
                if(!params[48].equals(""))
                    data +="&" + URLEncoder.encode("Address1", "UTF-8") + "="+params[48].toString();
                if(!params[49].equals(""))
                    data +="&" + URLEncoder.encode("Address2", "UTF-8") + "="+params[49].toString();
                if(!params[50].equals(""))
                    data +="&" + URLEncoder.encode("Address3", "UTF-8") + "="+params[50].toString();
                if(!params[51].equals(""))
                    data +="&" + URLEncoder.encode("Qualification", "UTF-8") + "="+params[51].toString();
                if(!params[52].equals(""))
                    data +="&" + URLEncoder.encode("Occupation", "UTF-8") + "="+params[52].toString();


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
                    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img1);
                }
            }
            if(Ckeck==2){


                img2 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                CaddA.setImageBitmap(photo);
                String photo1="aadhar_card";
                if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                }else {
                    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img2);
                }
            }
            if(Ckeck==3){

                img3 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                CaddD.setImageBitmap(photo);
                String photo1="driving_licence";
                if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                }else {
                    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img3);
                }
            }
            if(Ckeck==4){
                if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                }else {
                    img4 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                    CaddV.setImageBitmap(photo);
                    String photo1 = "voter_id";
                    new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img4);
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

        if(under_depart.equals("DSA Person")){


            Submit();

        }else {
            String appro="OK";
            showBottomSheet(appro);
        }
    }

    public void backok(View view){
       /* BlurBehind.getInstance().execute(Approve_Group_Details.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(Approve_Group_Details.this, Launch.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                finish();
            }
        });
        */
        String appro="NO";
        showBottomSheet(appro);
    }


    // Class with extends AsyncTask class
    public class CustomerLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Approve_Group_Details.this);
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
                            product_type       = jsonChildNode.optString("product_type").toString();
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
                            checkPhoto = photo;
                            String aadhar_card       = jsonChildNode.optString("aadhar_card").toString();
                            checkPhoto2 = aadhar_card;
                            String driving_licence       = jsonChildNode.optString("driving_licence").toString();
                            checkPhoto3=driving_licence;
                            String voter_id       = jsonChildNode.optString("voter_id").toString();
                            checkPhoto4=  voter_id;
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


                            Occupation.setText(OccupationS);
                            Qua.setText(QualificationS);
                            Address1= (EditText) findViewById(R.id.Address1);
                            Address2= (EditText) findViewById(R.id.Address2);
                            Address3= (EditText) findViewById(R.id.Address3);


                            Address1.setText(Address11);
                            Address2.setText(Address12);
                            Address3.setText(Address13);

                            Male = (RadioButton)findViewById(R.id.Male);
                            Female = (RadioButton)findViewById(R.id.Female);

                            MYES = (RadioButton)findViewById(R.id.MYES);
                            MNO = (RadioButton)findViewById(R.id.MNO);

                            switches_rb2ST = (RadioButton)findViewById(R.id.switches_rb2ST);
                            switches_rb2SC = (RadioButton)findViewById(R.id.switches_rb2SC);
                            switches_rb2OBC = (RadioButton)findViewById(R.id.switches_rb2OBC);
                            switches_rb2General = (RadioButton)findViewById(R.id.switches_rb2General);
                            switches_rb2Other = (RadioButton)findViewById(R.id.switches_rb2Other);

                            ParentalC = (RadioButton)findViewById(R.id.ParentalC);
                            OwenedC = (RadioButton)findViewById(R.id.OwenedC);
                            RentedC = (RadioButton)findViewById(R.id.RentedC);


                            // if(Gender5.equals("Male")){}

                            switch (Gender5) {
                                case "Male":
                                    gender = "Male";
                                    Male.setChecked(true);
                                    break;
                                case "Female":
                                    gender="Female";
                                    Female.setChecked(true);
                                    break;
                                default:
                                    Male.setChecked(false);Female.setChecked(false);
                                    break;
                            }
                            switch (Marital_status5) {
                                case "Yes":
                                    married="Yes";
                                    MYES.setChecked(true);
                                    break;
                                case "No":
                                    married="No";
                                    MNO.setChecked(true);
                                    break;
                                default:
                                    MYES.setChecked(false);MNO.setChecked(false);
                                    break;
                            }

                            switch (Category5) {
                                case "ST":
                                    categury="ST";
                                    switches_rb2ST.setChecked(true);
                                    break;
                                case "OBC":
                                    categury="OBC";
                                    switches_rb2OBC.setChecked(true);
                                    break;
                                case "General":
                                    categury="General";
                                    switches_rb2General.setChecked(true);
                                    break;
                                case "Other":
                                    categury="Other";
                                    switches_rb2Other.setChecked(true);
                                    break;
                                case "SC":
                                    categury="SC";
                                    switches_rb2SC.setChecked(true);
                                    break;

                                default:
                                    //switches_rb2ST.setChecked(false);
                                    break;
                            }
                            final LinearLayout padd=(LinearLayout)findViewById(R.id.padd);
                            // padd.setVisibility(View.GONE);

                            switch (house_type5) {
                                case "Parental":
                                    house="Parental";
                                    ParentalC.setChecked(true);
                                    padd.setVisibility(View.GONE);
                                    break;
                                case "Owned":
                                    house="Owned";
                                    OwenedC.setChecked(true);
                                    padd.setVisibility(View.GONE);
                                    break;
                                case "Rented":
                                    house="Owned";
                                    RentedC.setChecked(true);
                                    padd.setVisibility(View.VISIBLE);
                                    break;

                                default:
                                    //switches_rb2ST.setChecked(false);
                                    padd.setVisibility(View.GONE);
                                    break;
                            }


                            txtNameMother = (TextView)findViewById(R.id.txtNameMother);
                            txtNameFather= (TextView)findViewById(R.id.txtNameFather);
                            dob = (TextView)findViewById(R.id.dob);

                            No_of_Dependents = (TextView)findViewById(R.id.No_of_Dependents);
                            no_year_current =(TextView)findViewById(R.id.no_year_current);
                            aadhar_num = (TextView)findViewById(R.id.aadhar_num);

                            DL = (TextView)findViewById(R.id.DL);
                            LandMark1 = (TextView)findViewById(R.id.LandMark1);
                            LandMark2 = (TextView)findViewById(R.id.LandMark2);
                            LandMark3 = (TextView)findViewById(R.id.LandMark3);
                            PIN1 = (TextView)findViewById(R.id.PIN1);
                            PIN2 = (TextView)findViewById(R.id.PIN2);
                            PIN3 = (TextView)findViewById(R.id.PIN3);
                            City1 = (TextView)findViewById(R.id.City1);
                            City2 = (TextView)findViewById(R.id.City2);
                            City3 = (TextView)findViewById(R.id.City3);
                            State1 = (TextView)findViewById(R.id.State1);
                            State2 = (TextView)findViewById(R.id.State2);
                            State3 = (TextView)findViewById(R.id.State3);

                            productD = (TextView)findViewById(R.id.productD);
                            imei = (TextView)findViewById(R.id.imei);

                            recidentcontact = (TextView)findViewById(R.id.recidentcontact);
                            contactOffice = (TextView)findViewById(R.id.contactOffice);
                            Nationality = (TextView)findViewById(R.id.Nationality);
                            AnnualIncome = (TextView)findViewById(R.id.AnnualIncome);

                            txtNameMother.setText(Mother_Name5);


                            dob.setText(DOB5);
                            No_of_Dependents.setText(No_of_Dependents5);
                            aadhar_num.setText(ADHAR_Number5);
                            DL.setText(DL5);
                            LandMark1.setText(land_mark5);
                            LandMark2.setText(land_mark25);
                            LandMark3.setText(land_mark35);
                            PIN1.setText(zip5);
                            PIN2.setText(PIN5);
                            PIN3.setText(PIN35);
                            City1.setText(City5);
                            City2.setText(City25);
                            City3.setText(City35);
                            State1.setText(State5);
                            State2.setText(State25);
                            State3.setText(State35);

                            recidentcontact.setText(Residence5);
                            contactOffice.setText(Office5);
                            Nationality.setText(Nationality5);
                            AnnualIncome.setText(Annual_Income5);
                            //txtNameFather.setText();
                            no_year_current.setText(no_year_current_reci5);
                            productD.setText(product5);
                            imei.setText(product_imei5);

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
                            SchemeType.setText(scheme);
                            AdvanceEMI.setText(Advance_EMI);
                            DownPayment.setText(DownPay);
                            Disbursement.setText(Disbursement1);


                            int emi_type= 0,emi_type1=0,emi_type2=0,emi_type3=0;
                            int E1mT1,E2mT1,E3mT1,E4mT1,E5mT1,E1mT2,E2mT2,E3mT2,E4mT2,E5mT2;


                            int Tenure = Integer.valueOf(tunerPP.toString().trim());



                            String payedEMI       = jsonChildNode.optString("payedEMI").toString();
                            String notPayedEM       = jsonChildNode.optString("notPayedEM").toString();

                            SchemeType.setText(payedEMI + "|" + notPayedEM + "  EMI Type");

                            /*

                            if(scheme.equals("1")){
    E1mT1 = (80*Tenure)/100;
    E1mT2 =  Tenure-E1mT1;
    emi_type1=E1mT2;
                                SchemeType.setText(E1mT2+"|"+E1mT1+"  EMI Type");

}
                            if(scheme.equals("2")) {

                                E2mT1 = (70 * Tenure) / 100;
                                E2mT2 = Tenure - E2mT1;

                                emi_type2 = E2mT2;

                                SchemeType.setText(E2mT2 + "|" + E2mT1 + "  EMI Type");
                            }

                            if(scheme.equals("3")) {
                                E3mT1 = (60 * Tenure) / 100;
                                E3mT2 = Tenure - E3mT1;

                                emi_type3 = E3mT2;

                                SchemeType.setText(E3mT2 + "|" + E3mT1 + "  EMI Type");
                            }
                            if(scheme.equals("4")) {
                                E4mT1 = (50 * Tenure) / 100;
                                E4mT2 = Tenure - E4mT1;

                                emi_type3 = E4mT2;

                                SchemeType.setText(E4mT2 + "|" + E4mT1 + "  EMI Type");
                            }
                            if(scheme.equals("5")) {
                                E5mT1 = (40 * Tenure) / 100;
                                E5mT2 = Tenure - E5mT1;

                                emi_type3 = E5mT2;

                                SchemeType.setText(E5mT2 + "|" + E5mT1 + "  EMI Type");
                            }

*/
                            TextView textViewOrder = (TextView)findViewById(R.id.textViewOrder);
                            textViewOrder.setText("Customer Loan Process ID is "+order_id+"("+product_type+") Of Type "+product_compny+"("+product_name+") and Status is "+status);

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
                            // EditText  txtNameAddress = (EditText) findViewById(R.id.LandMark1);
                            EditText  txtNameCity = (EditText) findViewById(R.id.City1);
                            EditText  PinZip= (EditText) findViewById(R.id.PIN1);
                            EditText  txtNameEmailP = (EditText) findViewById(R.id.txtNameEmail);
                            EditText  txtNamePANP= (EditText) findViewById(R.id.txtNamePAN);



                            txtNameCusto.setText(customer_name);
                            txtNameFather.setText(father_name);
                            txtNameMobile.setText(mobile);
                            // txtNameAddress.setText(land_mark5);
                            txtNameCity.setText(city);
                            PinZip.setText(zipP);

                            txtNameEmailP.setText(Pemail);
                            txtNamePANP.setText(pan_number);


                            Cadd 	= (ImageView) findViewById(R.id.imageViewP);
                            CaddA 	= (ImageView) findViewById(R.id.imageViewA);
                            CaddD 	= (ImageView) findViewById(R.id.imageViewD);
                            CaddV 	= (ImageView) findViewById(R.id.imageViewV);


                            LinearLayout h1=(LinearLayout)findViewById(R.id.h1);
                            LinearLayout h2=(LinearLayout)findViewById(R.id.h2);
                            LinearLayout h3=(LinearLayout)findViewById(R.id.h3);
                            LinearLayout h4=(LinearLayout)findViewById(R.id.h4);
                            LinearLayout h5=(LinearLayout)findViewById(R.id.h5);
                            LinearLayout h6=(LinearLayout)findViewById(R.id.h6);

                            if(product_type.equals("Sartha Sahayak")||product_type.equals("Sartha Sahayak Plus")||
                                    product_type.equals("Sartha Subharambh")||product_type.equals("Sartha Subharambh Plus")){

                                h1.setVisibility(View.GONE);
                                h2.setVisibility(View.GONE);
                                h3.setVisibility(View.GONE);
                                h4.setVisibility(View.GONE);
                                h5.setVisibility(View.GONE);
                                h6.setVisibility(View.GONE);

                                productDetail.setVisibility(View.GONE);
                            }else{
                            }

 /*
                            TextView txtNameMother = (TextView)findViewById(R.id.txtNameMother);
                            TextView dob = (TextView)findViewById(R.id.dob);
                            TextView No_of_Dependents = (TextView)findViewById(R.id.No_of_Dependents);
                            TextView aadhar_num = (TextView)findViewById(R.id.aadhar_num);
                            TextView DL = (TextView)findViewById(R.id.DL);
                            TextView LandMark1 = (TextView)findViewById(R.id.LandMark1);
                            TextView LandMark2 = (TextView)findViewById(R.id.LandMark2);
                            TextView PIN1 = (TextView)findViewById(R.id.PIN1);
                            TextView PIN2 = (TextView)findViewById(R.id.PIN2);
                            TextView City1 = (TextView)findViewById(R.id.City1);
                            TextView City2 = (TextView)findViewById(R.id.City2);
                            TextView State1 = (TextView)findViewById(R.id.State1);
                            TextView State2 = (TextView)findViewById(R.id.State2);

                            TextView recidentcontact = (TextView)findViewById(R.id.recidentcontact);
                            TextView contactOffice = (TextView)findViewById(R.id.contactOffice);
                            TextView Nationality = (TextView)findViewById(R.id.Nationality);
                            TextView AnnualIncome = (TextView)findViewById(R.id.AnnualIncome);




                            Glide.with(Approve_Group_Details.this)
                                    .load(photo)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(Cadd);

                            //  Picasso.with(Approve_Group_Details.this)
                            //.(R.drawable.ic_launcher)
                            //        .load(photo)
//                                    .into(Cadd);
                        Glide.with(Approve_Group_Details.this)
                                    //.(R.drawable.ic_launcher)

                                    .load(aadhar_card)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(CaddA);

                            Glide.with(Approve_Group_Details.this)
                                    //.(R.drawable.ic_launcher)
                                    .load(driving_licence)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(CaddD);

                            Glide.with(Approve_Group_Details.this)
                                    //.(R.drawable.ic_launcher)
                                    .load(voter_id)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(CaddV);
*/
                            //imageLoader = new ImageLoader(getApplicationContext());

                            // imageLoader.DisplayImage(photo, Cadd);
                            // imageLoader.DisplayImage(aadhar_card, CaddA);
                            // imageLoader.DisplayImage(driving_licence, CaddD);
                            // imageLoader.DisplayImage(voter_id, CaddV);
                            new DownloadImageFromInternet(Cadd).execute(photo.replace("http","https"));
                            new DownloadImageFromInternet(CaddA).execute(aadhar_card.replace("http","https"));
                            new DownloadImageFromInternet(CaddD).execute(driving_licence.replace("http","https"));
                            new DownloadImageFromInternet(CaddV).execute(voter_id.replace("http","https"));
//                            new DownloadImageFromInternet(CCCCC).execute(Disbus_image);


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
        private ProgressDialog Dialog = new ProgressDialog(Approve_Group_Details.this);
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
    public class ManagerApprove  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Approve_Group_Details.this);
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


                Log.i("GCM",ApproveSend_Link);

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

                            if(under_depart.equals("DSA Person")){
                                intent1 = new Intent(Approve_Group_Details.this, Marketing.class);
                            }if(under_depart.equals("Manager")){
                                intent1 = new Intent(Approve_Group_Details.this, Manager_Launch.class);
                            }if(under_depart.equals("Admin")){
                                intent1 = new Intent(Approve_Group_Details.this, Admin_Launch.class);
                            }

                            Toast.makeText(getApplicationContext(), "Sent.", Toast.LENGTH_LONG).show();

                            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent1);

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


                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Approve_Group_Details.this);
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
                                        if(sts.equals("CLEAR")){
                                            new ManagerApprove().execute(Sales_Profile_Query_Send_Link,cid,cname,deviceIMEI,empname,regid,cc,lat,lon);
                                        }
                                    }
                                    if(under_depart.equals("Manager")){

                                        if(sts.equals("PENDING")){
                                            new ManagerApprove().execute(Manager_Profile_Query_Send_Link,cid,cname,deviceIMEI,empname,regid,cc,lat,lon);
                                        }if(sts.equals("DONE")){
                                            new ManagerApprove().execute(Manager_Finance_Query_Send_Link,cid,cname,deviceIMEI,empname,regid,cc,lat,lon);
                                        }
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
        private ProgressDialog Dialog = new ProgressDialog(Approve_Group_Details.this);
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

            Glide.with(Approve_Group_Details.this)
                    //.(R.drawable.ic_launcher)
                    .load(searchResults.get(position).get("photo").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.photo);
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

    public void showAppCasece(View view){
        mBottomSheetDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.zview_approved_casece, null);
        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window));

        FancyButton onBack = (FancyButton)v.findViewById(R.id.TaskM2441);


        inputSearch = (EditText) v.findViewById(R.id.inputSearch);

        proName = (TextView) v.findViewById(R.id.textPoster);
        // proName.setText("Select " + product);
        // mylistview1 = (ListView)v. findViewById(R.id.listViewtotal);

        listsubR.clear();
        listsub1.clear();
        listsub2.clear();
        listsub3.clear();
        icon.clear();

        new Approve_Summ_LongOperation().execute(Approve_Summ_LongOperation_url,cid,"","");
//        prog = (ProgressView)v.findViewById(R.id.progress_pv_circular_inout_colors);

        //       prog.start();



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




        onBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onPause();
            }

        });

        mBottomSheetDialog.contentView(v)
                .show();


    }


    public void save(View view){

        EditText txtNameCusto = (EditText) findViewById(R.id.txtNameCusto);
        EditText  txtNameFather = (EditText) findViewById(R.id.txtNameFather);
        EditText  txtNameMobile = (EditText) findViewById(R.id.txtNameMobile);
        EditText  txtNameAddress = (EditText) findViewById(R.id.LandMark1);
        EditText  txtNameCity = (EditText) findViewById(R.id.City1);
        EditText  PinZip= (EditText) findViewById(R.id.PIN1);
        EditText  txtNameEmailP = (EditText) findViewById(R.id.txtNameEmail);
        EditText  txtNamePANP= (EditText) findViewById(R.id.txtNamePAN);


        txtNameMother1=txtNameMother.getText().toString().trim() ;
        dob1=dob.getText().toString().trim() ;

        if(dob1.trim().length() < 1){
            Toast.makeText(Approve_Group_Details.this, "Please Select DOB", Toast.LENGTH_SHORT).show();
            return;
        }

  /*
    //  String dateStr = "21/20/2011";
    DateFormat srcDf = new SimpleDateFormat("dd-mm-yyyy");
    Date date = null;
    try {
        date = srcDf.parse(dob1);
    } catch (ParseException e) {
        e.printStackTrace();
    }
    DateFormat destDf = new SimpleDateFormat("yyyy-mm-dd");
    dob1 = destDf.format(date);
    //Toast.makeText(this,""+dob1, Toast.LENGTH_SHORT).show();
*/
        No_of_Dependents1=No_of_Dependents.getText().toString().trim() ;
        aadhar_num1 =aadhar_num.getText().toString().trim();
        DL1=DL.getText().toString().trim();
        LandMark11=LandMark1.getText().toString().trim();
        LandMark21=LandMark2.getText().toString().trim();
        LandMark31=LandMark3.getText().toString().trim();
        PIN11=PIN1.getText().toString().trim();
        PIN21=PIN2.getText().toString().trim();
        PIN31=PIN3.getText().toString().trim();
        City11=City1.getText().toString().trim();
        City21=City2.getText().toString().trim();
        City31=City3.getText().toString().trim();
        State11=State1.getText().toString().trim();
        State21=State2.getText().toString().trim();
        State31=State3.getText().toString().trim();


        OccupationS= Occupation.getText().toString().trim();
        OuaS= Qua.getText().toString().trim();

        recidentcontact1=recidentcontact.getText().toString().trim();
        contactOffice1=contactOffice.getText().toString().trim();
        Nationality1=Nationality.getText().toString().trim();
        AnnualIncome1=AnnualIncome.getText().toString().trim();
        txtNameFather1=txtNameFather.getText().toString().trim();
        no_year_current1=no_year_current.getText().toString().trim();
        productD1=productD.getText().toString().trim();
        imei1=imei.getText().toString().trim();



        Address1= (EditText) findViewById(R.id.Address1);
        Address2= (EditText) findViewById(R.id.Address2);
        Address3= (EditText) findViewById(R.id.Address3);


        Address11=Address1.getText().toString();

        Address12=Address2.getText().toString();

        Address13=Address3.getText().toString();


  /*
    EditText txtNameCusto = (EditText) findViewById(R.id.txtNameCusto);
    EditText  txtNameFather = (EditText) findViewById(R.id.txtNameFather);
    EditText  txtNameMobile = (EditText) findViewById(R.id.txtNameMobile);
    EditText  txtNameAddress = (EditText) findViewById(R.id.txtNameAddress);
    EditText  txtNameCity = (EditText) findViewById(R.id.txtNameCity);
    EditText  PinZip= (EditText) findViewById(R.id.PinZip);
    EditText  txtNameEmailP = (EditText) findViewById(R.id.txtNameEmailP);
    EditText  txtNamePANP= (EditText) findViewById(R.id.txtNamePANP);
*/


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


                                        if(txtNameMother1.trim().length() > 2){
                                            if(dob1.trim().length() > 2){
                                                if(No_of_Dependents1.trim().length() > 0){
                                                    // if(aadhar_num1.trim().length() > 2){
                                                    // if(DL1.trim().length() > 0){
                                                    if(PIN11.trim().length() > 2){
                                                        if(City11.trim().length() > 2){
                                                            if(State11.trim().length() > 2){
                                                                if(LandMark11.trim().length() > 2){
                                                                    if(Address11.trim().length() > 2){

                                                                        // if(recidentcontact1.trim().length() > 9){
                                                                        if(Nationality1.trim().length() > 2){
                                                                            if(AnnualIncome1.trim().length() > 2){
                                                                                if(txtNameFather1.trim().length() > 2){
                                                                                    if(no_year_current1.trim().length() > 0){
                                                                                        if(productD1.trim().length() > 2){

                                                                                            if(OuaS.trim().length() < 2){
                                                                                                Toast.makeText(Approve_Group_Details.this, "Please Select Qualification ", Toast.LENGTH_SHORT).show();
                                                                                                return;
                                                                                            }

                                                                                            if(OccupationS.trim().length() < 2){
                                                                                                Toast.makeText(Approve_Group_Details.this, "Please Select Occupation", Toast.LENGTH_SHORT).show();
                                                                                                return;
                                                                                            }



                                                                                            // if(c1.equals("1")){
                                                                                            //  if(c2.equals("1")){
                                                                                            //    if(c3.equals("1")){
                                                                                            //      if(c4.equals("1")){

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

                                                                                                            new LongOperation().execute(SERVER_URL, Name, Fname, tunMobile, Address, City, Zip, Xiemail, XPan, empname, regid, lat, lon, deviceIMEI, cid,bankName,BankBranchName,AccountNum,IFSCC,
                                                                                                                    txtNameMother1,
                                                                                                                    dob1 ,
                                                                                                                    No_of_Dependents1 ,
                                                                                                                    aadhar_num1 ,
                                                                                                                    DL1,
                                                                                                                    LandMark11,
                                                                                                                    LandMark21,
                                                                                                                    LandMark31,
                                                                                                                    PIN11,
                                                                                                                    PIN21,
                                                                                                                    PIN31,
                                                                                                                    City11,
                                                                                                                    City21,
                                                                                                                    City31,
                                                                                                                    State11,
                                                                                                                    State21,
                                                                                                                    State31,

                                                                                                                    recidentcontact1,
                                                                                                                    contactOffice1,
                                                                                                                    Nationality1,
                                                                                                                    AnnualIncome1,
                                                                                                                    txtNameFather1,
                                                                                                                    no_year_current1,
                                                                                                                    productD1,
                                                                                                                    imei1,

                                                                                                                    gender,
                                                                                                                    married,
                                                                                                                    categury,
                                                                                                                    house,

                                                                                                                    Address11,
                                                                                                                    Address12,
                                                                                                                    Address13,
                                                                                                                    OccupationS,
                                                                                                                    OuaS


                                                                                                            );


                                                                                                            //onStartTransaction();
                                                                                                        }
                                                                                                    })

                                                                                                    .setNegativeButton("Cancel", null)						//Do nothing on no
                                                                                                    .show();


                                                                                            //           }else{Toast.makeText(Approve_Group_Details.this, "Please Take Address Prof Image", Toast.LENGTH_SHORT).show();return;}
                                                                                            //           }else{Toast.makeText(Approve_Group_Details.this, "Please Take Pan Card Image", Toast.LENGTH_SHORT).show();return;}
                                                                                            //        }else{Toast.makeText(Approve_Group_Details.this, "Please Take Aadhar Card Image", Toast.LENGTH_SHORT).show();return;}
                                                                                            //        }else{Toast.makeText(Approve_Group_Details.this, "Please Take Customer Photo", Toast.LENGTH_SHORT).show();return;}


                                                                                        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Product Details", Toast.LENGTH_SHORT).show();return;}

                                                                                    }else{Toast.makeText(Approve_Group_Details.this, "Please Enter No of year current Residence", Toast.LENGTH_SHORT).show();return;}
                                                                                }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Father Name", Toast.LENGTH_SHORT).show();return;}

                                                                            }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Annual Income ", Toast.LENGTH_SHORT).show();return;}

                                                                        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Nationality", Toast.LENGTH_SHORT).show();return;}
                                                                        // }else{Toast.makeText(Approve_Group_Details.this, "Please Residence Contact", Toast.LENGTH_SHORT).show();return;}

                                                                    }else{Toast.makeText(Approve_Group_Details.this, "Please Residence Address", Toast.LENGTH_SHORT).show();return;}

                                                                }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Residence Address Land Mark", Toast.LENGTH_SHORT).show();return;}
                                                            }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Residence Address State", Toast.LENGTH_SHORT).show();return;}
                                                        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Residence Address City", Toast.LENGTH_SHORT).show();return;}
                                                    }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Residence Address PIN", Toast.LENGTH_SHORT).show();return;}
                                                    //   }else{Toast.makeText(Approve_Group_Details.this, "Please Enter DL", Toast.LENGTH_SHORT).show();return;}
                                                    //  }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Aadhar Number", Toast.LENGTH_SHORT).show();return;}
                                                }else{Toast.makeText(Approve_Group_Details.this, "Please Enter No of Dependents", Toast.LENGTH_SHORT).show();return;}
                                            }else{Toast.makeText(Approve_Group_Details.this, "Please Select DOB", Toast.LENGTH_SHORT).show();return;}
                                        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Mother Name", Toast.LENGTH_SHORT).show();return;}


                                    }else{Toast.makeText(Approve_Group_Details.this, "Please Enter PAN Number", Toast.LENGTH_SHORT).show();return;}

                                }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Email ID", Toast.LENGTH_SHORT).show();return;}

                            }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Zip Code", Toast.LENGTH_SHORT).show();return;}

                        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter City Name", Toast.LENGTH_SHORT).show();return;}

                    }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Address", Toast.LENGTH_SHORT).show();return;}

                }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();return;}

            }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Father's Name", Toast.LENGTH_SHORT).show();return;}

        }else{Toast.makeText(Approve_Group_Details.this, "Please Enter Name", Toast.LENGTH_SHORT).show();return;}

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

    /*
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
                // Cadd1.setImageBitmap(bitmap);
                // Cadd.setImageBitmap(bitmap);

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


                //   img1 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                // String photo1="image";
                // if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                // }else {
                // new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, deviceIMEI, img1);

                if(Ckeck==1){

                    img1 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);
                    Cadd.setImageBitmap(bitmap);

                    String photo1="image";
                    if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    }else {
                        //
                        //  c1="1";
                        checkPhoto="CustomerDetailDone";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img1);
                    }
                }
                if(Ckeck==2){
                    img2 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                    CaddA.setImageBitmap(bitmap);
                    String photo1="aadhar_card";
                    if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    }else {
                        // c2="1"; checkPhoto="CustomerDetailDone";
                        checkPhoto2="CustomerDetailDone";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img2);
                    }
                }
                if(Ckeck==3){
                    img3 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                    CaddD.setImageBitmap(bitmap);
                    String photo1="driving_licence";
                    if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    }else {
                        // c3="1";
                        checkPhoto3="CustomerDetailDone";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img3);
                    }
                }
                if(Ckeck==4){
                    img4 = android.util.Base64.encodeToString(bb, android.util.Base64.DEFAULT);

                    CaddV.setImageBitmap(bitmap);
                    if(sts=="DONE"){if(status=="DONE"){Toast.makeText(getBaseContext(), "Customer Details is Completed!", Toast.LENGTH_SHORT).show();return;}}if(status=="DONE"){
                    }else {
                        String photo1 = "voter_id";
                        //c4="1";
                        checkPhoto4="CustomerDetailDone";
                        new CustomerPhotoLongOperation().execute(Customer_uSERVER_URL_oneGG, photo1, cid, cname, deviceIMEI, img4);
                    }
                }

            }
        }
    }

    */
    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");

            if(Ckeck==1) {

                CropIntent.putExtra("outputX", 250);
                CropIntent.putExtra("outputY", 250);
                CropIntent.putExtra("aspectX", 1);
                CropIntent.putExtra("aspectY", 1);
            }
            if(Ckeck==2) {

                CropIntent.putExtra("outputX", 500);
                CropIntent.putExtra("outputY", 250);
                CropIntent.putExtra("aspectX", 2);
                CropIntent.putExtra("aspectY", 1);
            }  if(Ckeck==3) {

                CropIntent.putExtra("outputX", 500);
                CropIntent.putExtra("outputY", 250);
                CropIntent.putExtra("aspectX", 2);
                CropIntent.putExtra("aspectY", 1);
            }  if(Ckeck==4) {
                CropIntent.putExtra("outputX", 500);
                CropIntent.putExtra("outputY", 250);
                CropIntent.putExtra("aspectX", 2);
                CropIntent.putExtra("aspectY", 1);
            }
            CropIntent.putExtra("scaleUpIfNeeded", true);


            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

            Toast.makeText(Approve_Group_Details.this, "Camera Crop Option not Support!", Toast.LENGTH_LONG).show();
        }
    }
    //Image Crop Code End Here

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Approve_Group_Details.this,
                Manifest.permission.CAMERA)) {

            Toast.makeText(Approve_Group_Details.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Approve_Group_Details.this, new String[]{
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

    /*
    public static class UIUtils {


        public static boolean setListViewHeightBasedOnItems(ListView listView) {

            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter != null) {

                int numberOfItems = listAdapter.getCount();

                // Get total height of all items.
                int totalItemsHeight = 0;
                for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                    View item = listAdapter.getView(itemPos, null, listView);
                    item.measure(0, 0);
                    totalItemsHeight += item.getMeasuredHeight();
                }

                // Get total height of all item dividers.
                int totalDividersHeight = listView.getDividerHeight() *
                        (numberOfItems - 1);

                // Set list height.
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = totalItemsHeight + totalDividersHeight;
                listView.setLayoutParams(params);
                listView.requestLayout();

                return true;

            } else {
                return false;
            }

        }
}


    public static class Utility {
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }
            //I added this to try to fix half hidden row
            totalHeight++;

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }



    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    public static class Utility {
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }
    */

    public static void setListViewHeight(ListView listview){
        ListAdapter listAdapter = listview.getAdapter();
        if(listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        // int desiredWidth = MeasureSpec.makeMeasureSpec(listview.getWidth(), MeasureSpec.AT_MOST);
        for(int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listview);
            //listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight + (listview.getDividerHeight()*(listAdapter.getCount() - 1)) + totalHeight;
        listview.setLayoutParams(params);
        //listview.requestLayout();
    }

    public void OnAnsware(View view){
        if (!sts.equals("QUERY")) {

        }else{
            if(under_depart.equals("Manager")){
                String appro="ANS2";
                showBottomSheet
                        (appro);
            }
            if(under_depart.equals("DSA Person")) {
                if (checkPhoto.equals("null") || checkPhoto.equals("")|| checkPhoto.length()<5) {
                    String appro = "ANS";
                    showBottomSheet(appro);
                } else {
                    String appro = "ANS1";
                    showBottomSheet(appro);

                }
            }

        }
    }



    public void datapick(View view){
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
                Toast.makeText(Order.this, "Date is " + date, Toast.LENGTH_SHORT).show();
                EditText dob=(EditText)findViewById(R.id.dob);
                dob.setText(y+"-"+m1+"-"+d);
                // tbl=null;
                //disbusLoanDate(y,m1,d);
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

        */
    }

    @Override
    protected android.app.Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
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
        EditText dob=(EditText)findViewById(R.id.dob);
        dob.setText(new StringBuilder().append(day).append("-").append(month).append("-").append(year));

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

    public void selectState(){
        infracom.abcr.sarthamicrofinance.material.app.Dialog.Builder builder = null;

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
            /*
                if(sta==1){
                    State1.setText(getSelectedValue());
                } if(sta==2){
                    State2.setText(getSelectedValue());
                } if(sta==3){
                    State3.setText(getSelectedValue());
                }
            */
                //Toast.makeText(Order.this, "You have selected " + getSelectedValue() + " as phone ringtone.", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(Approve_Group_Details.this, "Cancelled" , Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder)builder).items(new String[]{"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Orissa", "Pondicherry", "Punjab",
                "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttaranchal", "Uttar Pradesh", "West Bengal"}, 0)
                .title("Select State")
                .positiveAction("OK")
                .negativeAction("CANCEL");


        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);

    }

    public void OnSelectState(View view){
        //   sta=1;
        selectState();
    }
    public void OnSelectState2(View view){
        //    sta=2;
        selectState();
    }
    public void OnSelectState3(View view){
        //sta=3;
        selectState();
    }

    public void Qualification(View view){
        // sta=3;
        selectQualification();
    }
    public void Occupation(View view){
        //sta=3;
        selectOccupation();
    }

    public void selectQualification(){
        infracom.abcr.sarthamicrofinance.material.app.Dialog.Builder builder = null;

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                Qua.setText(getSelectedValue());
                //Toast.makeText(Order.this, "You have selected " + getSelectedValue() + " as phone ringtone.", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(Approve_Group_Details.this, "Cancelled" , Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder)builder).items(new String[]{
                "10 + 2",
                "10th Pass",
                "8th Pass",
                "Diploma",
                "Graduate",
                "Under Graduate",
                "Other"
        }, 0)
                .title("Select Qualification")
                .positiveAction("OK")
                .negativeAction("CANCEL");


        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);

    }

    public void selectOccupation(){
        infracom.abcr.sarthamicrofinance.material.app.Dialog.Builder builder = null;

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                Occupation.setText(getSelectedValue());

                //Toast.makeText(Order.this, "You have selected " + getSelectedValue() + " as phone ringtone.", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(Approve_Group_Details.this, "Cancelled" , Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder)builder).items(new String[]{

                "Self Employee",
                "Service",
                "Other"
        }, 0)
                .title("Select Occupation")
                .positiveAction("OK")
                .negativeAction("CANCEL");


        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);

    }


}