package be.company.fca.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    /**
     * Permet de recuperer une date en precisant les heures, minutes, secondes et millisecondes a zero
     * @param date
     * @return
     */
    public static Date shrinkToDay(Date date){
        Calendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.HOUR_OF_DAY,0);
        gc.set(Calendar.MINUTE,0);
        gc.set(Calendar.SECOND,0);
        gc.set(Calendar.MILLISECOND,0);
        return gc.getTime();
    }

}
