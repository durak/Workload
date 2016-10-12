package workloadstats.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Ilkka
 */
public class DateTools {

    private DateTools() {

    }

    public static String checkAndCorrectMidnight(String startDateTime, String endDateTime) throws ParseException {
        String newEnd = endDateTime;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        Date start = format.parse(startDateTime);
        Date end = format.parse(endDateTime);
        if (end.before(start)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(end);
            cal.add(Calendar.DATE, 1);
            newEnd = format.format(cal.getTime());
        }
        return newEnd;
    }

    public static String dateFormatter(Date de) {
        Calendar c = Calendar.getInstance();
        c.setTime(de);
        int d = c.get(Calendar.DAY_OF_MONTH);
        int m = c.get(Calendar.MONTH) + 1;
        int y = c.get(Calendar.YEAR);
        String date = String.format("%04d", y) + String.format("%02d", m) + String.format("%02d", d);

        return date;
    }

    public static String timeFormatter(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        String time = String.format("%02d", h) + String.format("%02d", m);

        return time;
    }

}
