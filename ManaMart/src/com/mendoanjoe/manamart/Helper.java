package com.mendoanjoe.manamart;

import javax.swing.*;
import java.text.*;
import java.util.Date;
import java.util.regex.Pattern;

public class Helper {
    public static String convertToRupiah(int value) {
        DecimalFormat kursIndonesia = (DecimalFormat) NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol(Naming.TEXT_STRING_RUPIAH);
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        kursIndonesia.setMaximumFractionDigits(0);

        String rupiah = kursIndonesia.format(value);
        if (value < 0) {
            rupiah = "- " + rupiah;
        }

        return rupiah;
    }

    public static void showDialog(String value) {
        JOptionPane.showMessageDialog(null , value);
    }

    public static Date getDate(String value) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(value);
            return date;
        } catch (ParseException e) {
            Helper.showDialog("Gunakan format tanggal dd/mm/yyyy");
        }
        return date;
    }

    public static boolean validDate(String value) {
        String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";

        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches();
    }
}
