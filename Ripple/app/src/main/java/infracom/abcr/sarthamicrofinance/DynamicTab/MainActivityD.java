package infracom.abcr.sarthamicrofinance.DynamicTab;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.util.HashMap;
import java.util.List;

import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.DBAdapter;
import infracom.abcr.sarthamicrofinance.Dpost.ListViewAdapter;
import infracom.abcr.sarthamicrofinance.MainActivity;
import infracom.abcr.sarthamicrofinance.Order.Order;
import infracom.abcr.sarthamicrofinance.Order.Task_Process;
import infracom.abcr.sarthamicrofinance.Profile.EmpProfile;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.RMainActivity;
import infracom.abcr.sarthamicrofinance.SentFragment;
import infracom.abcr.sarthamicrofinance.TabFragment;

public class MainActivityD extends AppCompatActivity {
    Button buttonAddPage;
    FragmentParent fragmentParent;
    TextView textView;
    public static String x = "x";
    ArrayList<String> listsubR = new ArrayList<String>();
    SQLiteDatabase dbs;

    String SelectCat;
    String CID="CID";
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

    Controller aController = null;

    private MenuItem mSearchAction;
    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/Tab_json.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maind);
        //privecyTerms();



        aController = (Controller) getApplicationContext();
        getIDs();
        setEvents();
        //    new LongOperation().execute(serverURL,"","","");
       db = new DBAdapter(this);

        /**
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


         *  mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BlurBehind.getInstance().execute(MainActivityD.this, new OnBlurCompleteListener() {
                    @Override
                    public void onBlurComplete() {
                        Intent intent = new Intent(MainActivityD.this, MainActivityTask.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        startActivity(intent);
                        //finish();
                    }
                });
                //finish();
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
            }
        });

         * Setup click events on the Navigation View Items.
         */




        /**
         * Setup Drawer Toggle of the Toolbar
         */

        //android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        //ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,R.string.app_name);

        //mDrawerLayout.setDrawerListener(mDrawerToggle);

        //mDrawerToggle.syncState();



/*
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();



                if (menuItem.getItemId() == R.id.nav_item_sent) {
                    // Launch Main Activity
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }

                if (menuItem.getItemId() == R.id.nav_item_inbox) {
                   // FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                   // xfragmentTransaction.replace(R.id.containerView,new SubProduct()).commit();
                    Intent i = new Intent(getApplicationContext(), EmpProfile.class);
                    startActivity(i);
                    finish();
                }

                return false;
            }

        });


        BlurBehind.getInstance()
                .withAlpha(200)
                .withFilterColor(Color.parseColor("#222222"))
                .setBackground(this);
        */
        new LongOperation().execute(serverURL,"","","");

    }

    private void getIDs() {
        buttonAddPage = (Button) findViewById(R.id.buttonAddPage);
        fragmentParent = (FragmentParent) this.getSupportFragmentManager().findFragmentById(R.id.fragmentParent);

        //mFragmentManager = getSupportFragmentManager();
       //mFragmentTransaction = mFragmentManager.beginTransaction();
        //mFragmentTransaction.replace(R.id.containerView,new FragmentParent()).commit();

        textView = (TextView) findViewById(R.id.editTextPageName);


    }

    private void setEvents() {



        buttonAddPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LongOperation().execute(serverURL,"","","");

/*
                if (!textView.getText().toString().equals("")) {
                    fragmentParent.addPage(textView.getText() + "");
                    textView.setText("");
                } else {
                    Toast.makeText(MainActivityD.this, "Page name is empty", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });


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
        private ProgressDialog Dialog = new ProgressDialog(MainActivityD.this);
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

                                                    JSONArray pro = innerElem.getJSONArray("y");
                                                    ArrayList<String> listsub = new ArrayList<String>();
                                                    ArrayList<String> listsub1 = new ArrayList<String>();
                                                    ArrayList<String> listsub2 = new ArrayList<String>();
                                                    ArrayList<String> listsub3 = new ArrayList<String>();

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
                                                            }}}

                                                    //fragmentParent.addPage(sku);
                                                    //textView.setText("");
                                                   // HashMap<String, String> map = new HashMap<String, String>();

                                                   // jsonobject = jsonarray.getJSONObject(i);
                                                    // Retrive JSON Objects
                                                   // map.put("x", innerElem.getString("x"));

                                                    //db.addColumn("aaa");
                                                    //db.close();

                                                    fragmentParent.addPage(sku,listsub,listsub1,listsub2,listsub3);
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
        Toast.makeText(MainActivityD.this,
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

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityD.this);

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
                Toast.makeText(MainActivityD.this,
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

    public void  subProduct(final String product,final String ProPer){
        BlurBehind.getInstance().execute(MainActivityD.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(MainActivityD.this, Order.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                //intent.putExtra("product", product);
                //intent.putExtra("ProPer", ProPer);
                //intent.putExtra("SelectCat", SelectCat);
                intent.putExtra("data0", product);
                intent.putExtra("data1", product);
                intent.putExtra("ProPer", ProPer);
                intent.putExtra("data2", SelectCat);
                intent.putExtra("per", "75");
                startActivity(intent);
                //finish();
            }
        });

        //Intent intent = new Intent(getApplicationContext(), SubProduct.class);

        // Registering user on our server
        // Sending registraiton details to MainActivity

        //startActivity(intent);
    }

    public void GetCat(final String Acte){
        //Toast.makeText(this, ""+Acte, Toast.LENGTH_SHORT).show();

        SelectCat=Acte;

    }
}
