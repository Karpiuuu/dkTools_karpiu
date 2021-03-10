package pl.dkcode.tools.utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final LinkedHashMap<Integer, String> values;

    public DataUtil() {
    }

    public static String secondsToString(long l) {
        int seconds = (int)((l - System.currentTimeMillis())/ 1000);
        final StringBuilder sb = new StringBuilder();
        if(seconds < 1){
            return "<1s";
        }
        for (final Map.Entry<Integer, String> e : DataUtil.values.entrySet()) {
            final int iDiv = seconds / e.getKey();
            if (iDiv >= 1) {
                final int x = (int) Math.floor(iDiv);
                sb.append(x).append(e.getValue());
                seconds -= x * e.getKey();
            }
        }
        if(sb.toString().equals("")){
            return "<1s";
        }
        return sb.toString();
    }

    public static String getDate(long time) {
        return dateFormat.format(new Date(time));
    }

    public static String getTime(long time) {
        return timeFormat.format(new Date(time));
    }

    public static long parseDateDiff(String time, boolean future) {
        try {
            Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
            Matcher m = timePattern.matcher(time);
            int years = 0;
            int months = 0;
            int weeks = 0;
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            boolean found = false;

            while(m.find()) {
                if (m.group() != null && !m.group().isEmpty()) {
                    for(int i = 0; i < m.groupCount(); ++i) {
                        if (m.group(i) != null && !m.group(i).isEmpty()) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        if (m.group(1) != null && !m.group(1).isEmpty()) {
                            years = Integer.parseInt(m.group(1));
                        }

                        if (m.group(2) != null && !m.group(2).isEmpty()) {
                            months = Integer.parseInt(m.group(2));
                        }

                        if (m.group(3) != null && !m.group(3).isEmpty()) {
                            weeks = Integer.parseInt(m.group(3));
                        }

                        if (m.group(4) != null && !m.group(4).isEmpty()) {
                            days = Integer.parseInt(m.group(4));
                        }

                        if (m.group(5) != null && !m.group(5).isEmpty()) {
                            hours = Integer.parseInt(m.group(5));
                        }

                        if (m.group(6) != null && !m.group(6).isEmpty()) {
                            minutes = Integer.parseInt(m.group(6));
                        }

                        if (m.group(7) != null && !m.group(7).isEmpty()) {
                            seconds = Integer.parseInt(m.group(7));
                        }
                        break;
                    }
                }
            }

            if (!found) {
                return -1L;
            } else {
                Calendar c = new GregorianCalendar();
                if (years > 0) {
                    c.add(1, years * (future ? 1 : -1));
                }

                if (months > 0) {
                    c.add(2, months * (future ? 1 : -1));
                }

                if (weeks > 0) {
                    c.add(3, weeks * (future ? 1 : -1));
                }

                if (days > 0) {
                    c.add(5, days * (future ? 1 : -1));
                }

                if (hours > 0) {
                    c.add(11, hours * (future ? 1 : -1));
                }

                if (minutes > 0) {
                    c.add(12, minutes * (future ? 1 : -1));
                }

                if (seconds > 0) {
                    c.add(13, seconds * (future ? 1 : -1));
                }

                Calendar max = new GregorianCalendar();
                max.add(1, 10);
                return c.after(max) ? max.getTimeInMillis() : c.getTimeInMillis();
            }
        } catch (Exception var14) {
            return -1L;
        }
    }

    static {
        (values = new LinkedHashMap(6)).put(31104000, "y");
        values.put(2592000, "msc");
        values.put(86400, "d");
        values.put(3600, "h");
        values.put(60, "min");
        values.put(1, "s");
    }
}
