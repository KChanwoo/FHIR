/**
 * Class that provides the current date-time-minute-second format.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.06
 */
package kr.ac.yonsei.maist.global.common;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class CurrentDateTime {

    /**
     * Returns the current date-time-minute-second format.
     * @return String yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        Calendar time = Calendar.getInstance();
        return format.format(time.getTime());
    }

    /**
     * Convert local time to utc and returns.
     * @param reservationTime local time
     * @param region
     * @return String UTC
     */
    public static String getUtcDateTime(String reservationTime, String region) {

        int year = Integer.parseInt(reservationTime.substring(0,4));
        int month = Integer.parseInt(reservationTime.substring(5,7));
        int day = Integer.parseInt(reservationTime.substring(8,10));
        int hour = Integer.parseInt(reservationTime.substring(11,13));
        int minute = Integer.parseInt(reservationTime.substring(14,16));

        ZonedDateTime regionTime = Year.of(year).atMonth(month).atDay(day).atTime(hour, minute).atZone(ZoneId.of(region));
        ZonedDateTime convertUtcTime = regionTime.withZoneSameInstant(ZoneId.of("UTC"));
        String utc = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss").format(convertUtcTime);
        System.out.println("UTC 시간 : "+utc);
        return utc;
    }

    /**
     * Converts utc to local time and returns.
     * @param reservationTime UTC
     * @param region
     * @return String local time
     */
    public static String getRegionDateTime(String reservationTime, String region) {

        int year = Integer.parseInt(reservationTime.substring(0,4));
        int month = Integer.parseInt(reservationTime.substring(5,7));
        int day = Integer.parseInt(reservationTime.substring(8,10));
        int hour = Integer.parseInt(reservationTime.substring(11,13));
        int time = Integer.parseInt(reservationTime.substring(14,16));

        ZonedDateTime utcTime = Year.of(year).atMonth(month).atDay(day).atTime(hour, time).atZone(ZoneOffset.UTC);
        ZonedDateTime convertRegionTime = utcTime.withZoneSameInstant(ZoneId.of(region));
        String regionTime = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss").format(convertRegionTime);
        System.out.println("REGION 시간 : "+regionTime);
        return regionTime;
    }
}
