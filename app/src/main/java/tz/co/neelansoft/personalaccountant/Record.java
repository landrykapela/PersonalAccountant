package tz.co.neelansoft.personalaccountant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity (tableName = "record")
public class Record implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private double amount;
    private String description;
    private int recordType;
    @ColumnInfo(name = "updated_at")

    String updatedAt;


    private static final String DATE_FORMAT = "dd-MM-yyyy";

    @Ignore
    public Record(double amount, String description, int recordType){
        this.amount = amount;
        this.description = description;
        this.recordType = recordType;
        this.updatedAt = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(new Date());
    }
    @Ignore
    public Record(){};
    public Record(int id, double amount, String description, int recordType){
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.recordType = recordType;
        this.updatedAt = new SimpleDateFormat(DATE_FORMAT,Locale.getDefault()).format(new Date());
    }
    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(amount);
        dest.writeString(description);
        dest.writeString(updatedAt);
        dest.writeInt(recordType);
    }

    public Record(Parcel in){
        this.id = in.readInt();
        this.amount = in.readDouble();
        this.description = in.readString();
        this.updatedAt = in.readString();
        this.recordType = in.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Record>(){
        @Override
        public Record createFromParcel(Parcel source){
            return new Record(source);
        }

        @Override
        public Record[] newArray(int size){
            return new Record[size];
        }
    };
}
