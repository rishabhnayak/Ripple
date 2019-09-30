package infracom.abcr.sarthamicrofinance.GroupLoan;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.slidingtab.contacts.RowItem;

/**
 * Created by Narayan Singh on 22/04/2017.
 */

public class ChaildFragmentGroupApprove extends Fragment {
    String childname, title;
    TextView textViewChildName,member_namesS;
    ArrayList<String> mStrings, pop, pus, icon, sts;

    static String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/TAB_File/NewJson/json.php";


    String[] member_names = null;
    TypedArray profile_pics = null;
    String[] statues=null;
    String[] contactType=null;

    String[] appsts =null;

    List<RowItemApp> rowItems=null;
    ListView mylistview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_childd, container, false);

        // HashMap<String, String> map = new HashMap<String, String>();
        //arraylist = new ArrayList<HashMap<String, String>>();

        Bundle bundle = getArguments();
        childname = bundle.getString("data");
        title = bundle.getString("data1");
        mStrings = getArguments().getStringArrayList("foo");


        pop = getArguments().getStringArrayList("sub1");
        pus = getArguments().getStringArrayList("sub2");

        sts = getArguments().getStringArrayList("sub4");

        icon = getArguments().getStringArrayList("icon");

        //childname1 = (HashMap<String, String>) bundle.getSerializable("data");

        getIDs(view);
        setEvents();
        return view;
    }

    private void getIDs(View view) {
        textViewChildName = (TextView) view.findViewById(R.id.textViewChild);
        textViewChildName.setText("Group ID: "+childname+" and Title is "+title);

        mylistview = (ListView) view.findViewById(R.id.list12);
        member_namesS = (TextView) view.findViewById(R.id.member_name);


        String[] stockArr = new String[mStrings.size()];
        stockArr = mStrings.toArray(stockArr);

        String[] stockArr1 = new String[pop.size()];
        stockArr1 = pop.toArray(stockArr1);

        String[] stockArr2 = new String[pus.size()];
        stockArr2 = pus.toArray(stockArr2);

        String[] stockArr4 = new String[sts.size()];
        stockArr4 = sts.toArray(stockArr4);

        String[] stockArravt = new String[icon.size()];
        stockArravt = icon.toArray(stockArravt);



        rowItems = new ArrayList<RowItemApp>();
        statues = getResources().getStringArray(R.array.statues);
        contactType = getResources().getStringArray(R.array.contactType);

        member_names = getResources().getStringArray(R.array.Member_names);


        appsts = getResources().getStringArray(R.array.appsts);

        profile_pics = getResources().obtainTypedArray(R.array.profile_pics);

        for (int i = 0; i < stockArr.length; i++) {
            final int finalI = i;

            RowItemApp item = new RowItemApp(stockArr[i], stockArravt[i], stockArr1[i],
                    stockArr2[i], stockArr4[i]);
            rowItems.add(item);
        }

        GroupAdapterApprove adapter = new GroupAdapterApprove(getActivity(), rowItems);
        mylistview.setAdapter(adapter);
        profile_pics.recycle();





    }

    private void setEvents() {

    }

}