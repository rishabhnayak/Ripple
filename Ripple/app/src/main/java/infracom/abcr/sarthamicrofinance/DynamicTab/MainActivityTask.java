package infracom.abcr.sarthamicrofinance.DynamicTab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
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
import infracom.abcr.sarthamicrofinance.MainActivity;
import infracom.abcr.sarthamicrofinance.Order.Customer_Search_Activity;
import infracom.abcr.sarthamicrofinance.Order.Manager_search_Activity;
import infracom.abcr.sarthamicrofinance.Order.Order;
import infracom.abcr.sarthamicrofinance.Order.Task_Process;
import infracom.abcr.sarthamicrofinance.Profile.EmpProfile;
import infracom.abcr.sarthamicrofinance.Profile.Manager_Approve;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.SentFragment;
import infracom.abcr.sarthamicrofinance.ShowMessage;

/**
 * Created by samsung on 26/10/2016.
 */
public class MainActivityTask extends AppCompatActivity {
    Button buttonAddPage;
    FragmentParentTask fragmentParenttask;
    FragmentParent so;
    TextView textView;
    public static String x = "x";
    ArrayList<String> listsubR = new ArrayList<String>();
    ArrayList<String> listsubR1 = new ArrayList<String>();
    ArrayList<String> listsubR2 = new ArrayList<String>();
    SQLiteDatabase dbs;

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

   // Controller aController = null;
   String under_depart;
    private MenuItem mSearchAction;
    String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/DB_task.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintask);
        //privecyTerms();
       // aController = (Controller) getApplicationContext();
        getIDs();
        setEvents();
        //    new LongOperation().execute(serverURL,"","","");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DBAdapter(this);

        db = new DBAdapter(MainActivityTask.this);

        // db.open();
        HashMap<String, String> dataf = db.getLogininfo();
        String email = dataf.get("email");
        String empdevice_imei = dataf.get("device_imei");
        String user_id = dataf.get("user_id");

        under_depart = dataf.get("under_depart");
        //Toast.makeText(getContext(),""+deviceIMEI, Toast.LENGTH_SHORT).show();
        db.close();

/**
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BlurBehind.getInstance().execute(MainActivityTask.this, new OnBlurCompleteListener() {
                    @Override
                    public void onBlurComplete() {
                        Intent intent = new Intent(MainActivityTask.this, MainActivityD.class);
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


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();




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

       // new LongOperation().execute(serverURL,"","","");
        BlurBehind.getInstance()
                .withAlpha(200)
                .withFilterColor(Color.parseColor("#222222"))
                .setBackground(this);

        */
    }

    private void getIDs() {
        buttonAddPage = (Button) findViewById(R.id.buttonAddPage);
     //   fragmentParenttask = (FragmentParentTask) this.getSupportFragmentManager().findFragmentById(R.id.fragmentParenttask);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new FragmentParentTask()).commit();

        textView = (TextView) findViewById(R.id.editTextPageName);


    }

    private void setEvents() {



        buttonAddPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new LongOperation().execute(serverURL,"","","");

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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_addChat:

                BlurBehind.getInstance().execute(MainActivityTask.this, new OnBlurCompleteListener() {
                    @Override
                    public void onBlurComplete() {

                        Intent intent = new Intent(MainActivityTask.this, ShowMessage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        startActivity(intent);
                        //finish();
                    }
                });

                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_search:

               // so = (FragmentParent) this.getSupportFragmentManager().findFragmentById(R.id.fragmentParenttask);

                mFragmentManager = getSupportFragmentManager();
                 mFragmentTransaction = mFragmentManager.beginTransaction();

                db = new DBAdapter(MainActivityTask.this);

                // db.open();
                HashMap<String, String> dataf = db.getLogininfo();
                String email = dataf.get("email");
                String empdevice_imei = dataf.get("device_imei");
                String user_id = dataf.get("user_id");

                String under_depart = dataf.get("under_depart");
                //Toast.makeText(getContext(),""+deviceIMEI, Toast.LENGTH_SHORT).show();
                db.close();

                if(under_depart.equals("Manager")){

                    mFragmentTransaction.replace(R.id.containerView,new Manager_search_Activity()).commit();

                    //  new LongOperation().execute(serverURL1,"","","");
                }else{

                    mFragmentTransaction.replace(R.id.containerView,new Customer_Search_Activity()).commit();

                    // new LongOperation().execute(serverURL,"","","");
                }

               // FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
               // fragmentTransaction.replace(R.id.containerView,new SentFragment()).commit();

                handleMenuSearch();
                return true;
        }

        return super.onOptionsItemSelected(item);
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
       // Toast.makeText(MainActivityTask.this, "Your Message", Toast.LENGTH_LONG).show();


        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search_black_24dp));

            //mFragmentManager = getSupportFragmentManager();
            //mFragmentTransaction = mFragmentManager.beginTransaction();
            //mFragmentTransaction.replace(R.id.containerView,new FragmentParentTask()).commit();
            BlurBehind.getInstance().execute(MainActivityTask.this, new OnBlurCompleteListener() {
                @Override
                public void onBlurComplete() {

                    Intent intent = new Intent(MainActivityTask.this, MainActivityTask.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    //intent.putExtra("cname", product);
                    //intent.putExtra("cid", cid);

                    startActivity(intent);
                    //finish();
                }
            });
            isSearchOpened = false;

           // super.onBackPressed();
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(false); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(true); //hide the title

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
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_cancel_black_24dp));

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

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityTask.this);

       // String[] member_names = new String[0];
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
                Toast.makeText(MainActivityTask.this,
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

    public void  subProduct(final String product, final String cid,final String sts){

        BlurBehind.getInstance().execute(MainActivityTask.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent;

                if(under_depart.equals("Manager")){

                     intent = new Intent(MainActivityTask.this, Manager_Approve.class);

                    //  new LongOperation().execute(serverURL1,"","","");
                }else{

                     intent = new Intent(MainActivityTask.this, Task_Process.class);

                    // new LongOperation().execute(serverURL,"","","");
                }

               // Intent intent = new Intent(MainActivityTask.this, Manager_Approve.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                intent.putExtra("cname", product);
                intent.putExtra("cid", cid);
                intent.putExtra("sts", sts);
                startActivity(intent);
                //finish();
            }
        });

        //Intent intent = new Intent(getApplicationContext(), Task_Process.class);

        // Registering user on our server
        // Sending registraiton details to MainActivity

    }
}
