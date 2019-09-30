package infracom.abcr.sarthamicrofinance.DynamicTab;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import infracom.abcr.sarthamicrofinance.Controller;
import infracom.abcr.sarthamicrofinance.Dpost.ListViewAdapter;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.slidingtab.contacts.CustomAdapterTask;
import infracom.abcr.sarthamicrofinance.slidingtab.contacts.RowItemTask;

/**
 * Created by samsung on 26/10/2016.
 */
public class FragmentChildTask extends Fragment {
    String childname;
    HashMap<String, String> childname1;
    TextView textViewChildName,member_namesS;
    EditText editText;
    ArrayList<HashMap<String, String>> arraylist;
    ArrayList<String> mStrings, pop, pus, avt;

    static String serverURL = "https://sarthamicrofinance.com/admin/gcm_Device_to/gcm_server_files/json.php";


    String[] member_names,appliance,decore,books,men,women,kids, biks = null;
    TypedArray profile_pics, applianceP,decoreP,booksP,menP,womenP,kidsP, biksP = null;
    String[] statues=null;
    String[] contactType=null;
    List<RowItemTask> rowItems=null;
    ListView mylistview;
    ListViewAdapter adapter;
    static Controller aController = null;


    static ArrayList<String> listsubR = new ArrayList<String>();

    ListView listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_childd, container, false);

        // HashMap<String, String> map = new HashMap<String, String>();
        //arraylist = new ArrayList<HashMap<String, String>>();

        Bundle bundle = getArguments();
        childname = bundle.getString("data");
        mStrings = getArguments().getStringArrayList("foo");


        pop = getArguments().getStringArrayList("sub1");
        pus = getArguments().getStringArrayList("sub2");

        avt = getArguments().getStringArrayList("sub3");

        //childname1 = (HashMap<String, String>) bundle.getSerializable("data");

        getIDs(view);
        setEvents();
        return view;
    }

    private void getIDs(View view) {
        textViewChildName = (TextView) view.findViewById(R.id.textViewChild);
        textViewChildName.setText(childname);

        mylistview = (ListView) view.findViewById(R.id.list12);


        String teams[]={"https://image.flaticon.com/icons/png/128/227/227931.png",
                "https://image.flaticon.com/icons/png/128/227/227931.png",
                "https://image.flaticon.com/icons/png/128/227/227931.png",
                "https://image.flaticon.com/icons/png/128/227/227931.png",
                "https://image.flaticon.com/icons/png/128/227/227931.png",
                "https://image.flaticon.com/icons/png/128/227/227931.png",
                "https://image.flaticon.com/icons/png/128/227/227931.png"};
        // editText = (EditText) view.findViewById(R.id.editText);
        //editText.setText("");
        // Set the JSON Objects into the array

        //listview = (ListView) view.findViewById(R.id.listViewPost);
        // map = childname1;
        //arraylist.add(childname1);
        // Locate the listview in listview_main.xml
        // Pass the results into ListViewAdapter.java
        //adapter = new ListViewAdapter(arraylist);
        // Set the adapter to the ListView
        //listview.setAdapter(adapter);


        String[] stockArr = new String[mStrings.size()];
        stockArr = mStrings.toArray(stockArr);

        String[] stockArr1 = new String[pop.size()];
        stockArr1 = pop.toArray(stockArr1);

        String[] stockArr2 = new String[pus.size()];
        stockArr2 = pus.toArray(stockArr2);

        String[] stockArravt = new String[avt.size()];
        stockArravt = avt.toArray(stockArravt);


        rowItems = new ArrayList<>();
        statues = getResources().getStringArray(R.array.statues);
        contactType = getResources().getStringArray(R.array.contactType);

        member_names = getResources().getStringArray(R.array.Member_names);
        profile_pics = getResources().obtainTypedArray(R.array.profile_pics);

        for (int i = 0; i < stockArr.length; i++) {
            final int finalI = i;

            RowItemTask item = new RowItemTask(stockArr[i],stockArravt[i], stockArr1[i],
                    stockArr2[i]);
            rowItems.add(item);
        }

        CustomAdapterTask adapter = new CustomAdapterTask(getActivity(), rowItems);
        mylistview.setAdapter(adapter);
        profile_pics.recycle();





    }

    private void setEvents() {

    }

}

