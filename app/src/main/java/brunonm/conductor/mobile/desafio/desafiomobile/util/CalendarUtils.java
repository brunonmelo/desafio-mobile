package brunonm.conductor.mobile.desafio.desafiomobile.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarUtils {

    public static Calendar getCaledar(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
        Calendar calendar = new GregorianCalendar();
        try {
            Date data = sdf.parse(date);
            calendar.setTime(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
}
