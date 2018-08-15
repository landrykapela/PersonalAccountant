package tz.co.neelansoft.personalaccountant;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import tz.co.neelansoft.personalaccountant.library.AppExecutors;
import tz.co.neelansoft.personalaccountant.library.PADatabase;
import tz.co.neelansoft.personalaccountant.library.PARecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements PARecyclerViewAdapter.OnItemClickListener{

    private PARecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview);
        FloatingActionButton fab = findViewById(R.id.fab);
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
        final PADatabase db = PADatabase.getDatabaseInstance(this);
        LiveData<List<Record>> liveRecords = db.dao().getAllRecords();
                liveRecords.observe(this, new Observer<List<Record>>() {
                    @Override
                    public void onChanged(@Nullable List<Record> records) {
                        mRecyclerViewAdapter.setRecords(records);
                        mRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int itemPosition){
        Toast.makeText(MainActivity.this, "Got it, id: "+mRecyclerViewAdapter.getRecords().get(itemPosition), Toast.LENGTH_SHORT).show();
    }
}
