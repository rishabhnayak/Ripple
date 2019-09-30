package infracom.abcr.sarthamicrofinance.CATEGORIES;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import infracom.abcr.sarthamicrofinance.R;

public class Sartha_Personal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sartha__personal_activity);
    }

    public void sahayak(View view){
        Intent intent = new Intent(Sartha_Personal.this, Sartha_Purpose_loan.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("upto", "10000");
        intent.putExtra("interest", "3.0");
        intent.putExtra("product", "Sartha Sahayak");
        startActivity(intent);
    }
    public void sahayakplus(View view){
        Intent intent = new Intent(Sartha_Personal.this, Sartha_Purpose_loan.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("upto", "10000");
        intent.putExtra("interest", "3.0");
        intent.putExtra("product", "Sartha Sahayak Plus");
        startActivity(intent);
    }
    public void subharambh(View view){
        Intent intent = new Intent(Sartha_Personal.this, Sartha_Purpose_loan.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("upto", "25000");
        intent.putExtra("interest", "2.5");
        intent.putExtra("product", "Sartha Subharambh");
        startActivity(intent);
    }
    public void subharambhplus(View view){
        Intent intent = new Intent(Sartha_Personal.this, Sartha_Purpose_loan.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("upto", "25000");
        intent.putExtra("interest", "2.5");
        intent.putExtra("product", "Sartha Subharambh Plus");
        startActivity(intent);
    }
}
