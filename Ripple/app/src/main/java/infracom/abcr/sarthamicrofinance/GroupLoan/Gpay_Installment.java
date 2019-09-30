package infracom.abcr.sarthamicrofinance.GroupLoan;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import de.hdodenhof.circleimageview.CircleImageView;
import infracom.abcr.sarthamicrofinance.Config;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Dpost.ImageLoader;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.Launch;
import infracom.abcr.sarthamicrofinance.Main;
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

public class Gpay_Installment extends AppCompatActivity {

    String cid,cname,cphoto,eid,ename,ephoto,intervaltext,installment_date,loan_install_no,emiPay,lat,lon,cc,dd;
    TextView et_pass,SlipNo;
    ImageLoader imageLoader;


    private static final int REQUEST_PERMISSIONS = 20;

    DBAdapter db;

    private String empname,regid,late_chargeText,mobile;

    String under_depart;
    String deviceIMEI = "";


    Double panaltyPercent;
    Double emiP;
    Double interPanal;
    Double intervaltextTT;

    String Allot="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Pay.php";

    private  String Allot1="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/Payto.php";

    private  String Allot2="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/Other/NPayto.php";


    Double pana=0.0;
    Double tot=0.0;

    TextView textView8N;

    String bankName,BankBranchName,AccountNum,IFSCC,CHK,SlipNo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpay__installment);

        Intent inte = getIntent();
        String[] data = new String[0];



        textView8N=(TextView)findViewById(R.id.textView8N);

        imageLoader = new ImageLoader(this);

        cid = inte.getStringExtra("cname");
        cname = inte.getStringExtra("cid");
        cphoto = inte.getStringExtra("cphoto");
        eid = inte.getStringExtra("eid");
        ename = inte.getStringExtra("ename");
        ephoto = inte.getStringExtra("ephoto");

        loan_install_no = inte.getStringExtra("loan_install_no");

        installment_date = inte.getStringExtra("installment_date");

        intervaltext = inte.getStringExtra("intervaltext");

        emiPay = inte.getStringExtra("emiPay");
        lat = inte.getStringExtra("lat");
        lon = inte.getStringExtra("lon");


        String under_depart1 = inte.getStringExtra("under_depart");


        mobile = inte.getStringExtra("mobile");

        textView8N.setText(mobile);

        late_chargeText = inte.getStringExtra("late_chargeText");

        TextView cidc= (TextView) findViewById(R.id.txtNameCusto1);
        TextView  eice= (TextView) findViewById(R.id.txtNameCusto43);
        TextView  cn= (TextView) findViewById(R.id.txtNameCusto2);
        TextView  en= (TextView) findViewById(R.id.txtNameCusto3);
        TextView  date= (TextView) findViewById(R.id.datee);
        date.setText("Installment Date is "+installment_date);

        final TextView emi= (TextView) findViewById(R.id.emi);
        TextView  panalty= (TextView) findViewById(R.id.panalty);
        TextView  chage= (TextView) findViewById(R.id.charge);
        final TextView  tote= (TextView) findViewById(R.id.total);



        cidc.setText(cid);
        eice.setText(eid);
        cn.setText(cname);

        emi.setText(emiPay);



        emiP=Double.valueOf(emi.getText().toString().trim());

        intervaltextTT=Double.valueOf(intervaltext.toString().trim());

        panaltyPercent=(emiP*2)/100;

        interPanal=panaltyPercent*intervaltextTT;
        panalty.setText(interPanal.toString());

        CircleImageView imgC = (CircleImageView)findViewById(R.id.imageViewC);
        CircleImageView imgE = (CircleImageView)findViewById(R.id.imageViewE);

        imageLoader.DisplayImage(cphoto, imgC);
        imageLoader.DisplayImage(ephoto, imgE);



        db = new DBAdapter(this);

        // db.open();
        HashMap<String, String> dataf = db.getLogininfo();
        String email = dataf.get("email");
        empname = dataf.get("name");
        regid = dataf.get("regid");
        String empdevice_imei = dataf.get("device_imei");
        deviceIMEI = empdevice_imei.toString().trim();

        LinearLayout mamagerhide = (LinearLayout)findViewById(R.id.mamagerhide);

        if(!under_depart1.equals("Sales Person")){
            mamagerhide.setVisibility(View.GONE);
        }
        if(under_depart1.equals("DSA Person")){
            mamagerhide.setVisibility(View.VISIBLE);
        }

        under_depart = dataf.get("under_depart");
        //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
        db.close();
        //  new ManagerApprove().execute(Allot,cid,cname,deviceIMEI,empname,regid,cc,lat,lon,lonper11,lonper12,loan_install_no,installment_date);

        en.setText(empname);
        ename=empname;

        final EditText chard = (EditText)findViewById(R.id.charge);
        chard.setText(late_chargeText);
        pana=Double.valueOf(chard.getText().toString().trim());
     //   tot = panaltyPercent + pana + emiP;
        tot =  pana + emiP;
        dd=pana.toString();
        tote.setText(tot.toString());

        chard.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the TextView

                String pana1=chard.getText().toString();
                //int textLength=searchString.length();
                // searchResults1.clear();

                if(pana1.equals("")){
                    //Double pana=Double.valueOf(chard.getText().toString().trim());
                    chard.setText("0.0");
                    return;
                }else {
                    pana=Double.valueOf(chard.getText().toString().trim());
                   // tot = panaltyPercent + pana + emiP;
                    tot =  pana + emiP;
                    tote.setText(tot.toString());
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });

        // Button assi =(Button)findViewById(R.id.button6);
        // assi.onC



        final RadioButton rb1 = (RadioButton)findViewById(R.id.switches_rb1);
        final RadioButton rb2 = (RadioButton)findViewById(R.id.switches_rb2);

        final LinearLayout hhh=(LinearLayout)findViewById(R.id.diddd);
        final LinearLayout hhh1=(LinearLayout)findViewById(R.id.cheqeLeni);
        hhh.setVisibility(View.GONE);

        hhh1.setVisibility(View.GONE);



        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked){
                    rb1.setChecked(rb1 == buttonView);
                    rb2.setChecked(rb2 == buttonView);

                }

                if(rb1.isChecked()){
                    //checkTun="Month";
                    hhh.setVisibility(View.GONE);

                    hhh1.setVisibility(View.GONE);
                }
                if(rb2.isChecked()){
                    //checkTun="Fortnight";
                    hhh.setVisibility(View.VISIBLE);

                    hhh1.setVisibility(View.VISIBLE);

                }
            }

        };

        rb1.setOnCheckedChangeListener(listener);
        rb2.setOnCheckedChangeListener(listener);
    }

    public void assign(View view){

        final EditText chard = (EditText)findViewById(R.id.charge);
        String pana1=chard.getText().toString();


        //int textLength=searchString.length();
        // searchResults1.clear();
        if(pana1.equals("")){
            Toast.makeText(getApplicationContext(), "Please Enter Charge Field", Toast.LENGTH_SHORT).show();
            return;
        }else{
            postPay();
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

                ccccq.setText(eid);
                bbbbq.setText(ename);
                Glide.with(Gpay_Installment.this)
                        //.(R.drawable.ic_launcher)
                        .load(ephoto.replace("http","https"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_launcher)
                        .into(im);

            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                et_pass = (TextView)fragment.getDialog().findViewById(R.id.QueryID);
                //Toast.makeText(mActivity, "Connected", Toast.LENGTH_SHORT).show();
                //cc=et_pass.getText().toString().trim();

                cc=et_pass.getText().toString().trim();
                dd=pana.toString().trim();

                if(et_pass.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Please Type Remarks.", Toast.LENGTH_SHORT).show();
                }else{

                    //  new ManagerApprove().execute(Allot,cid,eid,deviceIMEI,empname,regid,cc,lat,lon,eid,ename,loan_install_no,installment_date,intervaltext,panaltyPercent.toString(),interPanal.toString(),pana.toString(),tot.toString());
                    // postPay();

                    final RadioButton rb2 = (RadioButton)findViewById(R.id.switches_rb2);


                    if(rb2.isChecked()) {
                        new ManagerApprove().execute(Allot, cid, eid, deviceIMEI, empname, regid, cc, lat, lon, bankName, BankBranchName, AccountNum, IFSCC, CHK, installment_date,SlipNo1,dd);
                    }else{
                        new ManagerApprove1().execute(Allot1, cid, eid, deviceIMEI, empname, regid, cc, lat, lon, installment_date,SlipNo1,dd);
                    }

                }

                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.title("Recover")
                .positiveAction("SEND")
                .negativeAction("CANCEL")
                .contentView(R.layout.z_allot);


        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);

    }


    public void queryDoF(View view){
        Dialog.Builder builder = null;

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;


        builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){

            @Override
            protected void onBuildDone(Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView ccccq = (TextView)dialog.findViewById(R.id.cccc);
                TextView bbbbq = (TextView)dialog.findViewById(R.id.bbbb);

                ImageView im = (ImageView)dialog.findViewById(R.id.profile_pic11);

                ccccq.setText(eid);
                bbbbq.setText(ename);
                Glide.with(Gpay_Installment.this)
                        //.(R.drawable.ic_launcher)
                        .load(ephoto.replace("http","https"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_launcher)
                        .into(im);

            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                et_pass = (TextView)fragment.getDialog().findViewById(R.id.QueryID);
                //Toast.makeText(mActivity, "Connected", Toast.LENGTH_SHORT).show();
                //cc=et_pass.getText().toString().trim();

                cc=et_pass.getText().toString().trim();
                dd=pana.toString().trim();
                SlipNo1=" ";
                if(et_pass.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Please Type Remarks.", Toast.LENGTH_SHORT).show();
                }else{

                    //  new ManagerApprove().execute(Allot,cid,eid,deviceIMEI,empname,regid,cc,lat,lon,eid,ename,loan_install_no,installment_date,intervaltext,panaltyPercent.toString(),interPanal.toString(),pana.toString(),tot.toString());
                    // postPay();

                    final RadioButton rb2 = (RadioButton)findViewById(R.id.switches_rb2);




                        new ManagerApprove1().execute(Allot2, cid, eid, deviceIMEI, empname, regid, cc, lat, lon, installment_date,SlipNo1,dd);


                }

                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.title("Recover")
                .positiveAction("SEND")
                .negativeAction("CANCEL")
                .contentView(R.layout.z_allot);


        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);

    }

    // Class with extends AsyncTask class
    public class ManagerApprove  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Gpay_Installment.this);
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
                    data +="&" + URLEncoder.encode("BankName", "UTF-8") + "="+params[9].toString();
                if(!params[10].equals(""))
                    data +="&" + URLEncoder.encode("BranchName", "UTF-8") + "="+params[10].toString();
                if(!params[11].equals(""))
                    data +="&" + URLEncoder.encode("AccountNum", "UTF-8") + "="+params[11].toString();
                if(!params[12].equals(""))
                    data +="&" + URLEncoder.encode("IFSC", "UTF-8") + "="+params[12].toString();
                if(!params[13].equals(""))
                    data +="&" + URLEncoder.encode("CHK", "UTF-8") + "="+params[13].toString();
                if(!params[14].equals(""))
                    data +="&" + URLEncoder.encode("installment_date", "UTF-8") + "="+params[14].toString();

                if(!params[15].equals(""))
                    data +="&" + URLEncoder.encode("SlipNo", "UTF-8") + "="+params[15].toString();
                if(!params[16].equals(""))
                    data +="&" + URLEncoder.encode("Penalty", "UTF-8") + "="+params[16].toString();

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
                // aController.clearUserData();
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

                            intent1 = new Intent(Gpay_Installment.this, Launch.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent1);

                            finish();

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
    // Class with extends AsyncTask class
    public class ManagerApprove1  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Gpay_Installment.this);
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
                    data +="&" + URLEncoder.encode("installment_date", "UTF-8") + "="+params[9].toString();
                if(!params[10].equals(""))
                    data +="&" + URLEncoder.encode("Penalty", "UTF-8") + "="+params[10].toString();
                if(!params[11].equals(""))
                    data +="&" + URLEncoder.encode("SlipNo", "UTF-8") + "="+params[11].toString();

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
                // aController.clearUserData();
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

                            intent1 = new Intent(Gpay_Installment.this, Main.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent1);

                            finish();

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


    public void postPay(){


        final TextView  SlipNo= (TextView) findViewById(R.id.SlipNo);
        SlipNo1=SlipNo.getText().toString().trim();






        final RadioButton rb2 = (RadioButton)findViewById(R.id.switches_rb2);
        if(rb2.isChecked()){



            EditText  BankName= (EditText) findViewById(R.id.BankName);
            EditText  BankBranchName1= (EditText) findViewById(R.id.BankBranchName);
            EditText  BankACC= (EditText) findViewById(R.id.BankACC);
            EditText  Bankifsc= (EditText) findViewById(R.id.Bankifsc);

            EditText  chknum= (EditText) findViewById(R.id.chknum);


            bankName=BankName.getText().toString();

            BankBranchName=BankBranchName1.getText().toString();

            AccountNum=BankACC.getText().toString();

            IFSCC=Bankifsc.getText().toString();
            CHK=chknum.getText().toString();

            if(bankName.trim().length() > 2){
                if(BankBranchName.trim().length() > 2){
                    if(AccountNum.trim().length() > 6){
                        //if(IFSCC.trim().length() > 3){
                        if(CHK.trim().length() > 3){
                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                            builder
                                    .setTitle("Pay")
                                    .setMessage("Are you Sure?")
                                    //.setMessage("Customer Details!")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //Yes button clicked, do something
                                            //Toast.makeText(Tahsil_data_entry.this, "Yes button pressed",Toast.LENGTH_SHORT).show();
                                            //new ManagerApprove().execute(SDisbus_Link,cid,cname,deviceIMEI,empname,regid,MSG,lat,lon);

                                            queryDo();
                                        }
                                    })

                                    .setNegativeButton("Cancel", null)						//Do nothing on no
                                    .show();

                        }else{Toast.makeText(Gpay_Installment.this, "Please Enter Cheque Number", Toast.LENGTH_SHORT).show();return;}
                        // }else{Toast.makeText(Gpay_Installment.this, "Please Enter IFSC Code", Toast.LENGTH_SHORT).show();return;}
                    }else{Toast.makeText(Gpay_Installment.this, "Please Enter Bank Account Number", Toast.LENGTH_SHORT).show();return;}
                }else{Toast.makeText(Gpay_Installment.this, "Please Enter Bank Branch Name", Toast.LENGTH_SHORT).show();return;}
            }else{Toast.makeText(Gpay_Installment.this, "Please Enter Bank Name", Toast.LENGTH_SHORT).show();return;}

        }

        else{

            if(SlipNo1.equals("")){
                Toast.makeText(getApplicationContext(), "Please Enter Slip No.", Toast.LENGTH_SHORT).show();
                return;
            }

            queryDo();
        }
    }

    public void docall(View view){
        onCall();
    }

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE}, Integer.parseInt("123"));
        } else {


            textView8N=(TextView)findViewById(R.id.textView8N);
            String number=textView8N.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+number));
            startActivity(callIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 123:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    onCall();
                } else {
                    Log.d("TAG", "Call Permission Not Granted");
                }
                break;

            default:
                break;
        }
    }


}
