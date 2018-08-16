package tz.co.neelansoft.personalaccountant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionDetailsActivity extends AppCompatActivity {

    private TextView mTextAmount;
    private TextView mTextDescription;
    private TextView mTextTransactionDate;
    private TextView mTextTransactionType;
    private LinearLayout linearLayout;
    Record my_record;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_details);

        mTextAmount = findViewById(R.id.tv_amount);
        mTextDescription = findViewById(R.id.tv_description);
        mTextTransactionDate = findViewById(R.id.tv_date);
        mTextTransactionType = findViewById(R.id.tv_label_type);
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
        java.util.Currency tsh = java.util.Currency.getInstance("TSH");
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        format.setCurrency(tsh);
        mTextAmount.setText(String.valueOf(format.format(record.getAmount())));

        mTextTransactionDate.setText(record.getUpdatedAt());
        mTextDescription.setText(record.getDescription());
        if(record.getRecordType() == 0){
            mTextTransactionType.setText(R.string.income);
            linearLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
            mTextAmount.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        else{
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.toolbar));
            mTextTransactionType.setText(R.string.expense);
            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            mTextAmount.setTextColor(getResources().getColor(android.R.color.white));
        }
    }
}
