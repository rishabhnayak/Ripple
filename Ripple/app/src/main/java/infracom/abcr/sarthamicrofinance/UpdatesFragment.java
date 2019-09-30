package infracom.abcr.sarthamicrofinance;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import infracom.abcr.sarthamicrofinance.slidingtab.contacts.RowItem;

/**
 * Created by Ratan on 7/29/2015.
 */
public class UpdatesFragment extends Fragment {

    TypedArray profile_pics;
    String[] statues;
    String[] contactType;
    List<RowItem> rowItems;

    ListView mylistview;
    String[] member_names;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.updates_layout, container, false);

        mylistview = (ListView) v.findViewById(R.id.listViewclear);
        rowItems = new ArrayList<RowItem>();

        member_names = getResources().getStringArray(R.array.Member_namesTask);
        profile_pics = getResources().obtainTypedArray(R.array.profile_picsTask);
        statues = getResources().getStringArray(R.array.Clear);
        contactType = getResources().getStringArray(R.array.Clear_note);

        for (int i = 0; i < member_names.length; i++) {
            RowItem item = new RowItem(member_names[i],
                    profile_pics.getNonResourceString(i), statues[i],
                    contactType[i]);
            rowItems.add(item);
        }

        infracom.abcr.sarthamicrofinance.slidingtab.contacts.TaskAdapter adapter = new infracom.abcr.sarthamicrofinance.slidingtab.contacts.TaskAdapter(getActivity(), rowItems);

        mylistview.setAdapter(adapter);
        profile_pics.recycle();
        return v;
    }
}
