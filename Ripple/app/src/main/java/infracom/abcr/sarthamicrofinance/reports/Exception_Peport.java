package infracom.abcr.sarthamicrofinance.reports;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.material.app.DialogFragment;
import infracom.abcr.sarthamicrofinance.material.app.ThemeManager;
import infracom.abcr.sarthamicrofinance.material.widget.ProgressView;

public class Exception_Peport extends AppCompatActivity {

    ArrayList<HashMap<String, Object>> searchResults;
    String currentD, currentDTo;
    //ArrayList that will hold the original Data
    ArrayList<HashMap<String, Object>> originalValues;
    LayoutInflater inflater;
    //String[] names;
    //String names[]={"Ronaldo","Messi","Torres","Iniesta", "Drogba","Gerrard","Rooney","Xavi"};

    ArrayList<String> listsubR = new ArrayList<String>();

    ArrayList<String> listsub1 = new ArrayList<String>();
    ArrayList<String> listsub2 = new ArrayList<String>();
    ArrayList<String> icon = new ArrayList<String>();


    ArrayList<String> no = new ArrayList<String>();
    ArrayList<String> city = new ArrayList<String>();
    ArrayList<String> product_name = new ArrayList<String>();
    ArrayList<String> emi = new ArrayList<String>();
    ArrayList<String> ROI_policy = new ArrayList<String>();
    ArrayList<String> remarks = new ArrayList<String>();


    ArrayList<String> ROI_Applied = new ArrayList<String>();
    ArrayList<String> LTV_Policy = new ArrayList<String>();
    ArrayList<String> LTV_Applied = new ArrayList<String>();
    //String serverURL=null;
    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Exception_list_report.php";

    String serverURL2 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Approved_Business_report.php";

    String serverURL3 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Declined_Business_report.php";

    String serverURL4 = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Disbursed_Business_report.php";



    String[] member_names1;
    String product,SelectCat,ProPer;
    TextView proName;

    ArrayAdapter<String> adapter;
    ProgressView prog;

    ListView mylistview1;
    public Controller aController = null;

    // Search EditText
    EditText inputSearch;


    EditText editTextB1, editTextB2;

    private Calendar calendar, calendar1;
    private EditText dateView,dateView1;
    private int year, month, day;
    int dd= 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exception__peport_activity);

        dateView = (EditText) findViewById(R.id.editTextB1);
        dateView1 = (EditText) findViewById(R.id.editTextB2);

        prog = (ProgressView)findViewById(R.id.progress_pv_circular_inout_colors);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);


        inputSearch = (EditText) findViewById(R.id.editTextB1);

        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        currentD=dateView.getText().toString().trim();
        new LongOperation().execute(serverURL,currentD,currentD,"");



    }




    @SuppressWarnings("deprecation")
    public void datapickFrom(View view) {
        //showDialog(999);
        dd=1;
        datapick();
        //Toast.makeText(getApplicationContext(), "ca",Toast.LENGTH_SHORT).show();
    }

    public void datapickTo(View view) {
        //showDialog(999);

        datapick();
        dd=2;
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
                    if(dd==1) {
                        showDate(arg1, arg2 + 1, arg3);
                    }else{
                        showDateto(arg1, arg2 + 1, arg3);

                    }
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));

        currentD=dateView.getText().toString().trim();
        //dateView1.setText("");
        currentDTo=dateView1.getText().toString().trim();
        fatch(currentD,currentDTo);
    }

    private void showDateto(int year, int month, int day) {
        dateView1.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));

        currentD=dateView.getText().toString().trim();
        currentDTo=dateView1.getText().toString().trim();


        fatch(currentD,currentDTo);

    }


    public void addone(){


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
        private ProgressDialog Dialog = new ProgressDialog(Exception_Peport.this);
        String data ="";
        int sizeData = 0;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

            prog.setVisibility(View.GONE);
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
            prog.stop();

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
            TextView name,team,city,price,price1,price2,price3,price4,sNo,ROI_Applied,LTV_Applied,LTV_Policy;

        }

        ViewHolder viewHolder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.excetion_peport_listreci, null);
                viewHolder=new ViewHolder();

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





                prog.stop();


                //link the cached views to the convertview
                convertView.setTag(viewHolder);

            }
            else
                viewHolder=(ViewHolder) convertView.getTag();


            // int photoId=(Integer) searchResults.get(position).get("photo");

            Glide.with(Exception_Peport.this)
                    //.(R.drawable.ic_launcher)
                    .load(searchResults.get(position).get("photo").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.photo);

            //set the data to be displayed
            // viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
            viewHolder.name.setText(searchResults.get(position).get("name").toString());
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




            //ArrayList<String> ROI_Applied = new ArrayList<String>();
            //ArrayList<String> LTV_Policy = new ArrayList<String>();
            //ArrayList<String> LTV_Applied = new ArrayList<String>();



            final ViewHolder finalHolder = viewHolder;
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String Product = finalHolder.name.getText().toString().trim();
                    String Team = lonper;
                    Toast.makeText(finalConvertView.getContext(), ""+Product, Toast.LENGTH_SHORT).show();
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
        new LongOperation().execute(serverURL,from,to,"");
    }



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
                Toast.makeText(Exception_Peport.this, "Date is " + date, Toast.LENGTH_SHORT).show();
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

}
