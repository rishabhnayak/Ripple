package infracom.abcr.sarthamicrofinance.Order;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;

import infracom.abcr.sarthamicrofinance.CATEGORIES.Sartha_Product;
import infracom.abcr.sarthamicrofinance.Main;
import infracom.abcr.sarthamicrofinance.material.app.DialogFragment;
import infracom.abcr.sarthamicrofinance.material.app.SimpleDialog;
import infracom.abcr.sarthamicrofinance.material.app.ThemeManager;
import infracom.abcr.sarthamicrofinance.material.widget.ProgressView;
import infracom.abcr.sarthamicrofinance.material.widget.RadioButton;
import mehdi.sakout.fancybuttons.FancyButton;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import infracom.abcr.sarthamicrofinance.Config;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.material.app.BottomSheetDialog;
import infracom.abcr.sarthamicrofinance.material.drawable.ThemeDrawable;
import infracom.abcr.sarthamicrofinance.material.util.ViewUtil;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order extends AppCompatActivity {


    String selectedImagePath = "",lonper11= "",lonper12,lonper13;
    ListView mylistview1;
    TextView inputSearch1;
    String serverURL1 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/empListDealer.php";
//    private ProgressDialog Dialog = new ProgressDialog(Order.this);
    private Handler mHandler;
    ProgressView prog;
    ArrayList<String> listsubR = new ArrayList<String>();

    ArrayList<String> listsub1 = new ArrayList<String>();
    ArrayList<String> listsub2 = new ArrayList<String>();

    ArrayList<String> listsub3 = new ArrayList<String>();

    ArrayList<String> listsub4 = new ArrayList<String>();
    ArrayList<String> listsub5 = new ArrayList<String>();
    ArrayList<String> icon = new ArrayList<String>();


    ArrayList<HashMap<String, Object>> originalValues1=null;
    ArrayList<HashMap<String, Object>> searchResults1=null;


    String num,Mnum,SelectCat, perNew, ProPer,empname,regid,user_id;
    private static double percent;
    private static double addEmi;
    private static double tot;
    int tunnr;
    TextView  Amount;
    TextView LoanAmount;
    EditText txtNameTuner1,ROIApplied,LTVPolicy;
    TextView editText2Emi,txtNameTotal,textView7;
    DBAdapter db;


    private int year, month, day;

int emi_type= 0,emi_type1=0,emi_type2=0,emi_type3=0,emi_type4=0,emi_type5=0;
    private BottomSheetDialog mBottomSheetDialog;

    GPSTracker gps;
    double latitude;
    double longitude;

    String deviceIMEI = "" ,LTV_Policy, ROI_policy,OldNewPercent;

    Controller aController = null;
    private ProgressDialog pDialog;

    String payedEMI,notPayedEM;

    private static final String TAG = Order.class.getSimpleName();  // Register button
    Button btnRegister;
    //private SQLiteHandler db;
    public static String URL_LOGIN = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/login.php";
    // WebServer Request URL to get All registered devices
    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Order.php";

    EditText inputSearchPrice, inputSearchPricePer, txtNamePrice2, tenurEdit,EmiFinaml, downPricel,txtNameDisbus,txtDownPay,LoanDisbursement,DownPayment,AdvanceEMIPay,PF;

    RadioButton rb1, rb12;
    RadioButton rb2, rb22;
    RadioButton rb3, rb32;
    RadioButton rb4, rb42;
    RadioButton rb5, rb52;
    TextView intrestRTEdit, EMITot, emiTypeTotalA, txtNameTotalPay,TunerTypue;

    public static String checkTun,chkType;
    int E1mT1,E2mT1,E3mT1,E1mT2,E2mT2,E3mT2,E4mT2,E5mT2,E4mT1,E5mT1;


    RadioButton Male;
    RadioButton Female;

    RadioButton MYES;
    RadioButton MNO;

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
    TextView dob;
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

    TextView Occupation;
    TextView Qua;
    TextView ProcessingFee;

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
    String R_name1, R_Adress1, R_Contact1, R_name2, R_Adress2, R_Contact2;
    EditText R_name1a, R_Adress1a, R_Contact1a, R_name2a, R_Adress2a, R_Contact2a;

    String OuaS;
    String OccupationS;

    String State31;

    String recidentcontact1;
    String contactOffice1;
    String Nationality1;
    String AnnualIncome1,txtNameFather1,no_year_current1,productD1,imei1,Address11,Address12,Address13;

int sta=0;

    String branch_name,branch_id;


    private Calendar calendar, calendar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        mHandler = new Handler();

        TextView  lblMessage = (TextView) findViewById(R.id.cccc);

        TextView  textView6 = (TextView) findViewById(R.id.textViewOrder);
        Amount = (TextView) findViewById(R.id.txtNamePrice);
        LoanAmount = (TextView) findViewById(R.id.LoanAmount);
        txtNameTotal= (TextView) findViewById(R.id.txtNameTotal);
        txtNameTuner1 = (EditText) findViewById(R.id.txtNameTuner1);
        editText2Emi = (TextView) findViewById(R.id.editText2Emi);
        textView7= (TextView) findViewById(R.id.textView7);


        AdvanceEMIPay = (EditText) findViewById(R.id.AdvanceEMIPay);
        DownPayment = (EditText) findViewById(R.id.zx);
        LoanDisbursement= (EditText) findViewById(R.id.LoanDisbursement);


        TunerTypue= (TextView) findViewById(R.id.TunerTypue);

        FancyButton TaskMoreOP = (FancyButton) findViewById(R.id.Taskmino);

        TaskMoreOP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                showBottomSheet();

            }

        });

        DBAdapter.init(this);

        // Get Global variable instance
        aController = (Controller) getApplicationContext();

        pDialog = new ProgressDialog(this);
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();

        // Check if Internet Connection present
        if (!aController.isConnectingToInternet()) {

            // Internet Connection is not present
            aController.showAlertDialog(Order.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);

            // stop executing code by return
            return;
        }

        // Check if GCM configuration is set
        if (Config.YOUR_SERVER_URL == null ||
                Config.GOOGLE_SENDER_ID == null ||
                Config.YOUR_SERVER_URL.length() == 0 ||
                Config.GOOGLE_SENDER_ID.length() == 0)
        {

            // GCM sernder id / server url is missing
            aController.showAlertDialog(Order.this,
                    "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID",
                    false);

            // stop executing code by return
            return;
        }

        Intent inte = getIntent();
        String[] data = new String[0];


        num = inte.getStringExtra("data0");
        Mnum = inte.getStringExtra("data1");
        //Mnum="Product";
        SelectCat = inte.getStringExtra("data2");
        perNew = inte.getStringExtra("per");
        //perNew="75";
        ProPer = inte.getStringExtra("ProPer");

        OldNewPercent=ProPer;

/*
        int foo = Integer.parseInt(num);
        for (int i = 0; i < foo; i++){
            data[i] = inte.getStringExtra("data"+i);

        }
        */

      //  BlurBehind.getInstance()
        //        .withAlpha(200)
          //      .withFilterColor(Color.parseColor("#222222"))
            //    .setBackground(this);

        lblMessage.setText(Mnum+"("+num+") Interest Rates Will be "+ProPer+"% of Product Price.");
        textView6.setText(SelectCat+"("+Mnum+"("+num+")"+" Loan Amount Will Accept with "+perNew+"% of Product Price.)");


        db = new DBAdapter(this);

        // db.open();
        HashMap<String, String> dataf = db.getLogininfo();
        String email = dataf.get("email");
        empname = dataf.get("name");
        regid = dataf.get("regid");
        String empdevice_imei = dataf.get("device_imei");
        String under_depart = dataf.get("under_depart");

        user_id = dataf.get("name");
        deviceIMEI = empdevice_imei;

        branch_name =  dataf.get("branch_name");
        branch_id =  dataf.get("branch_id");

        Toast.makeText(
                getApplicationContext(),""+deviceIMEI, Toast.LENGTH_SHORT).show();
        db.close();

        Button subButt = (Button) findViewById(R.id.submit);

        if(under_depart.equals("Dealer")){
            subButt.setVisibility(View.GONE);
        }
        else {
            subButt.setVisibility(View.VISIBLE);
        }


        gps = new GPSTracker(Order.this);

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


        final LinearLayout padd=(LinearLayout)findViewById(R.id.padd);
        padd.setVisibility(View.GONE);


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


        gender = "Male";
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


        married="Yes";
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


        categury="ST";
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


        house="Parental";
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


        Occupation = (TextView)findViewById(R.id.Occupation);
        Qua = (TextView)findViewById(R.id.Qua);

        productD = (TextView)findViewById(R.id.productD);
        imei = (TextView)findViewById(R.id.imei);

         recidentcontact = (TextView)findViewById(R.id.recidentcontact);
         contactOffice = (TextView)findViewById(R.id.contactOffice);
         Nationality = (TextView)findViewById(R.id.Nationality);
         AnnualIncome = (TextView)findViewById(R.id.AnnualIncome);

        R_name1a = (EditText) findViewById(R.id.rname1a);
        R_Adress1a = (EditText)findViewById(R.id.raddress1a);
        R_Contact1a = (EditText)findViewById(R.id.rcontact1a);
        R_name2a = (EditText)findViewById(R.id.rname2a);
        R_Adress2a = (EditText)findViewById(R.id.raddress2a);
        R_Contact2a = (EditText)findViewById(R.id.rcontact2a);

    }

    public  void CkeckLoan(){



        Amount = (TextView) findViewById(R.id.txtNamePrice);
        String am = Amount.getText().toString();

        if(am.trim().length() > 0){

        }else{
            Toast.makeText(Order.this, "Product Price Field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Double amount = Double.valueOf(Amount.getText().toString().trim());

        percent=Double.valueOf(perNew);


/*
        // if(num.toString().trim()=="Mobile"){}
        switch (SelectCat) {
            case "Electronics":
               // tabItemAvatar.setImageResource(R.drawable.electronics);
                Double amount = Double.valueOf(Amount.getText().toString().trim());

                percent=75.0;

               Double loan=(percent/100)*amount;
                LoanAmount.setText(loan.toString());

                break;
            case "Home Decor":
                Double amount1 = Double.valueOf(Amount.getText().toString().trim());
                percent=65.0;

                Double loan1=(percent/100)*amount1;
                LoanAmount.setText(loan1.toString());

                //tabItemAvatar.setImageResource(R.drawable.home_decore);
                break;
            case "Bikes":
                Double amount12 = Double.valueOf(Amount.getText().toString().trim());
                percent=55.0;

                Double loan12=(percent/100)*amount12;
                LoanAmount.setText(loan12.toString());
               // tabItemAvatar.setImageResource(R.drawable.bikes);
                break;
            case "Appliances":
               // tabItemAvatar.setImageResource(R.drawable.appliances);
                Double amount13 = Double.valueOf(Amount.getText().toString().trim());
                percent=50.0;

                Double loan13=(percent/100)*amount13;
                LoanAmount.setText(loan13.toString());
                break;
            case "Books & More":
                //tabItemAvatar.setImageResource(R.drawable.books_a_more);
                break;
            case "Men":
               // tabItemAvatar.setImageResource(R.drawable.men);
                break;
            case "Women":
               // tabItemAvatar.setImageResource(R.drawable.women);
                break;
            case "Baby & Kids":
               // tabItemAvatar.setImageResource(R.drawable.baby_a_kids);
                break;
            case "Other":
                Double amount134 = Double.valueOf(Amount.getText().toString().trim());
                percent=50.0;

                Double loan134=(percent/100)*amount134;
                LoanAmount.setText(loan134.toString());

                //  tabItemAvatar.setImageResource(R.drawable.other);
                break;
            default:
               // tabItemAvatar.setImageResource(R.drawable.smartphone);
                break;
        }
*/

    }

    public void CkeckEMI(){



        txtNameTuner1 = (EditText) findViewById(R.id.txtNameTuner1);
        Amount = (TextView) findViewById(R.id.txtNamePrice);

        LoanAmount = (TextView) findViewById(R.id.LoanAmount);

        String tun = txtNameTuner1.getText().toString();
        String am = Amount.getText().toString();
        String lm = LoanAmount.getText().toString();

        //String tun=txtNameTuner.toString().trim();

        if(am.trim().length() > 0){

        }else{
            Toast.makeText(Order.this, "Product Price Field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(tun.trim().length() > 0){

        }else{
            Toast.makeText(Order.this, "Tuner Field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Double amount = Double.valueOf(LoanAmount.getText().toString().trim());
        int tunner = Integer.valueOf(txtNameTuner1.getText().toString().trim());

        if(lm.trim().length() > 0){

        }else{
            Toast.makeText(Order.this, "Please Check Loan Amount", Toast.LENGTH_SHORT).show();
            return;
        }

        Double lamount = Double.valueOf(LoanAmount.getText().toString().trim());
        //LoanAmount
        Double emi=amount/tunner;
        percent=Double.valueOf(ProPer);

        Double loanPer=(percent/100)*amount;


         addEmi= emi+loanPer;

        Double NetPay = emi+loanPer;


        editText2Emi.setText("EMI is "+addEmi);
        textView7.setText("EMI is "+addEmi+" Rs./For Each, with "+ProPer+"% of Product Price." );

        tot = NetPay*tunner;
        txtNameTotal.setText("Total: "+tot);
    }

    public void Post(View view){


        try
        {
        EditText txtNameName = (EditText) findViewById(R.id.txtNameName);
        EditText  txtNameEmail = (EditText) findViewById(R.id.txtNameEmail);
        EditText  txtNameMobile = (EditText) findViewById(R.id.txtNameMobile);
        EditText  txtNamePAN = (EditText) findViewById(R.id.txtNamePAN);

        txtNameTotal= (EditText) findViewById(R.id.txtNameTotal);
        EditText  editText2Emi = (EditText) findViewById(R.id.editText2Emi);

        final String tunName = txtNameName.getText().toString();
        final String tunEmail = txtNameEmail.getText().toString();
        final String tunMobile = txtNameMobile.getText().toString();
        final String tunPan = txtNamePAN.getText().toString();

        String emiP = txtNameTotal.getText().toString();
        String TotalA = editText2Emi.getText().toString();


        txtNameTuner1 = (EditText) findViewById(R.id.txtNameTuner1);
        Amount = (TextView) findViewById(R.id.txtNamePrice);
        LoanAmount = (TextView) findViewById(R.id.LoanAmount);
        final String tun = txtNameTuner1.getText().toString();
        final String am = Amount.getText().toString();
        final String lm = LoanAmount.getText().toString();

        final String lat = Double.toString(latitude);
        final String lon = Double.toString(longitude);


        final String AdvanceEMIPayP =AdvanceEMIPay.getText().toString().trim();
        final String DownPaymentP = DownPayment.getText().toString().trim();
        final String LoanDisbursementP= LoanDisbursement.getText().toString().trim();

        final String addEmiP;
        addEmiP = String.valueOf(addEmi);



        final String totP;
        totP = String.valueOf(tot);

         txtNameMother1=txtNameMother.getText().toString().trim() ;
         dob1=dob.getText().toString().trim() ;

        if(dob1.trim().length() < 1){
            Toast.makeText(Order.this, "Please Select DOB", Toast.LENGTH_SHORT).show();
            return;
        }

      //  String dateStr = "21/20/2011";
       /*
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


            R_name1=R_name1a.getText().toString();
            R_Adress1=R_Adress1a.getText().toString();
            R_Contact1 =R_Contact1a.getText().toString();
            R_name2 =R_name2a.getText().toString();
            R_Adress2 =R_Adress2a.getText().toString();
            R_Contact2=R_Contact2a.getText().toString();

        Address1= (EditText) findViewById(R.id.Address1);
        Address2= (EditText) findViewById(R.id.Address2);
        Address3= (EditText) findViewById(R.id.Address3);


        Address11=Address1.getText().toString();

        Address12=Address2.getText().toString();

        Address13=Address3.getText().toString();

//        String gender,married,categury,house;

        if(house.equals("Rented")) {

            if (PIN31.trim().length() > 2) {
                if (City31.trim().length() > 2) {
                    if (State31.trim().length() > 2) {
                        if (LandMark31.trim().length() > 2) {
                           // if (contactOffice1.trim().length() > 9) {} else { Toast.makeText(Order.this, "Please Office Permanent Contact", Toast.LENGTH_SHORT).show();return; }
                            if (Address12.trim().length() > 2) {
                                // if (contactOffice1.trim().length() > 9) {} else { Toast.makeText(Order.this, "Please Office Permanent Contact", Toast.LENGTH_SHORT).show();return; }
                            } else {
                                Toast.makeText(Order.this, "Please Permanent Address", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } else {
                            Toast.makeText(Order.this, "Please Permanent Address Mark", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        Toast.makeText(Order.this, "Please Permanent Address State", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(Order.this, "Please Permanent Address City", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(Order.this, "Please Permanent Address PIN", Toast.LENGTH_SHORT).show();
                return;
            }
        }


          //  if (imei1.trim().length() > 2) {} else {Toast.makeText(Order.this, "Please Enter Product Model Number/IMEI", Toast.LENGTH_SHORT).show();return;}


        if(tunName.trim().length() > 2){
            if(tunEmail.trim().length() > 4){
                if(tunMobile.trim().length() > 9){
                    if(tunPan.trim().length() > 5){
                        if(am.trim().length() > 0){
                            if(lm.trim().length() > 0){
                                if(tun.trim().length() > 0){
                                    if(emiP.trim().length() > 0){


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

                                                                                                    if(lonper11.trim().length() < 1){
                                                                                                        Toast.makeText(Order.this, "Please Select Dealer Code", Toast.LENGTH_SHORT).show();
                                                                                                        return;
                                                                                                    }
                                                                                                    if(OccupationS.trim().length() < 2){
                                                                                                        Toast.makeText(Order.this, "Please Select Occupation", Toast.LENGTH_SHORT).show();
                                                                                                        return;
                                                                                                    }
                                                                                                    if(OuaS.trim().length() < 2){
                                                                                                        Toast.makeText(Order.this, "Please Select Qualification ", Toast.LENGTH_SHORT).show();
                                                                                                        return;
                                                                                                    }

                                                                                                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                                                                                                        builder
                                                                                                                //.setTitle("............Pay")
                                                                                                                .setMessage("Are you Sure to Save Customer New Order?")
                                                                                                                //.setMessage("Customer Details!")
                                                                                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                                                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                                                                        new LongOperation().execute(serverURL,empname,deviceIMEI,tunName,tunEmail,tunMobile,tunPan,am,lm,tun,addEmiP,totP,lat,lon,regid,num,Mnum,SelectCat, perNew, ProPer,user_id,checkTun,chkType,AdvanceEMIPayP,DownPaymentP,LoanDisbursementP,LTV_Policy,ROI_policy,payedEMI,notPayedEM,

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
                                                                                                                                AnnualIncome1,txtNameFather1,no_year_current1,productD1,imei1,
                                                                                                                                gender,married,categury,house,Address11,Address12,Address13,lonper11,lonper12,OccupationS,OuaS, R_name1, R_Adress1, R_Contact1, R_name2, R_Adress2, R_Contact2, branch_name, branch_id
                                                                                                                        );

                                                                                                                    }
                                                                                                                })

                                                                                                                .setNegativeButton("Cancel", null)						//Do nothing on no
                                                                                                                .show();


                                                                                               }else{Toast.makeText(Order.this, "Please Enter Product Details", Toast.LENGTH_SHORT).show();return;}

                                                                                            }else{Toast.makeText(Order.this, "Please Enter No of year current Residence", Toast.LENGTH_SHORT).show();return;}
                                                                                        }else{Toast.makeText(Order.this, "Please Enter Father Name", Toast.LENGTH_SHORT).show();return;}

                                                                                    }else{Toast.makeText(Order.this, "Please Enter Annual Income ", Toast.LENGTH_SHORT).show();return;}

                                                                                }else{Toast.makeText(Order.this, "Please Enter Nationality", Toast.LENGTH_SHORT).show();return;}
                                                                           // }else{Toast.makeText(Order.this, "Please Residence Contact", Toast.LENGTH_SHORT).show();return;}

                                                                            }else{Toast.makeText(Order.this, "Please Residence Address", Toast.LENGTH_SHORT).show();return;}

                                                                        }else{Toast.makeText(Order.this, "Please Enter Residence Address Land Mark", Toast.LENGTH_SHORT).show();return;}
                                                                    }else{Toast.makeText(Order.this, "Please Enter Residence Address State", Toast.LENGTH_SHORT).show();return;}
                                                                }else{Toast.makeText(Order.this, "Please Enter Residence Address City", Toast.LENGTH_SHORT).show();return;}
                                                            }else{Toast.makeText(Order.this, "Please Enter Residence Address PIN", Toast.LENGTH_SHORT).show();return;}
                                                     //   }else{Toast.makeText(Order.this, "Please Enter DL", Toast.LENGTH_SHORT).show();return;}
                                                  //  }else{Toast.makeText(Order.this, "Please Enter Aadhar Number", Toast.LENGTH_SHORT).show();return;}
                                                }else{Toast.makeText(Order.this, "Please Enter No of Dependents", Toast.LENGTH_SHORT).show();return;}
                                            }else{Toast.makeText(Order.this, "Please Select DOB", Toast.LENGTH_SHORT).show();return;}
                                        }else{Toast.makeText(Order.this, "Please Enter Mother Name", Toast.LENGTH_SHORT).show();return;}



                                    }else{Toast.makeText(Order.this, "Please Check EMI", Toast.LENGTH_SHORT).show();return;}

                                }else{Toast.makeText(Order.this, "Please Enter Tuner", Toast.LENGTH_SHORT).show();return;}

                            }else{Toast.makeText(Order.this, "Please Check Loan Amount", Toast.LENGTH_SHORT).show();return;}

                        }else{Toast.makeText(Order.this, "Please Enter Price", Toast.LENGTH_SHORT).show();return;}

                    }else{Toast.makeText(Order.this, "Please Enter Customer PAN Number", Toast.LENGTH_SHORT).show();return;}

                }else{Toast.makeText(Order.this, "Please Enter Customer Mobile Number", Toast.LENGTH_SHORT).show();return;}

            }else{Toast.makeText(Order.this, "Please Enter Customer Email", Toast.LENGTH_SHORT).show();return;}
        }else{Toast.makeText(Order.this, "Please Enter Customer Name", Toast.LENGTH_SHORT).show();return;}



       /* BlurBehind.getInstance().execute(Order.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(Order.this, MainActivityD.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                finish();
            }
        });
*/

        }
        catch(Exception ex) {

            Toast.makeText(getApplicationContext(), "Some Error Try again!", Toast.LENGTH_LONG).show();
        }
    }

    public void back(View view){
        BlurBehind.getInstance().execute(Order.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(Order.this, Launch.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                finish();
            }
        });
    }


    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Order.this);
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
                    data +="&" + URLEncoder.encode("emp_name", "UTF-8") + "="+params[1].toString();
                if(!params[2].equals(""))
                    data +="&" + URLEncoder.encode("emp_id", "UTF-8") + "="+params[2].toString();
                if(!params[3].equals(""))
                    data +="&" + URLEncoder.encode("customer_name", "UTF-8") + "="+params[3].toString();
                if(!params[4].equals(""))
                    data +="&" + URLEncoder.encode("email", "UTF-8") + "="+params[4].toString();
                if(!params[5].equals(""))
                    data +="&" + URLEncoder.encode("mobile", "UTF-8") + "="+params[5].toString();
                if(!params[6].equals(""))
                    data +="&" + URLEncoder.encode("pan", "UTF-8") + "="+params[6].toString();
                if(!params[7].equals(""))
                    data +="&" + URLEncoder.encode("price", "UTF-8") + "="+params[7].toString();
                if(!params[8].equals(""))
                    data +="&" + URLEncoder.encode("loan_amount", "UTF-8") + "="+params[8].toString();
                if(!params[9].equals(""))
                    data +="&" + URLEncoder.encode("tuner", "UTF-8") + "="+params[9].toString();
                if(!params[10].equals(""))
                    data +="&" + URLEncoder.encode("emi", "UTF-8") + "="+params[10].toString();
                if(!params[11].equals(""))
                    data +="&" + URLEncoder.encode("total", "UTF-8") + "="+params[11].toString();
                if(!params[12].equals(""))
                    data +="&" + URLEncoder.encode("lat", "UTF-8") + "="+params[12].toString();
                if(!params[13].equals(""))
                    data +="&" + URLEncoder.encode("lon", "UTF-8") + "="+params[13].toString();
                if(!params[14].equals(""))
                    data +="&" + URLEncoder.encode("regid", "UTF-8") + "="+params[14].toString();
                if(!params[15].equals(""))
                    data +="&" + URLEncoder.encode("product_name", "UTF-8") + "="+params[15].toString();
                if(!params[16].equals(""))
                    data +="&" + URLEncoder.encode("brand", "UTF-8") + "="+params[16].toString();
                if(!params[17].equals(""))
                    data +="&" + URLEncoder.encode("product_type", "UTF-8") + "="+params[17].toString();
                if(!params[18].equals(""))
                    data +="&" + URLEncoder.encode("Loan_percent", "UTF-8") + "="+params[18].toString();
                if(!params[19].equals(""))
                    data +="&" + URLEncoder.encode("percent_rate", "UTF-8") + "="+params[19].toString();
                if(!params[20].equals(""))
                    data +="&" + URLEncoder.encode("user_id", "UTF-8") + "="+params[20].toString();
                if(!params[21].equals(""))
                    data +="&" + URLEncoder.encode("frick", "UTF-8") + "="+params[21].toString();
                if(!params[22].equals(""))
                    data +="&" + URLEncoder.encode("scheme", "UTF-8") + "="+params[22].toString();
                if(!params[23].equals(""))
                    data +="&" + URLEncoder.encode("ADVEMI", "UTF-8") + "="+params[23].toString();
                if(!params[24].equals(""))
                    data +="&" + URLEncoder.encode("DownPay", "UTF-8") + "="+params[24].toString();
                if(!params[25].equals(""))
                    data +="&" + URLEncoder.encode("DisbusPay", "UTF-8") + "="+params[25].toString();
                if(!params[26].equals(""))
                    data +="&" + URLEncoder.encode("LTV_Policy", "UTF-8") + "="+params[26].toString();
                if(!params[27].equals(""))
                    data +="&" + URLEncoder.encode("ROI_policy", "UTF-8") + "="+params[27].toString();
                if(!params[28].equals(""))
                    data +="&" + URLEncoder.encode("payedEMI", "UTF-8") + "="+params[28].toString();
                if(!params[29].equals(""))
                    data +="&" + URLEncoder.encode("notPayedEM", "UTF-8") + "="+params[29].toString();
                if(!params[30].equals(""))
                    data +="&" + URLEncoder.encode("txtNameMother", "UTF-8") + "="+params[30].toString();
                if(!params[31].equals(""))
                    data +="&" + URLEncoder.encode("dob1", "UTF-8") + "="+params[31].toString();
                if(!params[32].equals(""))
                    data +="&" + URLEncoder.encode("No_of_Dependents1", "UTF-8") + "="+params[32].toString();
                if(!params[33].equals(""))
                    data +="&" + URLEncoder.encode("aadhar_num1", "UTF-8") + "="+params[33].toString();
                if(!params[34].equals(""))
                    data +="&" + URLEncoder.encode("DL", "UTF-8") + "="+params[34].toString();
                if(!params[35].equals(""))
                    data +="&" + URLEncoder.encode("LandMark11", "UTF-8") + "="+params[35].toString();
                if(!params[36].equals(""))
                    data +="&" + URLEncoder.encode("LandMark21", "UTF-8") + "="+params[36].toString();
                if(!params[37].equals(""))
                    data +="&" + URLEncoder.encode("LandMark31", "UTF-8") + "="+params[37].toString();
                if(!params[38].equals(""))
                    data +="&" + URLEncoder.encode("PIN11", "UTF-8") + "="+params[38].toString();
                if(!params[39].equals(""))
                    data +="&" + URLEncoder.encode("PIN21", "UTF-8") + "="+params[39].toString();
                if(!params[40].equals(""))
                    data +="&" + URLEncoder.encode("PIN31", "UTF-8") + "="+params[40].toString();
                if(!params[41].equals(""))
                    data +="&" + URLEncoder.encode("City11", "UTF-8") + "="+params[41].toString();
                if(!params[42].equals(""))
                    data +="&" + URLEncoder.encode("City21", "UTF-8") + "="+params[42].toString();
                if(!params[43].equals(""))
                    data +="&" + URLEncoder.encode("City31", "UTF-8") + "="+params[43].toString();
                if(!params[44].equals(""))
                    data +="&" + URLEncoder.encode("State11", "UTF-8") + "="+params[44].toString();
                if(!params[45].equals(""))
                    data +="&" + URLEncoder.encode("State21", "UTF-8") + "="+params[45].toString();
                if(!params[46].equals(""))
                    data +="&" + URLEncoder.encode("State31", "UTF-8") + "="+params[46].toString();
                if(!params[47].equals(""))
                    data +="&" + URLEncoder.encode("recidentcontact", "UTF-8") + "="+params[47].toString();
                if(!params[48].equals(""))
                    data +="&" + URLEncoder.encode("contactOffice", "UTF-8") + "="+params[48].toString();
                if(!params[49].equals(""))
                    data +="&" + URLEncoder.encode("Nationality", "UTF-8") + "="+params[49].toString();
                if(!params[50].equals(""))
                    data +="&" + URLEncoder.encode("AnnualIncome", "UTF-8") + "="+params[50].toString();
                if(!params[51].equals(""))
                    data +="&" + URLEncoder.encode("txtNameFather", "UTF-8") + "="+params[51].toString();
                if(!params[52].equals(""))
                    data +="&" + URLEncoder.encode("no_year_current", "UTF-8") + "="+params[52].toString();
                if(!params[53].equals(""))
                    data +="&" + URLEncoder.encode("productD1", "UTF-8") + "="+params[53].toString();
                if(!params[54].equals(""))
                    data +="&" + URLEncoder.encode("imei", "UTF-8") + "="+params[54].toString();
                if(!params[55].equals(""))
                    data +="&" + URLEncoder.encode("gender", "UTF-8") + "="+params[55].toString();
                if(!params[56].equals(""))
                    data +="&" + URLEncoder.encode("married", "UTF-8") + "="+params[56].toString();
                if(!params[57].equals(""))
                    data +="&" + URLEncoder.encode("categury", "UTF-8") + "="+params[57].toString();
                if(!params[58].equals(""))
                    data +="&" + URLEncoder.encode("house", "UTF-8") + "="+params[58].toString();
                if(!params[59].equals(""))
                    data +="&" + URLEncoder.encode("Address1", "UTF-8") + "="+params[59].toString();
                if(!params[60].equals(""))
                    data +="&" + URLEncoder.encode("Address2", "UTF-8") + "="+params[60].toString();
                if(!params[61].equals(""))
                    data +="&" + URLEncoder.encode("Address3", "UTF-8") + "="+params[61].toString();
                if(!params[62].equals(""))
                    data +="&" + URLEncoder.encode("dealer_naem", "UTF-8") + "="+params[62].toString();
                if(!params[63].equals(""))
                    data +="&" + URLEncoder.encode("dealer_id", "UTF-8") + "="+params[63].toString();
                if(!params[64].equals(""))
                    data +="&" + URLEncoder.encode("Qualification", "UTF-8") + "="+params[64].toString();
                if(!params[65].equals(""))
                    data +="&" + URLEncoder.encode("Occupation", "UTF-8") + "="+params[65].toString();

                if(!params[66].equals(""))
                    data +="&" + URLEncoder.encode("rn1", "UTF-8") + "="+params[66].toString();
                if(!params[67].equals(""))
                    data +="&" + URLEncoder.encode("ra1", "UTF-8") + "="+params[67].toString();
                if(!params[68].equals(""))
                    data +="&" + URLEncoder.encode("rc1", "UTF-8") + "="+params[68].toString();
                if(!params[69].equals(""))
                    data +="&" + URLEncoder.encode("rn2", "UTF-8") + "="+params[69].toString();
                if(!params[70].equals(""))
                    data +="&" + URLEncoder.encode("ra2", "UTF-8") + "="+params[70].toString();
                if(!params[71].equals(""))
                    data +="&" + URLEncoder.encode("rc2", "UTF-8") + "="+params[71].toString();

                if(!params[72].equals(""))
                    data +="&" + URLEncoder.encode("branch_name", "UTF-8") + "="+params[72].toString();
                if(!params[73].equals(""))
                    data +="&" + URLEncoder.encode("branch_id", "UTF-8") + "="+params[73].toString();


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

                        Toast.makeText(getApplicationContext(), "Order Saved Successfully.", Toast.LENGTH_LONG).show();
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
                            BlurBehind.getInstance().execute(Order.this, new OnBlurCompleteListener() {
                                @Override
                                public void onBlurComplete() {
                                    Intent intent = new Intent(Order.this, Launch.class);
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


    private void showBottomSheet(){
        mBottomSheetDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.view_emi_calculater, null);
        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window));
        FancyButton onBack = (FancyButton)v.findViewById(R.id.TaskM2441);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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



        rb1 = (RadioButton)v.findViewById(R.id.switches_rb1);
        rb2 = (RadioButton)v.findViewById(R.id.switches_rb2);
        rb3 = (RadioButton)v.findViewById(R.id.switches_rb3);

        rb12 = (RadioButton)v.findViewById(R.id.switches_rb12);
        rb22 = (RadioButton)v.findViewById(R.id.switches_rb22);
        rb32 = (RadioButton)v.findViewById(R.id.switches_rb32);
        rb42 = (RadioButton)v.findViewById(R.id.switches_rb33);
        rb52 = (RadioButton)v.findViewById(R.id.switches_rb34);


        txtDownPay = (EditText) v.findViewById(R.id.txtDownPay);
        txtNameDisbus = (EditText) v.findViewById(R.id.txtNameDisbus);

        downPricel = (EditText) v.findViewById(R.id.downPricel);

        emiTypeTotalA =  (TextView) v.findViewById(R.id.emiTypeTotalA);

        txtNameTotalPay=  (TextView) v.findViewById(R.id.txtNameTotalPay);


        ProcessingFee=(TextView)v.findViewById(R.id.ProcessingFee);

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb1.setChecked(rb1 == buttonView);
                    rb2.setChecked(rb2 == buttonView);
                    rb3.setChecked(rb3 == buttonView);
                }

                if(rb1.isChecked()){
                   checkTun="Month";
                }
                if(rb2.isChecked()){
                    checkTun="Fortnight";
                }
                if(rb3.isChecked()){
                     checkTun="Week";
                }
            }

        };

        rb1.setOnCheckedChangeListener(listener);
        rb2.setOnCheckedChangeListener(listener);
        rb3.setOnCheckedChangeListener(listener);

        CompoundButton.OnCheckedChangeListener listener2 = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb12.setChecked(rb12 == buttonView);
                    rb22.setChecked(rb22 == buttonView);
                    rb32.setChecked(rb32 == buttonView);
                    rb42.setChecked(rb42 == buttonView);
                    rb52.setChecked(rb52 == buttonView);


                }
//                int temp = Integer.valueOf(tenurEdit.getText().toString().trim());

                if(tenurEdit.getText().toString().trim().equals("")){
                    tenurEdit.setText("0");
                    Toast.makeText(getApplicationContext(), "Please Enter Tenure", Toast.LENGTH_SHORT).show();
                }
                if(emi_type1==0||emi_type2==0||emi_type3==0||emi_type4==0||emi_type5==0){

                    Toast.makeText(getApplicationContext(), "Please Enter Tenure", Toast.LENGTH_SHORT).show();
                    return;
                }


                Double amount12 = Double.valueOf(inputSearchPricePer.getText().toString().trim());


                Double Real_amount = Double.valueOf(inputSearchPrice.getText().toString().trim());


                Double pfee=0.0;
                String ppff=PF.getText().toString().trim();

                if(ppff.equals("")){


                    if(Real_amount>10000){
                        pfee=599.0;
                    }
                    else{
                        pfee=499.0;
                    }

                    PF.setText(pfee.toString());

                }else{



                    Double dff = Double.valueOf(PF.getText().toString().trim());
                    pfee=dff;

                    //   Douwpay=Douwpay+pfee;
                }

                if(rb12.isChecked()){
                    Double dounpay= addEmi*emi_type1;
                    downPricel.setText(dounpay.toString());

                    emiTypeTotalA.setText(E1mT2+"|"+E1mT1+" Type "+addEmi+"(EMI)*"+emi_type1+"="+dounpay.toString());

                    Double Douwpay=dounpay+(Real_amount-amount12);
                    Douwpay=Douwpay+pfee;
                    Double disbus=Real_amount-Douwpay;



                    txtDownPay.setText(Douwpay.toString());
                    txtNameDisbus.setText(disbus.toString());

                    Amount.setText(Real_amount.toString());

                    CkeckLoan();

                    int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());
                    txtNameTuner1.setText(""+Tu);
                    CkeckEMI();

                    AdvanceEMIPay.setText(dounpay.toString());
                    DownPayment.setText(Douwpay.toString());
                    LoanDisbursement.setText(disbus.toString());

                    chkType="1";

                    payedEMI= String.valueOf(E1mT2);
                    notPayedEM= String.valueOf(E1mT1);
                }
                if(rb22.isChecked()){
                    Double dounpay= addEmi*emi_type2;
                    downPricel.setText(dounpay.toString());

                    emiTypeTotalA.setText(E2mT2+"|"+E2mT1+" Type "+addEmi+"(EMI)*"+emi_type2+"="+dounpay.toString());

                    Double Douwpay=dounpay+(Real_amount-amount12);

                    Douwpay=Douwpay+pfee;
                    Double disbus=Real_amount-Douwpay;


                    txtDownPay.setText(Douwpay.toString());
                    txtNameDisbus.setText(disbus.toString());

                    Amount.setText(Real_amount.toString());

                    CkeckLoan();

                    int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());
                    txtNameTuner1.setText(""+Tu);
                    CkeckEMI();

                    AdvanceEMIPay.setText(dounpay.toString());
                    DownPayment.setText(Douwpay.toString());
                    LoanDisbursement.setText(disbus.toString());
                    chkType="2";

                    payedEMI= String.valueOf(E2mT2);
                    notPayedEM= String.valueOf(E2mT1);
                }
                if(rb32.isChecked()){
                    Double dounpay= addEmi*emi_type3;
                    downPricel.setText(dounpay.toString());

                    emiTypeTotalA.setText(E3mT2+"|"+E3mT1+" Type "+addEmi+"(EMI)*"+emi_type3+"="+dounpay.toString());

                    Double Douwpay=dounpay+(Real_amount-amount12);

                    Douwpay=Douwpay+pfee;
                    Double disbus=Real_amount-Douwpay;

                    txtDownPay.setText(Douwpay.toString());
                    txtNameDisbus.setText(disbus.toString());

                    Amount.setText(Real_amount.toString());

                    CkeckLoan();

                    int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());
                    txtNameTuner1.setText(""+Tu);
                    CkeckEMI();

                    AdvanceEMIPay.setText(dounpay.toString());
                    DownPayment.setText(Douwpay.toString());
                    LoanDisbursement.setText(disbus.toString());

                    chkType="3";

                    payedEMI= String.valueOf(E3mT2);
                    notPayedEM= String.valueOf(E3mT1);
                }
                if(rb42.isChecked()){
                    Double dounpay= addEmi*emi_type4;
                    downPricel.setText(dounpay.toString());

                    emiTypeTotalA.setText(E4mT2+"|"+E4mT1+" Type "+addEmi+"(EMI)*"+emi_type4+"="+dounpay.toString());

                    Double Douwpay=dounpay+(Real_amount-amount12);

                    Douwpay=Douwpay+pfee;
                    Double disbus=Real_amount-Douwpay;


                    txtDownPay.setText(Douwpay.toString());
                    txtNameDisbus.setText(disbus.toString());

                    Amount.setText(Real_amount.toString());

                    CkeckLoan();

                    int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());
                    txtNameTuner1.setText(""+Tu);
                    CkeckEMI();

                    AdvanceEMIPay.setText(dounpay.toString());
                    DownPayment.setText(Douwpay.toString());
                    LoanDisbursement.setText(disbus.toString());

                    chkType="4";

                    payedEMI= String.valueOf(E5mT2);
                    notPayedEM= String.valueOf(E5mT1);
                }
                if(rb52.isChecked()){
                    Double dounpay= addEmi*emi_type5;
                    downPricel.setText(dounpay.toString());

                    emiTypeTotalA.setText(E5mT2+"|"+E5mT1+" Type "+addEmi+"(EMI)*"+emi_type5+"="+dounpay.toString());

                    Double Douwpay=dounpay+(Real_amount-amount12);

                    Douwpay=Douwpay+pfee;
                    Double disbus=Real_amount-Douwpay;


                    txtDownPay.setText(Douwpay.toString());
                    txtNameDisbus.setText(disbus.toString());

                    Amount.setText(Real_amount.toString());

                    CkeckLoan();

                    int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());
                    txtNameTuner1.setText(""+Tu);
                    CkeckEMI();

                    AdvanceEMIPay.setText(dounpay.toString());
                    DownPayment.setText(Douwpay.toString());
                    LoanDisbursement.setText(disbus.toString());

                    chkType="5";

                    payedEMI= String.valueOf(E5mT2);
                    notPayedEM= String.valueOf(E5mT1);

                }



            }

        };

        rb12.setOnCheckedChangeListener(listener2);
        rb22.setOnCheckedChangeListener(listener2);
        rb32.setOnCheckedChangeListener(listener2);
        rb42.setOnCheckedChangeListener(listener2);
        rb52.setOnCheckedChangeListener(listener2);

//        txtNamePrice2 = (EditText) v.findViewById(R.id.txtNamePrice2);

        TextView LoanAm = (TextView) v.findViewById(R.id.textView32);
        LoanAm.setText("Of "+perNew+"% is");


        tenurEdit = (EditText) v.findViewById(R.id.tenure);

        inputSearchPrice = (EditText) v.findViewById(R.id.Price);
        LTVPolicy = (EditText) v.findViewById(R.id.LTVPolicy);

        PF= (EditText) v.findViewById(R.id.PF);


        inputSearchPricePer = (EditText) v.findViewById(R.id.txtNamePrice);
       // String searchString2=txtNamePrice2.getText().toString();
        //inputSearchPrice.setText(searchString2);

        final Double amount = 0.0;

        inputSearchPrice.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                //String searchString=inputSearchPrice.getText().toString();
                //int textLength=searchString.length();
                //inputSearchPricePer.clear();
                //String p = inputSearchPrice.getText().toString();
               // inputSearchPricePer.setText(p);

                String searchString=inputSearchPrice.getText().toString();
                if(searchString.equals("")) {
                    Double amount = 0.0;
                    inputSearchPrice.setText(amount.toString());
                    inputSearchPricePer.setText(amount.toString());
                    return;

                    }else{

                   Double amount1 = Double.valueOf(inputSearchPrice.getText().toString().trim());

                    percent = Double.valueOf(perNew);

                        Double loan = (percent / 100) * amount1;
                        inputSearchPricePer.setText(loan.toString());
                    LTV_Policy=loan.toString();

                    LTVPolicy.setText(loan.toString());
                    tenurEdit.setText("");
                }
            }

                //adapteroo.notifyDataSetChanged();
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });



        inputSearchPricePer.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String searchString=inputSearchPricePer.getText().toString();
                tenurEdit.setText("");
                LoanAmount.setText(searchString.toString());
                ROIApplied.setText(ProPer.toString());

                //Double MainAm=Double.valueOf(Tenure);


                intrestRTEdit.setText("Interest is (ROI Policy)- "+ProPer+"%");


            }

            //adapteroo.notifyDataSetChanged();
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });

        ROIApplied = (EditText)v.findViewById(R.id.ROIApplied);
        ROIApplied.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String searchString4=ROIApplied.getText().toString();
                tenurEdit.setText("");
                ProPer=searchString4.toString();

                AdvanceEMIPay.setText("");
                DownPayment.setText("");
                LoanDisbursement.setText("");
                emiTypeTotalA.setText("");


                downPricel.setText("");

                emiTypeTotalA.setText("");

                // Double amount1 = Double.valueOf(inputSearchPrice.getText().toString().trim());

                txtDownPay.setText("");
                txtNameDisbus.setText("");


                EmiFinaml.setText("");
                //LoanAmount.setText(searchString.toString());
                if(searchString4.equals("")) {
                    Double amount = 0.0;
                    //inputSearchPrice.setText(amount.toString());
                    ROIApplied.setText(amount.toString());

                    return;

                }
            }

            //adapteroo.notifyDataSetChanged();
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });



        intrestRTEdit = (TextView) v.findViewById(R.id.intrestRT);
        EMITot= (TextView) v.findViewById(R.id.EMITot);
        EmiFinaml = (EditText) v.findViewById(R.id.EmiFinaml);



        Amount = (TextView)findViewById(R.id.txtNamePrice);
        txtNameTuner1 = (EditText)
                findViewById(R.id.txtNameTuner1);

        tenurEdit.addTextChangedListener(new TextWatcher() {


            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                //String searchString=inputSearchPrice.getText().toString();
                //int textLength=searchString.length();
                //inputSearchPricePer.clear();
                //String p = inputSearchPrice.getText().toString();
                // inputSearchPricePer.setText(p);

                String searchString=inputSearchPrice.getText().toString();
                if(searchString.equals("")||searchString.equals("0.0")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Product Price", Toast.LENGTH_SHORT).show();

                    return;
                }

                String searchString123=tenurEdit.getText().toString();
                if(searchString123.equals("")) {
                   // Toast.makeText(getApplicationContext(), "Please Enter Tenure Field", Toast.LENGTH_SHORT).show();

                    rb12.setText("X|Y  EMI Type");
                    rb22.setText("M|N  EMI Type");
                    rb32.setText("P|Q  EMI Type");
                    rb42.setText("R|S  EMI Type");
                    rb52.setText("C|D  EMI Type");

                    return;
                }

                String searchString1=tenurEdit.getText().toString();
                if(searchString1.equals(getIntent())) {
                    // int amount = 0;
                    // tenurEdit.setText(amount);
                    //tenurEdit.setText(amount);
                    return;
                }else{

                    if(inputSearchPricePer.getText().toString().trim().equals("")){

                        Toast.makeText(getApplicationContext(), "Product Preferable Loan Field is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Double amount12 = Double.valueOf(inputSearchPricePer.getText().toString().trim());


                    Double Real_amount = Double.valueOf(inputSearchPrice.getText().toString().trim());

                    int Tenure = Integer.valueOf(tenurEdit.getText().toString().trim());

                     E1mT1 = Tenure-1;
                     E1mT2 =  1;

                    emi_type1=E1mT2;
                    rb12.setText(E1mT2+"|"+E1mT1+"  EMI Type");
                    rb12.setChecked(true);

                    if(E1mT1<E1mT2){
                        rb12.setVisibility(View.GONE);
                    }else{
                        rb12.setVisibility(View.VISIBLE);
                    }

                     E2mT1 = Tenure-2;
                     E2mT2 =  2;
                    emi_type2=E2mT2;
                    rb22.setText(E2mT2+"|"+E2mT1+"  EMI Type");

                    if(E2mT1<E2mT2){
                        rb22.setVisibility(View.GONE);
                    }else{
                        rb22.setVisibility(View.VISIBLE);
                    }

                     E3mT1 = Tenure-3;
                     E3mT2 =  3;
                    emi_type3=E3mT2;
                    rb32.setText(E3mT2+"|"+E3mT1+"  EMI Type");

                    if(E3mT1<E3mT2){
                        rb32.setVisibility(View.GONE);
                    }else{
                        rb32.setVisibility(View.VISIBLE);
                    }


                    E4mT1 = Tenure -4;
                    E4mT2 =  4;
                    emi_type4=E4mT2;
                    rb42.setText(E4mT2+"|"+E4mT1+"  EMI Type");


                    if(E4mT1<E4mT2){
                        rb42.setVisibility(View.GONE);
                    }else{
                        rb42.setVisibility(View.VISIBLE);
                    }

                    E5mT1 = Tenure-5;
                    E5mT2 =  5;
                    emi_type5=E5mT2;
                    rb52.setText(E5mT2+"|"+E5mT1+"  EMI Type");

                    if(E5mT1<E5mT2){
                        rb52.setVisibility(View.GONE);
                    }else{
                        rb52.setVisibility(View.VISIBLE);
                    }

                    // percent = Double.valueOf(perNew);
                    Double emi=amount12/Tenure;
                    percent=Double.valueOf(ProPer);


                    Double loanPer=(percent/100)*amount12;

                   // ROIApplied.setText(ProPer.toString());

                    Double addEmi= emi+loanPer;
                    Double NetPay = emi+loanPer;
                    Double totalimi=addEmi*Tenure;
                    //Double loan = (percent / 100) * amount1;
                    EmiFinaml.setText(NetPay.toString());

                    //Double MainAm=Double.valueOf(Tenure);


                    intrestRTEdit.setText("Interest is (ROI Policy) "+OldNewPercent+"%");

                    ROI_policy=OldNewPercent;

                    EMITot.setText("Total EMI is "+totalimi);

                    Double totta=Tenure*addEmi;
                    txtNameTotalPay.setText(totta.toString());

                    //rb32.setChecked(true);


                    Double dounpay= addEmi*emi_type1;
                    downPricel.setText(dounpay.toString());

                    emiTypeTotalA.setText(E1mT2+"|"+E1mT1+" Type "+addEmi+"(EMI)*"+emi_type1+"="+dounpay.toString());

                    payedEMI= String.valueOf(E1mT2);
                    notPayedEM= String.valueOf(E1mT1);
                   // Double amount1 = Double.valueOf(inputSearchPrice.getText().toString().trim());

                    Double pfee=0.0;


                        if(amount12>10000){
                            pfee=599.0;
                        }
                        else{
                            pfee=499.0;
                        }

                        PF.setText(pfee.toString());



                    Double Douwpay=dounpay+(Real_amount-amount12);
                    Douwpay=Douwpay+pfee;
                    Double disbus=Real_amount-Douwpay;


                    txtDownPay.setText(Douwpay.toString());
                    txtNameDisbus.setText(disbus.toString());

                    Amount.setText(Real_amount.toString());

                    CkeckLoan();

                    int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());
                    txtNameTuner1.setText(""+Tu);
                    CkeckEMI();

                    AdvanceEMIPay.setText(dounpay.toString());
                    DownPayment.setText(Douwpay.toString());
                    LoanDisbursement.setText(disbus.toString());

                    chkType="1";

                    if(rb1.isChecked()){
                        TunerTypue.setText("Instant EMI Pay is "+E1mT2+"|"+E1mT1+" Type "+addEmi+"(EMI)*"+emi_type1+"="+dounpay.toString()+" and Monthly Installment EMI is "+addEmi);
                        checkTun="Month";
                    }
                    if(rb2.isChecked()){
                        TunerTypue.setText("Instant EMI Pay is "+E1mT2+"|"+E1mT1+" Type "+addEmi+"(EMI)*"+emi_type1+"="+dounpay.toString()+" and Fortnight Installment EMI is "+addEmi);
                        checkTun="Fortnight";
                    }
                    if(rb3.isChecked()){
                        TunerTypue.setText("Instant EMI Pay is "+E1mT2+"|"+E1mT1+" Type "+addEmi+"(EMI)*"+emi_type1+"="+dounpay.toString()+" and Week Installment EMI is "+addEmi);
                        checkTun="Week";
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
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


    @Override
    public void onBackPressed() {

            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder
                    //.setTitle("............Pay")
                    .setMessage("Are you Sure to Go Back")
                    //.setMessage("Customer Details!")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Order.this, Main.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.putExtra("upto", "10000");
                            intent.putExtra("product", "Sartha Samriddhi");
                            startActivity(intent);
                            finish();

                        }
                    })

                    .setNegativeButton("No", null)						//Do nothing on no
                    .show();

    }

    @Override
    public void onResume() {
        super.onResume();
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

    public void cal(View view){
        showBottomSheet();
    }


    @Override
    protected Dialog onCreateDialog(int id) {
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


    public void Allot(){
        showBottomSheet1();
    }

    public void showBottomSheet1(){
        mBottomSheetDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.z_emp_popup_list, null);
        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window));


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        FancyButton onBack = (FancyButton)v.findViewById(R.id.TaskM2441);
TextView textPoster=(TextView)v.findViewById(R.id.textPoster);
        textPoster.setText("Select Dealer");
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




        inputSearch1 = (TextView) v.findViewById(R.id.inputSearch);


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
        final CustomAdapter1 adapteroo=new CustomAdapter1(getApplicationContext(), R.layout.z_emp_list_data,searchResults1);

        //finally,set the adapter to the default ListView
        mylistview1.setAdapter(adapteroo);

        inputSearch1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the TextView
                String searchString=inputSearch1.getText().toString();
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

    private class CustomAdapter1 extends ArrayAdapter<HashMap<String, Object>>
    {

        public CustomAdapter1(Context context, int textViewResourceId,
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

            // tot= Double.valueOf(searchResults1.get(position).get("Sid").toString());
            // log= Double.valueOf(searchResults1.get(position).get("Sname").toString());



            //Double cal= 0.0;
            //cal= tot/log;

            //trendid.setText(total_dis+"/"+total_log+" = "+cal);



            //viewHolder.contact_type.setText(cal.toString());

//            viewHolder.tt.setText(tot.toString());
            //          viewHolder.ll.setText(log.toString());

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

                    ImageView imageView23 =(ImageView)findViewById(R.id.imageView23);
                    TextView textView6= (TextView)findViewById(R.id.textView6);

                    Glide.with(getContext())
                            //.(R.drawable.ic_launcher)
                            .load(lonper13.replace("http","https"))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_launcher)
                            .into(imageView23);

                    textView6.setText(" "+lonper12+"("+lonper11+")");
                    if(mBottomSheetDialog != null){
                        mBottomSheetDialog.dismissImmediately();
                        mBottomSheetDialog = null;
                    }
  /*                  ImageView imageView23=(ImageView)findViewById(R.id.imageView23);
                    Glide.with(getContext())
                            //.(R.drawable.ic_launcher)
                            .load(lonper13)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_launcher)
                            .into(imageView23);

                    BlurBehind.getInstance().execute(Order.this, new OnBlurCompleteListener() {
                        @Override
                        public void onBlurComplete() {
                            Intent intent = new Intent(SubProduct.this, Order.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                            intent.putExtra("data0", product);
                            intent.putExtra("data1", Product1);
                            intent.putExtra("ProPer", ProPer);
                            intent.putExtra("data2", SelectCat);
                            intent.putExtra("per", Team1);

                            startActivity(intent);
                            //finish();
                        }
                    });
                            lonper11=searchResults1.get(position).get("team").toString();
                          lonper12= searchResults1.get(position).get("name").toString();
                        lonper13=searchResults1.get(position).get("photo").toString();

                    //final String namee = finalHolder.name.getText().toString().trim();
                    //final String eid = finalHolder.team.getText().toString().trim();
                    //final String sts = finalHolder.contact_type.getText().toString().trim();

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
                    */
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

    public void selectDealer(View view){
        showBottomSheet1();
    }

    public void selectState(){
        infracom.abcr.sarthamicrofinance.material.app.Dialog.Builder builder = null;

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                if(sta==1){
                    State1.setText(getSelectedValue());
                } if(sta==2){
                    State2.setText(getSelectedValue());
                } if(sta==3){
                    State3.setText(getSelectedValue());
                }
                //Toast.makeText(Order.this, "You have selected " + getSelectedValue() + " as phone ringtone.", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(Order.this, "Cancelled" , Toast.LENGTH_SHORT).show();
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
        sta=1;
        selectState();
    }
    public void OnSelectState2(View view){
        sta=2;
        selectState();
    }
    public void OnSelectState3(View view){
        sta=3;
        selectState();
    }

    public void Qualification(View view){
       // sta=3;
        selectQualification();
    } public void Occupation(View view){
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
                Toast.makeText(Order.this, "Cancelled" , Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder)builder).items(new String[]{
                "12th Pass",
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
                Toast.makeText(Order.this, "Cancelled" , Toast.LENGTH_SHORT).show();
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
