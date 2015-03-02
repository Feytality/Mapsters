package delta.soen390.mapsters.Utils;

import com.google.api.client.util.DateTime;
import java.util.Date;

/**
 * The purpose of the class is to hold all utility methods for manipulating time related objects.
 *
 * Created by Felicia on 2015-02-27.
 */
public class TimeUtil {

    /**
     * Converts millisecond to a DateTime object. Might belong in a utility class instead.
     *
     * @param   millisString
     * @return  DateTime object represented by the accepted string.
     */
    public static DateTime millisToDateTime(String millisString)
    {
        if(millisString == null) {
            return null;
        }
        long millis = Long.parseLong(millisString);
        return new DateTime(millis);
    }

    /**
     * Subtracts two dates to return the DateTime with the difference.
     *
     * @param startDate
     * @param subDate
     * @return
     */
    public static Date subtractDates(DateTime startDate, DateTime subDate) {
        if(startDate != null || subDate != null) {
            long diff = Math.abs(startDate.getValue() - subDate.getValue());
            return (new Date(diff));
        } else {
            return null;
        }
    }
}
