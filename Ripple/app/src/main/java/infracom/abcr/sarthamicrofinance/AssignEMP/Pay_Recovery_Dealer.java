package infracom.abcr.sarthamicrofinance.AssignEMP;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

import de.hdodenhof.circleimageview.CircleImageView;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Dpost.ImageLoader;
import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.Main;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.material.app.BottomSheetDialog;
import infracom.abcr.sarthamicrofinance.material.app.DialogFragment;
import infracom.abcr.sarthamicrofinance.material.app.SimpleDialog;
import infracom.abcr.sarthamicrofinance.material.app.ThemeManager;
import infracom.abcr.sarthamicrofinance.material.drawable.ThemeDrawable;
import infracom.abcr.sarthamicrofinance.material.util.ViewUtil;
import infracom.abcr.sarthamicrofinance.material.widget.LinearLayout;
import infracom.abcr.sarthamicrofinance.material.widget.ProgressView;
import infracom.abcr.sarthamicrofinance.material.widget.RadioButton;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;
import mehdi.sakout.fancybuttons.FancyButton;

public class Pay_Recovery_Dealer extends AppCompatActivity {


    static  String cphoto;

    public static final String CROPPED_IMAGE_FILEPATH = "/sdcard/SarthaMicroFinance.jpg";
    TextView et_pass;
    GPSTracker gps;
    double latitude;
    double longitude;

    ArrayList<HashMap<String, Object>> searchResults,searchResults1;
    String currentD, currentDTo;
    //ArrayList that will hold the original Data
    ArrayList<HashMap<String, Object>> originalValues;
    LayoutInflater inflater;
    //String[] names;
    //String names[]={"Ronaldo","Messi","Torres","Iniesta", "Drogba","Gerrard","Rooney","Xavi"};


    static  String cid,cname;


    ArrayList<String> listsubR = new ArrayList<String>();

    ArrayList<String> listsub1 = new ArrayList<String>();
    ArrayList<String> listsub2 = new ArrayList<String>();
    ArrayList<String> listsub3 = new ArrayList<String>();
    ArrayList<String> icon = new ArrayList<String>();

    ArrayList<String> ROI_Applied = new ArrayList<String>();
    ArrayList<String> LTV_Policy = new ArrayList<String>();
    ArrayList<String> LTV_Applied = new ArrayList<String>();

    ArrayList<String> listsubR1 = new ArrayList<String>();

    ArrayList<String> listsub11 = new ArrayList<String>();
    ArrayList<String> listsub21 = new ArrayList<String>();
    ArrayList<String> listsub31 = new ArrayList<String>();
    ArrayList<String> icon1 = new ArrayList<String>();


    ArrayList<String> interval = new ArrayList<String>();
    ArrayList<String> emp_id = new ArrayList<String>();
    ArrayList<String> Ename = new ArrayList<String>();

    ArrayList<String> no = new ArrayList<String>();
    ArrayList<String> city = new ArrayList<String>();
    ArrayList<String> product_name = new ArrayList<String>();
    ArrayList<String> emi = new ArrayList<String>();
    ArrayList<String> ROI_policy = new ArrayList<String>();
    ArrayList<String> installment_date = new ArrayList<String>();

    ArrayList<String> late_charge = new ArrayList<String>();

    ArrayList<String>  mobile = new ArrayList<String>();

    ArrayList<String> remarks = new ArrayList<String>();



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
    private BottomSheetDialog mBottomSheetDialog;
    private  String Approve_Summ_LongOperation_url="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Approve_Summ_LongOperation.php";

    String serverURL=null;
    String serverURL1 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Assign_Pending_list.php";

    String serverURLS11 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Recovery_Search.php";


    ProgressView prog;
    String type;

    ListView mylistview1, mylistview2;
    String[] member_names1;
    String product,SelectCat,ProPer,lonper11,lonper12,lonper13,lonper14,lonper15,lonper16,lonper17,lonper18,lonper19,lonper112;
    TextView proName, dddd,cccc;

    ArrayAdapter<String> adapter;

    public Controller aController = null;

    // Search EditText
    EditText inputSearch,inputSearchCHQ;


    EditText editTextB1, editTextB2;

    String status,installment_date2,emiPay,total_late_charge_panalty,mobile1;

    String paid_amount,intervaltext;
    String loan_install_no;
    String cheque_number;

    private Handler mHandler;

    private ProgressView pv_circular_inout_colors;

    private Calendar calendar, calendar1;
    private EditText dateView,dateView1;
    private int year, month, day;
    int dd= 1;

    static String img1,img2,img3,img4, Name,Fname,tunMobile, Address,City,Zip,Xiemail,XPan="",lat,lon,img5;
    DBAdapter db;

    private String empname,regid;

    String under_depart,late_chargeText;
    String deviceIMEI = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recovery__person);



        Intent inte = getIntent();
        String[] data = new String[0];


        mHandler = new Handler();

        pv_circular_inout_colors = (ProgressView)findViewById(R.id.progress_pv_circular_inout_colors);

        String caseq = inte.getStringExtra("case");
        type = inte.getStringExtra("type");

        TextView textView62 = (TextView)findViewById(R.id.textView62);

        //textView62.setText(caseq);
        // cname = inte.getStringExtra("cname");

        dateView = (EditText) findViewById(R.id.editTextB1);
        dateView1 = (EditText) findViewById(R.id.editTextB2);

        // prog = (ProgressView)findViewById(R.id.progress_pv_circular_inout_colors);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);


        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearchCHQ = (EditText) findViewById(R.id.inputSearchCHQ);

        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        currentD=dateView.getText().toString().trim();


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

        cid=empdevice_imei;
        //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
        db.close();
        //  new ManagerApprove().execute(Allot,cid,cname,deviceIMEI,empname,regid,cc,lat,lon,lonper11,lonper12,loan_install_no,installment_date);
        gps = new GPSTracker(Pay_Recovery_Dealer.this);

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


        new LongOperation().execute(serverURL,"2017-1-31","2017-1-31","");

        inputSearchCHQ.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String inputSearchCHQF=inputSearchCHQ.getText().toString();
                int textLength=inputSearchCHQF.length();

                new LongOperation().execute(serverURLS11,inputSearchCHQF,"ji","");

            }
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}public void afterTextChanged(Editable s) {
            }
        });



    }


    @Override
    public void onPause() {
        super.onPause();

        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(MSG_START_PROGRESS, START_DELAY);
    }



    @SuppressWarnings("deprecation")
    public void datapickFrom(View view) {
        // showDialog(999);
        dd=1;
        datapick();
        //Toast.makeText(getApplicationContext(), "ca",Toast.LENGTH_SHORT).show();
    }

    public void datapickTo(View view) {
        //showDialog(999);
        dd=2;
        datapick();
        //Toast.makeText(getApplicationContext(), "ca1", Toast.LENGTH_SHORT).show();
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
                    // }else{
                    //     showDateto(arg1, arg2 + 1, arg3);

                    //  }
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));

        currentD=dateView.getText().toString().trim();
        //dateView1.setText("");
        currentDTo=dateView1.getText().toString().trim();
        fatch("","");
    }

    private void showDateto(int year, int month, int day) {
        dateView1.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));

        currentD=dateView.getText().toString().trim();
        currentDTo=dateView1.getText().toString().trim();


        fatch(currentD,currentDTo);

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

        String[] installment_date1 = new String[icon.size()];
        installment_date1 = installment_date.toArray(installment_date1);


        String[] interval1 = new String[interval.size()];
        interval1 = interval.toArray(interval1);


        String[] emp_id1 = new String[emp_id.size()];
        emp_id1 = emp_id.toArray(emp_id1);


        String[] Ename1 = new String[Ename.size()];
        Ename1 = Ename.toArray(Ename1);

        String[] late_charge1 = new String[late_charge.size()];
        late_charge1 = late_charge.toArray(late_charge1);

        String[] mobile2 = new String[late_charge.size()];
        mobile2 = mobile.toArray(mobile2);

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
            temp.put("late_charge", late_charge1[i]);
            temp.put("mobile", mobile2[i]);


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
        final CustomAdapter adapter=new CustomAdapter(this, R.layout.business_report_list,searchResults);

        mylistview1 = (ListView)findViewById(R.id.listview);

        //finally,set the adapter to the default ListView
        mylistview1.setAdapter(adapter);
        inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString=inputSearch.getText().toString();
                int textLength=searchString.length();
                searchResults.clear();

                for(int i=0;i<originalValues.size();i++)
                {

                    String playerNameid=originalValues.get(i).get("team").toString();
                    String playerName=originalValues.get(i).get("name").toString();
                    if(textLength<=playerName.length()){
                        //compare the String in EditText with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
                            searchResults.add(originalValues.get(i));
                    }
                    if(textLength<=playerNameid.length()){
                        //compare the String in EditText with Names in the ArrayList
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


    }

    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {




        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Pay_Recovery_Dealer.this);
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
                    installment_date.clear();

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


                                                installment_date.add(prods.getJSONObject(j).getString("installment_date"));


                                                emp_id.add(prods.getJSONObject(j).getString("emp_id"));
                                                Ename.add(prods.getJSONObject(j).getString("Ename"));
                                                interval.add(prods.getJSONObject(j).getString("www"));

                                                late_charge.add(prods.getJSONObject(j).getString("late_charge"));
                                                mobile.add(prods.getJSONObject(j).getString("mobile"));
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
            TextView name,team,city,price,price1,price2,price3,price4,sNo,ROI_Applied,LTV_Applied,LTV_Policy,installment_date,mobile,emp_id,Ename,interval;
            LinearLayout relativeLayout1;
        }

        ViewHolder viewHolder;

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {

                convertView=inflater.inflate(R.layout.rcovery_list_allote, null);

                viewHolder=new ViewHolder();


//                relativeLayout1=(LinearLayout)convertView.findViewById(R.id.relativeLayout1);


                //cache the views
                viewHolder.photo=(ImageView) convertView.findViewById(R.id.profile_pic);
                viewHolder.name=(TextView) convertView.findViewById(R.id.customername);
                viewHolder.team=(TextView) convertView.findViewById(R.id.city);


                viewHolder.sNo=(TextView) convertView.findViewById(R.id.sNo);
                viewHolder.city=(TextView) convertView.findViewById(R.id.city);
                viewHolder.price1=(TextView) convertView.findViewById(R.id.price1);
                viewHolder.price2=(TextView) convertView.findViewById(R.id.price2);
                viewHolder.price3=(TextView) convertView.findViewById(R.id.price3);
                viewHolder.price4=(TextView) convertView.findViewById(R.id.price4);


                viewHolder.ROI_Applied=(TextView) convertView.findViewById(R.id.price5);
                viewHolder.LTV_Policy=(TextView) convertView.findViewById(R.id.price6);
                viewHolder.LTV_Applied=(TextView) convertView.findViewById(R.id.price7);



                viewHolder.installment_date=(TextView) convertView.findViewById(R.id.price8);
                viewHolder.Ename=(TextView) convertView.findViewById(R.id.price9);
//                prog.stop();


                //link the cached views to the convertview
                convertView.setTag(viewHolder);

            }
            else
                viewHolder=(ViewHolder) convertView.getTag();

            String remarkss = searchResults.get(position).get("remarks").toString();
            if(remarkss.equals("")||remarkss.equals("0")||remarkss.equals("null")){
                convertView.setBackgroundColor(Color.parseColor("#FFD7ACAC"));
            }else {
                convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            // int photoId=(Integer) searchResults.get(position).get("photo");

            Glide.with(Pay_Recovery_Dealer.this)
                    //.(R.drawable.ic_launcher)
                    .load(searchResults.get(position).get("photo").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.photo);

            //set the data to be displayed
            // viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
            viewHolder.name.setText(searchResults.get(position).get("name").toString()+"("+searchResults.get(position).get("team").toString()+")");
            final String lonper = searchResults.get(position).get("team").toString();
            String prop=searchResults.get(position).get("team").toString();
            viewHolder.team.setText(prop);

            viewHolder.sNo.setText(searchResults.get(position).get("no").toString());
            viewHolder.city.setText(searchResults.get(position).get("city").toString());
            viewHolder.price1.setText(searchResults.get(position).get("product_name").toString());
            viewHolder.price2.setText(searchResults.get(position).get("emi").toString());
            viewHolder.price3.setText(searchResults.get(position).get("ROI_policy").toString());

            viewHolder.ROI_Applied.setText(searchResults.get(position).get("ROI_Applied").toString());
            viewHolder.LTV_Policy.setText(searchResults.get(position).get("LTV_Policy").toString());
            viewHolder.LTV_Applied.setText(searchResults.get(position).get("LTV_Applied").toString());

            viewHolder.price4.setText(searchResults.get(position).get("remarks").toString());
            viewHolder.installment_date.setText(searchResults.get(position).get("installment_date").toString());
            viewHolder.Ename.setText(searchResults.get(position).get("Ename").toString()+":"+searchResults.get(position).get("emp_id").toString()+"");




            final ViewHolder finalHolder = viewHolder;
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
                    late_chargeText = searchResults.get(position).get("late_charge").toString();
                    mobile1=searchResults.get(position).get("mobile").toString();
                    //Viewuser(lonper);
                    //showAppCasece(lonper);
                    Viewuser();
                    // Toast.makeText(finalConvertView.getContext(), ""+Team, Toast.LENGTH_SHORT).show();
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

    public void  subProduct(final String productsub,final String per){
        // Intent intent = new Intent(getApplicationContext(), Order.class);
/*
        BlurBehind.getInstance().execute(business_reports.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(business_reports.this, Order.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                intent.putExtra("data0", product);
                intent.putExtra("data1", productsub);
                intent.putExtra("ProPer", ProPer);
                intent.putExtra("data2", SelectCat);
                intent.putExtra("per", per);

                startActivity(intent);
                //finish();
            }
        });

        */
        // Registering user on our server
        // Sending registraiton details to MainActivity

        //startActivity(intent);
    }

    public void fatch(String from, String to){
        //  if(type.equals("1")){
        serverURL=serverURL1;
        new LongOperation().execute(serverURL,cid,to,"");
/*
        } if(type.equals("2")){
            serverURL=serverURL2;
            new LongOperation().execute(serverURL,from,to,"");

        } if(type.equals("3")){
            serverURL=serverURL3;
            new LongOperation().execute(serverURL,from,to,"");

        } if(type.equals("4")){
            serverURL=serverURL4;
            new LongOperation().execute(serverURL,from,to,"");

        }
        */
    }


    // datapick

    public void datapick(){

        infracom.abcr.sarthamicrofinance.material.app.Dialog.Builder builder = null;


        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;


        builder = new infracom.abcr.sarthamicrofinance.material.app.DatePickerDialog.Builder(isLightTheme ? R.style.Material_App_Dialog_DatePicker_Light :  R.style.Material_App_Dialog_DatePicker){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                infracom.abcr.sarthamicrofinance.material.app.DatePickerDialog dialog = (infracom.abcr.sarthamicrofinance.material.app.DatePickerDialog)fragment.getDialog();
                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                int y=dialog.getYear();
                int m1=dialog.getMonth();
                m1=m1+1;
                int d=dialog.getDay();
                Toast.makeText(Pay_Recovery_Dealer.this, "Date is " + date, Toast.LENGTH_SHORT).show();
                //chequedate=(EditText)findViewById(R.id.chequedate);
                if(dd==1) {
                    dateView.setText(y+"-"+m1+"-"+d);
                    currentD=dateView.getText().toString().trim();
                    dateView1.setText("");
                    currentDTo=dateView1.getText().toString().trim();
                    fatch(currentD,currentDTo);
                }else{
                    dateView1.setText(y+"-"+m1+"-"+d);
                    currentD=dateView.getText().toString().trim();
                    //dateView1.setText("");
                    currentDTo=dateView1.getText().toString().trim();
                    fatch(currentD,currentDTo);
                }
                // tbl=null;
                // disbusLoanDate(y,m1,d);
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




    public void Viewuser(){
        infracom.abcr.sarthamicrofinance.material.app.Dialog.Builder builder = null;

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;


        builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){

            @Override
            protected void onBuildDone(infracom.abcr.sarthamicrofinance.material.app.Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView ccccq = (TextView)dialog.findViewById(R.id.cccc);
                TextView bbbbq = (TextView)dialog.findViewById(R.id.bbbb);

                ImageView im = (ImageView)dialog.findViewById(R.id.profile_pic11);

                ccccq.setText(lonper11);
                bbbbq.setText(lonper12);
                Glide.with(Pay_Recovery_Dealer.this)
                        //.(R.drawable.ic_launcher)
                        .load(lonper13.replace("http","https"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_launcher)
                        .into(im);


            }



            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                //   EditText et_pass = (EditText)fragment.getDialog().findViewById(R.id.QueryID);
                //Toast.makeText(mActivity, "Connected", Toast.LENGTH_SHORT).show();
/*
                cc=et_pass.getText().toString().trim();
                if(cc.toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Please Type Query...", Toast.LENGTH_SHORT).show();
                }else{


                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(business_reports.this);
                    builder
                            .setTitle("Customer View")
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

               }
            */

                //intent.putExtra("name", name);
                //intent.putExtra("email", email);
                //intent.putExtra("latitude", lat);
                //intent.putExtra("longitude", lon)

                Intent intent = new Intent(Pay_Recovery_Dealer.this, Pay_Installment.class);


                intent.putExtra("eid", cid);
                intent.putExtra("ename", cname);
                intent.putExtra("ephoto", cphoto);
                intent.putExtra("cid", lonper12);
                intent.putExtra("cname", lonper11);
                intent.putExtra("cphoto", lonper13);
                intent.putExtra("intervaltext", intervaltext);
                intent.putExtra("installment_date", installment_date2);
                intent.putExtra("loan_install_no", loan_install_no);

                intent.putExtra("emiPay", emiPay);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);

                intent.putExtra("mobile", mobile1);
                intent.putExtra("under_depart", under_depart);
                intent.putExtra("late_chargeText", late_chargeText);


                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                //finish();

                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_SHORT).show();

                showAppCasece(lonper11);
                super.onNegativeActionClicked(fragment);
            }

            @Override
            public void onNeutralActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_SHORT).show();

                super.onNeutralActionClicked(fragment);

            }


        };



        builder.title(lonper12)
                .positiveAction("View Details")
                // .negativeAction("Approve Case")
                .neutralAction("CANCEL")
                .contentView(R.layout.zcustomer_view_profile);


        DialogFragment fragment = DialogFragment.newInstance(builder);

        fragment.show(getSupportFragmentManager(), null);

    }


    // Class with extends AsyncTask class
    public class Approve_Summ_LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Pay_Recovery_Dealer.this);
        String data ="";
        int sizeData = 0;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

            //Dialog.setMessage("Loading...");
            //Dialog.show();


            pv_circular_inout_colors.start();
            pv_circular_inout_colors.setVisibility(View.VISIBLE);

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
                                                listsubR1.add(prods.getJSONObject(j).getString("x"));

                                                listsub11.add(prods.getJSONObject(j).getString("sub1"));

                                                listsub21.add(prods.getJSONObject(j).getString("sub2"));

                                                listsub31.add(prods.getJSONObject(j).getString("sub3"));

                                                icon1.add(prods.getJSONObject(j).getString("icon"));
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


    public void showAppCasece(String id){
        mBottomSheetDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.zview_approved_casece, null);
        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window));

        FancyButton bt_wrapMin = (FancyButton)v.findViewById(R.id.Taskmin);
        FancyButton bt_wrapMax = (FancyButton)v.findViewById(R.id.Taskmax);
        FancyButton onBack = (FancyButton)v.findViewById(R.id.TaskM2441);


        prog = (ProgressView)v.findViewById(R.id.progress_pv_circular_inout_colors12);
        prog.setVisibility(View.VISIBLE);
        prog.start();

        inputSearch = (EditText) v.findViewById(R.id.inputSearch);
        proName = (TextView) v.findViewById(R.id.textPoster);
        // proName.setText("Select " + product);
        mylistview2 = (ListView)v. findViewById(R.id.listViewtotal);

        inputSearch.setVisibility(View.GONE);
        listsubR1.clear();
        listsub11.clear();
        listsub21.clear();
        listsub31.clear();
        icon1.clear();



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

        bt_wrapMin.setVisibility(View.GONE);
        bt_wrapMax.setVisibility(View.GONE);
        onBack.setVisibility(View.GONE);

        onBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onPause();
            }

        });


        //        prog = (ProgressView)v.findViewById(R.id.progress_pv_circular_inout_colors);

        //       prog.start();



        mBottomSheetDialog.contentView(v)
                .show();
        new Approve_Summ_LongOperation().execute(Approve_Summ_LongOperation_url,id,"","");


    }


    public void addone1(){


        prog.stop();
        prog.setVisibility(View.GONE);
        String[] names = new String[listsubR1.size()];
        names = listsubR1.toArray(names);

        String[] teams = new String[listsub11.size()];
        teams = listsub11.toArray(teams);

        String[] message = new String[listsub21.size()];
        message = listsub21.toArray(message);

        String[] depart = new String[listsub31.size()];
        depart = listsub31.toArray(depart);

        String[] teamsP = new String[icon.size()];
        teamsP = icon1.toArray(teamsP);

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
        searchResults1=new ArrayList<HashMap<String,Object>>(originalValues);

        //create the adapter
        //first param-the context
        //second param-the id of the layout file
        //you will be using to fill a row
        //third param-the set of values that
        //will populate the ListView
        final CustomAdapter1 adapter1=new CustomAdapter1(this, R.layout.z_summry,searchResults1);

        //finally,set the adapter to the default ListView
        mylistview2.setAdapter(adapter1);
        inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString=inputSearch.getText().toString();
                int textLength=searchString.length();
                searchResults1.clear();

                for(int i=0;i<originalValues.size();i++)
                {
                    String playerName=originalValues.get(i).get("name").toString();

                    if(textLength<=playerName.length()){
                        //compare the String in EditText with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
                            searchResults1.add(originalValues.get(i));
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


    }

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
            TextView name,team,message,depart;

        }

        ViewHolder1 viewHolder1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.z_summry, null);
                viewHolder1=new ViewHolder1();

                //cache the views
                viewHolder1.photo=(ImageView) convertView.findViewById(R.id.profile_pic);
                viewHolder1.name=(TextView) convertView.findViewById(R.id.member_name);
                viewHolder1.team=(TextView) convertView.findViewById(R.id.status);
                viewHolder1.message=(TextView) convertView.findViewById(R.id.contact_typetime);
                viewHolder1.depart=(TextView) convertView.findViewById(R.id.depart);

                //link the cached views to the convertview
                convertView.setTag(viewHolder1);

            }
            else
                viewHolder1=(ViewHolder1) convertView.getTag();


            // int photoId=(Integer) searchResults.get(position).get("photo");

            Glide.with(Pay_Recovery_Dealer.this)
                    //.(R.drawable.ic_launcher)
                    .load(searchResults1.get(position).get("photo").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder1.photo);
            //set the data to be displayed
            // viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
            viewHolder1.depart.setText(searchResults1.get(position).get("depart").toString());
            viewHolder1.message.setText(searchResults1.get(position).get("message").toString());
            viewHolder1.name.setText(searchResults1.get(position).get("name").toString()+"("+searchResults1.get(position).get("team").toString()+")");
            final String lonper = searchResults1.get(position).get("team").toString();
            String prop=searchResults1.get(position).get("team").toString()+"";
            viewHolder1.team.setText(prop);

            final ViewHolder1 finalHolder = viewHolder1;
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String Product = finalHolder.name.getText().toString().trim();
                    String Team = lonper;


                    // lonper11=lonper;
                    Toast.makeText(finalConvertView.getContext(), ""+Product, Toast.LENGTH_SHORT).show();
                    //MainActivityD mainD = null;
                    //mainD.privecyTerms();

                    // showAppCasece(lonper);

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
