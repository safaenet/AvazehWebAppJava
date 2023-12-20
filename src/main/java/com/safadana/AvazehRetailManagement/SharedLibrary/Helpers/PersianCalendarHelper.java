package com.safadana.AvazehRetailManagement.SharedLibrary.Helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import com.github.mfathi91.time.PersianDate;
import com.github.mfathi91.time.PersianDateTime;

public class PersianCalendarHelper {

    // Gets the current Persian date in format of "YYYY/mm/dd"
    public static String getPersianDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return dtf.format(PersianDate.now());
    }

    // Gets the current Persian date + AddDays in format of "YYYY/mm/dd"
    public static String getPersianDate(int addDays) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return dtf.format(PersianDate.now().plusDays(addDays));
    }

    // Gets the current Persian date and time in format of "YYYY/mm/dd HH:mm:ss"
    public static String getPersianDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(PersianDateTime.now());
    }

    // Gets the current Persian date + AddDays in format of "YYYY/mm/dd"
    public static String getPersianDateTime(int addDays) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(PersianDateTime.now().plus(Period.ofDays(addDays)));
    }

    // Gets the current Persian date in format of "YYYYmmdd"
    public static String getRawPersianDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        return dtf.format(PersianDate.now());
    }

    // Gets the current Persian date + AddDays in format of "YYYYmmdd"
    public static String getRawPersianDate(int addDays) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        return dtf.format(PersianDate.now().plusDays(addDays));
    }

    public static String toPersianDate(LocalDate localDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return dtf.format(PersianDate.fromGregorian(localDate));
    }

    public static String toPersianDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dtf.format(PersianDateTime.fromGregorian(localDateTime));
    }

    public static LocalDate toGregorianDate(String persianDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        PersianDate pd = PersianDate.parse(persianDate, dtf);
        return pd.toGregorian();
    }

    public static LocalDateTime toGregorianDateTime(String persianDateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        PersianDateTime pd = PersianDateTime.parse(persianDateTime, dtf);
        return pd.toGregorian();
    }

    public static boolean isValidPersianDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            PersianDateTime.parse(date, dtf);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidPersianDateTime(String dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        try {
            PersianDateTime.parse(dateTime, dtf);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
