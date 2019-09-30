package infracom.abcr.sarthamicrofinance.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import infracom.abcr.sarthamicrofinance.Config;
import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.helper.SessionManager;
import infracom.abcr.sarthamicrofinance.utils.GPSTracker;

public class Customet_Emi_View extends AppCompatActivity {
    ArrayList<HashMap<String, Object>> searchResults;
    ArrayList<HashMap<String, Object>> originalValues;
    LayoutInflater inflater;

    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Customer_EMI.php";
    ListView mylistview1;
    Double totalReceviAAll=0.0;

    ArrayList<String> no = new ArrayList<String>();
    ArrayList<String> icon = new ArrayList<String>();
    ArrayList<String> cus_name = new ArrayList<String>();
    ArrayList<String> install_date = new ArrayList<String>();
    ArrayList<String> emi = new ArrayList<String>();
    ArrayList<String> remarks = new ArrayList<String>();
    ArrayList<String> paid = new ArrayList<String>();
    ArrayList<String> cheqe = new ArrayList<String>();


    Controller aController = null;
    private String empname,regid;

    static String img1,img2,img3,img4, Name,Fname,tunMobile, Address,City,Zip,Xiemail,XPan="",sms_status;

    private ProgressDialog pDialog;
    String mypath;

    int Ckeck=0;
    public static String Finalmedia = "";


    private SessionManager session;


    TextView CustomerNameID, DetailsView, ProductName, PPprice,PLoanAmount,PRate,PTunner,Pemi,Ptotal,notifi;

    DBAdapter db;

    static  String cid,cname;

    GPSTracker gps;
    double latitude;
    double longitude;

    String deviceIMEI = "";

    TextView textView29;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customet__emi_view);


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
            aController.showAlertDialog(Customet_Emi_View.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);

            // stop executing code by return
            return;
        }

        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Check if GCM configuration is set
        if (Config.YOUR_SERVER_URL == null ||
                Config.GOOGLE_SENDER_ID == null ||
                Config.YOUR_SERVER_URL.length() == 0 ||
                Config.GOOGLE_SENDER_ID.length() == 0)
        {

            // GCM sernder id / server url is missing
            aController.showAlertDialog(Customet_Emi_View.this,
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


        new LongOperation().execute(serverURL,cid,cid,cid);


    }


    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {




        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Customet_Emi_View.this);
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

          //  textView29.setText(names[i]);

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
        searchResults=new ArrayList<HashMap<String,Object>>(originalValues);

        //create the adapter
        //first param-the context
        //second param-the id of the layout file
        //you will be using to fill a row
        //third param-the set of values that
        //will populate the ListView
        final CustomAdapter adapter=new CustomAdapter(this, R.layout.excetion_peport_listreci,searchResults);

        mylistview1 = (ListView)findViewById(R.id.listview);
        //finally,set the adapter to the default ListView
        mylistview1.setAdapter(adapter);



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

            Glide.with(Customet_Emi_View.this)
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
                    Toast.makeText(finalConvertView.getContext(), ""+Product, Toast.LENGTH_SHORT).show();
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


}
