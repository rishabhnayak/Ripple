package infracom.abcr.sarthamicrofinance.Profile;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

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

import infracom.abcr.sarthamicrofinance.AllShow_Message;
import infracom.abcr.sarthamicrofinance.AssignEMP.Recovery_Person;
import infracom.abcr.sarthamicrofinance.CATEGORIES.Sartha_Personal;
import infracom.abcr.sarthamicrofinance.CATEGORIES.Sartha_Product;
import infracom.abcr.sarthamicrofinance.Chart.data.Common;
import infracom.abcr.sarthamicrofinance.Chart.data.XY;
import infracom.abcr.sarthamicrofinance.Chart.view.AxisXView;
import infracom.abcr.sarthamicrofinance.Chart.view.AxisYView_NormalType;
import infracom.abcr.sarthamicrofinance.Chart.view.LineView;
import infracom.abcr.sarthamicrofinance.Chart.view.TitleView;
import infracom.abcr.sarthamicrofinance.Config;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.MainActivityTask;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.GridViewExample;
import infracom.abcr.sarthamicrofinance.GroupLoan.Approve_Group_Details;
import infracom.abcr.sarthamicrofinance.GroupLoan.DsaRecovery;
import infracom.abcr.sarthamicrofinance.GroupLoan.GroupDisbursement;
import infracom.abcr.sarthamicrofinance.GroupLoan.GroupLoanSearch;
import infracom.abcr.sarthamicrofinance.GroupLoan.Group_Approve_List;
import infracom.abcr.sarthamicrofinance.Main;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.RegisterActivity;
import infracom.abcr.sarthamicrofinance.helper.SessionManager;
import infracom.abcr.sarthamicrofinance.material.app.BottomSheetDialog;
import infracom.abcr.sarthamicrofinance.material.drawable.ThemeDrawable;
import infracom.abcr.sarthamicrofinance.material.util.ViewUtil;
import infracom.abcr.sarthamicrofinance.material.widget.ProgressView;
import infracom.abcr.sarthamicrofinance.material.widget.RadioButton;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;
import mehdi.sakout.fancybuttons.FancyButton;

public class Marketing extends AppCompatActivity {

    //RSSFeed lfflfeed;
    private static final int REQUEST_LOCATION = 1;
    String latitude;
    String longitude;
    LocationManager locationManager;
    // String lat,lon;

    String branch_name,branch_id;

    EditText PF,inputSearchPrice, inputSearchPricePer, txtNamePrice2, tenurEdit,EmiFinaml, downPricel,txtNameDisbus,txtDownPay,LoanDisbursement,DownPayment,AdvanceEMIPay;
    RadioButton rb1, rb12;
    RadioButton rb2, rb22;
    RadioButton rb3, rb32;
    RadioButton rb4, rb42;
    RadioButton rb5, rb52;
    TextView intrestRTEdit, EMITot, emiTypeTotalA, txtNameTotalPay,TunerTypue;

    public static String checkTun,chkType;
    int E1mT1,E2mT1,E3mT1,E1mT2,E2mT2,E3mT2,E4mT2,E5mT2,E4mT1,E5mT1;


    int emi_type= 0,emi_type1=0,emi_type2=0,emi_type3=0,emi_type4=0,emi_type5=0;

    String payedEMI,notPayedEM;
    private static double percent;
    double addEmi1;
    private static double tot;
    TextView  Amount;
    TextView LoanAmount;
    EditText txtNameTuner1,ROIApplied,LTVPolicy;
    TextView editText2Emi,txtNameTotal,textView7;


    private static double addEmi;

    String num,Mnum, perNew="85";
    String LTV_Policy, ROI_policy,OldNewPercent;


    private LinearLayout axisYLayout = null;
    private LinearLayout axisXLayout = null;
    private LinearLayout threndLine_Layout = null;
    private LinearLayout title_layout = null;



    private TitleView titleView;
    private LineView lineView;
    private AxisYView_NormalType axisY_2;
    private AxisXView axisX;

    private XY xy = new XY();
    private float oldX = 0;
    private float oldY = 0;

    private CoordinatorLayout coordinatorLayout;

    private static final long PROGRESS_INTERVAL = 7000;
    private static final long START_QUERY_DELAY = PROGRESS_INTERVAL / 2;
    ArrayList<HashMap<String, Object>> searchResults1=null;

    //ArrayList that will hold the original Data
    ArrayList<HashMap<String, Object>> originalValues1=null;

    ArrayList<String> listsubR = new ArrayList<String>();
    ProgressView prog;
    ArrayList<String> listsub1 = new ArrayList<String>();
    ArrayList<String> listsub2 = new ArrayList<String>();
    ArrayList<String> listsub3 = new ArrayList<String>();

    ArrayList<String> listsub4 = new ArrayList<String>();
    ArrayList<String> listsub5 = new ArrayList<String>();
    ArrayList<String> listsub6 = new ArrayList<String>();

    ArrayList<String> listsub7 = new ArrayList<String>();

    ArrayList<String> listsub8 = new ArrayList<String>();


    ArrayList<String> listsub9 = new ArrayList<String>();

    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/DSA_All_Customer_list.php";

    String serverURL1 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/DSA_All_Customer_list.php";

    String QueryserverURL1 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Query_DSA_Customer_list.php";

    ListView mylistview1;
    String[] member_names1;
    String product,SelectCat,ProPer="2.5", empname,task1;
    TextView proName;

    EditText inputSearch;

    ArrayAdapter<String> adapter;

    private EditText edtSeach;

    String PENDING,sms_status,CLEAR,DONE,TOTAL,FINANCIAL,DISBURSEMENT,iQuery,ins;
    ProgressDialog dialog;
    public String user_id, email, name, lastname, mobile, fullname,sts,regid;
    private String Customer_uSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/DSA_ALL_View_customer_details.php";

    TextView notVery,Veri,textViewDone,textViewTot,notifi,Disbue,Query,adminfinancial;

    Controller aController = null;


    private ProgressDialog pDialog;

    DBAdapter db;

    static  String cid,cname;


    String deviceIMEI = "";

    private BottomSheetDialog mBottomSheetDialog;

    TextView ProcessingFee;



    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketing);


        session = new SessionManager(getApplicationContext());
        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Marketing.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }



        try{
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            gps();
        }catch (Exception e){}


        FancyButton task = (FancyButton) findViewById(R.id.Task);

        FancyButton pListP = (FancyButton) findViewById(R.id.pListP);

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();


        notVery = (TextView) findViewById(R.id.notVery);
        Veri = (TextView) findViewById(R.id.Veri);
        textViewDone = (TextView) findViewById(R.id.textViewDone);
        textViewTot = (TextView) findViewById(R.id.textViewTot);
        notifi =  (TextView) findViewById(R.id.notifi);


        Disbue = (TextView) findViewById(R.id.DISBURSEMENT);
        Query = (TextView) findViewById(R.id.Query);

        adminfinancial= (TextView)findViewById(R.id.adminfinancial);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // Get Global variable instance
        aController = (Controller) getApplicationContext();

        pDialog = new ProgressDialog(this);
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();

        // Check if Internet Connection present
        if (!aController.isConnectingToInternet()) {

            // Internet Connection is not present
            aController.showAlertDialog(Marketing.this,
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
            aController.showAlertDialog(Marketing.this,
                    "Configuration Error!",
                    "Please set your Server URL and FCM Sender ID",
                    false);

            // stop executing code by return
            return;
        }


        db = new DBAdapter(this);

        // db.open();
        HashMap<String, String> dataf = db.getLogininfo();
        String email = dataf.get("email");
        //empname = dataf.get("name");
        regid = dataf.get("regid");
        String empdevice_imei = dataf.get("device_imei");
        String user_id = dataf.get("user_id");
        cid=empdevice_imei;
        deviceIMEI = empdevice_imei.toString().trim();
        //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();

        branch_name =  dataf.get("branch_name");
        branch_id =  dataf.get("branch_id");

        TextView textView29 = (TextView)findViewById(R.id.textView29);
        textView29.setText("MARKETING PERSON TASK - " +branch_name+"("+branch_id+")"+" v2.1");


        db.close();




        String lat = latitude;
        String lon = longitude;


        new ManagerLongOperation().execute(Customer_uSERVER_URL,cid,deviceIMEI,lat,lon,regid);


        task.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                BlurBehind.getInstance().execute(Marketing.this, new OnBlurCompleteListener() {
                    @Override
                    public void onBlurComplete() {
                        Intent intent = new Intent(Marketing.this, MainActivityTask.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        startActivity(intent);
                        // finish();
                    }
                });

            }

        });


        pListP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                BlurBehind.getInstance().execute(Marketing.this, new OnBlurCompleteListener() {
                    @Override
                    public void onBlurComplete() {
                        Intent intent = new Intent(Marketing.this, Sartha_Personal.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        startActivity(intent);
                        // finish();
                    }
                });

            }

        });


    }


    public void logout(View view){


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder
                //.setTitle("............Pay")
                .setMessage("Are you Sure to Exit!")
                //.setMessage("Customer Details!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        session.setLogin(false);

                        DBAdapter.deleteAll();
                        logt();

                        // Launching the login activity
                        Toast.makeText(getApplicationContext(), "Logged Out!", Toast.LENGTH_LONG).show();
                    }
                })

                .setNegativeButton("Cancel", null)						//Do nothing on no
                .show();
        //db.deleteUsers();

    }
    public void logt(){
        Intent intent1 = new Intent(Marketing.this, RegisterActivity.class);
        startActivity(intent1);
        finish();
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void grouploan(View view){
        Intent intent1 = new Intent(Marketing.this, GroupLoanSearch.class);
        startActivity(intent1);
        //finish();
    }


    // Class with extends AsyncTask class
    public class ManagerLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Marketing.this);
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
                    data +="&" + URLEncoder.encode("emp_id", "UTF-8") + "="+params[2].toString();
                if(!params[3].equals(""))
                    data +="&" + URLEncoder.encode("latitude", "UTF-8") + "="+params[3].toString();
                if(!params[4].equals(""))
                    data +="&" + URLEncoder.encode("longitude", "UTF-8") + "="+params[4].toString();
                if(!params[5].equals(""))
                    data +="&" + URLEncoder.encode("regid", "UTF-8") + "="+params[5].toString();


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

                        //  Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_LONG).show();
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        boolean error       = jsonChildNode.getBoolean("error");
                        if (!error) {
                            PENDING       = jsonChildNode.optString("PENDING").toString();
                            CLEAR       = jsonChildNode.optString("CLEAR").toString();
                            DONE       = jsonChildNode.optString("DONE").toString();
                            TOTAL       = jsonChildNode.optString("TOTAL").toString();


                            DISBURSEMENT       = jsonChildNode.optString("DISBURSEMENT").toString();
                            FINANCIAL       = jsonChildNode.optString("FINANCIAL").toString();
                            iQuery       = jsonChildNode.optString("QUERY").toString();

                            sms_status       = jsonChildNode.optString("sms_status").toString();

                            sms_status       = jsonChildNode.optString("sms_status").toString();

                            ins       = jsonChildNode.optString("ins").toString();

                            TextView notifiAllot = (TextView)findViewById(R.id.notifiAllot);

                            if(ins.equals("0")){
                                notifiAllot.setVisibility(View.GONE);
                            }else{
                                notifiAllot.setVisibility(View.VISIBLE);
                                notifiAllot.setText(ins);
                            }


                            notVery.setText(PENDING);
                            Veri.setText(CLEAR);
                            textViewDone.setText(DONE);
                            textViewTot.setText(TOTAL);


                            if(PENDING.equals("0")){
                                notVery.setVisibility(View.GONE);
                            }else {
                                notVery.setVisibility(View.VISIBLE);
                                notVery.setText(PENDING);
                            }
                            if(CLEAR.equals("0")){
                                Veri.setVisibility(View.GONE);
                            }else {
                                Veri.setVisibility(View.VISIBLE);
                                Veri.setText(CLEAR);
                            }
                            if(DONE.equals("0")){
                                textViewDone.setVisibility(View.GONE);
                            }else {
                                textViewDone.setVisibility(View.VISIBLE);
                                textViewDone.setText(DONE);
                            }
                            if(TOTAL.equals("0")){
                                textViewTot.setVisibility(View.GONE);
                            }else {
                                textViewTot.setVisibility(View.VISIBLE);
                                textViewTot.setText(TOTAL);
                            }

                            if(DISBURSEMENT.equals("0")){
                                Disbue.setVisibility(View.GONE);
                            }else {
                                Disbue.setVisibility(View.VISIBLE);
                                Disbue.setText(DISBURSEMENT);
                            }

                            if(FINANCIAL.equals("0")){
                                adminfinancial.setVisibility(View.GONE);
                            }else {
                                adminfinancial.setVisibility(View.VISIBLE);
                                adminfinancial.setText(FINANCIAL);
                            }

                            if(iQuery.equals("0")){
                                Query.setVisibility(View.GONE);
                            }else {
                                Query.setVisibility(View.VISIBLE);
                                Query.setText(iQuery);
                            }

                            if(sms_status.equals("null")||sms_status.equals("")||sms_status.equals("0")){
                                notifi.setVisibility(View.GONE);
                            }else {
                                notifi.setText(sms_status);
                                cler();
                            }

                        }else
                        {
                            //  Toast.makeText(getApplicationContext(), "Server Error Try Again!", Toast.LENGTH_LONG).show();
                        }

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }

    }


    public void backokLLL(View view){
        BlurBehind.getInstance().execute(Marketing.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(Marketing.this, EmpProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                //finish();
            }
        });
    }

    public void showMesaage(View view){
        notifi.setText("");
        notifi.setVisibility(View.GONE);
        Intent intent = new Intent(Marketing.this, AllShow_Message.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        //finish();

    }


    public void NotVery(View v){

        //Intent intent = new Intent(getApplicationContext(), MainActivityTask.class);
        // intent.putExtra("Task", "PENDING");
        // intent.putExtra("emp", "Manager");
        // intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        // startActivity(intent);
        task1="PENDING";
        showBottomSheet(task1);

    }
    public void VeryD(View v){
        //Intent intent = new Intent(getApplicationContext(), MainActivityTask.class);
        //intent.putExtra("Task", "CLEAR");
        //intent.putExtra("emp", "Manager");
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //startActivity(intent);
        task1="CLEAR";
        showBottomSheet(task1);
    }
    public void DoneF(View v){
        //Intent intent = new Intent(getApplicationContext(), MainActivityTask.class);
        //intent.putExtra("Task", "DONE");
        //intent.putExtra("emp", "Manager");
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //startActivity(intent);
        task1="DONE";
        showBottomSheet(task1);
    }
    public void TotD(View v){
        //Intent intent = new Intent(getApplicationContext(), MainActivityTask.class);
        //intent.putExtra("Task", "TOTAL");
        //intent.putExtra("emp", "Manager");
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //startActivity(intent);
        task1="TOTAL";
        showBottomSheet(task1);
    }

    public void QueryClick(View view){
        task1="QUERY";
        showBottomSheet(task1);
    }

    public void Disbus(View view){
        task1="DISBUS";
        showBottomSheet(task1);
    }

    public void showBottomSheet(String task1){
        mBottomSheetDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.view_manager_search, null);
        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window));

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
        listsub6.clear();
        listsub7.clear();
        listsub8.clear();
        listsub9.clear();

        originalValues1=null;
        searchResults1=null;


        inputSearch = (EditText) v.findViewById(R.id.inputSearch);

        db = new DBAdapter(getApplicationContext());

        // db.open();
        HashMap<String, String> dataf = db.getLogininfo();
        String user_id = dataf.get("user_id");
        empname = dataf.get("name");
        regid = dataf.get("regid");
        String empdevice_imei = dataf.get("device_imei");
        deviceIMEI =empdevice_imei;
        //Toast.makeText(getContext(),""+deviceIMEI, Toast.LENGTH_SHORT).show();
        db.close();

        // Menu menu; {
        // MenuItem mSearchAction = v.menu.findItem(R.id.action_search);


        //  inputSearch = (EditText)v.findViewById(R.id.edtSearch); //the text editor



//        aController = (Controller)v.getContext();
        product = "Mobile";
        //  SelectCat = intent.getStringExtra("SelectCat");
        // ProPer = intent.getStringExtra("ProPer");

        //   proName = (TextView) findViewById(R.id.textPoster);
        //  proName.setText("Select " + product);
        mylistview1 = (ListView) v.findViewById(R.id.listViewtotalCC);

        // inflater=(LayoutInflater) v.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(task1.equals("TOTAL")){
            new LongOperation().execute(serverURL,cid,"","");

        }
        if(task1.equals("DISBUS")){

            new LongOperation().execute(serverURL1, cid, task1, "");
        }

        if(task1.equals("QUERY")){
            new LongOperation().execute(QueryserverURL1,cid,task1,"");

        }if(task1.equals("CLEAR")){
            new LongOperation().execute(serverURL1,cid,task1,"");

        }if(task1.equals("DONE")){
            new LongOperation().execute(serverURL1,cid,task1,"");

        }







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

    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {

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
//            Dialog.setMessage("Loading...");
            //          Dialog.show();

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


                                                listsub4.add(prods.getJSONObject(j).getString("sub4"));
                                                listsub5.add(prods.getJSONObject(j).getString("sub5"));
                                                listsub6.add(prods.getJSONObject(j).getString("sub6"));
                                                listsub7.add(prods.getJSONObject(j).getString("sub7"));
                                                listsub8.add(prods.getJSONObject(j).getString("sub8"));
                                                listsub9.add(prods.getJSONObject(j).getString("sub9"));

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

        String[] Simage = new String[listsub6.size()];
        Simage = listsub6.toArray(Simage);

        String[] message = new String[listsub7.size()];
        message = listsub7.toArray(message);


        String[] time = new String[listsub8.size()];
        time = listsub8.toArray(time);

        String[] casec = new String[listsub9.size()];
        casec = listsub9.toArray(casec);

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
            temp.put("Simage", Simage[i]);
            temp.put("message", message[i]);
            temp.put("time", time[i]);
            temp.put("casec", casec[i]);

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
        final CustomAdapter adapteroo=new CustomAdapter(getApplicationContext(), R.layout.list_item,searchResults1);

        //finally,set the adapter to the default ListView
        mylistview1.setAdapter(adapteroo);
        inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString=inputSearch.getText().toString();
                int textLength=searchString.length();
                searchResults1.clear();

                for(int i=0;i<originalValues1.size();i++)
                {

                    String playerName=originalValues1.get(i).get("name").toString();
                    String idP=originalValues1.get(i).get("team").toString();

                    if(textLength<=playerName.length()){
                        //compare the String in EditText with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
                            searchResults1.add(originalValues1.get(i));
                    }
                    if(textLength<=idP.length()){
                        //compare the String in EditText with Names in the ArrayList
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
            ImageView photo,Simage;
            TextView name,team,contact_type,Sname,Sid,message,time,casec;

        }

        ViewHolder viewHolder=null;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                //View view = inflater.inflate(android.R.layout.list_item_recyclerView, parent, false);
                if(task1.equals("QUERY")){
                    convertView=inflater.inflate(R.layout.query, null);
                }else {
                    convertView=inflater.inflate(R.layout.mng_list, null);
                }

                viewHolder=new ViewHolder();

                //cache the views
                viewHolder.photo=(ImageView) convertView.findViewById(R.id.profile_pic);
                viewHolder.name=(TextView) convertView.findViewById(R.id.member_name);
                viewHolder.team=(TextView) convertView.findViewById(R.id.status);
                viewHolder.contact_type=(TextView) convertView.findViewById(R.id.contact_typetime);

                viewHolder.casec=(TextView) convertView.findViewById(R.id.csase);

                viewHolder.message=(TextView) convertView.findViewById(R.id.textView37);
                viewHolder.time=(TextView) convertView.findViewById(R.id.timrid);
                viewHolder.Sname=(TextView) convertView.findViewById(R.id.sname);
                viewHolder.Sid=(TextView) convertView.findViewById(R.id.sid);
                viewHolder.Simage=(ImageView) convertView.findViewById(R.id.profile_pic1);
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
            Glide.with(getContext())
                    //.(R.drawable.ic_launcher)
                    .load(searchResults1.get(position).get("Simage").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.Simage);
            //imageLoader.DisplayImage(searchResults1.get(position).get("photo").toString(), viewHolder.photo);
            //set the data to be displayed
            //viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
            viewHolder.name.setText(searchResults1.get(position).get("name").toString());


            viewHolder.Sname.setText(searchResults1.get(position).get("Sname").toString());
            viewHolder.Sid.setText(searchResults1.get(position).get("Sid").toString());
            viewHolder.time.setText(searchResults1.get(position).get("time").toString());
            viewHolder.message.setText(searchResults1.get(position).get("message").toString());

            viewHolder.casec.setText(searchResults1.get(position).get("casec").toString());

            viewHolder.contact_type.setText(searchResults1.get(position).get("status").toString());

            final String lonper = searchResults1.get(position).get("team").toString();
            String prop=searchResults1.get(position).get("team").toString()+"";


            viewHolder.team.setText(prop);

            final ViewHolder finalHolder = viewHolder;
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    final String namee = finalHolder.name.getText().toString().trim();
                    final String eid = finalHolder.team.getText().toString().trim();
                    final String sts = finalHolder.contact_type.getText().toString().trim();
                    // Toast.makeText(finalConvertView.getContext(), ""+namee, Toast.LENGTH_SHORT).show();
                    //MainActivityD mainD = null;
                    //mainD.privecyTerms();




                    if(task1.equals("QUERY")){
                        Intent intent = new Intent(getContext(), Approve_Group_Details.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        intent.putExtra("cname", namee);
                        intent.putExtra("cid", eid);
                        intent.putExtra("sts", "QUERY");
                        startActivity(intent);
                    }else {


                        //Toast.makeText(getApplicationContext(), "else In", Toast.LENGTH_SHORT).show();
                        if (task1.equals("DISBUS")) {
                            Intent intent1 = new Intent(getContext(), GroupDisbursement.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                            intent1.putExtra("cname", namee);
                            intent1.putExtra("cid", eid);
                            intent1.putExtra("sts", "DISBUS");
                            startActivity(intent1);
                        }else{


                            // Toast.makeText(getApplicationContext(), "else Go", Toast.LENGTH_SHORT).show();

                            Intent intent2 = new Intent(getContext(), Group_Approve_List.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                            intent2.putExtra("cname", namee);
                            intent2.putExtra("cid", eid);
                            intent2.putExtra("sts", sts);

                            startActivity(intent2);
                        }
                    }
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


    public void shoNewsms(View view){
        Intent intent = new Intent(Marketing.this, GridViewExample.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverLoadTodays, new IntentFilter("update-message"));
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private BroadcastReceiver broadcastReceiverLoadTodays = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(getApplicationContext(), intent.getExtras().getString("message"), Toast.LENGTH_SHORT).show();
            //  TextView textViewMessage = (TextView) findViewById(R.id.lblMessage);
            //  textViewMessage.setText(intent.getExtras().getString("message"));

            cler();


            db = new DBAdapter(getApplicationContext());

            // db.open();
            HashMap<String, String> dataf = db.getLogininfo();
            String email = dataf.get("email");
            //empname = dataf.get("name");
            regid = dataf.get("regid");
            String empdevice_imei = dataf.get("device_imei");
            String user_id = dataf.get("user_id");
            cid=empdevice_imei;
            deviceIMEI = empdevice_imei.toString().trim();
            //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
            db.close();




            String lat = latitude;
            String lon = longitude;


            new ManagerLongOperation().execute(Customer_uSERVER_URL,cid,deviceIMEI,lat,lon,regid);
        }
    };



    public void cler(){

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .pop);

        Snackbar snackbar = Snackbar.make(
                coordinatorLayout,
                "Please Check Verification Pending Details.",
                Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(25000);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.WHITE);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.BLUE);

        snackbar.setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(Manager_Launch.this,"CLOSED", Toast.LENGTH_LONG).show();
            }
        });

        snackbar.show();
/*

    Dialog.Builder builder = null;

    boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;


    builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){

        @Override
        protected void onBuildDone(Dialog dialog) {
            dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        @Override
        public void onPositiveActionClicked(DialogFragment fragment) {
            // EditText et_pass = (EditText)fragment.getDialog().findViewById(R.id.custom_et_password);
            //Toast.makeText(mActivity, "Connected", Toast.LENGTH_SHORT).show();
            super.onPositiveActionClicked(fragment);
        }

        @Override
        public void onNegativeActionClicked(DialogFragment fragment) {
            //Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_SHORT).show();
            super.onNegativeActionClicked(fragment);
        }
    };

    builder.title("Google Wi-Fi")
            .positiveAction("CONNECT")
            .negativeAction("CANCEL")
            .contentView(R.layout.custom_layout);


    DialogFragment fragment = DialogFragment.newInstance(builder);
    fragment.show(getSupportFragmentManager(), null);
*/
    }



    public void Recovery(View view){

        Intent intent = new Intent(Marketing.this, DsaRecovery.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("depart", "DSA Person");
        startActivity(intent);

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

                   // Amount.setText(Real_amount.toString());

                  //  CkeckLoan();

                    int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());
                  //  txtNameTuner1.setText(""+Tu);
                  //  CkeckEMI();

                  //  AdvanceEMIPay.setText(dounpay.toString());
                   // DownPayment.setText(Douwpay.toString());
                   // LoanDisbursement.setText(disbus.toString());

                    chkType="1";

                    payedEMI= String.valueOf(E1mT2);
                    notPayedEM= String.valueOf(E1mT1);
                }
                if(rb22.isChecked()){
                    Double dounpay= addEmi*emi_type2;
                    downPricel.setText(dounpay.toString());

                    emiTypeTotalA.setText(E2mT2+"|"+E2mT1+" Type "+addEmi+"(EMI)*"+emi_type2+"="+dounpay.toString());

                    Double Douwpay=dounpay+(Real_amount-amount12);
                    Double disbus=Real_amount-Douwpay;
                    txtDownPay.setText(Douwpay.toString());
                    txtNameDisbus.setText(disbus.toString());

                    //Amount.setText(Real_amount.toString());

                  //  CkeckLoan();

                    int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());
               //     txtNameTuner1.setText(""+Tu);
                   // CkeckEMI();

                   // AdvanceEMIPay.setText(dounpay.toString());
                   // DownPayment.setText(Douwpay.toString());
                  //  LoanDisbursement.setText(disbus.toString());
                    chkType="2";

                    payedEMI= String.valueOf(E2mT2);
                    notPayedEM= String.valueOf(E2mT1);
                }
                if(rb32.isChecked()){
                    Double dounpay= addEmi*emi_type3;
                    downPricel.setText(dounpay.toString());

                    emiTypeTotalA.setText(E3mT2+"|"+E3mT1+" Type "+addEmi+"(EMI)*"+emi_type3+"="+dounpay.toString());

                    Double Douwpay=dounpay+(Real_amount-amount12);
                    Double disbus=Real_amount-Douwpay;
                    txtDownPay.setText(Douwpay.toString());
                    txtNameDisbus.setText(disbus.toString());

//                    Amount.setText(Real_amount.toString());

                 //   CkeckLoan();

                    int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());
                  //  txtNameTuner1.setText(""+Tu);
                  //  CkeckEMI();



                //    AdvanceEMIPay.setText(dounpay.toString());
                 //   DownPayment.setText(Douwpay.toString());
                 //   LoanDisbursement.setText(disbus.toString());

                    chkType="3";

                    payedEMI= String.valueOf(E3mT2);
                    notPayedEM= String.valueOf(E3mT1);
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
               // LoanAmount.setText(searchString.toString());
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

                //AdvanceEMIPay.setText("");
               // DownPayment.setText("");
               // LoanDisbursement.setText("");
               // emiTypeTotalA.setText("");


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
                    Double emi=Real_amount/Tenure;
                    percent=Double.valueOf(ProPer);


                    Double loanPer=(percent/100)*Real_amount;

                    // ROIApplied.setText(ProPer.toString());

                    Double addEmi= emi+loanPer;
                    Double NetPay = emi+loanPer;
                    Double totalimi=addEmi*Tenure;
                    //Double loan = (percent / 100) * amount1;
                    EmiFinaml.setText(NetPay.toString());

                    //Double MainAm=Double.valueOf(Tenure);


                    intrestRTEdit.setText("Interest is (ROI Policy) "+ProPer+"%");

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

                //    Amount.setText(Real_amount.toString());

                   // CkeckLoan();

                    int Tu = Integer.valueOf(tenurEdit.getText().toString().trim());
                    //txtNameTuner1.setText(""+Tu);
                  //  CkeckEMI();

                   // AdvanceEMIPay.setText(dounpay.toString());
                  //  DownPayment.setText(Douwpay.toString());
                  //  LoanDisbursement.setText(disbus.toString());

                    chkType="1";
/*
                    if(rb1.isChecked()){
                        TunerTypue.setText("Monthly Installment EMI is "+addEmi);
                        checkTun="Month";
                    }
                    if(rb2.isChecked()){
                        TunerTypue.setText("Fortnight Installment EMI is "+addEmi);
                        checkTun="Fortnight";
                    }
                    if(rb3.isChecked()){
                        TunerTypue.setText("Week Installment EMI is "+addEmi);
                        checkTun="Week";
                    }
                    */
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

    public void calcu(View view){
        showBottomSheet();
    }


    public void gps() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(Marketing.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (Marketing.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Marketing.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);


//                Toast.makeText(this, "Your current location is"+ "\n" + "Lattitude = " + latitude + "\n" + "Longitude = " + longitude ,Toast.LENGTH_SHORT).show();


            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                latitude = String.valueOf(latti);
                longitude= String.valueOf(longi);

//                Toast.makeText(this, "Your current location is"+ "\n" + "Lattitude = " + latitude + "\n" + "Longitude = " + longitude ,Toast.LENGTH_SHORT).show();


            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                latitude = String.valueOf(latti);
                longitude= String.valueOf(longi);

//                 Toast.makeText(this,"Your current location is"+ "\n" + "Lattitude = " + latitude + "\n" + "Longitude = " + longitude,Toast.LENGTH_SHORT).show();

            }else{

                //             Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



}