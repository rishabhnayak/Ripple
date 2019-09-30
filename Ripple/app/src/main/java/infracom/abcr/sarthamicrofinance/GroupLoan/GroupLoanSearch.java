package infracom.abcr.sarthamicrofinance.GroupLoan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.Main;
import infracom.abcr.sarthamicrofinance.Order.Order;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.material.app.Dialog;
import infracom.abcr.sarthamicrofinance.material.app.DialogFragment;
import infracom.abcr.sarthamicrofinance.material.app.SimpleDialog;
import infracom.abcr.sarthamicrofinance.material.app.ThemeManager;
import infracom.abcr.sarthamicrofinance.material.widget.ProgressView;
import mehdi.sakout.fancybuttons.FancyButton;

public class GroupLoanSearch extends AppCompatActivity {







    CollapsingToolbarLayout collapsingToolbar;

    private ProgressView pv_circular_inout_colors;

    String CID1, GID1,ROI1;

    GroupFragment fragmentParent;
    TextView textView;
    public static String x = "x";
    ArrayList<String> listsubR = new ArrayList<String>();
    SQLiteDatabase dbs;

    String SelectCat;
    String CID,sku,sku1;
    String   DName="DName";
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private Toolbar toolbar;
    TextView mSearchText;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    ListView listview;

    DBAdapter db;

    FancyButton fab;

    ArrayList<String> listsub = new ArrayList<String>();
    ArrayList<String> listsub1 = new ArrayList<String>();
    ArrayList<String> listsub2 = new ArrayList<String>();
    ArrayList<String> listsub3 = new ArrayList<String>();
    private String Customer_uSERVER_URL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/FatchID.php";

    Controller aController = null;

    private MenuItem mSearchAction;
    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/GroupLoan/GroupFatch.php";

    CardView listcaed;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_loan_search);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Group Loan");
        //collapsingToolbar.setBackgroundResource(R.drawable.pavanb_pic);
        collapsingToolbar.setBackgroundColor(Color.LTGRAY);

        pv_circular_inout_colors = (ProgressView)findViewById(R.id.progress_pv_circular_inout_colors);

        fab=  (FancyButton)findViewById(R.id.fab);

        fab.setVisibility(View.GONE);
        aController = (Controller) getApplicationContext();
        getIDs();
        setEvents();
        //    new LongOperation().execute(serverURL,"","","");
        db = new DBAdapter(this);


        // Get Global variable instance
        pDialog = new ProgressDialog(this);
        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final Controller aController = (Controller) getApplicationContext();

        // Check if Internet Connection present
        if (!aController.isConnectingToInternet()) {

            // Internet Connection is not present
            aController.showAlertDialog(GroupLoanSearch.this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);

            // stop executing code by return
            return;
        }

        listcaed = (CardView)findViewById(R.id.listcaed);
        listcaed.setVisibility(View.GONE);

        new ManagerLongOperation().execute(Customer_uSERVER_URL,"","");

      final TextView  SearchEditText = (TextView) findViewById(R.id.Search_editText);


        SearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {
                    //your functionality
                    findkey();
                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(SearchEditText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    return true;
                }
                return false;
            }
        });

 /*
       // EditText SearchEditText =(EditText)findViewById(R.id.txtMapSearch);
        SearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener(){

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if(arg1 == EditorInfo.IME_ACTION_SEARCH)
                {
                    findkey();// search pressed and perform your functionality.
                }
                return false;
            }

        });
        inputSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString=inputSearch.getText().toString();
                int textLength=searchString.length();


                if(searchString.equals("")){
                    Toast.makeText(
                    getApplicationContext(),"Please Enter Group ID", Toast.LENGTH_SHORT).show();

                    pv_circular_inout_colors.setVisibility(View.GONE);
                    pv_circular_inout_colors.stop();
                    listcaed.setVisibility(View.GONE);
                    return;
                }else{


                    pv_circular_inout_colors.setVisibility(View.VISIBLE);
                    pv_circular_inout_colors.start();

                    new LongOperation().execute(serverURL,searchString,"","");
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });
          */
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_view_icon_text_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, Main.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void search(View view){

        findkey();
    }

    public void findkey(){

        TextView  search = (TextView) findViewById(R.id.Search_editText);

        String searchString=search.getText().toString();
        if(searchString.equals("")){
            Toast.makeText(getApplicationContext(),"Please Enter Group ID", Toast.LENGTH_SHORT).show();

            pv_circular_inout_colors.setVisibility(View.GONE);
            pv_circular_inout_colors.stop();
            //    listcaed.setVisibility(View.GONE);

            return;
        }else{
            // Check if Internet Connection present
            if (!aController.isConnectingToInternet()) {

                // Internet Connection is not present
                aController.showAlertDialog(GroupLoanSearch.this,
                        "Internet Connection Error",
                        "Please connect to working Internet connection", false);

                // stop executing code by return
                return;
            }

            pv_circular_inout_colors.setVisibility(View.VISIBLE);
            pv_circular_inout_colors.start();


            new LongOperation().execute(serverURL,searchString,"","");
        }


    }

    public void addn(View view){


       // new ManagerLongOperation().execute(Customer_uSERVER_URL,"","");

        Dialog.Builder builder = null;

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;


        builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){


            @Override
            protected void onBuildDone(Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView GID2 = (TextView) dialog.findViewById(R.id.GID2);
                TextView CID2 = (TextView) dialog.findViewById(R.id.CID2);


                TextView view23 = (TextView) dialog.findViewById(R.id.txtNameName);
                view23.setText("Group Member");
                view23.setVisibility(View.VISIBLE);

                GID2.setText(GID1);
                CID2.setText(CID1);
            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {

                //Toast.makeText(mActivity, "Connected", Toast.LENGTH_SHORT).show();
               // cc=et_pass.getText().toString().trim();
               // if(cc.toString().trim().equals("")){
               //     Toast.makeText(getApplicationContext(), "Please Type Query...", Toast.LENGTH_SHORT).show();
               // }else{

                TextView GID2 = (TextView) fragment.getDialog().findViewById(R.id.GID2);
                final String GID2T = GID2.getText().toString().trim();

                if(GID2T.equals("")){
                    Toast.makeText(getApplicationContext(), "Group ID is Loading Please Retry", Toast.LENGTH_SHORT).show();
                    return;
                }

                TextView view23 = (TextView) fragment.getDialog().findViewById(R.id.txtNameName);
                final String leader = view23.getText().toString().trim();


                TextView txtNameTitle = (TextView) fragment.getDialog().findViewById(R.id.txtNameTitle);
                final String Gtitle = txtNameTitle.getText().toString().trim();

                if(Gtitle.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Type Group Tittle", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(leader.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Type Group Leader Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(GroupLoanSearch.this);
                    builder
                            .setTitle("Group ID: "+GID1)
                            //.setMessage("Are you Sure to create new?")
                            .setMessage("Customer ID: "+CID1)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // String appro="QUERY";
                                    //showBottomSheet(appro);

                                   // pv_circular_inout_colors.setVisibility(View.VISIBLE);
                                   // pv_circular_inout_colors.start();

                                    Intent intent = new Intent(GroupLoanSearch.this, GroupProfileForm.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                                    intent.putExtra("GID", GID1);
                                    intent.putExtra("CID", CID1);
                                    intent.putExtra("ROI", ROI1);
                                    intent.putExtra("LEADER", leader);
                                    intent.putExtra("GLEADER", Gtitle);
                                    startActivity(intent);

                                }
                            })

                            .setNegativeButton("NO", null)						//Do nothing on no
                            .show();

               // }

                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                //Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_SHORT).show();

                super.onNegativeActionClicked(fragment);
            }
        };

        builder.title("Create New Group")
                 .positiveAction("Create")
                .negativeAction("Cancel")
                .contentView(R.layout.add_group);


        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getSupportFragmentManager(), null);

    }


    // Class with extends AsyncTask class
    public class ManagerLongOperation  extends AsyncTask<String, Void, String> {
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(GroupLoanSearch.this);
        String data ="";
        int sizeData = 0;
        protected void onPreExecute() {
            // Dialog.setMessage("Loading...");
            // Dialog.show();
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
                           String GID       = jsonChildNode.optString("GID").toString();
                            String CID       = jsonChildNode.optString("CID").toString();
                            String ROI       = jsonChildNode.optString("ROI").toString();
                          //  DONE       = jsonChildNode.optString("DONE").toString();
                            CID1 = CID;
                                    GID1=GID;
                            ROI1=ROI;

                        }else
                        {
                            //  Toast.makeText(getApplicationContext(), "Server Error Try Again!", Toast.LENGTH_LONG).show();
                        }

                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }


            }
        }

    }


    private void getIDs() {
        //buttonAddPage = (Button) findViewById(R.id.buttonAddPage);
        fragmentParent = (GroupFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragmentParent);

        //mFragmentManager = getSupportFragmentManager();
        //mFragmentTransaction = mFragmentManager.beginTransaction();
        //mFragmentTransaction.replace(R.id.containerView,new FragmentParent()).commit();

        textView = (TextView) findViewById(R.id.editTextPageName);


    }

    private void setEvents() {


/*
        buttonAddPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LongOperation().execute(serverURL,"","","");


                if (!textView.getText().toString().equals("")) {
                    fragmentParent.addPage(textView.getText() + "");
                    textView.setText("");
                } else {
                    Toast.makeText(MainActivityD.this, "Page name is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        */

        // mFragmentManager = getSupportFragmentManager();
        //mFragmentTransaction = mFragmentManager.beginTransaction();
        //mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();

        // mFragmentManager = getSupportFragmentManager();
        // mFragmentTransaction = mFragmentManager.beginTransaction();
        // mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();



    }
    /*
        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
            mSearchAction = menu.findItem(R.id.action_search);
            return super.onPrepareOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            switch (id) {
                case R.id.action_settings:
                    return true;
                case R.id.action_search:
                    handleMenuSearch();
                    return true;
            }

            return super.onOptionsItemSelected(item);
        }
    */
    // Class with extends AsyncTask class
    public class LongOperation  extends AsyncTask<String, Void, String> {

        // Required initialization

        //private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(GroupLoanSearch.this);
        String data ="";
        int sizeData = 0;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //Start Progress Dialog (Message)

           // Dialog.setMessage("Loading...");
           // Dialog.show();

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
            //Dialog.dismiss();

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

                    listsub.clear();
                    listsub1.clear();
                    listsub2.clear();
                    listsub3.clear();
                    //sku.clear();



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
                                                 sku = innerElem.getString("x");

                                                sku1 = innerElem.getString("x1");

                                                if(sku.equals("Not Found!")){
                                                    pv_circular_inout_colors.stop();
                                                    pv_circular_inout_colors.setVisibility(View.GONE);

                                                    listcaed.setVisibility(View.GONE);

                                                    Toast.makeText(getBaseContext(), "Not Found!", Toast.LENGTH_SHORT).show();
                                                    break;
                                                }

                                                JSONArray pro = innerElem.getJSONArray("y");

                                                if(pro != null){
                                                    for(int k = 0; k < pro.length();k++){
                                                        JSONObject innerElemN = pro.getJSONObject(k);
                                                        if(innerElemN != null){

                                                            //String sub = innerElem.getString("sub");

                                                            listsub.add(pro.getJSONObject(k).getString("sub"));

                                                            listsub1.add(pro.getJSONObject(k).getString("sub1"));

                                                            listsub2.add(pro.getJSONObject(k).getString("sub2"));
                                                            listsub3.add(pro.getJSONObject(k).getString("icon"));




 /*                                                               JSONArray proTo = innerElemN.getJSONArray("w");

                                                                if(proTo != null){
                                                                    for(int l = 0; l < proTo.length();l++){
                                                                        JSONObject innerElemNTo = proTo.getJSONObject(l);
                                                                        if(innerElemNTo != null){

                                                                            //String sub = innerElem.getString("sub");

                                                                            listsubR.add(proTo.getJSONObject(l).getString("pro"));


                                                                        }}}
*/
                                                        }else{
                                                            pv_circular_inout_colors.stop();
                                                            pv_circular_inout_colors.setVisibility(View.GONE);

                                                            listcaed.setVisibility(View.GONE);

                                                            Toast.makeText(getBaseContext(), "Not Found!", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }}

                                                //fragmentParent.addPage(sku);
                                                //textView.setText("");
                                                // HashMap<String, String> map = new HashMap<String, String>();

                                                // jsonobject = jsonarray.getJSONObject(i);
                                                // Retrive JSON Objects
                                                // map.put("x", innerElem.getString("x"));

                                                //db.addColumn("aaa");
                                                //db.close();

                                                fragmentParent.addPage(sku,sku1,listsub,listsub1,listsub2,listsub3);

                                                pv_circular_inout_colors.stop();
                                                pv_circular_inout_colors.setVisibility(View.GONE);

                                                fab.setVisibility(View.VISIBLE);
                                                listcaed.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }
                                }
                            }
                        }
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


    public void  privecyTerms(){

        LayoutInflater inflater = getLayoutInflater();
        View alertLayoutreg = inflater.inflate(R.layout.splash, null);

        android.support.v7.app.AlertDialog.Builder alertreg = new android.support.v7.app.AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        alertreg.setTitle("Proceed to Payment.");
        // this is set the view from XML inside AlertDialog
        alertreg.setView(alertLayoutreg);
        //String htmlText = " %s ";
        //String myData = "Hello World! This tutorial is to show demo of displaying text with justify alignment in WebView.";
        //String mystring = getResources().getString(R.string.benef);
        //WebView webView = (WebView)alertLayoutreg.findViewById(R.id.webView1);
        //webView.loadData(String.format(htmlText, mystring), "text/html", "utf-8");
        // disallow cancel of AlertDialog on click of back button and outside touch
        alertreg.setCancelable(false);
        alertreg.setNegativeButton("Next", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        android.support.v7.app.AlertDialog dialogreg = alertreg.create();
        dialogreg.show();
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar
        Toast.makeText(GroupLoanSearch.this,
                "Your Message", Toast.LENGTH_LONG).show();
        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.compose));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_tab_call));

            isSearchOpened = true;
        }
    }
    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }

    private void doSearch() {
//
    }

    public void  mobile(){

        AlertDialog.Builder builder = new AlertDialog.Builder(GroupLoanSearch.this);

        String[] member_names = new String[0];
        // String array for alert dialog multi choice items
        String[] colors = new String[]{
                "Red",
                "Green",
                "Blue",
                "Purple",
                "Olive"
        };


        // member_names = getResources().getStringArray(R.array.mobile);
        // Boolean array for initial selected items
        //final boolean[] checkedColors = new boolean[0];

        String[] stockArr = new String[listsubR.size()];
        stockArr = listsubR.toArray(stockArr);

        final boolean[] checkedColors = new boolean[stockArr.length];
        Arrays.fill(checkedColors, false);

        // Convert the color array to list



        final List<String> colorsList = Arrays.asList(stockArr);
                /*for (int i = 0; i < member_names.length; i++) {
                    final int finalI = i;
                    checkedColors[i]=false;

                }
                // Set multiple choice items for alert dialog

                    AlertDialog.Builder setMultiChoiceItems(CharSequence[] items, boolean[]
                    checkedItems, DialogInterface.OnMultiChoiceClickListener listener)
                        Set a list of items to be displayed in the dialog as the content,
                        you will be notified of the selected item via the supplied listener.
                 */
                /*
                    DialogInterface.OnMultiChoiceClickListener
                    public abstract void onClick (DialogInterface dialog, int which, boolean isChecked)

                        This method will be invoked when an item in the dialog is clicked.

                        Parameters
                        dialog The dialog where the selection was made.
                        which The position of the item in the list that was clicked.
                        isChecked True if the click checked the item, else false.
*/
        builder.setMultiChoiceItems(stockArr, checkedColors, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                // Update the current focused item's checked status
                checkedColors[which] = isChecked;

                // Get the current focused item
                String currentItem = colorsList.get(which);

                // Notify the current action
                Toast.makeText(GroupLoanSearch.this,
                        currentItem + " Selected ", Toast.LENGTH_SHORT).show();
            }
        });

        // Specify the dialog is not cancelable
        builder.setCancelable(false);

        // Set a title for alert dialog
        builder.setTitle("Select Item");
        builder.setIcon(R.drawable.ic_launcher);
        final Intent intent = new Intent(getApplicationContext(), Order.class);

        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click positive button
                //tv.setText("Your preferred colors..... \n");
                for (int i = 0; i<checkedColors.length; i++){
                    boolean checked = checkedColors[i];
                    if (checked) {
                        //tv.setText(tv.getText() + colorsList.get(i) + "\n");
                        // Toast.makeText(MainActivityD.this, ""+colorsList.get(i), Toast.LENGTH_SHORT).show();

                        intent.putExtra("data0", colorsList.get(i));

                    }
                }

                //intent.putExtra("num", checkedColors.length);
                startActivity(intent);
                //finish();
            }
        });


        // Set the negative/no button click listener
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the negative button
            }
        });

        // Set the neutral/cancel button click listener
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when click the neutral button
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    public void  subProduct(){

                Intent intent = new Intent(GroupLoanSearch.this, GroupProfileForm.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                //intent.putExtra("product", product);
                //intent.putExtra("ProPer", ProPer);
                //intent.putExtra("SelectCat", SelectCat);
                intent.putExtra("GID", sku);
                intent.putExtra("CID", CID1);
                intent.putExtra("ROI", ROI1);
                startActivity(intent);
                //finish();


        //Intent intent = new Intent(getApplicationContext(), SubProduct.class);

        // Registering user on our server
        // Sending registraiton details to MainActivity

        //startActivity(intent);
    }
public  void addnew(View view){

    //new ManagerLongOperation().execute(Customer_uSERVER_URL,"","");

    Dialog.Builder builder = null;

    boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;


    builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog){


        @Override
        protected void onBuildDone(Dialog dialog) {
            dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView GID2 = (TextView) dialog.findViewById(R.id.GID2);
            TextView CID2 = (TextView) dialog.findViewById(R.id.CID2);


            TextView view23 = (TextView) dialog.findViewById(R.id.txtNameName);
            TextView txtNameTitle = (TextView) dialog.findViewById(R.id.txtNameTitle);

            view23.setText("");
            view23.setVisibility(View.GONE);

            txtNameTitle.setText(sku1);
            txtNameTitle.setEnabled(false);
            GID2.setText(sku);
            CID2.setText(CID1);
        }

        @Override
        public void onPositiveActionClicked(DialogFragment fragment) {

            //Toast.makeText(mActivity, "Connected", Toast.LENGTH_SHORT).show();
            // cc=et_pass.getText().toString().trim();
            // if(cc.toString().trim().equals("")){
            //     Toast.makeText(getApplicationContext(), "Please Type Query...", Toast.LENGTH_SHORT).show();
            // }else{
            TextView view23 = (TextView) fragment.getDialog().findViewById(R.id.txtNameName);

            final String leader = view23.getText().toString().trim();

            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(GroupLoanSearch.this);
            builder
                    .setTitle("Group ID: "+sku)
                    //.setMessage("Are you Sure to create new?")
                    .setMessage("Customer ID: "+CID1)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // String appro="QUERY";
                            //showBottomSheet(appro);

                            // pv_circular_inout_colors.setVisibility(View.VISIBLE);
                            // pv_circular_inout_colors.start();

                            Intent intent = new Intent(GroupLoanSearch.this, GroupProfileForm.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                            intent.putExtra("GID", sku);
                            intent.putExtra("CID", CID1);
                            intent.putExtra("ROI", ROI1);
                            intent.putExtra("LEADER", leader);
                            intent.putExtra("GLEADER", sku1);
                            startActivity(intent);

                        }
                    })

                    .setNegativeButton("NO", null)						//Do nothing on no
                    .show();

            // }

            super.onPositiveActionClicked(fragment);
        }

        @Override
        public void onNegativeActionClicked(DialogFragment fragment) {
            //Toast.makeText(mActivity, "Cancelled", Toast.LENGTH_SHORT).show();

            super.onNegativeActionClicked(fragment);
        }
    };

    builder.title("Create New Customer Profile")
            .positiveAction("Create")
            .negativeAction("Cancel")
            .contentView(R.layout.add_group);


    DialogFragment fragment = DialogFragment.newInstance(builder);
    fragment.show(getSupportFragmentManager(), null);

}
    public void GetCat(final String Acte){
        //Toast.makeText(this, ""+Acte, Toast.LENGTH_SHORT).show();

        SelectCat=Acte;

    }


}
