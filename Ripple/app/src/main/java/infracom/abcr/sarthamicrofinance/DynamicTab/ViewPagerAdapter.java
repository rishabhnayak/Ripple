package infracom.abcr.sarthamicrofinance.DynamicTab;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import infracom.abcr.sarthamicrofinance.R;

/**
 * Created by DAT on 8/16/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private final ArrayList<String> mFragmentTitleList = new ArrayList<>();
    Context context;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView tabItemName;
    public ViewPagerAdapter(FragmentManager manager, Context context, ViewPager viewPager,
        TabLayout tabLayout) {
        super(manager);
        this.context = context;
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public void removeFrag(int position) {
        removeTab(position);
        Fragment fragment = mFragmentList.get(position);
        mFragmentList.remove(fragment);
        mFragmentTitleList.remove(position);
        destroyFragmentView(viewPager, position, fragment);
        notifyDataSetChanged();
    }

    public View getTabView(final int position) {


       // ((MainActivityD)context).GetCat(position);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab_itemd, null);
        tabItemName = (TextView) view.findViewById(R.id.textViewTabItemName);
        CircleImageView tabItemAvatar =
            (CircleImageView) view.findViewById(R.id.imageViewTabItemAvatar);
        /*
        ImageButton remove = (ImageButton) view.findViewById(R.id.imageButtonRemove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Remove", "Remove");
                removeFrag(position);
            }
        });
*/
        tabItemName.setText(mFragmentTitleList.get(position));
        tabItemName.setTextColor(context.getResources().getColor(R.color.quantum_grey));

        switch (mFragmentTitleList.get(position)) {
            case "Electronics":
                tabItemAvatar.setImageResource(R.drawable.ic_electronics_devices_other_black_24dp);
                break;
            case "Home Decore":
                tabItemAvatar.setImageResource(R.drawable.ic_home_shopping_basket_black_24dp);
                break;
            case "Bikes":
                tabItemAvatar.setImageResource(R.drawable.ic_directions_car_black_24dp);
                break;
            case "Appliances":
                tabItemAvatar.setImageResource(R.drawable.ic_applien_local_laundry_service_black_24dp);
                break;
            case "Other":
                tabItemAvatar.setImageResource(R.drawable.ic_other_shopping_cart_black_24dp);
                break;
            case "Mobile":
                tabItemAvatar.setImageResource(R.drawable.ic_phone_iphone_black_24dp);
                break;
            case "Computer":
                tabItemAvatar.setImageResource(R.drawable.ic_desktop_windows_black_24dp);
                break;
            case "Laptop":
                tabItemAvatar.setImageResource(R.drawable.ic_laptop_mac_black_24dp);
                break;
            case "Others":
                tabItemAvatar.setImageResource(R.drawable.ic_other_shopping_cart_black_24dp);
                break;
            case "PENDING":
                tabItemAvatar.setImageResource(R.drawable.ic_pending_timelapse_black_24dp_2);
                break;
            case "CLEAR":
                tabItemAvatar.setImageResource(R.drawable.ic_clear_radio_button_checked_black_24dp_2);
                break;
            case "DONE":
                tabItemAvatar.setImageResource(R.drawable.ic_done_assignment_turned_in_black_24dp_2);
                break;
            case "TOTAL":
                tabItemAvatar.setImageResource(R.drawable.ic_total_insert_chart_black_24dp_2);
                break;
            case "VERIFIED":
                tabItemAvatar.setImageResource(R.drawable.ic_done_assignment_turned_in_black_24dp_2);
                break;
            case "NOT VERIFIED":
                tabItemAvatar.setImageResource(R.drawable.ic_assignment_late_black_24dp_2);
                break;
            default:
                tabItemAvatar.setImageResource(R.drawable.ic_other_shopping_cart_black_24dp);
                break;
        }

             //   tabItemAvatar.setImageResource(R.drawable.smartphone);


        return view;
    }

    public void destroyFragmentView(final ViewGroup container, int position, Object object) {
        FragmentManager manager = ((Fragment) object).getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();

        //((MainActivityD)context).GetCat(position);

        //trans.remove((Fragment) object);
        //trans.commit();
    }

    public void removeTab(int position) {
        if (tabLayout.getChildCount() > 0) {
            tabLayout.removeTabAt(position);
        }
    }

    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {

                // TODO Auto-generated method stub

               // String Acteg = position;
                //Toast.makeText(g, ""+Acteg, Toast.LENGTH_SHORT).show();
                //MainActivityD mainD = null;
                //mainD.privecyTerms();

//if(Product.toString().trim()=="Mobile"){}
                //(MainActivityD).mobile();
                //FragmentChild.doSubProduct(Product);
               // ((MainActivityD)context).GetCat(position);

        return mFragmentTitleList.get(position);
    }


}
