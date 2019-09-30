package infracom.abcr.sarthamicrofinance.slidingtab.contacts;
import java.util.ArrayList;
import java.util.List;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import infracom.abcr.sarthamicrofinance.R;

public class Contact extends ListFragment {

	String[] member_names;
	TypedArray profile_pics;
	String[] statues;
	String[] contactType;
	List<RowItem> rowItems;
	ListView mylistview;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.contact, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		rowItems = new ArrayList<RowItem>();

		member_names = getResources().getStringArray(R.array.Member_names);
		profile_pics = getResources().obtainTypedArray(R.array.profile_pics);
		statues = getResources().getStringArray(R.array.statues);
		contactType = getResources().getStringArray(R.array.contactType);

		for (int i = 0; i < member_names.length; i++) {
			RowItem item = new RowItem(member_names[i],
			profile_pics.getNonResourceString(i), statues[i],
					contactType[i]);
			rowItems.add(item);
		}

		CustomAdapter adapter = new CustomAdapter(getActivity(), rowItems);
		setListAdapter(adapter);
		profile_pics.recycle();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
}
