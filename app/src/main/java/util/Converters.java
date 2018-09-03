package util;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String getEdad(Date fechaNacimiento) {
        Calendar cumple = Calendar.getInstance();
        cumple.setTime(fechaNacimiento);
        Calendar hoy = Calendar.getInstance();
        int year = hoy.get(Calendar.YEAR) - cumple.get(Calendar.YEAR);
        int month = hoy.get(Calendar.MONTH) - cumple.get(Calendar.MONTH) + 1;
        if (month < 0) {
            year = year - 1;
        }
        if (month < 0) {
            month = month + 12;
        }
        if (month == 0) {
            month = 12;
        }
        if ((hoy.get(Calendar.DAY_OF_MONTH) < cumple.get(Calendar.DAY_OF_MONTH))
                && hoy.get(Calendar.MONTH) == cumple.get(Calendar.MONTH)) {
            month = month - 1;
            year = year - 1;
        }
        if (month == 12 && year == 0) {
            return "Un año";
        }
        if (year < 1) {
            if (month <= 1) {
                return "Un mes";
            } else {
                return month + " meses";
            }
        } else if (year > 0 && year < 2) {
            if (month == 0) {
                return "Un año";
            }
            return "Un año con " + month + (month == 1 ? " mes" : " meses");
        } else {
            return year + " años";
        }
    }
}
