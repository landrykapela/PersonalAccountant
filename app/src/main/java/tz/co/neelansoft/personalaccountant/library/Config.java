package tz.co.neelansoft.personalaccountant.library;

import java.text.NumberFormat;
import java.util.Locale;

public class Config {

    public static NumberFormat CurrencyFormat(){
        java.util.Currency tsh = java.util.Currency.getInstance("TZS");
        java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault());
        format.setCurrency(tsh);
        return format;
    }
}
