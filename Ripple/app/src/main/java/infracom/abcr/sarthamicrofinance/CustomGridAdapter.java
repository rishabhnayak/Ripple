package infracom.abcr.sarthamicrofinance;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;



public class CustomGridAdapter extends BaseAdapter {
	
	private Context context; 
	//private final String[] gridValues;
    private Controller aController;
	
	//Constructor to initialize values
	public CustomGridAdapter(Context context, Controller aController) {
		this.context = context;
		this.aController = aController;
	}
	
	@Override
	public int getCount() {
		
		// Number of times getView method call depends upon gridValues.length
		return aController.getUserDataSize();
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}
	
	
    // Number of times getView method call depends upon gridValues.length
	
	public View getView(int position, View convertView, ViewGroup parent) {

		//LayoutInflator to call external grid_item.xml file
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View gridView;

		if (convertView == null) {

			gridView = new View(context);

			// get layout from grid_item.xml
			gridView = inflater.inflate(R.layout.grid_item, null);

			UserData userdataObj = aController.getUserData(position);
			
			// set value into textview
			
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			TextView textView1 = (TextView) gridView
					.findViewById(R.id.grid_item_label_static);

			ImageView ImageV = (ImageView) gridView.findViewById(R.id.grid_item_image);


			textView.setText(userdataObj.getName());
			textView1.setText(userdataObj.getIMEI());
			Glide.with(context)
					.load(userdataObj.getImage().replace("http","https"))
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					.error(R.drawable.ic_launcher)
					.into(ImageV);

			
		} else {
			gridView = (View) convertView;
		}

		return gridView;
	}
}
