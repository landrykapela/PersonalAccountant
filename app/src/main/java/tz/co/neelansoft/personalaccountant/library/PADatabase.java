package tz.co.neelansoft.personalaccountant.library;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import tz.co.neelansoft.personalaccountant.Record;

@Database(entities = {Record.class}, version=1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class PADatabase extends RoomDatabase {

    private static final String TAG = "PADatabase";
    private static final String DATABASE_NAME = "pad_db";
    private static final Object LOCK = new Object();
    private static PADatabase sInstance;

    public static PADatabase getDatabaseInstance(Context context){

        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),PADatabase.class,DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract PADao dao();
}
