package infracom.abcr.sarthamicrofinance.CATEGORIES;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import infracom.abcr.sarthamicrofinance.AssignEMP.Pay_Recovery_Dealer;
import infracom.abcr.sarthamicrofinance.Profile.ManageTeam;
import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.Recovery_Allot;

public class LestOFEMP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emp_lest_ofempm);


    }

    public void manager(View view){

        Intent intent = new Intent(LestOFEMP.this, ManageTeam.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("depart", "Manager");
        startActivity(intent);

    }

    public void sales(View view){

        Intent intent = new Intent(LestOFEMP.this, ManageTeam.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("depart", "Sales Person");
        startActivity(intent);

    }
    public void dealer(View view){

        Intent intent = new Intent(LestOFEMP.this, ManageTeam.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("depart", "Dealer");
        startActivity(intent);

    }
}
