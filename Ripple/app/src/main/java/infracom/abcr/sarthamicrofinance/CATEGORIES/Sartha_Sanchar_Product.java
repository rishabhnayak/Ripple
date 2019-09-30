package infracom.abcr.sarthamicrofinance.CATEGORIES;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.DynamicTab.SubProduct;
import infracom.abcr.sarthamicrofinance.Order.Order;
import infracom.abcr.sarthamicrofinance.R;

public class Sartha_Sanchar_Product extends AppCompatActivity {

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
    ArrayList<String> icon = new ArrayList<String>();

    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Sanchar_Product_db.php";

    ListView mylistview1;
    String[] member_names1;
    String product,SelectCat,ProPer,ProductName;
    TextView proName;

    ArrayAdapter<String> adapter;

    public Controller aController = null;

    // Search EditText
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sartha__sanchar__product);


        aController = (Controller) getApplicationContext();
        Intent intent = getIntent();

        inputSearch = (EditText) findViewById(R.id.inputSearch);


        product = intent.getStringExtra("product");
        String Se = intent.getStringExtra("upto");

        proName = (TextView) findViewById(R.id.textPoster);
        proName.setText("" + product);
        mylistview1 = (ListView) findViewById(R.id.listViewtotal);

        inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        new LongOperation().execute(serverURL,product,"","");

        //these arrays are just the data that
        //I'll be using to populate the ArrayList
        //You can use our own methods to get the data


       // BlurBehind.getInstance()
        //        .withAlpha(200)
          //      .withFilterColor(Color.parseColor("#222222"))
            //    .setBackground(this);


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
        private ProgressDialog Dialog = new ProgressDialog(Sartha_Sanchar_Product.this);
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

            Glide.with(Sartha_Sanchar_Product.this)
                    //.(R.drawable.ic_launcher)
                    .load(searchResults.get(position).get("photo").toString().replace("http","https"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_launcher)
                    .into(viewHolder.photo);
            //set the data to be displayed
            // viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
            viewHolder.name.setText(searchResults.get(position).get("name").toString());
            final String lonper = searchResults.get(position).get("team").toString();
            String prop=searchResults.get(position).get("team").toString()+"% Percent Rate will Consider On This Product Price";
            viewHolder.team.setText(prop);

            final ViewHolder finalHolder = viewHolder;
            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String Product = finalHolder.name.getText().toString().trim();
                    ProductName=Product;
                    String Team = lonper;
                    ProPer=lonper;
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

        BlurBehind.getInstance().execute(Sartha_Sanchar_Product.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(Sartha_Sanchar_Product.this, Order.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                //intent.putExtra("product", ProductName);
                //intent.putExtra("ProPer", ProPer);
                //intent.putExtra("SelectCat", product);

                intent.putExtra("data0", product);
                intent.putExtra("data1", productsub);
                intent.putExtra("ProPer", ProPer);
                intent.putExtra("data2", productsub);
                intent.putExtra("per", "85");

                startActivity(intent);
                //finish();
            }
        });
        // Registering user on our server
        // Sending registraiton details to MainActivity

        //startActivity(intent);
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