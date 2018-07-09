package brunonm.conductor.mobile.desafio.desafiomobile.util;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalendarUtils {

    public static Calendar getCaledar(String date) {
        Pattern pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+)T(\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3})Z");
        Matcher matcher = pattern.matcher(date);

        Calendar calendar = Calendar.getInstance();

        while (matcher.find()) {
            calendar.set(Calendar.YEAR, Integer.parseInt(matcher.group(1)));
            calendar.set(Calendar.MONTH, Integer.parseInt(matcher.group(2)) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(matcher.group(3)));
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(matcher.group(4)));
            calendar.set(Calendar.MINUTE, Integer.parseInt(matcher.group(5)));
            calendar.set(Calendar.SECOND, Integer.parseInt(matcher.group(6)));
            calendar.set(Calendar.MILLISECOND, Integer.parseInt(matcher.group(7)));
        }
        return calendar;
    }
}
