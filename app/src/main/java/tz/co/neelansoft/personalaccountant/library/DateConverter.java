package tz.co.neelansoft.personalaccountant.library;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timeInMills){
        if(timeInMills != null){
            return new Date(timeInMills);
        }
        else{
            return null;
        }
    }

    @TypeConverter
    public static Long toTimestamp(Date date){
        return date.getTime();
    }
}
