package infracom.abcr.sarthamicrofinance.GroupLoan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import infracom.abcr.sarthamicrofinance.DynamicTab.ViewPagerAdapter;
import infracom.abcr.sarthamicrofinance.R;

/**
 * Created by Narayan Singh on 22/04/2017.
 */

public class GroupFragmentApprove extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parentd, container, false);
        getIDs(view);
        setEvents();
        return view;
    }

    private void getIDs(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.my_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.my_tab_layout);
        adapter = new ViewPagerAdapter(getFragmentManager(), getActivity(), viewPager, tabLayout);
        viewPager.setAdapter(adapter);
    }

    int selectedTabPosition;

    private void setEvents() {

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                viewPager.setCurrentItem(tab.getPosition());
                selectedTabPosition = viewPager.getCurrentItem();
                //   Log.d("Selected", "Selected " + tab.getPosition());

                ((Group_Approve_List)getContext()).GetCat((String) tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                //     Log.d("Unselected", "Unselected " + tab.getPosition());
            }
        });
    }

    public void addPage(String pagename, String pagename1, ArrayList<String> listsub, ArrayList<String> listsub1, ArrayList<String> listsub2, ArrayList<String> listsub3,ArrayList<String> listsub4) {
        Bundle bundle = new Bundle();
        bundle.putString("data", pagename);
        bundle.putString("data1", pagename1);
        bundle.putStringArrayList("foo",listsub);


        bundle.putStringArrayList("sub1",listsub1);

        bundle.putStringArrayList("sub2",listsub2);

        bundle.putStringArrayList("sub4",listsub4);

        bundle.putStringArrayList("icon",listsub3);
        // bundle.putStringArray("list", listsub);
        //bundle.putSerializable("data1", map);
        ChaildFragmentGroupApprove fragmentChild = new ChaildFragmentGroupApprove();
        fragmentChild.setArguments(bundle);
        adapter.addFrag(fragmentChild, pagename);
        adapter.notifyDataSetChanged();
        if (adapter.getCount() > 0) tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(adapter.getCount() - 1);
        setupTabLayout();
    }

    public void setupTabLayout() {
        selectedTabPosition = viewPager.getCurrentItem();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(adapter.getTabView(i));
        }
    }
}