package tz.co.neelansoft.personalaccountant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddNewRecordActivity extends AppCompatActivity {

    private EditText mTextAmount;
    private EditText mTextDescription;
    private EditText mTextPayer;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioIncome;
    private RadioButton mRadioExpense;
    private int recordType;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_record);

        mTextAmount      = findViewById(R.id.etAmount);
        mTextDescription = findViewById(R.id.etDescription);
        mTextPayer       = findViewById(R.id.et_payer);

        mRadioGroup      = findViewById(R.id.radioGroup);
        mRadioIncome     = findViewById(R.id.rb_income);
        mRadioExpense    = findViewById(R.id.rb_expense);

        mRadioGroup.check(R.id.rb_expense);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_income:
                        mTextPayer.setHint(R.string.pay_by);
                        recordType = 0;
                        break;
                    case R.id.rb_expense:
                        mTextPayer.setHint(R.string.pay_to);
                        recordType = 1;
                        break;
                    default:
                        mTextPayer.setHint(R.string.pay_to);
                        recordType = 1;
                        break;
                }
            }
        });

        Record record = new Record(Double.parseDouble(mTextAmount.getText().toString()),mTextDescription.getText().toString(),recordType);

    }

    private void saveRecord(Record rec){

    }
}
