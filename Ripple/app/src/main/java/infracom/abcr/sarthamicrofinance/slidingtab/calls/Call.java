package infracom.abcr.sarthamicrofinance.slidingtab.calls;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.activity.MainActivity3;
import infracom.abcr.sarthamicrofinance.slidetablistview.MainActivity2;


public class Call extends Fragment{
Button btn;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.call, container, false);

		btn = (Button) v.findViewById(R.id.button);
		//btn.setOnClickListener(this);

		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {

				// Start NewActivity.class
				Intent myIntent = new Intent(getActivity(),MainActivity3.class);
				startActivity(myIntent);
			}
		});

		return v;
	}

}