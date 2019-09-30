package infracom.abcr.sarthamicrofinance.CATEGORIES;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import infracom.abcr.sarthamicrofinance.R;

public class Sartha_Purpose_loan extends AppCompatActivity {

    String tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sartha__purpose_loan);




        final Intent intent = getIntent();

        final String product = intent.getStringExtra("product");
        final String interest = intent.getStringExtra("interest");
        String Se = intent.getStringExtra("upto");


        TextView LoanType1 = (TextView) findViewById(R.id.typ);

        TextView textView79 = (TextView) findViewById(R.id.textView79);


        final EditText othertyp = (EditText) findViewById(R.id.othertyp);


        LoanType1.setText(product);
        textView79.setText(product+" (up to "+Se+"/-)   ROI "+interest+"% ");



        final RadioButton rb12 = (RadioButton)findViewById(R.id.radioButton);
        final RadioButton rb22 = (RadioButton)findViewById(R.id.radioButton2);
        final RadioButton  rb32 = (RadioButton)findViewById(R.id.radioButton3);

        final RadioButton  rb42 = (RadioButton)findViewById(R.id.radioButton4);


        if(product.equals("Sartha Subharambh")||product.equals("Sartha Subharambh Plus")){
            rb12.setText("Individual Business Loan");
            rb32.setText("Group Business Loan");
            rb22.setText("New Business Loan");
           // rb22.setVisibility(View.GONE);
        }

        othertyp.setVisibility(View.GONE);

        rb12.setChecked(true);
        if(rb12.isChecked()){
            //checkTun="Month";
            othertyp.setVisibility(View.GONE);
            othertyp.setText(rb12.getText().toString());
        }

        CompoundButton.OnCheckedChangeListener listener2 = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb12.setChecked(rb12 == buttonView);
                    rb22.setChecked(rb22 == buttonView);
                    rb32.setChecked(rb32 == buttonView);
                    rb42.setChecked(rb42 == buttonView);
                }

                if(rb12.isChecked()){
                    //checkTun="Month";
                    othertyp.setVisibility(View.GONE);
                    othertyp.setText(rb12.getText().toString());
                }
                if(rb22.isChecked()){
                    //checkTun="Fortnight";
                    othertyp.setVisibility(View.GONE);
                    othertyp.setText(rb22.getText().toString());
                }
                if(rb32.isChecked()){
                   // checkTun="Week";
                    othertyp.setVisibility(View.GONE);
                    othertyp.setText(rb32.getText().toString());
                }if(rb42.isChecked()){
                    // checkTun="Week";
                    othertyp.setVisibility(View.VISIBLE);
                    othertyp.setText("");
                }
            }

        };

        rb12.setOnCheckedChangeListener(listener2);
        rb22.setOnCheckedChangeListener(listener2);
        rb32.setOnCheckedChangeListener(listener2);
        rb42.setOnCheckedChangeListener(listener2);




        Button Next=(Button)findViewById(R.id.Next);
        Next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {


               tt =othertyp.getText().toString().trim();
                /*
                if(!rb12.isChecked()||!rb22.isChecked()||!rb32.isChecked()||!rb42.isChecked()){

                    Toast.makeText(
                            getApplicationContext(),"Select Loan Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                */
                if(tt.equals("")){

                    Toast.makeText(
                            getApplicationContext(),"Enter Loan Type", Toast.LENGTH_SHORT).show();
                    return;
                }

                //showBottomSheet();
                Intent intent = new Intent(Sartha_Purpose_loan.this, Personal_Loan_Pass.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("data0", product);
                intent.putExtra("data1", tt);
                intent.putExtra("data2", product);
                intent.putExtra("per", "100");
                intent.putExtra("ProPer", interest);
                startActivity(intent);
            }

        });
    }

}
