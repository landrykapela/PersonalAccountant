package tz.co.neelansoft.personalaccountant;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tz.co.neelansoft.personalaccountant.library.Config;
import tz.co.neelansoft.personalaccountant.library.PADatabase;

public class AccountActivity extends AppCompatActivity {

    NumberFormat format = Config.CurrencyFormat();



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);
        final TextView mTextBalance = findViewById(R.id.tv_balance);
        final TextView mTextIncome = findViewById(R.id.tv_income);
        final TextView mTextExpense = findViewById(R.id.tv_expense);
        TextView mTextDate = findViewById(R.id.tv_date);
        final TextView mTextDifference = findViewById(R.id.tv_diff);


        mTextDate.setText(Config.LongAppDateFormat.format(new Date()));


        PADatabase db = PADatabase.getDatabaseInstance(this);

        LiveData<List<Record>> liveData = db.dao().getAllRecords();
        liveData.observe(this, new Observer<List<Record>>() {
            Double income = 0.0;
            Double expense = 0.0;
            @Override
            public void onChanged(@Nullable List<Record> records) {
                for(int i=0; i<records.size();i++){
                    if(records.get(i).getRecordType() == 0){
                        income += records.get(i).getAmount();
                    }
                    else{
                        expense += records.get(i).getAmount();
                    }
                }
                Double balance = income - expense;
                mTextDifference.setText(String.valueOf(balance));
                mTextBalance.setText(String.valueOf(format.format(balance)));
                mTextIncome.setText(String.valueOf(income));
                mTextExpense.setText(String.valueOf(expense));
                if(balance < 0){
                    mTextBalance.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            }

        });
    }
}
