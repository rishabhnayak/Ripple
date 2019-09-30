package infracom.abcr.sarthamicrofinance;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import infracom.abcr.sarthamicrofinance.slidingtab.contacts.*;
import infracom.abcr.sarthamicrofinance.utils.CustomAlertAdapter;

/**
 * Created by Ratan on 7/29/2015.
 */
public class PrimaryFragment extends Fragment {

    String[] member_names;
    TypedArray profile_pics;
    String[] statues;
    String[] contactType;
    List<RowItem> rowItems;

    private Button btn_listviewdialog = null;
    private EditText txt_item = null;
    private String TitleName[] = {"Sunil Gupta", "Ram Chnadra", " Abhishek Tripathi", "Amit Verma", "Sandeep Pal", "Awadhesh Diwakar", "Shishir Verma", "Ravi Vimal", "Prabhakr Singh", "Manish Srivastva", "Jitendra Singh", "Surendra Pal"};
    private ArrayList<String> array_sort;
    int textlength = 0;
    private AlertDialog myalertDialog = null;
    List<CharSequence> list = new ArrayList<CharSequence>();

    ListView mylistview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.primary_layout, container, false);

        mylistview = (ListView) v.findViewById(R.id.listViewpending);
   /*
        for (int i=0;i<20;i++){
            list.add("test " + i);  // Add the item in the list
        }

        View openDialog = (View) v.findViewById(R.id.openDialog);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                // Intialize  readable sequence of char values
                final CharSequence[] dialogList=  list.toArray(new CharSequence[list.size()]);
                final AlertDialog.Builder builderDialog = new AlertDialog.Builder(getContext());
                builderDialog.setTitle("Select Item");
                int count = dialogList.length;
                boolean[] is_checked = new boolean[count];

                // Creating multiple selection by using setMutliChoiceItem method
                builderDialog.setMultiChoiceItems(dialogList, is_checked,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton, boolean isChecked) {
                            }
                        });

                builderDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ListView list = ((AlertDialog) dialog).getListView();
                                // make selected item in the comma seprated string
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < list.getCount(); i++) {
                                    boolean checked = list.isItemChecked(i);

                                    if (checked) {
                                        if (stringBuilder.length() > 0) stringBuilder.append(",");
                                        stringBuilder.append(list.getItemAtPosition(i));


                                    }
                                }

                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.

                                if (stringBuilder.toString().trim().equals("")) {

                                    ((TextView) v.findViewById(R.id.text)).setText("Click here to open Dialog");
                                    stringBuilder.setLength(0);

                                } else {

                                    ((TextView) v.findViewById(R.id.text)).setText(stringBuilder);
                                }
                            }
                        });

                builderDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((TextView) v.findViewById(R.id.text)).setText("Click here to open Dialog");
                            }
                        });
                AlertDialog alert = builderDialog.create();
                alert.show();

            }
        });

*/
        rowItems = new ArrayList<RowItem>();

        member_names = getResources().getStringArray(R.array.Member_namesTask);
        profile_pics = getResources().obtainTypedArray(R.array.profile_picsTask);
        statues = getResources().getStringArray(R.array.pending);
        contactType = getResources().getStringArray(R.array.pending_note);

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