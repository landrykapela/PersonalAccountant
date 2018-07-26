package tz.co.neelansoft.personalaccountant;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "record")
public class Record {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private double amount;
    private String description;
    @ColumnInfo(name = "updated_at")
    String updatedAt;

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    @Ignore
    public Record(double amount, String description){
        this.amount = amount;
        this.description = description;
        this.updatedAt = new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }
    public Record(int id, double amount, String description){
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.updatedAt = new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}