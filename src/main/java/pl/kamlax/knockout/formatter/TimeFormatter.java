package pl.kamlax.knockout.formatter;

import java.util.concurrent.TimeUnit;

/**
 * @author Kamil "kamlax" Okoń
 */

public final class TimeFormatter {
    public static String getSecondsFromLong(long time) {
        if (time < 1L)
            return "<1sec";
        var seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        return seconds > 0L ? seconds + "sec" : "0." + time + "sec" ;
    }
}
