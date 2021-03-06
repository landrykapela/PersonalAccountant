package tz.co.neelansoft.personalaccountant;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import tz.co.neelansoft.personalaccountant.library.AppExecutors;
import tz.co.neelansoft.personalaccountant.library.PADatabase;

public class AddNewRecordActivity extends AppCompatActivity {

    private static final String TAG = "AddNewRecordActivity";
    private EditText mTextAmount;
    private EditText mTextDescription;
    private EditText mTextPayer;
    private TextView mTextSetDate;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioIncome;
    private RadioButton mRadioExpense;
    private Button mButtonSave;
    private int recordType;
    private boolean isNew = true;
    private int recordId;

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
        mTextSetDate     = findViewById(R.id.tv_set_date);


        mButtonSave      = findViewById(R.id.btn_save);

        mRadioGroup.check(R.id.rb_expense);

        Intent intent = getIntent();
        if(intent.getParcelableExtra("record") != null && intent.getStringExtra("tag").equalsIgnoreCase("edit")){
            isNew = false;
            Record record_to_edit = intent.getParcelableExtra("record");
            recordId = record_to_edit.getId();

            mTextAmount.setText(String.valueOf(record_to_edit.getAmount()));
            mTextDescription.setText(record_to_edit.getDescription());
            mTextPayer.setText(record_to_edit.getPayer());

            if(record_to_edit.getRecordType() == 0){
                mRadioGroup.check(R.id.rb_income);
            }
            else{
                mRadioGroup.check(R.id.rb_expense);
            }

           mButtonSave.setText("Update");
        }

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
               Record record = new Record(Double.parseDouble(mTextAmount.getText().toString()),mTextDescription.getText().toString(),recordType, mTextPayer.getText().toString());
               Toast.makeText(AddNewRecordActivity.this,"details: rt-"+recordType,Toast.LENGTH_SHORT).show();
               if(isNew) saveRecord(record);
               else {
                   record.setId(recordId);
                   updateRecord(record);}
           }
        });

    }
    private void showDatePicker(View v){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(),"datePickerFragment");

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
        Log.e(TAG,"Creating record... "+rec.getId());
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

    private void updateRecord(final Record rec){
        Log.e(TAG,"Udating record... "+rec.getId());
        final PADatabase db = PADatabase.getDatabaseInstance(AddNewRecordActivity.this);
        AppExecutors.getInstance().diskIO().execute(
                new Runnable(){
                    @Override
                    public void run(){
                        db.dao().updateRecord(rec);
                    }
                });
        finish();
    }
}
