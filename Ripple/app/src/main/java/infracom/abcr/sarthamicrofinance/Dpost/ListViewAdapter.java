package infracom.abcr.sarthamicrofinance.Dpost;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import infracom.abcr.sarthamicrofinance.DynamicTab.MainActivityD;
import infracom.abcr.sarthamicrofinance.R;


public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ListViewAdapter(Context context,
						   ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView rank;
		TextView country;
		TextView population;
		ImageView flag;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.list_card_view, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		rank = (TextView) itemView.findViewById(R.id.rank);
		country = (TextView) itemView.findViewById(R.id.country);
		population = (TextView) itemView.findViewById(R.id.population);

		// Locate the ImageView in listview_item.xml
		flag = (ImageView) itemView.findViewById(R.id.flag);

		// Capture position and set results to the TextViews
	//	rank.setText(resultp.get(Dpost.A_title));
		//country.setText(resultp.get(Dpost.A_details));
	//	population.setText(resultp.get(Dpost.A_date));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
	//	imageLoader.DisplayImage(resultp.get(Dpost.image_url), flag);
		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, SingleItemView.class);
		///		// Pass all data rank
		//		intent.putExtra("A_id", resultp.get(Dpost.A_id));
		//		intent.putExtra("rank", resultp.get(Dpost.A_title));
				// Pass all data country
		///		intent.putExtra("country", resultp.get(Dpost.A_details));
				// Pass all data population
		//		intent.putExtra("population",resultp.get(Dpost.A_date));
				// Pass all data flag
		//		intent.putExtra("flag", resultp.get(Dpost.image_url));
				// Start SingleItemView Class
				context.startActivity(intent);

			}
		});
		return itemView;
	}
}
