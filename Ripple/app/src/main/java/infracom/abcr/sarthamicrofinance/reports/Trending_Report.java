package infracom.abcr.sarthamicrofinance.reports;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Profile.Disbursement;
import infracom.abcr.sarthamicrofinance.Profile.Manager_Approve;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.material.app.BottomSheetDialog;
import infracom.abcr.sarthamicrofinance.material.drawable.ThemeDrawable;
import infracom.abcr.sarthamicrofinance.material.util.ViewUtil;
import infracom.abcr.sarthamicrofinance.material.widget.ProgressView;
import mehdi.sakout.fancybuttons.FancyButton;

public class Trending_Report extends AppCompatActivity {


    private BottomSheetDialog mBottomSheetDialog;

    ArrayList<HashMap<String, Object>> searchResults1=null;

    //ArrayList that will hold the original Data
    ArrayList<HashMap<String, Object>> originalValues1=null;

    private Handler mHandler;

    static  String cid,cname,task;
    ListView mylistview1;
    String[] member_names1;
    String product,SelectCat,ProPer, empname;

    DBAdapter db;

    EditText inputSearch;



    ArrayList<String> listsubR = new ArrayList<String>();
    ArrayList<String> listsub1 = new ArrayList<String>();
    ArrayList<String> listsub2 = new ArrayList<String>();
    ArrayList<String> listsub3 = new ArrayList<String>();

    ArrayList<String> listsub4 = new ArrayList<String>();
    ArrayList<String> listsub5 = new ArrayList<String>();
    ArrayList<String> listsub6 = new ArrayList<String>();

    ArrayList<String> listsub7 = new ArrayList<String>();

    ArrayList<String> listsub8 = new ArrayList<String>();
    ArrayList<String> listsub9 = new ArrayList<String>();


    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Trending_Reports.php";

    String serverURL1 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/empList.php";

    ProgressView prog;

    ImageView Ephoto;

    Double tot,log,tot1,log1;

    TextView TDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trending__report_activity);


        Ephoto=(ImageView) findViewById(R.id.profile_picTR);
       TDD=(TextView) findViewById(R.id.TDD);
       // viewHolder.team=(TextView) findViewById(R.id.status);
       // viewHolder.contact_type=(TextView) findViewById(R.id.contact_typetime);

        //viewHolder.tt=(TextView) findViewById(R.id.t);
       // viewHolder.ll=(TextView) findViewById(R.id.l);

        new CustomerLongOperation().execute(serverURL,"id");

    }

    public void showBottomSheet(){
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

        TextView ff = (TextView)v.findViewById(R.id.textPoster);
        ff.setText("Select Sales Person");
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




        inputSearch = (EditText) v.findViewById(R.id.inputSearch);


        new LongOperation().execute(serverURL1,"id","id","id");


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


        //  inputSearch = (EditText)v.findViewById(R.id.edtSearch); //the text editor



//        aController = (Controller)v.getContext();
        product = "Mobile";
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

                    //final String namee = finalHolder.name.getText().toString().trim();
                    //final String eid = finalHolder.team.getText().toString().trim();
                    //final String sts = finalHolder.contact_type.getText().toString().trim();

                    tot1= Double.valueOf(finalHolder.tt.getText().toString().trim());
                    log1=Double.valueOf(finalHolder.ll.getText().toString().trim());

                    Double cal= 0.0;
                    cal= log1/tot1;

                    Glide.with(getContext())
                            //.(R.drawable.ic_launcher)
                            .load(searchResults1.get(position).get("photo").toString().replace("http","https"))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_launcher)
                            .into(Ephoto);



                    TDD.setText(log1+"/"+tot1+"="+cal.toString());

                    if(mBottomSheetDialog != null){
                        mBottomSheetDialog.dismissImmediately();
                        mBottomSheetDialog = null;
                    }

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




    // Class with extends AsyncTask class
    public class CustomerLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Trending_Report.this);
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
                    data +="&" + URLEncoder.encode("emp_id", "UTF-8") + "="+params[1].toString();

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
                //aController.clearUserData();
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

                            String total_dis       = jsonChildNode.optString("total_dis").toString();

                            String total_log       = jsonChildNode.optString("total_log").toString();


                            TextView trendid = (TextView)findViewById(R.id.trendid);

                            Double cal= 0.0;
                            cal= Double.valueOf(total_log)/Double.valueOf(total_dis);

                            trendid.setText(total_log+"/"+total_dis+" = "+cal);

                           // trendid.setText("llllllllllllllllllllll");

                         //   TextView  EMPIDtextView = (TextView) findViewById(R.id.EMPIDtextView);
                           // tadsilnamCe.setText("Post- "+depart);
                           // EMPIDtextView.setText("ID - SMF"+int_id);
                           // Newint_id="SMF"+int_id;
                            // ProductName = (TextView)findViewById(R.id.Product_Name);
                            //PPprice = (TextView)findViewById(R.id.Product_Price);
                            //PLoanAmount = (TextView)findViewById(R.id.Loan_Amount);
                            // PRate = (TextView)findViewById(R.id.Percenr_Rate);
                            // PTunner = (TextView)findViewById(R.id.Ptuner_y);
                            // Pemi = (TextView)findViewById(R.id.textViewPemi);
                            // Ptotal = (TextView)findViewById(R.id.textViewPtotal);

                            //ProductName.setText(product_compny+"("+product_name+")");
                            //PPprice.setText(product_price+"Rs.");
                            //PLoanAmount.setText(loan_amount+"Rs.");
                            //PRate.setText(percent_rate+"% Of Product Price");
                            //  PTunner.setText(tunerPP);
                            // Pemi.setText(emiPP+"Rs.");
                            // Ptotal.setText(total_amountPP+"Rs.");


                            //EditText Password = (EditText) findViewById(R.id.Password);
                            // EditText  Emobile = (EditText) findViewById(R.id.Emobile);
                            // EditText  Eemail = (EditText) findViewById(R.id.Eemail);
                            // EditText  Eaddress = (EditText) findViewById(R.id.Eaddress);
                            //EditText  PinZip= (EditText) findViewById(R.id.PinZip);

                            //EditText  txtNameEmailP = (EditText) findViewById(R.id.txtNameEmailP);
                            //EditText  txtNamePANP= (EditText) findViewById(R.id.txtNamePANP);



                           // Password.setText(int_id);
                            // Eemail.setText(Pemail);
                            // Emobile.setText(mobile);

                            // Eaddress.setText(addressP);
                            // txtNameCity.setText(city);
                            // PinZip.setText(zipP);

                            //txtNameEmailP.setText(Pemail);
                            //txtNamePANP.setText(pan_number);


                            //Cadd 	= (ImageView) findViewById(R.id.backdrop);
                            //Cadd1 	= (CircleImageView) findViewById(R.id.imageShow1l);
                            // CaddD 	= (ImageView) findViewById(R.id.imageViewD);
                            // CaddV 	= (ImageView) findViewById(R.id.imageViewV);


                            //imageLoader.DisplayImage(photo, Cadd);
                            //imageLoader.DisplayImage(photo, Cadd);
                            //imageLoader.DisplayImage(photo, Cadd1);
                            //imageLoader.DisplayImage(voter_id, CaddV);

                            // Drawable drawable = LoadImageFromWebOperations(photo);
                            // Cadd.setImageDrawable(drawable);
                            // Cadd1.setImageDrawable(drawable);
/*
                            Glide.with(EmpProfile.this)
                                    //.(R.drawable.ic_launcher)
                                    .load(photo)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(Cadd);
                            Glide.with(EmpProfile.this)
                                    .load(photo)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_launcher)
                                    .into(Cadd1);
*/
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


public void findemp(View view){

    showBottomSheet();
}


}
