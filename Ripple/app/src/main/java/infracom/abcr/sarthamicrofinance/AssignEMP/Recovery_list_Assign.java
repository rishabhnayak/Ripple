package infracom.abcr.sarthamicrofinance.AssignEMP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.Recovery_Allot;

public class Recovery_list_Assign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recovery_list_activity_assign);
    }

    public void assign(View view){

        Intent intent = new Intent(Recovery_list_Assign.this, Recovery_Allot.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("depart", "Sales Person");
        startActivity(intent);

    }

    public void assignpending(View view){

        Intent intent = new Intent(Recovery_list_Assign.this, Pay_Recovery_Dealer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("depart", "Sales Person");
        startActivity(intent);

    }
}
