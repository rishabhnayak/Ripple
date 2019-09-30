package infracom.abcr.sarthamicrofinance.Profile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.Order.Order;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.material.app.BottomSheetDialog;
import infracom.abcr.sarthamicrofinance.material.drawable.ThemeDrawable;
import infracom.abcr.sarthamicrofinance.material.util.ViewUtil;
import infracom.abcr.sarthamicrofinance.material.widget.ProgressView;
import mehdi.sakout.fancybuttons.FancyButton;

public class SeeAllCustomer extends AppCompatActivity {
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


    ArrayList<String> listsub6 = new ArrayList<String>();
    ArrayList<String> listsub7 = new ArrayList<String>();

    ArrayList<String> listsub8 = new ArrayList<String>();

    ArrayList<String> listsub9 = new ArrayList<String>();
    ArrayList<String> listsub10 = new ArrayList<String>();

    ArrayList<String> icon = new ArrayList<String>();
    String call;
    DBAdapter db;

    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/AllCustomer.php";

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

    String serverURL1 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/AllCustomer.php";

    public Controller aController = null;

    // Search EditText
    EditText inputSearch;
    TextView inputSearch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_all_customer);
        aController = (Controller) getApplicationContext();
        Intent intent = getIntent();


       // db = new DBAdapter(this);

        inputSearch = (EditText) findViewById(R.id.inputSearch);


        product = intent.getStringExtra("cid");
        SelectCat = intent.getStringExtra("depart");
        //ProPer =

        if(SelectCat.equals("Dealer")){

            serverURL="https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Dealer_AllCustomer.php";
        }

        proName = (TextView) findViewById(R.id.textPoster);
        proName.setText("");
        mylistview1 = (ListView) findViewById(R.id.listViewtotal);

        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        new LongOperation().execute(serverURL,product,"","");

        //these arrays are just the data that
        //I'll be using to populate the ArrayList
        //You can use our own methods to get the data


//        BlurBehind.getInstance()
  //              .withAlpha(200)
    //            .withFilterColor(Color.parseColor("#222222"))
      //          .setBackground(this);


    }

    public  void reload(View view){

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
        listsub10.clear();
        
        new LongOperation().execute(serverURL,product,"","");

    }

    public void addone(){

        String[] names = new String[listsubR.size()];
        names = listsubR.toArray(names);

        String[] teams = new String[listsub1.size()];
        teams = listsub1.toArray(teams);

        String[] teamsP = new String[icon.size()];
        teamsP = icon.toArray(teamsP);

        String[] teamsP1 = new String[listsub2.size()];
        teamsP1 = listsub2.toArray(teamsP1);

        String[] Saddress = new String[listsub4.size()];
        Saddress = listsub4.toArray(Saddress);


        String[] Smobile = new String[listsub5.size()];
        Smobile = listsub5.toArray(Smobile);

        String[] scheme = new String[listsub6.size()];
        scheme = listsub6.toArray(scheme);

        String[] emi = new String[listsub7.size()];
        emi = listsub7.toArray(emi);


        String[] loan_amount = new String[listsub8.size()];
        loan_amount = listsub8.toArray(loan_amount);


        String[] product_price = new String[listsub9.size()];
        product_price = listsub9.toArray(product_price);

        String[] product = new String[listsub10.size()];
        product = listsub10.toArray(product);


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
            temp.put("depart", teamsP1[i]);
            temp.put("photo", teamsP[i]);

            temp.put("mobile", Smobile[i]);
            temp.put("address", Saddress[i]);

            temp.put("emi", emi[i]);
            temp.put("scheme", scheme[i]);

            temp.put("loanamount", loan_amount[i]);
            temp.put("price", product_price[i]);
            temp.put("product", product[i]);
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
                    String idP=originalValues.get(i).get("team").toString();

                    if(textLength<=playerName.length()){
                        //compare the String in TextView with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
                            searchResults.add(originalValues.get(i));
                    }
                    if(textLength<=idP.length()){
                        //compare the String in TextView with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(idP.substring(0,textLength)))
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
        private ProgressDialog Dialog = new ProgressDialog(SeeAllCustomer.this);
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


                                                listsub4.add(prods.getJSONObject(j).getString("address"));
                                                listsub5.add(prods.getJSONObject(j).getString("mobile"));

                                                icon.add(prods.getJSONObject(j).getString("icon"));


                                                listsub6.add(prods.getJSONObject(j).getString("scheme"));
                                                listsub7.add(prods.getJSONObject(j).getString("emi"));
                                                listsub8.add(prods.getJSONObject(j).getString("loan_amount"));
                                                listsub9.add(prods.getJSONObject(j).getString("product_price"));
                                                listsub10.add(prods.getJSONObject(j).getString("product"));


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
            TextView name,team,contact_type, mobile, address, Productid,Pricei,Loan_Amount,scheme,Emi;

        }

        ViewHolder viewHolder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.all_customer_adapter, null);
                viewHolder=new ViewHolder();

                //cache the views
                viewHolder.photo=(ImageView) convertView.findViewById(R.id.profile_pic);
                viewHolder.name=(TextView) convertView.findViewById(R.id.member_name);
                viewHolder.team=(TextView) convertView.findViewById(R.id.status);
                viewHolder.contact_type=(TextView) convertView.findViewById(R.id.contact_typetime);
                viewHolder.mobile=(TextView) convertView.findViewById(R.id.Mobile);
                viewHolder.address=(TextView) convertView.findViewById(R.id.Address23);


                viewHolder.Productid=(TextView) convertView.findViewById(R.id.Productid);
                viewHolder.Pricei=(TextView) convertView.findViewById(R.id.Pricei);
                viewHolder.Loan_Amount=(TextView) convertView.findViewById(R.id.Loan_Amount);
                viewHolder.scheme=(TextView) convertView.findViewById(R.id.scheme);
                viewHolder.Emi=(TextView) convertView.findViewById(R.id.Emi);
                //link the cached views to the convertview
                convertView.setTag(viewHolder);

            }
            else
                viewHolder=(ViewHolder) convertView.getTag();


            // int photoId=(Integer) searchResults.get(position).get("photo");

            Glide.with(SeeAllCustomer.this)
                    //.(R.drawable.ic_launcher)
                    .load(searchResults.get(position).get("photo").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.photo);
            //set the data to be displayed
            // viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
            viewHolder.name.setText(searchResults.get(position).get("name").toString());
            viewHolder.mobile.setText(searchResults.get(position).get("mobile").toString());
            viewHolder.address.setText(searchResults.get(position).get("address").toString());
            final String lonper = searchResults.get(position).get("team").toString();
            viewHolder.contact_type.setText(searchResults.get(position).get("depart").toString());
            String prop=searchResults.get(position).get("team").toString()+"";
            viewHolder.team.setText(prop);

            viewHolder.scheme.setText(searchResults.get(position).get("scheme").toString());
            viewHolder.Emi.setText(searchResults.get(position).get("emi").toString());
            viewHolder.Productid.setText(searchResults.get(position).get("product").toString());
            viewHolder.Pricei.setText(searchResults.get(position).get("price").toString());
            viewHolder.Loan_Amount.setText(searchResults.get(position).get("loanamount").toString());


            final ViewHolder finalHolder = viewHolder;
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    // String Product = finalHolder.name.getText().toString().trim();
                    //String Team = lonper;
                    call = finalHolder.mobile.getText().toString().trim();
                    // Toast.makeText(finalConvertView.getContext(), ""+Product, Toast.LENGTH_SHORT).show();

                    //Product1=Product;
                    //Team1=Team;

                   // onCall();
                    //MainActivityD mainD = null;
                    //mainD.privecyTerms();

//if(Product.toString().trim()=="Mobile"){}
                    //(MainActivityD).mobile();
                    //FragmentChild.doSubProduct(Product);
                    //   subProduct(Product,Team);

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
         listsub6.clear();
         listsub7.clear();
         listsub8.clear();
         listsub9.clear();
        listsub10.clear();


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




        // String teams[]={"Real Madrid","Barcelona","Chelsea", "Barcelona","Chelsea","Liverpool", "ManU","Barcelona"};
        Integer[] photos={R.drawable.smartphone,R.drawable.bikes,
                R.drawable.home_decore,R.drawable.books_a_more,
                R.drawable.electronics,R.drawable.appliances,
                R.drawable.books_a_more,R.drawable.fixtures};

        originalValues1=new ArrayList<HashMap<String,Object>>();
        HashMap<String , Object> temp=null;
        int noOfPlayers=names.length;
        for(int i=0;i<noOfPlayers;i++)
        {
            temp=new HashMap<String, Object>();

            temp.put("name", names[i]);
            temp.put("team", teams[i]);
            temp.put("photo", teamsP[i]);
            temp.put("status", teamsS[i]);
            temp.put("Sid", Sid[i]);
            temp.put("Sname", Sname[i]);
            originalValues1.add(temp);
        }
        searchResults1=new ArrayList<HashMap<String,Object>>(originalValues1);

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

                convertView.setTag(viewHolder);

            }
            else
                viewHolder=(ViewHolder) convertView.getTag();

            Glide.with(getContext())
                    //.(R.drawable.ic_launcher)
                    .load(searchResults1.get(position).get("photo").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.photo);

            viewHolder.name.setText(searchResults1.get(position).get("name").toString());
            final String lonper = searchResults1.get(position).get("team").toString();
            String prop=searchResults1.get(position).get("team").toString()+"";


            viewHolder.team.setText(prop);
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    BlurBehind.getInstance().execute(SeeAllCustomer.this, new OnBlurCompleteListener() {
                        @Override
                        public void onBlurComplete() {
                            Intent intent = new Intent(SeeAllCustomer.this, Order.class);
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

                }
            });
            return convertView;
        }

    }


    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE}, Integer.parseInt("123"));
        } else {


            // textView8N=(TextView)findViewById(R.id.textView8N);
            // String number=textView8N.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+call));
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

