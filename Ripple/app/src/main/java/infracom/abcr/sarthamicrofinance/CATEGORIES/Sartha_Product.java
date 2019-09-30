package infracom.abcr.sarthamicrofinance.CATEGORIES;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import infracom.abcr.sarthamicrofinance.DynamicTab.MainActivityD;
import infracom.abcr.sarthamicrofinance.DynamicTab.Product;
import infracom.abcr.sarthamicrofinance.R;

public class Sartha_Product extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sartha__product_activity);
    }

    public void samriddhi(View view){
        Intent intent = new Intent(Sartha_Product.this, Sartha_Sanchar_Product.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("upto", "10000");
        intent.putExtra("product", "Sartha Samriddhi");
        startActivity(intent);
    }
    public void samriddhiplus(View view){
        Intent intent = new Intent(Sartha_Product.this, Sartha_Sanchar_Product.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("upto", "10000");
        intent.putExtra("product", "Sartha Samriddhi Plus");
        startActivity(intent);
    }
    public void sanchar(View view){
        Intent intent = new Intent(Sartha_Product.this, Sartha_Sanchar_Product.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("upto", "10000");
        intent.putExtra("product", "Sartha Sanchar");
        startActivity(intent);
    }
    public void sancharplus(View view){
        Intent intent = new Intent(Sartha_Product.this, Sartha_Sanchar_Product.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("upto", "10000");
        intent.putExtra("product", "Sartha Sanchar Plus");
        startActivity(intent);
    }

    public void other(View view){
        Intent intent = new Intent(Sartha_Product.this, MainActivityD.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
       startActivity(intent);
    }
}
