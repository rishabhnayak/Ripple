package infracom.abcr.sarthamicrofinance.AssignEMP;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Dpost.ImageLoader;
import infracom.abcr.sarthamicrofinance.Profile.Admin_Launch;
import infracom.abcr.sarthamicrofinance.Profile.Manager_Launch;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.material.app.Dialog;
import infracom.abcr.sarthamicrofinance.material.app.DialogFragment;
import infracom.abcr.sarthamicrofinance.material.app.SimpleDialog;
import infracom.abcr.sarthamicrofinance.material.app.ThemeManager;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class Panalty_Count extends AppCompatActivity {

    String cid,cname,cphoto,eid,ename,ephoto,intervaltext,installment_date,loan_install_no,emiPay,lat,lon,cc;
    TextView et_pass;
    ImageLoader imageLoader;
    DBAdapter db;
    private String empname,regid;
    String under_depart;
    String deviceIMEI = "";

    Double panaltyPercent;
    Double emiP;
    Double interPanal;
    Double intervaltextTT;


    private  String Allot="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Allot.php";
    Double pana=0.0;
    Double tot=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panalty__count_activity_);

        Intent inte = getIntent();
        String[] data = new String[0];



        imageLoader = new ImageLoader(this);

        cid = inte.getStringExtra("cid");
        cname = inte.getStringExtra("cname");
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

       // under_depart = inte.getStringExtra("lon");


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
        en.setText(ename);

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

        under_depart = dataf.get("under_depart");
        //Toast.makeText(getApplicationContext(),""+sts, Toast.LENGTH_SHORT).show();
        db.close();
      //  new ManagerApprove().execute(Allot,cid,cname,deviceIMEI,empname,regid,cc,lat,lon,lonper11,lonper12,loan_install_no,installment_date);


        final EditText chard = (EditText)findViewById(R.id.charge);
        chard.setText("0.0");
        pana=Double.valueOf(chard.getText().toString().trim());
        tot = panaltyPercent + pana + emiP;
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
                    tot = panaltyPercent + pana + emiP;
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
    }

    public void assign(View view){

        final EditText chard = (EditText)findViewById(R.id.charge);
        String pana1=chard.getText().toString();
        //int textLength=searchString.length();
        // searchResults1.clear();
        if(pana1.equals("")){
           // Toast.makeText(getApplicationContext(), "Please Enter", Toast.LENGTH_SHORT).show();
            return;
        }else{
            queryDo();
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
                Glide.with(Panalty_Count.this)
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
                cc=et_pass.getText().toString().trim();
                if(cc.toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Please Type...", Toast.LENGTH_SHORT).show();
                }else{


                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Panalty_Count.this);
                    builder
                            .setTitle("Recovery Allotment")
                            .setMessage("Are you Sure?")
                            //.setMessage("Customer Details!")
                            .setIcon(android.R.drawable.ic_menu_send)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // String appro="QUERY";
                                    //showBottomSheet(appro);
                                    new ManagerApprove().execute(Allot,cid,eid,deviceIMEI,empname,regid,cc,lat,lon,eid,ename,loan_install_no,installment_date,intervaltext,panaltyPercent.toString(),interPanal.toString(),pana.toString(),tot.toString());

                                    // new ManagerApprove().execute(Allot,cid,cname,deviceIMEI,empname,regid,cc,lat,lon,lonper11,lonper12,loan_install_no,installment_date);
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

    // Class with extends AsyncTask class
    public class ManagerApprove  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Panalty_Count.this);
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
                if(!params[13].equals(""))
                    data +="&" + URLEncoder.encode("day_interval", "UTF-8") + "="+params[13].toString();
                if(!params[14].equals(""))
                    data +="&" + URLEncoder.encode("panaltyPercent_amount", "UTF-8") + "="+params[14].toString();
                if(!params[15].equals(""))
                    data +="&" + URLEncoder.encode("total_panalty", "UTF-8") + "="+params[15].toString();
                if(!params[16].equals(""))
                    data +="&" + URLEncoder.encode("late_charge", "UTF-8") + "="+params[16].toString();
                if(!params[17].equals(""))
                    data +="&" + URLEncoder.encode("total_late_charge_panalty", "UTF-8") + "="+params[17].toString();


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

                                intent1 = new Intent(Panalty_Count.this, Manager_Launch.class);
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

}
