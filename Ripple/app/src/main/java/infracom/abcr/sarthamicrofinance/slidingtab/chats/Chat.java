package infracom.abcr.sarthamicrofinance.slidingtab.chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import infracom.abcr.sarthamicrofinance.MainActivity;
import infracom.abcr.sarthamicrofinance.R;

/**
 * Created by hp1 on 21-01-2015.
 */
public class Chat extends Fragment {
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.chat,container,false);


        btn = (Button) v.findViewById(R.id.button2);
        //btn.setOnClickListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(getActivity(),MainActivity.class);
                startActivity(myIntent);
            }
        });

        return v;
    }
}