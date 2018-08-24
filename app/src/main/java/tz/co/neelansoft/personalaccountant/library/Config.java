package tz.co.neelansoft.personalaccountant.library;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Config {
    private static String dateFormatPattern = "dd-MM-yyyy";
    private static String shortDateFormatPattern = "dd MMM yyyy";
    private static String longDateFormatPattern = "EEE dd MMM, yyyy";

    public static NumberFormat CurrencyFormat(){
        java.util.Currency tsh = java.util.Currency.getInstance("TZS");
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        format.setCurrency(tsh);
        return format;
    }

    public static SimpleDateFormat AppDateFormat = new SimpleDateFormat(dateFormatPattern,Locale.getDefault());
    public static SimpleDateFormat LongAppDateFormat = new SimpleDateFormat(longDateFormatPattern,Locale.getDefault());
    public static SimpleDateFormat ShortAppDateFormat = new SimpleDateFormat(shortDateFormatPattern,Locale.getDefault());
}
