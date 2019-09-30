package infracom.abcr.sarthamicrofinance.Profile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StringLoader;
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

import de.hdodenhof.circleimageview.CircleImageView;
import infracom.abcr.sarthamicrofinance.AllShow_Message;
import infracom.abcr.sarthamicrofinance.Chart.data.Common;
import infracom.abcr.sarthamicrofinance.Config;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Dpost.ImageLoader;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.MainActivityTask;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.GroupLoan.ImagePopup;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.RegisterActivity;
import infracom.abcr.sarthamicrofinance.ShowMessage;
import infracom.abcr.sarthamicrofinance.activity.AboutSartha;
import infracom.abcr.sarthamicrofinance.helper.SessionManager;
import infracom.abcr.sarthamicrofinance.mainact.SplashActivity;
import infracom.abcr.sarthamicrofinance.material.app.BottomSheetDialog;
import infracom.abcr.sarthamicrofinance.material.drawable.ThemeDrawable;
import infracom.abcr.sarthamicrofinance.material.util.ViewUtil;
import infracom.abcr.sarthamicrofinance.material.widget.FloatingActionButton;
import infracom.abcr.sarthamicrofinance.reports.Receivable_Reports;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;
import mehdi.sakout.fancybuttons.FancyButton;

public class Customer_Launch extends AppCompatActivity {


    ArrayList<HashMap<String, Object>> searchResults;
    String currentD, currentDTo;
    //ArrayList that will hold the original Data
    ArrayList<HashMap<String, Object>> originalValues;
    LayoutInflater inflater;
    //String[] names;
    //String names[]={"Ronaldo","Messi","Torres","Iniesta", "Drogba","Gerrard","Rooney","Xavi"};



    TextView totalRecevi;
    Double totalReceviAAll=0.0;

    ArrayList<String> no = new ArrayList<String>();
    ArrayList<String> icon = new ArrayList<String>();
    ArrayList<String> cus_name = new ArrayList<String>();
    ArrayList<String> install_date = new ArrayList<String>();
    ArrayList<String> emi = new ArrayList<String>();
    ArrayList<String> remarks = new ArrayList<String>();
    ArrayList<String> paid = new ArrayList<String>();
    ArrayList<String> cheqe = new ArrayList<String>();


    String popup_title,popup_desc, popup_image;
    String dss;
    //String serverURL=null;
    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Customer_EMI.php";
    //  ListView mylistview1;


    ImageLoader imageLoader;
    String message,customer_name,voter_id,driving_licence,aadhar_card,zipP,city,addressP,father_name,Pemail,
            emp_name,emp_id,tunerPP,percent_rate,total_amountPP,product_poan_percent,loan_amount,product_price,product_name,
            product_compny,emiPP,product_type,pan_number,customer_id,order_id,photo1,emi_date;


    String status;
    ProgressDialog dialog;
    public String user_id, password, email, name, lastname, mobile, fullname,sts;
    private Uri mImageCaptureUri, mImageCaptureUriTo;
    ImageView Cadd, CaddV, CaddA, CaddD;

    private static Bitmap bm;
    static Bitmap bitmap;
    View v;

    String Pphoto;

    private String Customer_uSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/View_customer_details.php";
    private String Customer_uSERVER_URL1 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/send_customer_details.php";

    private String Customer_request = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/send_req.php";


    CircleImageView profile_pic1;
    Controller aController = null;

    static String img1,img2,img3,img4, Name,Fname,tunMobile, Address,City,Zip,Xiemail,XPan="",sms_status;

    private ProgressDialog pDialog;
    String mypath;

    int Ckeck=0;
    public static String Finalmedia = "";

    private static final String TAG = Customer_Launch.class.getSimpleName();

    private String empname,regid;
    Bitmap photo;
    //private static final int PICK_FROM_CAMERA = 1;
    //private static final int CROP_FROM_CAMERA = 2;
    //private static final int PICK_FROM_FILE = 3;

    Uri selectedImageUri = null;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    Uri imageUri;


    private SessionManager session;


    TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal,notifi;

    DBAdapter db;

    static  String cid,cname;

    String branch_name,branch_id;

    private static final int REQUEST_LOCATION = 1;
    GPSTracker gps;
    String latitude;
    String longitude;
    LocationManager locationManager;


    String deviceIMEI = "",under_depart;

    TextView textView29;

    private BottomSheetDialog mBottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customera_ctivity__launch);

        try{
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            gps();
        }catch (Exception e){}


        profile_pic1 =(CircleImageView)findViewById(R.id.profile_pic1);

//        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

  //      BlurBehind.getInstance()
    //            .withAlpha(200)
      //          .withFilterColor(Color.parseColor("#222222"))
        //        .setBackground(this);

        //FancyButton list = (FancyButton) findViewById(R.id.pList);
        //  FancyButton task = (FancyButton) findViewById(R.id.TaskM);

        //  FancyButton Taskmessage = (FancyButton) findViewById(R.id.Taskmessage);
        FancyButton TaskLoanEMI = (FancyButton) findViewById(R.id.TaskLoan);
        FancyButton TaskLoanDetails = (FancyButton) findViewById(R.id.TaskLoanDetails);
        FloatingActionButton TaskCustomerDe = (FloatingActionButton) findViewById(R.id.TaskCustomerDe);
        FancyButton TaskInfo = (FancyButton) findViewById(R.id.TaskInfo);
        FancyButton TaskHelp = (FancyButton) findViewById(R.id.TaskHelp);
        FancyButton TaskContact = (FancyButton) findViewById(R.id.TaskContact);


        textView29 =(TextView)findViewById(R.id.textView29);
        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        notifi =  (TextView) findViewById(R.id.notifi);

        //  FancyButton TaskMoreOP = (FancyButton) findViewById(R.id.TaskM21);

        session = new SessionManager(getApplicationContext());
        // Check if user is already logged in or not
        if (!session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Customer_Launch.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }

        //   FancyButton onBack = (FancyButton) findViewById(R.id.TaskM2441);

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();
/*
        Taskmessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
               // showBottomSheet();
                Intent intent = new Intent(Customer_Launch.this, ShowMessage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }

        });

        */

        TaskCustomerDe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                //showBottomSheetCustomer();
                gooV();
            }

        });
/*
        onBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                onPause();
            }

        });

        TaskMoreOP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                showBottomSheet();
            }

        });
        */

/*
        list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {

                BlurBehind.getInstance().execute(Manager_Launch.this, new OnBlurCompleteListener() {
                    @Override
                    public void onBlurComplete() {
                        Intent intent = new Intent(Manager_Launch.this, MainActivityD.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        startActivity(intent);
                        //finish();
                    }
                });


            }

        });

        task.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                BlurBehind.getInstance().execute(Customer_Launch.this, new OnBlurCompleteListener() {
                    @Override
                    public void onBlurComplete() {
                        Intent intent = new Intent(Customer_Launch.this, MainActivityTask.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        startActivity(intent);
                        // finish();
                    }
                });

            }

        });

        */


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
            aController.showAlertDialog(Customer_Launch.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);

            // stop executing code by return
            return;
        }


        imageLoader = new ImageLoader(this);


        // Check if GCM configuration is set
        if (Config.YOUR_SERVER_URL == null ||
                Config.GOOGLE_SENDER_ID == null ||
                Config.YOUR_SERVER_URL.length() == 0 ||
                Config.GOOGLE_SENDER_ID.length() == 0)
        {

            // GCM sernder id / server url is missing
            aController.showAlertDialog(Customer_Launch.this,
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
        String user_id = dataf.get("user_id");
        cid=user_id;
        deviceIMEI = empdevice_imei.toString().trim();
        //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
        db.close();

        new CustomerLongOperation().execute(Customer_uSERVER_URL,cid,deviceIMEI);

        //      new LongOperation().execute(serverURL,cid,cid,cid);

        textView29.setText(cid);
        gps = new GPSTracker(Customer_Launch.this);



        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        Common.screenWidth = mDisplayMetrics.widthPixels;
        Common.screenHeight = mDisplayMetrics.heightPixels;

        //设置图区宽高、内容宽高
        Common.layoutWidth = Common.screenWidth *5/2;
        Common.layoutHeight = Common.screenHeight * 6/8;
        Common.viewWidth = Common.screenWidth *5/2;
        Common.viewHeight = Common.screenHeight *12/8;

        // init();


        //监听双击事件
//		threndLine_Layout.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				//滑动不会触发up事件，故在up触发时计数
//				if(MotionEvent.ACTION_UP == event.getAction()){
//					count++;
//					if(count == 1){
//						firClick = System.currentTimeMillis();
//						new Thread(new Runnable(){
//						    public void run(){
//						        try {
//									Thread.sleep(500);
//									count=0;
//									firClick=0;
//									secClick=0;
//								} catch (InterruptedException e) {
//									e.printStackTrace();
//								}
//						    }
//						}).start();
//
//					} else if (count == 2){
//						secClick = System.currentTimeMillis();
//						if(secClick - firClick < 500){
//							if(mp==false)
//								mp=true;
//							else
//								mp=false;
//							addView();
//						}
//						count = 0;
//						firClick = 0;
//						secClick = 0;
//					}
//				}
//				return true;
//			}
//		});

        //自定义参数
        //   setTitle();
        //  setYName();
        // setKey();
        // setAxis();
        // setData();

        //填充
        // addView();

    }



    // Class with extends AsyncTask class
    public class CustomerLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Customer_Launch.this);
        String data ="";
        int sizeData = 0;
        protected void onPreExecute() {
            //Dialog.setMessage("Loading...");
            //Dialog.show();
        }
        protected String doInBackground(String... params) {
            BufferedReader reader=null;
            String Content = "";
            try{URL url = new URL(params[0]);
                if(!params[1].equals(""))
                    data +="&" + URLEncoder.encode("cid", "UTF-8") + "="+params[1].toString();

                if(!params[2].equals(""))
                    data +="&" + URLEncoder.encode("emp_id", "UTF-8") + "="+params[2].toString();

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
                            customer_id       = jsonChildNode.optString("customer_id").toString();
                            order_id       = jsonChildNode.optString("order_id").toString();
                            pan_number       = jsonChildNode.optString("pan_number").toString();
                            product_type       = jsonChildNode.optString("product_type").toString();
                            emiPP       = jsonChildNode.optString("emi").toString();
                            emi_date       = jsonChildNode.optString("emi_date").toString();
                            product_compny       = jsonChildNode.optString("product_compny").toString();
                            product_name       = jsonChildNode.optString("product_name").toString();
                            product_price       = jsonChildNode.optString("product_price").toString();
                            loan_amount       = jsonChildNode.optString("loan_amount").toString();
                            product_poan_percent       = jsonChildNode.optString("product_poan_percent").toString();
                            total_amountPP       = jsonChildNode.optString("total_amount").toString();
                            percent_rate       = jsonChildNode.optString("percent_rate").toString();
                            tunerPP       = jsonChildNode.optString("tuner").toString();
                            emp_id       = jsonChildNode.optString("emp_id").toString();
                            emp_name       = jsonChildNode.optString("emp_name").toString();

                            Pemail       = jsonChildNode.optString("email").toString();
                            father_name       = jsonChildNode.optString("father_name").toString();
                            addressP       = jsonChildNode.optString("address").toString();
                            city       = jsonChildNode.optString("city").toString();
                            zipP       = jsonChildNode.optString("zip").toString();
                            photo1       = jsonChildNode.optString("photo").toString();
                            aadhar_card       = jsonChildNode.optString("aadhar_card").toString();
                            driving_licence       = jsonChildNode.optString("driving_licence").toString();
                            voter_id       = jsonChildNode.optString("voter_id").toString();
                            mobile       = jsonChildNode.optString("mobile").toString();
                            customer_name       = jsonChildNode.optString("customer_name").toString();
                            message       = jsonChildNode.optString("message").toString();
                            status       = jsonChildNode.optString("status").toString();

                            sms_status       = jsonChildNode.optString("sms_status").toString();


                            popup_title       = jsonChildNode.optString("popup_title").toString();
                            popup_desc       = jsonChildNode.optString("popup_desc").toString();
                            popup_image       = jsonChildNode.optString("popup_image").toString();



                            if(popup_title.equals("")||popup_title.equals("null")){

                            }
                            else{
                                gooV();
                                SharedPreferences prefs = getSharedPreferences("HIDE_POP", MODE_PRIVATE);
                                String restoredText = prefs.getString("pop", null);
                                if (restoredText != null) {
                                    String pop = prefs.getString("pop", "no");//"No name defined" is the default value.
                                }

                                SharedPreferences.Editor editor = getSharedPreferences("HIDE_POP", MODE_PRIVATE).edit();
                                editor.putString("pop", "no");
                                editor.apply();
                            }

                            // CustomerNameID = (TextView)v.findViewById(R.id.textViewNameID);
                            // CustomerNameID.setText(customer_name+"("+customer_id+")");
                            cid = customer_id;
                            textView29.setText(customer_name+" ("+customer_id+")");

                            Pphoto = photo1;

                            try{
                                Glide.with(Customer_Launch.this)
                                        //.(R.drawable.ic_launcher)

                                        .load(photo1.replace("http","https"))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .error(R.drawable.ic_launcher)
                                        .into(profile_pic1);
                            }catch (Exception e){}


                            if(sms_status.equals("null")||sms_status.equals("")||sms_status.equals("0")){
                                notifi.setVisibility(View.GONE);
                            }else {
                                notifi.setText(sms_status);
                            }

                            // Toast.makeText(getApplicationContext(), "Fetch!  "+status, Toast.LENGTH_LONG).show();


                            //TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal;


                            //cid = inte.getStringExtra("cid");
                            //cname = inte.getStringExtra("cname");

                            //sts = inte.getStringExtra("sts");


                        }else
                        {
                            // Toast.makeText(getApplicationContext(), "Server Error Try Again!", Toast.LENGTH_LONG).show();
                        }

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }

    }



    /**
     * 初始化各绘图组件
     * 包括设置高宽、位置

     private void init(){
     title_layout = (LinearLayout) findViewById(R.id.titleView1);

     axisXLayout = (LinearLayout) findViewById(R.id.axisX1);
     RelativeLayout.LayoutParams xParams = (RelativeLayout.LayoutParams) axisXLayout.getLayoutParams();
     xParams.height = Common.layoutHeight;
     xParams.width = Common.layoutWidth;
     xParams.setMargins(xParams.leftMargin+Common.YWidth, xParams.topMargin, xParams.rightMargin, xParams.bottomMargin);
     axisXLayout.setLayoutParams(xParams);

     axisYLayout = (LinearLayout) findViewById(R.id.axisY1);
     RelativeLayout.LayoutParams yParams = (RelativeLayout.LayoutParams) axisYLayout.getLayoutParams();
     yParams.height = Common.layoutHeight;
     yParams.setMargins(yParams.leftMargin, yParams.topMargin, yParams.rightMargin, yParams.bottomMargin + Common.XHeight);
     axisYLayout.setLayoutParams(yParams);

     threndLine_Layout = (LinearLayout) findViewById(R.id.thrend_line1);
     RelativeLayout.LayoutParams hParams = (RelativeLayout.LayoutParams) threndLine_Layout.getLayoutParams();
     hParams.height = Common.layoutHeight;
     hParams.width = Common.layoutWidth;
     hParams.setMargins(hParams.leftMargin+Common.YWidth, hParams.topMargin, hParams.rightMargin, hParams.bottomMargin + Common.XHeight);
     threndLine_Layout.setLayoutParams(hParams);



     //实例化View
     axisY_2 = new AxisYView_NormalType(this);
     axisX = new AxisXView(this);
     lineView = new LineView(this);
     titleView = new TitleView(this);
     }

     private void setData() {
     MyData data1 = new MyData();
     data1.setName("Price");
     data1.setData( new int[]{25,50,40,110,86,299,
     87,99,101,213,119,233,
     95,45,76,68,149,56,
     47,72,23,192,115,214} );
     data1.setColor(0xff8d77ea);

     //		MyData data2 = new MyData();
     //		data2.setName("CO");
     //		data2.setData( new int[]{-1,210,190,-1,240,250,
     //								80,85,90,230,100,220,
     //								70,30,70,80,130,40,
     //								30,80,40,160,100,210} );
     //		data2.setColor(0xff43ce17);

     Common.DataSeries = new ArrayList<MyData>();
     Common.DataSeries.add(data1);
     //		Common.DataSeries.add(data2);

     }

     private void setTitle(){
     // Common.title = "Grow";
     // Common.secondTitle = "[Market]";
     Common.title = "";
     Common.secondTitle = "";
     Common.titleX = 40;
     Common.titleY = 70;
     Common.StitleX =50;
     Common.StitleY = 110;
     Common.titleColor = Color.GREEN;
     }

     private void setYName(){
     Common.YName = "Y";
     Common.YName2Left = 40;
     Common.YName2Top = 450;
     Common.titleColor = Color.GREEN;
     }

     private void setKey(){
     //设置图例参数
     Common.keyWidth = 30;
     Common.keyHeight = 10;
     Common.keyToLeft = 300;
     Common.keyToTop = 80;
     Common.keyToNext = 80;
     Common.keyTextPadding = 5;
     }

     private void setAxis(){
     //设置轴参数
     Common.xScaleArray = new String[]{"0","100","200","300","400","500","600","700","800","900","1000","1100","1200","1300","1400","1500","1600","1700","1800","1900","2000","2100","2200","2300"};
     //		Common.xScaleColor = Color.YELLOW;

     //yScaleArray需要比levelName和color多出一个数
     Common.yScaleArray = new int[]{23,25,50,100,200,300,500};
     Common.levelName = new String[]{"1","2","3","4","5","6"};
     Common.yScaleColors = new int[]{0xff00ff00,0xffffff00,0xffffa500,0xffff4500,0xffdc143c,0xffa52a2a};
     }

     private void addView(){
     int width=0;
     //		if(mp==false)
     //			width=Common.screenWidth*7/8+10;
     //		else
     width=Common.viewWidth;

     //设定初始定位Y坐标
     xy.y = Common.viewHeight - Common.layoutHeight;

     lineView.initValue(width, Common.viewHeight, true);//传入宽、高、是否在折线图上显示数据
     lineView.scrollTo(0, xy.y);

     axisY_2.initValue(Common.viewHeight);//传入高度
     axisY_2.scrollTo(0, xy.y);

     axisX.initValue(width, Common.viewHeight);//传入高度
     axisX.scrollTo(0, xy.y);

     axisYLayout.removeAllViews();
     axisYLayout.addView(axisY_2);

     axisXLayout.removeAllViews();
     axisXLayout.addView(axisX);

     threndLine_Layout.removeAllViews();
     threndLine_Layout.addView(lineView);

     title_layout.removeAllViews();
     title_layout.addView(titleView);

     //监听滑动事件
     lineView.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
    if(event.getAction() == MotionEvent.ACTION_DOWN){
    oldX = event.getX();
    oldY = event.getY();
    }
    if(event.getAction() == MotionEvent.ACTION_MOVE){
    parseXY( xy.x+=oldX-event.getX() , xy.y+=oldY-event.getY() , lineView.getWidth() , lineView.getHeight() , threndLine_Layout);
    lineView.scrollTo(xy.x, xy.y);
    axisY_2.scrollTo(0, xy.y);
    axisX.scrollTo(xy.x, Common.viewHeight - Common.layoutHeight);
    oldX = event.getX();
    oldY = event.getY();
    }
    return true;
    }
    });
     }

     protected void parseXY(float x,float y,int width,int height,LinearLayout parent) {
     int parentWidth = parent.getWidth();
     int parentHeight = parent.getHeight();
     if(x<0)
     xy.x = 0;
     else if(x > width-parentWidth)
     xy.x = width-parentWidth;
     else
     xy.x = (int) x;

     if(y<0)
     xy.y = 0;
     else if(y > height-parentHeight)
     xy.y = height-parentHeight;
     else
     xy.y = (int) y;

     //初步防抖
     if(width<=parentWidth)
     xy.x = 0;
     if(height<=parentHeight)
     xy.y = 0;
     }


     */
    public void backokLLL(View view){
        BlurBehind.getInstance().execute(Customer_Launch.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(Customer_Launch.this, EmpProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                //finish();
            }
        });
    }

    public void showMesaage(View view){

        notifi.setText("");
        notifi.setVisibility(View.GONE);
        Intent intent = new Intent(Customer_Launch.this, ShowMessage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        //finish();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        showBottomSheet();
        //getMenuInflater().inflate(R.menu.menu_custom_view_icon_text_tabs, menu);
        return true;
    }

    private void showBottomSheet(){
        mBottomSheetDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.view_bottomsheet, null);
        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window));
        Button bt_match = (Button)v.findViewById(R.id.sheet_bt_match);
        FancyButton onBack = (FancyButton)v.findViewById(R.id.TaskM2441);

        bt_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
            }
        });
        Button bt_wrap = (Button)v.findViewById(R.id.sheet_bt_wrap);
        bt_wrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });


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


    private void showBottomSheetCustomer(){
        mBottomSheetDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
        v = LayoutInflater.from(this).inflate(R.layout.view_customer_details, null);
        ViewUtil.setBackground(v, new ThemeDrawable(R.array.bg_window));
        FancyButton onBack = (FancyButton)v.findViewById(R.id.TaskM2441C);


        FancyButton bt_wrapMin = (FancyButton)v.findViewById(R.id.TaskminC);
        FancyButton bt_wrapMax = (FancyButton)v.findViewById(R.id.TaskmaxC);

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

//        status= sts;


        TextView textViewOrder = (TextView)v.findViewById(R.id.textViewOrder);
        textViewOrder.setText("Customer ID is "+cid+"("+product_type+"),  Scheme Type "+product_compny+"("+product_name+")"+", The Loan Percent is "+product_poan_percent+"% Of Product Price and Status is "+status);

        ProductName = (TextView)v.findViewById(R.id.Product_Name);
        PPprice = (TextView)v.findViewById(R.id.Product_Price);
        PLoanAmount = (TextView)v.findViewById(R.id.Loan_Amount);
        PRate = (TextView)v.findViewById(R.id.Percenr_Rate);
        PTunner = (TextView)v.findViewById(R.id.Ptuner_y);
        Pemi = (TextView)v.findViewById(R.id.textViewPemi);
        Ptotal = (TextView)v.findViewById(R.id.textViewPtotal);

        ProductName.setText(product_compny+"("+product_name+")");
        PPprice.setText(product_price+"Rs.");
        PLoanAmount.setText(loan_amount+"Rs.");
        PRate.setText(percent_rate+"% Of Product Price");
        PTunner.setText(tunerPP);
        Pemi.setText(emiPP+"Rs.");
        Ptotal.setText(emi_date+"th Of Every Month");


        EditText txtNameCusto = (EditText) v.findViewById(R.id.txtNameCusto);
        EditText  txtNameFather = (EditText) v.findViewById(R.id.txtNameFather);
        EditText  txtNameMobile = (EditText) v.findViewById(R.id.txtNameMobile);
        EditText  txtNameAddress = (EditText) v.findViewById(R.id.txtNameAddress);
        EditText  txtNameCity = (EditText) v.findViewById(R.id.txtNameCity);
        EditText  PinZip= (EditText) v.findViewById(R.id.PinZip);

        EditText  txtNameEmailP = (EditText) v.findViewById(R.id.txtNameEmailP);
        EditText  txtNamePANP= (EditText) v.findViewById(R.id.txtNamePANP);


        CustomerNameID = (TextView)v.findViewById(R.id.textViewNameID);
        CustomerNameID.setText(customer_name+"("+cid+")");

        txtNameCusto.setText(customer_name);
        txtNameFather.setText(father_name);
        txtNameMobile.setText(mobile);
        txtNameAddress.setText(addressP);
        txtNameCity.setText(city);
        PinZip.setText(zipP);

        txtNameEmailP.setText(Pemail);
        txtNamePANP.setText(pan_number);


        Cadd 	= (ImageView) v.findViewById(R.id.imageViewP);
        CaddA 	= (ImageView) v.findViewById(R.id.imageViewA);
        CaddD 	= (ImageView) v.findViewById(R.id.imageViewD);
        CaddV 	= (ImageView) v.findViewById(R.id.imageViewV);

        CaddV.setVisibility(View.GONE);
        CaddD.setVisibility(View.GONE);
        CaddA.setVisibility(View.GONE);

        System.out.println(photo1);
        Glide.with(Customer_Launch.this)
                .load(photo1.replace("http","https"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_launcher)
                .into(Cadd);

        //  Picasso.with(Task_Process.this)
        //.(R.drawable.ic_launcher)
        //        .load(photo)
//                                    .into(Cadd);

        System.out.println("This is url"+aadhar_card.replace("http","https"));
        Glide.with(Customer_Launch.this)
                //.(R.drawable.ic_launcher)

                .load(aadhar_card.replace("http","https"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_launcher)
                .into(CaddA);

        Glide.with(Customer_Launch.this)
                //.(R.drawable.ic_launcher)
                .load(driving_licence.replace("http","https"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_launcher)
                .into(CaddD);

        Glide.with(Customer_Launch.this)
                //.(R.drawable.ic_launcher)
                .load(voter_id.replace("http","https"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_launcher)
                .into(CaddV);

        FloatingActionButton buttonP 	= (FloatingActionButton) v.findViewById(R.id.imageButtonP);
        FloatingActionButton buttonD 	= (FloatingActionButton) v.findViewById(R.id.imageButtonD);
        FloatingActionButton buttonA 	= (FloatingActionButton) v.findViewById(R.id.imageButtonA);
        FloatingActionButton buttonV 	= (FloatingActionButton) v.findViewById(R.id.imageButtonV);

        //   if(sts.equals("DONE")){
        //     buttonP.setEnabled(false);
        //    buttonA.setEnabled(false);
        //   buttonD.setEnabled(false);
        ///  buttonV.setEnabled(false);
        // }

        //imageLoader.DisplayImage(photo, Cadd);
        //imageLoader.DisplayImage(aadhar_card, CaddA);
        //imageLoader.DisplayImage(driving_licence, CaddD);
        //imageLoader.DisplayImage(voter_id, CaddV);


        ImagePopup.enablePopUpOnClick(this, Cadd);
        ImagePopup.enablePopUpOnClick(this, CaddA);
        ImagePopup.enablePopUpOnClick(this, CaddD);
        ImagePopup.enablePopUpOnClick(this, CaddV);


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
        Intent intent1 = new Intent(Customer_Launch.this, RegisterActivity.class);
        startActivity(intent1);
        finish();
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void EMI_View(View view){
        Intent intent1 = new Intent(Customer_Launch.this, Customet_Emi_View.class);
        startActivity(intent1);
    }



    public void addone(){


        String[] names = new String[cus_name.size()];
        names = cus_name.toArray(names);

        String[] photo = new String[icon.size()];
        photo = icon.toArray(photo);

        String[] no1 = new String[icon.size()];
        no1 = no.toArray(no1);

        String[] installment_date = new String[install_date.size()];
        installment_date = install_date.toArray(installment_date);


        String[] emi1 = new String[icon.size()];
        emi1 = emi.toArray(emi1);

        String[] cheqe1 = new String[icon.size()];
        cheqe1 = cheqe.toArray(cheqe1);

        String[] paid1 = new String[icon.size()];
        paid1 = paid.toArray(paid1);


        String[] remarks1 = new String[icon.size()];
        remarks1 = remarks.toArray(remarks1);




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

            textView29.setText(names[i]);

            temp.put("name", names[i]);
            temp.put("photo", photo[i]);

            temp.put("no", no1[i]);
            temp.put("installment_date", installment_date[i]);
            temp.put("emi", emi1[i]);
            temp.put("remarks", remarks1[i]);

            temp.put("cheqe", cheqe1[i]);
            temp.put("paid", paid1[i]);

            totalReceviAAll=totalReceviAAll+Double.valueOf(emi1[i]);



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

//        totalRecevi.setText(totalReceviAAll.toString());

        //searchResults=OriginalValues initially
        //     searchResults=new ArrayList<HashMap<String,Object>>(originalValues);

        //create the adapter
        //first param-the context
        //second param-the id of the layout file
        //you will be using to fill a row
        //third param-the set of values that
        //will populate the ListView
        //   final CustomAdapter adapter=new CustomAdapter(this, R.layout.excetion_peport_listreci,searchResults);

        //     mylistview1 = (ListView)findViewById(R.id.listview);
        //finally,set the adapter to the default ListView
        //   mylistview1.setAdapter(adapter);



    }

    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {




        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Customer_Launch.this);
        String data ="";
        int sizeData = 0;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

            //  prog.setVisibility(View.GONE);
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
            //prog.stop();

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
                                                cus_name.add(prods.getJSONObject(j).getString("x"));
                                                icon.add(prods.getJSONObject(j).getString("icon"));

                                                no.add(prods.getJSONObject(j).getString("no"));

                                                install_date.add(prods.getJSONObject(j).getString("installment_date"));
                                                emi.add(prods.getJSONObject(j).getString("emi"));
                                                remarks.add(prods.getJSONObject(j).getString("remarks"));

                                                cheqe.add(prods.getJSONObject(j).getString("cheque_number"));
                                                paid.add(prods.getJSONObject(j).getString("p_amount"));


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
            TextView name,team,city,price,cheque,i_amount,paida,remarks,sNo,install_date,emi;

        }

        CustomAdapter.ViewHolder viewHolder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            totalReceviAAll =0.0;
            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.report_list, null);
                viewHolder=new CustomAdapter.ViewHolder();

                //cache the views
                viewHolder.photo=(ImageView) convertView.findViewById(R.id.profile_pic);
                viewHolder.name=(TextView) convertView.findViewById(R.id.customername);
                viewHolder.install_date=(TextView) convertView.findViewById(R.id.installment_date);


                viewHolder.sNo=(TextView) convertView.findViewById(R.id.sNo);
                viewHolder.emi=(TextView) convertView.findViewById(R.id.i_amount);
                viewHolder.cheque=(TextView) convertView.findViewById(R.id.cheque);
                viewHolder.i_amount=(TextView) convertView.findViewById(R.id.i_amount);
                viewHolder.paida=(TextView) convertView.findViewById(R.id.paida);
                viewHolder.remarks=(TextView) convertView.findViewById(R.id.remarks);






                //  prog.stop();


                //link the cached views to the convertview
                convertView.setTag(viewHolder);

            }
            else
                viewHolder=(CustomAdapter.ViewHolder) convertView.getTag();


            // int photoId=(Integer) searchResults.get(position).get("photo");



            Glide.with(Customer_Launch.this)
                    //.(R.drawable.ic_launcher)
                    .load(searchResults.get(position).get("photo").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.photo);

            //set the data to be displayed
            // viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
            viewHolder.name.setText(searchResults.get(position).get("name").toString());

            viewHolder.sNo.setText(searchResults.get(position).get("no").toString());
            viewHolder.install_date.setText(searchResults.get(position).get("installment_date").toString());
            viewHolder.remarks.setText(searchResults.get(position).get("remarks").toString());
            viewHolder.emi.setText(searchResults.get(position).get("emi").toString());
            viewHolder.cheque.setText(searchResults.get(position).get("cheqe").toString());


            viewHolder.paida.setText(searchResults.get(position).get("paid").toString());

            Double emit=Double.valueOf(searchResults.get(position).get("emi").toString());


            //ArrayList<String> ROI_Applied = new ArrayList<String>();
            //ArrayList<String> LTV_Policy = new ArrayList<String>();
            //ArrayList<String> LTV_Applied = new ArrayList<String>();



            final CustomAdapter.ViewHolder finalHolder = viewHolder;
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String Product = finalHolder.name.getText().toString().trim();
                    // String Team = lonper;
                  //  Toast.makeText(finalConvertView.getContext(), ""+Product, Toast.LENGTH_SHORT).show();
                    //MainActivityD mainD = null;
                    //mainD.privecyTerms();

//if(Product.toString().trim()=="Mobile"){}
                    //(MainActivityD).mobile();
                    //FragmentChild.doSubProduct(Product);
                    //    subProduct(Product,Team);


                }
            });


            //return the view to be displayed
            return convertView;
        }

    }


    public void sharee(View view){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, R.string.sharee);
        startActivity(shareIntent);
    }
    public void aboute(View view){
        Intent intent = new Intent(Customer_Launch.this, AboutSartha.class);
        startActivity(intent);

    }


    public void goo(View v){

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.reqest_popup_list, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);


        //  String popup_title,popup_desc, popup_image;
        final Button loan = (Button)promptsView.findViewById(R.id.loan);
        final Button noc = (Button)promptsView.findViewById(R.id.noc);
        final Button other = (Button)promptsView.findViewById(R.id.other);

        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick(loan.getText().toString().trim());
            }
        });

        noc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick(noc.getText().toString().trim());
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick(other.getText().toString().trim());
            }
        });


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                //   Toast.makeText(getApplicationContext(),""+userInput.getText(), Toast.LENGTH_SHORT).show();
                                //result.setText(userInput.getText());
                            }
                        });
          /*
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        */
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public void pick(final String ttp){
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.request_message, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);


        //  String popup_title,popup_desc, popup_image;
        final EditText sub = (EditText)promptsView.findViewById(R.id.sub);
        final EditText descP = (EditText)promptsView.findViewById(R.id.descP);
        sub.setText(ttp);
        descP.setText(dss);


        // set dialog message
        alertDialogBuilder
                .setTitle(ttp)
                .setCancelable(false)
                .setPositiveButton("Send",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dss = descP.getText().toString().trim();
                                AlertDialog.Builder builder = new AlertDialog.Builder(Customer_Launch.this);
                                builder
                                        //.setTitle("............Pay")
                                        .setMessage("Are you Sure!")
                                        //.setMessage("Customer Details!")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                new CustomerSent().execute(Customer_request,cid,ttp,dss);

                                            }
                                        })

                                        .setNegativeButton("Cancel", null)						//Do nothing on no
                                        .show();
                                //db.deleteUsers();

                            }
                        })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();

    }




    public void gooV(){

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.android_refr, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);


        //  String popup_title,popup_desc, popup_image;
        final TextView tit = (TextView)promptsView.findViewById(R.id.titlep);
        final TextView dsc = (TextView)promptsView.findViewById(R.id.descp);

        ImageView imm = (ImageView)promptsView.findViewById(R.id.imagep);
        tit.setText(popup_title);
        dsc.setText(popup_desc);

        Glide.with(Customer_Launch.this)
                //.(R.drawable.ic_launcher)
                .load(popup_image.replace("http","https"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_launcher)
                .into(imm);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Hide",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                //   Toast.makeText(getApplicationContext(),""+userInput.getText(), Toast.LENGTH_SHORT).show();
                                //result.setText(userInput.getText());

                                SharedPreferences.Editor editor = getSharedPreferences("HIDE_POP", MODE_PRIVATE).edit();
                                editor.putString("pop", "yes");
                                editor.apply();

                            }
                        });
          /*
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        */
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public void showMesaagess(View view){

        notifi.setText("");
        notifi.setVisibility(View.GONE);
        Intent intent = new Intent(Customer_Launch.this, AllShow_Message.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        //finish();
    }

    public void profilee(View view) {
        showBottomSheetCustomer();
    }


    // Class with extends AsyncTask class
    public class CustomerSent  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Customer_Launch.this);
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
                    data +="&" + URLEncoder.encode("sub", "UTF-8") + "="+params[2].toString();

                if(!params[3].equals(""))
                    data +="&" + URLEncoder.encode("desc", "UTF-8") + "="+params[3].toString();

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
                            customer_id       = jsonChildNode.optString("customer_id").toString();
                            Toast.makeText(getApplicationContext(), "Sent!", Toast.LENGTH_LONG).show();
                            Dialog.dismiss();
                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Server Error Try Again!", Toast.LENGTH_LONG).show();
                            Dialog.dismiss();
                        }

                    }


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Server Error Try Again!", Toast.LENGTH_LONG).show();
                    Dialog.dismiss();
                    e.printStackTrace();
                }


            }
        }

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
        if (ActivityCompat.checkSelfPermission(Customer_Launch.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (Customer_Launch.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Customer_Launch.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                latitude = String.valueOf(latti);
                longitude = String.valueOf(longi);


                //            Toast.makeText(this, "Your current location is"+ "\n" + "Lattitude = " + latitude + "\n" + "Longitude = " + longitude ,Toast.LENGTH_SHORT).show();


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

                //               Toast.makeText(this,"Your current location is"+ "\n" + "Lattitude = " + latitude + "\n" + "Longitude = " + longitude,Toast.LENGTH_SHORT).show();

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

