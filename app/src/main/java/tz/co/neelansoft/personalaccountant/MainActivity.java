package tz.co.neelansoft.personalaccountant;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tz.co.neelansoft.personalaccountant.library.AppExecutors;
import tz.co.neelansoft.personalaccountant.library.PADatabase;
import tz.co.neelansoft.personalaccountant.library.PARecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements PARecyclerViewAdapter.ItemClickListener{

    private PARecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTextNoRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton fab = findViewById(R.id.fab);
        mTextNoRecords = findViewById(R.id.tv_no_data);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddNewRecordActivity.class));
            }
        });
        mRecyclerViewAdapter = new PARecyclerViewAdapter(this,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        if(savedInstanceState == null) {
            retrieveRecords();
        }
        else{
            List<Record> myRecords = savedInstanceState.getParcelableArrayList("records");
            mRecyclerViewAdapter.setRecords(myRecords);
            mRecyclerViewAdapter.notifyDataSetChanged();
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.dialog_title_delete)
                        .setMessage(R.string.dialog_confirm_delete)
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteRecord(viewHolder);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                retrieveRecords();
                            }
                        });
                if(!builder.create().isShowing()) builder.create().show();
            }
        }).attachToRecyclerView(mRecyclerView);

    }

    private void retrieveRecords(){
        final PADatabase db = PADatabase.getDatabaseInstance(this);
        LiveData<List<Record>> liveRecords = db.dao().getAllRecords();
        liveRecords.observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> records) {
                if(records.size() == 0){
                    mTextNoRecords.setVisibility(View.VISIBLE);
                }
                else {
                    mRecyclerViewAdapter.setRecords(records);
                    mRecyclerViewAdapter.notifyDataSetChanged();
                    mTextNoRecords.setVisibility(View.GONE);
                }
            }
        });

    }
    private void deleteRecord(final RecyclerView.ViewHolder vh){
        int index = vh.getAdapterPosition();

        final PADatabase db = PADatabase.getDatabaseInstance(this);
        final Record recordToDelete = mRecyclerViewAdapter.getRecords().get(index);
        AppExecutors.getInstance().diskIO().execute(new Runnable(){
            @Override
            public void run(){
                db.dao().deleteRecord(recordToDelete);
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("records",(ArrayList<Record>)mRecyclerViewAdapter.getRecords());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings:
                openSettingsActivity();
                return true;
            case R.id.action_account:
                openAccountActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAccountActivity() {
        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
    }

    private void openSettingsActivity() {
        startActivity(new Intent(getApplicationContext(),SettingsActivity.class));

    }

    @Override
    public void onItemClick(int itemId){
        Intent detail_intent = new Intent(getApplicationContext(),TransactionDetailsActivity.class);
        detail_intent.putExtra("record",mRecyclerViewAdapter.getRecordWithId(itemId));
        startActivity(detail_intent);
    }
}
