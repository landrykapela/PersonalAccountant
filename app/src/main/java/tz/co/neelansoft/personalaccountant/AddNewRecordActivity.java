package tz.co.neelansoft.personalaccountant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import tz.co.neelansoft.personalaccountant.library.AppExecutors;
import tz.co.neelansoft.personalaccountant.library.PADatabase;

public class AddNewRecordActivity extends AppCompatActivity {

    private EditText mTextAmount;
    private EditText mTextDescription;
    private EditText mTextPayer;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioIncome;
    private RadioButton mRadioExpense;
    private Button mButtonSave;
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

        mButtonSave      = findViewById(R.id.btn_save);

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

        mTextAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateFields();
            }
        });

        mTextDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateFields();
            }
        });

        mTextPayer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateFields();
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               Record record = new Record(Double.parseDouble(mTextAmount.getText().toString()),mTextDescription.getText().toString(),recordType);
               saveRecord(record);
           }
        });

    }

    private void validateFields(){
        boolean allFieldsValid = false;
        String amount = mTextAmount.getText().toString();
        String description = mTextDescription.getText().toString();
        String payer = mTextPayer.getText().toString();
        if(!amount.isEmpty() && amount.length() > 0){
            if(!description.isEmpty() && description.length() > 0){
                if(!payer.isEmpty() && payer.length() > 0){
                    allFieldsValid = true;
                }
                else{
                    mTextPayer.setError("Empty field");
                }
            }
            else{
                mTextDescription.setError("Empty field");
            }
        }
        else{
            mTextAmount.setError("Empty field");
        }

        if(allFieldsValid){
            enableSave(allFieldsValid);
        }
    }

    private void enableSave(boolean state){
        if(state) {
            mButtonSave.setVisibility(View.VISIBLE);
        }
        else{
            mButtonSave.setVisibility(View.GONE);
        }
        mButtonSave.setEnabled(state);
    }
    private void saveRecord(final Record rec){
        final PADatabase db = PADatabase.getDatabaseInstance(AddNewRecordActivity.this);
        AppExecutors.getInstance().diskIO().execute(
            new Runnable(){
            @Override
            public void run(){
              db.dao().insertRecord(rec);
            }
        });
        finish();
    }
}
