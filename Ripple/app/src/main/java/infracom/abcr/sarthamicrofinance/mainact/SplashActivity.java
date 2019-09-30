package infracom.abcr.sarthamicrofinance.mainact;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import infracom.abcr.sarthamicrofinance.Calculator.FORECLOSURE_LETTER;
import infracom.abcr.sarthamicrofinance.CropIMG;
import infracom.abcr.sarthamicrofinance.DynamicTab.BlurBehind;
import infracom.abcr.sarthamicrofinance.DynamicTab.OnBlurCompleteListener;
import infracom.abcr.sarthamicrofinance.Main;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.Register.FMainActivity;
import infracom.abcr.sarthamicrofinance.domparser.DOMParser;
import infracom.abcr.sarthamicrofinance.domparser.RSSFeed;
import infracom.abcr.sarthamicrofinance.utils.Preferences;


public class SplashActivity extends AppCompatActivity {

    //the default feed
    //public static String default_feed_value = "http://feeds.feedburner.com/xdadevs";

    //the items
    RSSFeed lfflfeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the navbar tint if the preference is enabled
        Preferences.applyNavTint(this, getBaseContext(), R.color.quantum_grey);

        //set LightStatusBar
        Preferences.applyLightIcons(this);

        // Detect if there's a connection issue or not
        ConnectivityManager cM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // If there's a connection problem
        if (cM.getActiveNetworkInfo() == null) {

            // Show alert splash
            setContentView(R.layout.splash_no_internet);
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    // and finish the splash activity
                    SplashActivity.this.finish();

                }
            }, 1000);

        } else {

            //else :P, start the default splash screen and parse the RSSFeed and save the object
            setContentView(R.layout.splash);
            //new AsyncLoadXMLFeed().execute();
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    // and finish the splash activity
//                    SplashActivity.this.finish();

                    startListActivity();
                }
            }, 1000);

        }
    }

    //using intents we send the lfflfeed (the parsed xml to populate the listview)
    // from the async task to listactivity
    private void startListActivity() {

        BlurBehind.getInstance().execute(SplashActivity.this, new OnBlurCompleteListener() {
            @Override
            public void onBlurComplete() {
                Intent intent = new Intent(SplashActivity.this, Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(intent);
                finish();
            }
        });

      //  Bundle bundle = new Bundle();
      //  bundle.putSerializable("feed", lfflfeed);
      //  Intent i = new Intent(SplashActivity.this, MainActivityD.class);
      //  i.putExtras(bundle);
      //  startActivity(i);

    }

    //parse the xml in an async task (background thread)
    private class AsyncLoadXMLFeed extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            DOMParser Do = new DOMParser();
          //  lfflfeed = Do.parseXml(default_feed_value);

            return null;

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
           // startListActivity(lfflfeed);
        }

    }
}
