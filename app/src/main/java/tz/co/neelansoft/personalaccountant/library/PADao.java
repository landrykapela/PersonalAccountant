package tz.co.neelansoft.personalaccountant.library;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import tz.co.neelansoft.personalaccountant.Record;

public interface PADao {

    @Query("SELECT * FROM record")
    LiveData<List<Record>> getAllRecords();

    @Query("SELECT * FROM record WHERE id = :id")
    Record getRecordWithId(int id);

    @Insert
    void insertRecord(Record record);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecord(Record record);

    @Delete
    void deleteRecord(Record record);

}
