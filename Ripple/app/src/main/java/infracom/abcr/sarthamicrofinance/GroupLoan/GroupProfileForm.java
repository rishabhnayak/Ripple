package infracom.abcr.sarthamicrofinance.GroupLoan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.HashMap;

import infracom.abcr.sarthamicrofinance.Config;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.Main;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.material.app.BottomSheetDialog;
import infracom.abcr.sarthamicrofinance.material.app.DialogFragment;
import infracom.abcr.sarthamicrofinance.material.app.SimpleDialog;
import infracom.abcr.sarthamicrofinance.material.app.ThemeManager;
import infracom.abcr.sarthamicrofinance.material.drawable.ThemeDrawable;
import infracom.abcr.sarthamicrofinance.material.util.ViewUtil;
import infracom.abcr.sarthamicrofinance.material.widget.ProgressView;
import infracom.abcr.sarthamicrofinance.material.widget.RadioButton;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;
import mehdi.sakout.fancybuttons.FancyButton;

public class GroupProfileForm extends AppCompatActivity {

    private String selectedImagePath = "",lonper11,lonper12,lonper13;
    ListView mylistview1;
    TextView inputSearch1;
    String serverURL1 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/empList.php";
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



    private int year, month, day;

    int sta=0;
    String num,Mnum,SelectCat, perNew, ProPer,empname,regid,user_id;
    private static double percent;
    private static double addEmi;
    private static double tot;
    int tunnr;
    TextView Amount;
    TextView LoanAmount;
    EditText txtNameTuner1,ROIApplied,LTVPolicy;
    TextView editText2Emi,txtNameTotal,textView7;
    DBAdapter db;

    int emi_type= 0,emi_type1=0,emi_type2=0,emi_type3=0;
    private BottomSheetDialog mBottomSheetDialog;

    GPSTracker gps;
    double latitude;
    double longitude;

    String deviceIMEI = "" ,LTV_Policy, ROI_policy,OldNewPercent;

    Controller aController = null;
    private ProgressDialog pDialog;

    String payedEMI,notPayedEM;

    private static final String TAG = GroupProfileForm.class.getSimpleName();  // Register button
    Button btnRegister;
    //private SQLiteHandler db;
    public static String URL_LOGIN = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/login.php";
    // WebServer Request URL to get All registered devices
    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/GroupOrder.php";

    EditText inputSearchPrice, inputSearchPricePer, txtNamePrice2, tenurEdit,EmiFinaml1, downPricel,txtNameDisbus,txtDownPay,LoanDisbursement,DownPayment,AdvanceEMIPay,PF;

    RadioButton rb1, rb12;
    RadioButton rb2, rb22;
    RadioButton rb3, rb32;
    TextView intrestRTEdit, EMITot, emiTypeTotalA, txtNameTotalPay,TunerTypue;

    public static String checkTun,chkType;
    int E1mT1,E2mT1,E3mT1,E1mT2,E2mT2,E3mT2;

    Double emi1,emi2,emi3;

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

    String gender,married,categury,house, addEmiP;


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
    String State31,TRG1;

    RadioButton RP1,RP2,UB1,UB2,UB3,UB4,RG1,RG2,RG3,RG4,RG5,CH1,CH2;

    TextView Occupation;
    TextView Qua;

    String OuaS;
    String OccupationS;

    String GID,CID,ROI, LEADER,GLEADER;

    String recidentcontact1;
    String contactOffice1;
    String Nationality1;
    String AnnualIncome1,txtNameFather1,no_year_current1,productD1,imei1,imei2,Address11,Address12,Address13;


    private Calendar calendar, calendar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_profile_form);

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
            aController.showAlertDialog(GroupProfileForm.this,
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
            aController.showAlertDialog(GroupProfileForm.this,
                    "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID",
                    false);

            // stop executing code by return
            return;
        }

       Intent inte = getIntent();
        String[] data = new String[0];


        GID = inte.getStringExtra("GID");
        CID = inte.getStringExtra("CID");
        ROI = inte.getStringExtra("ROI");
        LEADER = inte.getStringExtra("LEADER");

        GLEADER = inte.getStringExtra("GLEADER");
        //intent.putExtra("GLEADER", Gtitle);

        TextView  GIDA = (TextView) findViewById(R.id.GIDA);
        TextView  CIDA = (TextView) findViewById(R.id.CIDA);

        GIDA.setText(GID);
        CIDA.setText(CID);
//        SelectCat = inte.getStringExtra("data2");
//        perNew = inte.getStringExtra("per");
//        ProPer = inte.getStringExtra("ProPer");

        num = GLEADER;
        Mnum = "Customer Group Loan Form";
        SelectCat = "Group Loan";
        perNew = "100";
        ProPer = ROI;

        OldNewPercent=ProPer;

        EditText txtNameName = (EditText) findViewById(R.id.txtNameName);
        txtNameName.setText(LEADER);
/*
        int foo = Integer.parseInt(num);
        for (int i = 0; i < foo; i++){
            data[i] = inte.getStringExtra("data"+i);

        }
        */

        BlurBehind.getInstance()
                .withAlpha(200)
                .withFilterColor(Color.parseColor("#222222"))
                .setBackground(this);

        lblMessage.setText(Mnum+"("+num+") Interest Rates Will be "+ProPer+"%");
        textView6.setText("");


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
        Toast.makeText(
                getApplicationContext(),""+deviceIMEI, Toast.LENGTH_SHORT).show();
        db.close();


        Button subButt = (Button) findViewById(R.id.submit);

        if(under_depart.equals("Dealer")){
            subButt.setVisibility(View.GONE);
        }else {
            subButt.setVisibility(View.VISIBLE);
        }


        gps = new GPSTracker(GroupProfileForm.this);

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


        Occupation = (TextView)findViewById(R.id.Occupation);
        Qua = (TextView)findViewById(R.id.Qua);

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
                    house="Owened";
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

        RG1 = (RadioButton)findViewById(R.id.RG1);
        RG2 = (RadioButton)findViewById(R.id.RG2);
        RG3 = (RadioButton)findViewById(R.id.RG3);
        RG4 = (RadioButton)findViewById(R.id.RG4);
        RG5 = (RadioButton)findViewById(R.id.RG5);

        TRG1="Hindu";
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
                    TRG1="Hindu";
                }
                if(RG2.isChecked()){// checkTun="Fortnight";
                    TRG1="Muslim";
                }
                if(RG3.isChecked()){//checkTun="Month";
                    TRG1="Sikh";
                }
                if(RG4.isChecked()){// checkTun="Fortnight";
                    TRG1="Christian";
                }
                if(RG5.isChecked()){// checkTun="Fortnight";
                    TRG1="Buddhist";
                }

            }
        };


        RG1.setOnCheckedChangeListener(listenerU);
        RG2.setOnCheckedChangeListener(listenerU);
        RG3.setOnCheckedChangeListener(listenerU);
        RG4.setOnCheckedChangeListener(listenerU);
        RG5.setOnCheckedChangeListener(listenerU);


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


    }

    public  void CkeckLoan(){



        Amount = (TextView) findViewById(R.id.txtNamePrice);
        String am = Amount.getText().toString();

        if(am.trim().length() > 0){

        }else{
            Toast.makeText(GroupProfileForm.this, "Please Calculate EMI", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(GroupProfileForm.this, "Loan Field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(tun.trim().length() > 0){

        }else{
            Toast.makeText(GroupProfileForm.this, "Tuner Field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Double amount = Double.valueOf(LoanAmount.getText().toString().trim());
        int tunner = Integer.valueOf(txtNameTuner1.getText().toString().trim());

        if(lm.trim().length() > 0){

        }else{
            Toast.makeText(GroupProfileForm.this, "Please Check Loan Amount", Toast.LENGTH_SHORT).show();
            return;
        }

        percent=Double.valueOf(ProPer);

        editText2Emi.setText("EMI is "+addEmi);
        textView7.setText("EMI is "+addEmi+" Rs./For Each, with "+ProPer+"% of Loan Price." );

        tot = addEmi*tunner;
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

            //final String addEmiP;
           // addEmiP = String.valueOf(addEmi);



            final String totP;
            totP = String.valueOf(tot);

            txtNameMother1=txtNameMother.getText().toString().trim() ;
            dob1=dob.getText().toString().trim() ;


            if(dob1.trim().length() < 1){
                Toast.makeText(GroupProfileForm.this, "Please Select DOB", Toast.LENGTH_SHORT).show();
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

            recidentcontact1=recidentcontact.getText().toString().trim();
            contactOffice1=contactOffice.getText().toString().trim();
            Nationality1=Nationality.getText().toString().trim();
            AnnualIncome1=AnnualIncome.getText().toString().trim();
            txtNameFather1=txtNameFather.getText().toString().trim();
            no_year_current1=no_year_current.getText().toString().trim();
            productD1=productD.getText().toString().trim();
            imei1=GID;
                    //imei.getText().toString().trim();

            imei2=imei.getText().toString().trim();

            OccupationS= Occupation.getText().toString().trim();
            OuaS= Qua.getText().toString().trim();

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
                                if (Address12.trim().length() > 2) {
                                    // if (contactOffice1.trim().length() > 9) {} else { Toast.makeText(Order.this, "Please Office Permanent Contact", Toast.LENGTH_SHORT).show();return; }
                                } else {
                                    Toast.makeText(GroupProfileForm.this, "Please Permanent Address", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                // if (contactOffice1.trim().length() > 9) {} else { Toast.makeText(GroupProfileForm.this, "Please Office Permanent Contact", Toast.LENGTH_SHORT).show();return; }
                            } else {
                                Toast.makeText(GroupProfileForm.this, "Please Permanent Address Mark", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            Toast.makeText(GroupProfileForm.this, "Please Permanent Address State", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        Toast.makeText(GroupProfileForm.this, "Please Permanent Address City", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(GroupProfileForm.this, "Please Permanent Address PIN", Toast.LENGTH_SHORT).show();
                    return;
                }
            }



            if(tunName.trim().length() > 2){
               // if(tunEmail.trim().length() > 4){
                    if(tunMobile.trim().length() > 9){
                       // if(tunPan.trim().length() > 5){
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
                                                                            if(recidentcontact1.trim().length() > 9){
                                                                                if(Nationality1.trim().length() > 2){
                                                                                    if(AnnualIncome1.trim().length() > 2){
                                                                                        if(txtNameFather1.trim().length() > 2){
                                                                                            if(no_year_current1.trim().length() > 0){
                                                                                                //    if(productD1.trim().length() > 2){
                                                                                                //      if(imei1.trim().length() > 2){

                                                                                                if(OccupationS.trim().length() < 2){
                                                                                                    Toast.makeText(GroupProfileForm.this, "Please Select Occupation", Toast.LENGTH_SHORT).show();

                                                                                                    return;
                                                                                                }
                                                                                                if(OuaS.trim().length() < 2){
                                                                                                    Toast.makeText(GroupProfileForm.this, "Please Select Qualification", Toast.LENGTH_SHORT).show();
                                                                                                    return;
                                                                                                }

                                                                                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                                                                                                builder
                                                                                                        //.setTitle("............Pay")
                                                                                                        .setMessage("Are you Sure to Save Customer Group Profile Form?")
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
                                                                                                                        gender,married,categury,house,Address11,Address12,Address13,OccupationS,OuaS,imei2,TRG1
                                                                                                                );

                                                                                                            }
                                                                                                        })

                                                                                                        .setNegativeButton("Cancel", null)						//Do nothing on no
                                                                                                        .show();


                                                                                                //   }else{Toast.makeText(GroupProfileForm.this, "Please Enter Product Model Number/IMEI", Toast.LENGTH_SHORT).show();return;}
                                                                                                // }else{Toast.makeText(GroupProfileForm.this, "Please Enter Product Details", Toast.LENGTH_SHORT).show();return;}

                                                                                            }else{Toast.makeText(GroupProfileForm.this, "Please Enter No of year current Residence", Toast.LENGTH_SHORT).show();return;}
                                                                                        }else{Toast.makeText(GroupProfileForm.this, "Please Enter Father Name", Toast.LENGTH_SHORT).show();return;}

                                                                                    }else{Toast.makeText(GroupProfileForm.this, "Please Enter Annual Income ", Toast.LENGTH_SHORT).show();return;}


                                                                                }else{Toast.makeText(GroupProfileForm.this, "Please Enter Nationality", Toast.LENGTH_SHORT).show();return;}


                                                                            }else{Toast.makeText(GroupProfileForm.this, "Please Residence Contact", Toast.LENGTH_SHORT).show();return;}

                                                                        }else{Toast.makeText(GroupProfileForm.this, "Please Residence Address", Toast.LENGTH_SHORT).show();return;}

                                                                    }else{Toast.makeText(GroupProfileForm.this, "Please Enter Residence Address Land Mark", Toast.LENGTH_SHORT).show();return;}
                                                                }else{Toast.makeText(GroupProfileForm.this, "Please Enter Residence Address State", Toast.LENGTH_SHORT).show();return;}
                                                            }else{Toast.makeText(GroupProfileForm.this, "Please Enter Residence Address City", Toast.LENGTH_SHORT).show();return;}
                                                        }else{Toast.makeText(GroupProfileForm.this, "Please Enter Residence Address PIN", Toast.LENGTH_SHORT).show();return;}
                                                        //}else{Toast.makeText(GroupProfileForm.this, "Please Enter DL", Toast.LENGTH_SHORT).show();return;}
                                                        //}else{Toast.makeText(GroupProfileForm.this, "Please Enter Aadhar Number", Toast.LENGTH_SHORT).show();return;}
                                                    }else{Toast.makeText(GroupProfileForm.this, "Please Enter No of Dependents", Toast.LENGTH_SHORT).show();return;}
                                                }else{Toast.makeText(GroupProfileForm.this, "Please Select DOB", Toast.LENGTH_SHORT).show();return;}
                                            }else{Toast.makeText(GroupProfileForm.this, "Please Enter Mother Name", Toast.LENGTH_SHORT).show();return;}


/*
                                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                                        builder
                                                //.setTitle("............Pay")
                                                .setMessage("Are you Sure to Save Customer New Order?")
                                                //.setMessage("Customer Details!")
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        new LongOperation().execute(serverURL,empname,deviceIMEI,tunName,tunEmail,tunMobile,tunPan,am,lm,tun,addEmiP,totP,lat,lon,regid,num,Mnum,SelectCat, perNew, ProPer,user_id,checkTun,chkType,AdvanceEMIPayP,DownPaymentP,LoanDisbursementP,LTV_Policy,ROI_policy,payedEMI,notPayedEM);

                                                    }
                                                })

                                                .setNegativeButton("Cancel", null)						//Do nothing on no
                                                .show();
*/
                                        }else{Toast.makeText(GroupProfileForm.this, "Please Check EMI", Toast.LENGTH_SHORT).show();return;}

                                    }else{Toast.makeText(GroupProfileForm.this, "Please Enter Tuner", Toast.LENGTH_SHORT).show();return;}

                                }else{Toast.makeText(GroupProfileForm.this, "Please Check Loan Amount", Toast.LENGTH_SHORT).show();return;}

                            }else{Toast.makeText(GroupProfileForm.this, "Please Enter Price", Toast.LENGTH_SHORT).show();return;}

                    //    }else{Toast.makeText(GroupProfileForm.this, "Please Enter Customer PAN Number", Toast.LENGTH_SHORT).show();return;}

                    }else{Toast.makeText(GroupProfileForm.this, "Please Enter Customer Mobile Number", Toast.LENGTH_SHORT).show();return;}

            //    }else{Toast.makeText(GroupProfileForm.this, "Please Enter Customer Email", Toast.LENGTH_SHORT).show();return;}
            }else{Toast.makeText(GroupProfileForm.this, "Please Enter Customer Name", Toast.LENGTH_SHORT).show();return;}



       /* BlurBehind.getInstance().execute(GroupProfileForm.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(GroupProfileForm.this, MainActivityD.class);
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
        BlurBehind.getInstance().execute(GroupProfileForm.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(GroupProfileForm.this, Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

                //finish();
            }
        });
    }


    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(GroupProfileForm.this);
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
                    data +="&" + URLEncoder.encode("Qualification", "UTF-8") + "="+params[62].toString();
                if(!params[63].equals(""))
                    data +="&" + URLEncoder.encode("Occupation", "UTF-8") + "="+params[63].toString();
                if(!params[64].equals(""))
                    data +="&" + URLEncoder.encode("imei2", "UTF-8") + "="+params[64].toString();
                if(!params[65].equals(""))
                    data +="&" + URLEncoder.encode("religion", "UTF-8") + "="+params[65].toString();

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

                        Toast.makeText(getApplicationContext(), "Loan Information Saved Successfully.", Toast.LENGTH_LONG).show();
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
                            BlurBehind.getInstance().execute(GroupProfileForm.this, new OnBlurCompleteListener() {
                                @Override
                                public void onBlurComplete() {
                                    Intent intent = new Intent(GroupProfileForm.this, Main.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);

                                    //finish();
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
        View v = LayoutInflater.from(this).inflate(R.layout.view_personal_emi_calculater, null);
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


        txtDownPay = (EditText) v.findViewById(R.id.txtDownPay);
        txtNameDisbus = (EditText) v.findViewById(R.id.txtNameDisbus);

        downPricel = (EditText) v.findViewById(R.id.downPricel);

        emiTypeTotalA =  (TextView) v.findViewById(R.id.emiTypeTotalA);

        txtNameTotalPay=  (TextView) v.findViewById(R.id.txtNameTotalPay);




        intrestRTEdit = (TextView) v.findViewById(R.id.intrestRT);
        EMITot= (TextView) v.findViewById(R.id.EMITot);
        EmiFinaml1 = (EditText) v.findViewById(R.id.EmiFinaml1);



        Amount = (TextView)findViewById(R.id.txtNamePrice);
        txtNameTuner1 = (EditText)
                findViewById(R.id.txtNameTuner1);


        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb1.setChecked(rb1 == buttonView);
                    rb2.setChecked(rb2 == buttonView);
                    rb3.setChecked(rb3 == buttonView);
                }

                String searchString1=tenurEdit.getText().toString();
                if(searchString1.equals("")){

                    Toast.makeText(getApplicationContext(), "Please Enter Tenure", Toast.LENGTH_SHORT).show();
                    return;
                }
                int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());

                emi1= addEmi;
                emi2= addEmi/2;
                emi3= addEmi/4;

                if(rb1.isChecked()){
                    checkTun="Month";


                    chkType="1";
                    EmiFinaml1.setText(emi1+"");
                    editText2Emi.setText("EMI is "+emi1);
                    textView7.setText("EMI is "+emi1+" Rs./For Each, with "+ProPer+"% of Loan Price." );
                    TunerTypue.setText("Monthly Installment EMI is "+emi1);
                    tot = emi1*Tu;

                    addEmiP = String.valueOf(emi1);

                }
                if(rb2.isChecked()){
                    checkTun="Fortnight";


                    chkType="2";
                    EmiFinaml1.setText(emi2+"");
                    editText2Emi.setText("EMI is "+emi2);
                    textView7.setText("EMI is "+emi2+" Rs./For Each, with "+ProPer+"% of Loan Price." );

                    TunerTypue.setText("Fortnight Installment EMI is "+emi2);
                    tot = emi2*Tu;
                    addEmiP = String.valueOf(emi2);
                }
                if(rb3.isChecked()){
                    checkTun="Week";



                    chkType="3";
                    EmiFinaml1.setText(emi3+"");
                    editText2Emi.setText("EMI is "+emi3);
                    textView7.setText("EMI is "+emi3+" Rs./For Each, with "+ProPer+"% of Loan Price." );

                    TunerTypue.setText("Week Installment EMI is "+emi3);

                    tot = emi3*Tu;

                    addEmiP = String.valueOf(emi3);
                }


                txtNameTotal.setText("Total: "+tot);
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


                }
//                int temp = Integer.valueOf(tenurEdit.getText().toString().trim());

                if(emi_type1==0||emi_type2==0||emi_type3==0){

                    Toast.makeText(getApplicationContext(), "Please Enter Tenure", Toast.LENGTH_SHORT).show();
                    return;
                }


                Double amount12 = Double.valueOf(inputSearchPricePer.getText().toString().trim());


                Double Real_amount = Double.valueOf(inputSearchPrice.getText().toString().trim());


                if(rb12.isChecked()){
                    Double dounpay= addEmi*emi_type1;
                    downPricel.setText(dounpay.toString());

                    emiTypeTotalA.setText(E1mT2+"|"+E1mT1+" Type "+addEmi+"(EMI)*"+emi_type1+"="+dounpay.toString());

                    Double Douwpay=dounpay+(Real_amount-amount12);
                    Double disbus=Real_amount-Douwpay;
                    txtDownPay.setText(Douwpay.toString());
                    txtNameDisbus.setText(disbus.toString());

                    Amount.setText(Real_amount.toString());

                    CkeckLoan();

                    int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());
                    txtNameTuner1.setText(""+Tu);
                    CkeckEMI();

                    chkType="1";
                    addEmiP = String.valueOf(emi1);


                }
                if(rb22.isChecked()){
                    Double dounpay= addEmi*emi_type2;
                    downPricel.setText(dounpay.toString());

                    emiTypeTotalA.setText(E2mT2+"|"+E2mT1+" Type "+addEmi+"(EMI)*"+emi_type2+"="+dounpay.toString());

                    Double Douwpay=dounpay+(Real_amount-amount12);
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

                    addEmiP = String.valueOf(emi2);

                }
                if(rb32.isChecked()){
                    Double dounpay= addEmi*emi_type3;
                    downPricel.setText(dounpay.toString());

                    emiTypeTotalA.setText(E3mT2+"|"+E3mT1+" Type "+addEmi+"(EMI)*"+emi_type3+"="+dounpay.toString());

                    Double Douwpay=dounpay+(Real_amount-amount12);
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
                    addEmiP = String.valueOf(emi3);

                }

            }

        };

        rb12.setOnCheckedChangeListener(listener2);
        rb22.setOnCheckedChangeListener(listener2);
        rb32.setOnCheckedChangeListener(listener2);

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


                EmiFinaml1.setText("");
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
                    Toast.makeText(getApplicationContext(), "Please Enter Loan Amount", Toast.LENGTH_SHORT).show();

                    return;
                }

                String searchString123=tenurEdit.getText().toString();
                if(searchString123.equals("")) {
                    // Toast.makeText(getApplicationContext(), "Please Enter Tenure Field", Toast.LENGTH_SHORT).show();

                    rb12.setText("X|Y  EMI Type");
                    rb22.setText("M|N  EMI Type");
                    rb32.setText("P|Q  EMI Type");

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

                        Toast.makeText(getApplicationContext(), "Preferable Loan Field is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Double amount12 = Double.valueOf(inputSearchPricePer.getText().toString().trim());


                    Double Real_amount = Double.valueOf(inputSearchPrice.getText().toString().trim());

                    int Tenure = Integer.valueOf(tenurEdit.getText().toString().trim());

                    E1mT1 = Tenure;
                    E1mT2 =  0;

                    emi_type1=E1mT2;

                    rb12.setText(E1mT2+"|"+E1mT1+"  EMI Type");

                    E2mT1 = (60*Tenure)/100;
                    E2mT2 =  Tenure-E2mT1;

                    emi_type2=E2mT2;

                    rb22.setText(E2mT2+"|"+E2mT1+"  EMI Type");

                    E3mT1 = (50*Tenure)/100;
                    E3mT2 =  Tenure-E3mT1;

                    emi_type3=E3mT2;

                    rb32.setText(E3mT2+"|"+E3mT1+"  EMI Type");

                    // percent = Double.valueOf(perNew);
                    Double emi=amount12/Tenure;
                    percent=Double.valueOf(ProPer);


                    Double loanPer=(percent/100)*amount12;

                    // ROIApplied.setText(ProPer.toString());

                    addEmi= emi+loanPer;
                    Double NetPay = emi+loanPer;
                    Double totalimi=addEmi*Tenure;
                    //Double loan = (percent / 100) * amount1;

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


                  if (amount12>=15000){
                        pfee=599.0;
                    }
                     if (amount12>=20001){
                        pfee=699.0;
                    }
                    if (amount12>=25001){
                        pfee=799.0;
                    }
                     if (amount12>=30001){
                        pfee=899.0;
                    }
                    if(amount12<15000){
                        pfee=599.0;
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
                   // CkeckEMI();

                    AdvanceEMIPay.setText(dounpay.toString());
                    DownPayment.setText(Douwpay.toString());
                    LoanDisbursement.setText(disbus.toString());

                    //chkType="1";

                   emi1= addEmi;
                    emi2= addEmi/2;
                    emi3= addEmi/4;

                    if(rb1.isChecked()){

                        EmiFinaml1.setText(emi1.toString());

                        TunerTypue.setText("Monthly Installment EMI is "+emi1);
                        checkTun="Month";

                        editText2Emi.setText("EMI is "+emi1);
                        textView7.setText("EMI is "+emi1+" Rs./For Each, with "+ProPer+"% of Loan Price." );

                        tot = emi1*Tenure;

                        addEmiP = String.valueOf(emi1);


                        chkType="1";

                    }
                    if(rb2.isChecked()){
                        EmiFinaml1.setText(emi2.toString());
                        TunerTypue.setText("Fortnight Installment EMI is "+emi2);
                        checkTun="Fortnight";

                        editText2Emi.setText("EMI is "+emi2);
                        textView7.setText("EMI is "+emi2+" Rs./For Each, with "+ProPer+"% of Loan Price." );

                        tot = emi2*Tenure;

                        addEmiP = String.valueOf(emi2);
                        chkType="2";

                    }
                    if(rb3.isChecked()){
                        EmiFinaml1.setText(emi3.toString());
                        TunerTypue.setText("Week Installment EMI is "+emi3);
                        checkTun="Week";

                        editText2Emi.setText("EMI is "+emi3);
                        textView7.setText("EMI is "+emi3+" Rs./For Each, with "+ProPer+"% of Loan Price." );

                        tot = emi3*Tenure;
                        addEmiP = String.valueOf(emi3);

                        chkType="3";
                    }


                    txtNameTotal.setText("Total: "+tot);
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
    public void onResume() {
        super.onResume();
    }



    public void datapick(View view){
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
                Toast.makeText(GroupProfileForm.this, "Date is " + date, Toast.LENGTH_SHORT).show();
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
        showDialog(999);
    }

    public void cal(View view){
        showBottomSheet();
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
        EditText dob=(EditText)findViewById(R.id.dob);
        //  dob.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
        dob.setText(new StringBuilder().append(day).append("-").append(month).append("-").append(year));

    }



    public void selectState(){
        infracom.abcr.sarthamicrofinance.material.app.Dialog.Builder builder = null;

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                if(sta==1){
                    State1.setText(getSelectedValue());
                } if(sta==3){
                    State2.setText(getSelectedValue());
                } if(sta==2){
                    State3.setText(getSelectedValue());
                }
                //Toast.makeText(GroupProfileForm.this, "You have selected " + getSelectedValue() + " as phone ringtone.", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(GroupProfileForm.this, "Cancelled" , Toast.LENGTH_SHORT).show();
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
                Toast.makeText(GroupProfileForm.this, "Cancelled" , Toast.LENGTH_SHORT).show();
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
                Toast.makeText(GroupProfileForm.this, "Cancelled" , Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(GroupProfileForm.this, Main.class);
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


    public void Purpose(View view){
        infracom.abcr.sarthamicrofinance.material.app.Dialog.Builder builder = null;

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
        builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                productD.setText(getSelectedValue());
                //Toast.makeText(Order.this, "You have selected " + getSelectedValue() + " as phone ringtone.", Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(GroupProfileForm.this, "Cancelled" , Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        ((SimpleDialog.Builder)builder).items(new String[]{
                "To Start New Business",
                "Enhance Present Business",
                "Domestic Need",
                "Family Need",
                "Medical Agency",
                "Other"
        }, 0)
                .title("Select Loan Purpose")
                .positiveAction("OK")
                .negativeAction("CANCEL");


        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);

    }



}
