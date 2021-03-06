package tz.co.neelansoft.personalaccountant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import tz.co.neelansoft.personalaccountant.library.Config;

public class TransactionDetailsActivity extends AppCompatActivity {

    private TextView mTextAmount;
    private TextView mTextDescription;
    private TextView mTextTransactionDate;
    private TextView mTextTransactionType;
    private TextView mTextPayerPayee;
    private TextView mTextLabelPayerPayee;
    private LinearLayout linearLayout;
    Record my_record;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_details);

        mTextAmount = findViewById(R.id.tv_amount);
        mTextDescription = findViewById(R.id.tv_description);
        mTextTransactionDate = findViewById(R.id.tv_date);
        mTextTransactionType = findViewById(R.id.tv_type);
        mTextPayerPayee  = findViewById(R.id.tv_payer);
        mTextLabelPayerPayee  = findViewById(R.id.tv_label_payer);
        linearLayout = findViewById(R.id.linearLayout);


        if(savedInstanceState != null){
            setData((Record)savedInstanceState.getParcelable("record"));
        }
        else {
            Intent record_intent = getIntent();
            my_record = record_intent.getExtras().getParcelable("record");
            if (my_record == null) {
                Toast.makeText(this, "No record found", Toast.LENGTH_SHORT).show();

            } else {
                setData(my_record);
            }
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable("record",my_record);
    }
    private void setData(Record record){
        NumberFormat format = Config.CurrencyFormat();
        mTextAmount.setText(String.valueOf(format.format(record.getAmount())));

        mTextTransactionDate.setText(record.getUpdatedAt());
        mTextDescription.setText(record.getDescription());
        mTextPayerPayee.setText(record.getPayer());
        if(record.getRecordType() == 0){
            mTextTransactionType.setText(R.string.income);
            linearLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
            mTextAmount.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            mTextLabelPayerPayee.setText(getResources().getString(R.string.pay_by));
        }
        else{
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar));
            mTextTransactionType.setText(R.string.expense);
            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            mTextAmount.setTextColor(getResources().getColor(android.R.color.white));
            mTextLabelPayerPayee.setText(getResources().getString(R.string.pay_to));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        switch(itemId){
            case R.id.action_edit:
                editRecord();
                return true;
            default:
                editRecord();
                return true;
        }
    }
    private void editRecord(){
        Intent edit_intent = new Intent(getApplicationContext(),AddNewRecordActivity.class);
        edit_intent.putExtra("record",my_record);
        edit_intent.putExtra("tag","edit");
        startActivity(edit_intent);
        finish();
    }
}
