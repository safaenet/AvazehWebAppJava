package com.safadana.AvazehRetailManagement.SharedLibrary.Helpers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.github.mfathi91.time.PersianDate;

public class PersianCalendarHelper {

    // Gets the current Persian date in format of "YYYY/mm/dd"
    public static String getCurrentPersianDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return dtf.format(PersianDate.now());
    }

    // Gets the current Persian date + AddDays in format of "YYYY/mm/dd"
    public static String getPersianDate(int addDays) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return dtf.format(PersianDate.now().plusDays(addDays));
    }

    // Gets the current Persian date in format of "YYYYmmdd"
    public static String getCurrentRawPersianDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        return dtf.format(PersianDate.now());
    }

    // Gets the current Persian date + AddDays in format of "YYYYmmdd"
    public static String getRawPersianDate(int addDays) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        return dtf.format(PersianDate.now().plusDays(addDays));
    }

    // Gets the current time in format of "HH:mm:ss"
    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
    }

    public static String toPersianDate(LocalDate localDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return dtf.format(PersianDate.fromGregorian(localDate));
    }

    public static String toPersianDateTime(LocalDate localDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return dtf.format(PersianDate.fromGregorian(localDate));
    }

    public static LocalDate toGregorianDate(String persianDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        PersianDate pd = PersianDate.parse(persianDate, dtf);
        return pd.toGregorian();
    }
}
