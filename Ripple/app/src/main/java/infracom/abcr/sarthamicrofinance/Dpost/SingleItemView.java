package infracom.abcr.sarthamicrofinance.Dpost;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import infracom.abcr.sarthamicrofinance.R;

public class SingleItemView extends Activity {
	// Declare Variables
	String rank, my_password, payUidS, payUstatusS, payamount;
	String country;String population;
	String flag, A_id;
	String position;
	ImageLoader imageLoader = new ImageLoader(this);


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemview);

		Intent i = getIntent();
		// Get the result of rank
		A_id = i.getStringExtra("A_id");

		rank = i.getStringExtra("rank");
		// Get the result of country
		country = i.getStringExtra("country");
		// Get the result of population
		population = i.getStringExtra("population");
		// Get the result of flag
		flag = i.getStringExtra("flag");



	}


}