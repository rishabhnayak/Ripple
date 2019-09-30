package infracom.abcr.sarthamicrofinance.DynamicTab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import infracom.abcr.sarthamicrofinance.AssignEMP.Panalty_Count;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Order.Order;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.material.app.BottomSheetDialog;
import infracom.abcr.sarthamicrofinance.material.drawable.ThemeDrawable;
import infracom.abcr.sarthamicrofinance.material.util.ViewUtil;
import infracom.abcr.sarthamicrofinance.material.widget.ProgressView;
import infracom.abcr.sarthamicrofinance.slidingtab.contacts.RowItem;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by samsung on 26/10/2016.
 */
public class SubProduct extends AppCompatActivity {
    //ArrayList thats going to hold the search results
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

    ArrayList<String> listsub4 = new ArrayList<String>();
    ArrayList<String> listsub5 = new ArrayList<String>();
    ArrayList<String> icon = new ArrayList<String>();

    DBAdapter db;

    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Product_db.php";

    private  String Allot="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/Allot.php";

    private Handler mHandler;


    private BottomSheetDialog mBottomSheetDialog;

    String[] member_names1;
    String product,SelectCat,ProPer;
    TextView proName;
    ProgressView prog;

    ArrayAdapter<String> adapter;

    private String empname,regid;

    String Product1,Team1;
    ArrayList<HashMap<String, Object>> originalValues1=null;
    ArrayList<HashMap<String, Object>> searchResults1=null;

    ListView mylistview1;

    String serverURL1 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/empList.php";

    public Controller aController = null;

    // Search EditText
    EditText inputSearch;
    TextView inputSearch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_product);
        //privecyTerms();

        aController = (Controller) getApplicationContext();
        Intent intent = getIntent();


        db = new DBAdapter(this);

        inputSearch = (EditText) findViewById(R.id.inputSearch);


        product = intent.getStringExtra("product");
        SelectCat = intent.getStringExtra("SelectCat");
        ProPer = intent.getStringExtra("ProPer");

        proName = (TextView) findViewById(R.id.textPoster);
        proName.setText("Select " + product);
        mylistview1 = (ListView) findViewById(R.id.listViewtotal);

        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        new LongOperation().execute(serverURL,product,"","");

        //these arrays are just the data that
        //I'll be using to populate the ArrayList
        //You can use our own methods to get the data


        BlurBehind.getInstance()
                .withAlpha(200)
                .withFilterColor(Color.parseColor("#222222"))
                .setBackground(this);


    }

    public void addone(){

        String[] names = new String[listsubR.size()];
        names = listsubR.toArray(names);

        String[] teams = new String[listsub1.size()];
        teams = listsub1.toArray(teams);

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
        final CustomAdapter adapter=new CustomAdapter(this, R.layout.list_item,searchResults);

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
                    String playerName=originalValues.get(i).get("name").toString();
                    if(textLength<=playerName.length()){
                        //compare the String in EditText with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
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
        private ProgressDialog Dialog = new ProgressDialog(SubProduct.this);
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
            TextView name,team;

        }

        ViewHolder viewHolder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.list_item, null);
                viewHolder=new ViewHolder();

                //cache the views
                viewHolder.photo=(ImageView) convertView.findViewById(R.id.profile_pic);
                viewHolder.name=(TextView) convertView.findViewById(R.id.member_name);
                viewHolder.team=(TextView) convertView.findViewById(R.id.status);

                //link the cached views to the convertview
                convertView.setTag(viewHolder);

            }
            else
                viewHolder=(ViewHolder) convertView.getTag();


           // int photoId=(Integer) searchResults.get(position).get("photo");

            Glide.with(SubProduct.this)
                    //.(R.drawable.ic_launcher)
                    .load(searchResults.get(position).get("photo").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.photo);
            //set the data to be displayed
           // viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
            viewHolder.name.setText(searchResults.get(position).get("name").toString());
            final String lonper = searchResults.get(position).get("team").toString();
            String prop=searchResults.get(position).get("team").toString()+"% Loan Amount will Consider On This Product Price";
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

                    Product1=Product;
                    Team1=Team;
                    //MainActivityD mainD = null;
                    //mainD.privecyTerms();

//if(Product.toString().trim()=="Mobile"){}
                    //(MainActivityD).mobile();
                    //FragmentChild.doSubProduct(Product);
                    subProduct(Product,Team);

                }
            });

            //return the view to be displayed
            return convertView;
        }

    }

    public void  subProduct(final String productsub,final String per){
        // Intent intent = new Intent(getApplicationContext(), Order.class);

        Allot();


        // Registering user on our server
        // Sending registraiton details to MainActivity

        //startActivity(intent);
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

                    BlurBehind.getInstance().execute(SubProduct.this, new OnBlurCompleteListener() {
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
            //        lonper11=searchResults1.get(position).get("team").toString();
              //      lonper12= searchResults1.get(position).get("name").toString();
                //    lonper13=searchResults1.get(position).get("photo").toString();

                    //final String namee = finalHolder.name.getText().toString().trim();
                    //final String eid = finalHolder.team.getText().toString().trim();
                    //final String sts = finalHolder.contact_type.getText().toString().trim();
/*
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

}


 /*       rowItems1 = new ArrayList<RowItem>();

        member_names1 = getResources().getStringArray(R.array.Member_namesTask);
        profile_pics1 = getResources().obtainTypedArray(R.array.profile_picsTask);
        statues1 = getResources().getStringArray(R.array.Total);
        contactType1 = getResources().getStringArray(R.array.total_note);



        for (int i = 0; i < member_names1.length; i++) {
            item = new RowItem(member_names1[i],
                    profile_pics1.getResourceId(i, -1), statues1[i],
                    contactType1[i]);
            rowItems1.add(item);
        }

        final infracom.abcr.sarthamicrofinance.slidingtab.contacts.TaskAdapter adapter1 = new infracom.abcr.sarthamicrofinance.slidingtab.contacts.TaskAdapter(this, rowItems1);

        mylistview1.setAdapter(adapter1);
        profile_pics1.recycle();

        inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString = inputSearch.getText().toString();
                int textLength = searchString.length();
                rowItems1.clear();

                for (int i = 0; i < rowItems1.size(); i++) {
                    RowItem row_pos = rowItems1.get(i);
                    String playerName = row_pos.getMember_name().toString();
                    if (textLength <= playerName.length()) {
                        //compare the String in EditText with Names in the ArrayList
                        if (searchString.equalsIgnoreCase(playerName.substring(0, textLength)))

                            rowItems1.add(adapter1);
                    }
                }

                adapter1.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {


            }
        });
    }
}
*/