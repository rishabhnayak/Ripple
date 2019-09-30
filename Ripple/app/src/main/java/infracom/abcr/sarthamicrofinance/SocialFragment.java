package infracom.abcr.sarthamicrofinance;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import infracom.abcr.sarthamicrofinance.slidingtab.contacts.RowItem;

/**
 * Created by Ratan on 7/29/2015.
 */
public class SocialFragment extends Fragment {

    TypedArray profile_pics;
    String[] statues;
    String[] contactType;
    List<RowItem> rowItems;

    ListView mylistview;
    String[] member_names;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.social_layout, container, false);

        mylistview = (ListView) v.findViewById(R.id.listViewdone);


        // final RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rl);
      /*  Button btn = (Button) v.findViewById(R.id.btn);
        final TextView tv = (TextView) v.findViewById(R.id.tv);

        member_names = getResources().getStringArray(R.array.sports_array);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // String array for alert dialog multi choice items
                String[] colors = new String[]{
                        "Red",
                        "Green",
                        "Blue",
                        "Purple",
                        "Olive"
                };

                // Boolean array for initial selected items
                //final boolean[] checkedColors = new boolean[0];

                final boolean[] checkedColors = new boolean[member_names.length];
                Arrays.fill(checkedColors, false);

                // Convert the color array to list
                final List<String> colorsList = Arrays.asList(member_names);
                /*for (int i = 0; i < member_names.length; i++) {
                    final int finalI = i;
                    checkedColors[i]=false;

                }
                // Set multiple choice items for alert dialog

                    AlertDialog.Builder setMultiChoiceItems(CharSequence[] items, boolean[]
                    checkedItems, DialogInterface.OnMultiChoiceClickListener listener)
                        Set a list of items to be displayed in the dialog as the content,
                        you will be notified of the selected item via the supplied listener.
                 */
                /*
                    DialogInterface.OnMultiChoiceClickListener
                    public abstract void onClick (DialogInterface dialog, int which, boolean isChecked)

                        This method will be invoked when an item in the dialog is clicked.

                        Parameters
                        dialog The dialog where the selection was made.
                        which The position of the item in the list that was clicked.
                        isChecked True if the click checked the item, else false.

                builder.setMultiChoiceItems(member_names, checkedColors, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        // Update the current focused item's checked status
                        checkedColors[which] = isChecked;

                        // Get the current focused item
                        String currentItem = colorsList.get(which);

                        // Notify the current action
                        Toast.makeText(getContext(),
                                currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                    }
                });

                // Specify the dialog is not cancelable
                builder.setCancelable(false);

                // Set a title for alert dialog
                builder.setTitle("Your preferred colors?");
                builder.setIcon(R.drawable.ic_launcher);

                // Set the positive/yes button click listener
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click positive button
                        tv.setText("Your preferred colors..... \n");
                        for (int i = 0; i<checkedColors.length; i++){
                            boolean checked = checkedColors[i];
                            if (checked) {
                                tv.setText(tv.getText() + colorsList.get(i) + "\n");
                            }
                        }
                    }
                });

                // Set the negative/no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the negative button
                    }
                });

                // Set the neutral/cancel button click listener
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when click the neutral button
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });

       */

        rowItems = new ArrayList<RowItem>();

        member_names = getResources().getStringArray(R.array.Member_namesTask);
        profile_pics = getResources().obtainTypedArray(R.array.profile_picsTask);
        statues = getResources().getStringArray(R.array.done);
        contactType = getResources().getStringArray(R.array.done_note);

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
