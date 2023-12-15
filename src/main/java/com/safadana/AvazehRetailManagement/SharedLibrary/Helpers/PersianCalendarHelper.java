package com.safadana.AvazehRetailManagement.SharedLibrary.Helpers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.PersianChronology;
import java.time.format.DateTimeFormatter;

public class PersianCalendarHelper {

    // Gets the current Persian date in format of "YYYY/mm/dd"
    public static String getCurrentPersianDate() {
        LocalDate now = LocalDate.now(PersianChronology.INSTANCE);
        return String.format("%04d/%02d/%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    // Gets the current Persian date + AddDays in format of "YYYY/mm/dd"
    public static String getPersianDate(int addDays) {
        LocalDate now = LocalDate.now(PersianChronology.INSTANCE).plusDays(addDays);
        return String.format("%04d/%02d/%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    // Gets the current Persian date in format of "YYYYmmdd"
    public static String getCurrentRawPersianDate() {
        LocalDate now = LocalDate.now(PersianChronology.INSTANCE);
        return String.format("%04d%02d%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    // Gets the current Persian date + AddDays in format of "YYYYmmdd"
    public static String getRawPersianDate(int addDays) {
        LocalDate now = LocalDate.now(PersianChronology.INSTANCE).plusDays(addDays);
        return String.format("%04d%02d%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    // Gets the current time in format of "HH:mm:ss"
    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
    }

    public static String toPersianDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return localDate.format(formatter);
    }

    public static String toPersianDateTime(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return localDate.format(formatter);
    }

    public static LocalDate toGregorianDate(String persianDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(persianDate, formatter).withChronology(PersianChronology.INSTANCE);
    }
}
