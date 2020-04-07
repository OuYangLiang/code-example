package com.personal.oyl.code.example.redis.delay;

import java.util.Calendar;
import java.util.Date;

/**
 * @author OuYang Liang
 * @since 2019-09-24
 */
public final class TimeUtil {
    private static volatile TimeUtil instance;
    private TimeUtil() {

    }

    public static TimeUtil getInstance() {
        if (null == instance) {
            synchronized (TimeUtil.class) {
                if (null == instance) {
                    instance = new TimeUtil();
                }
            }
        }
        return instance;
    }


    public long format(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTimeInMillis();
    }
}
