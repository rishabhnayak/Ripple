package infracom.abcr.sarthamicrofinance.reports.Business_Report;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import infracom.abcr.sarthamicrofinance.R;
import infracom.abcr.sarthamicrofinance.reports.business_reports;

public class Business_report_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_report_list_activity);
    }

    public void sahayak(View view){
        Intent intent = new Intent(Business_report_list.this, business_reports.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("case", "List Of Login Cases");
        intent.putExtra("type", "1");
        intent.putExtra("product", "Sartha Sahayak");
        startActivity(intent);
    }
    public void sahayakplus(View view){
        Intent intent = new Intent(Business_report_list.this, business_reports.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("case", "List Of Approved Cases");
        intent.putExtra("type", "2");
        intent.putExtra("product", "Sartha Sahayak Plus");
        startActivity(intent);
    }
    public void subharambh(View view){
        Intent intent = new Intent(Business_report_list.this, business_reports.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("case", "List Of Declined Cases");
        intent.putExtra("type", "3");
        intent.putExtra("product", "Sartha Subharambh");
        startActivity(intent);
    }
    public void subharambhplus(View view){
        Intent intent = new Intent(Business_report_list.this, business_reports.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("case", "List of Disbursed Cases ");
        intent.putExtra("type", "4");
        intent.putExtra("product", "Sartha Subharambh Plus");
        startActivity(intent);
    }
}
