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
 * Created by samsung on 12/10/2016.
 */
public class Trash extends Fragment {
    TypedArray profile_pics1;
    String[] statues1;
    String[] contactType1;
    List<RowItem> rowItems1;

    ListView mylistview1;
    String[] member_names1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.trash, container, false);

        mylistview1 = (ListView) v.findViewById(R.id.listViewtotal);
        rowItems1 = new ArrayList<RowItem>();

        member_names1 = getResources().getStringArray(R.array.Member_namesTask);
        profile_pics1 = getResources().obtainTypedArray(R.array.profile_picsTask);
        statues1 = getResources().getStringArray(R.array.Total);
        contactType1 = getResources().getStringArray(R.array.total_note);

        for (int i = 0; i < member_names1.length; i++) {
            RowItem item = new RowItem(member_names1[i],
                    profile_pics1.getNonResourceString(i), statues1[i],
                    contactType1[i]);
            rowItems1.add(item);
        }

        infracom.abcr.sarthamicrofinance.slidingtab.contacts.TaskAdapter adapter1 = new infracom.abcr.sarthamicrofinance.slidingtab.contacts.TaskAdapter(getActivity(), rowItems1);

        mylistview1.setAdapter(adapter1);
        profile_pics1.recycle();
        return v;

     }
}
