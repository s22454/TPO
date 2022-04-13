/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.*;

public class Time {
    public static String passed(String fromText, String toText) {

        try {
            String res;

            if (fromText.matches(".*T.*") && toText.matches(".*T.*")) {
                LocalDateTime from = LocalDateTime.parse(fromText);
                LocalDateTime to = LocalDateTime.parse(toText);

                from.atZone(ZoneId.of("Europe/Warsaw"));
                to.atZone(ZoneId.of("Europe/Warsaw"));

                long daysBetween = DAYS.between(from, to);
                

                res =           // from section
                        "Od " +
                                from.getDayOfMonth() + " " +
                                from.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " +
                                from.getYear() +
                                " (" + from.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + ")" +
                                " godz. " + from.format(DateTimeFormatter.ISO_TIME).substring(0, 5) +

                                // to section
                                " do " +
                                to.getDayOfMonth() + " " +
                                to.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " +
                                to.getYear() +
                                " (" + to.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + ")" +
                                " godz. " + to.format(DateTimeFormatter.ISO_TIME).substring(0, 5) +

                                // info section
                                "\n - mija: " + daysBetween + (!(daysBetween == 1) ? " dni" : " dzień") + ", " +
                                "tygodni " + (((int) ((daysBetween / 7.0) * 100)) / 100.0) +
                                "\n - godzin: " + HOURS.between(from, to) + ", minut: " + MINUTES.between(from, to) +
                                ((!durationToString(Period.between(from.toLocalDate(), to.toLocalDate())).equals(""))? ("\n - kalendarzowo: " + durationToString(Period.between(from.toLocalDate(), to.toLocalDate()))) : "");

            } else {
                LocalDate from = LocalDate.parse(fromText);
                LocalDate to = LocalDate.parse(toText);
                long daysBetween = DAYS.between(from, to);

                res =           // from section
                        "Od " +
                                from.getDayOfMonth() + " " +
                                from.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " +
                                from.getYear() +
                                " (" + from.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + ")" +

                                // to section
                                " do " +
                                to.getDayOfMonth() + " " +
                                to.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " +
                                to.getYear() +
                                " (" + to.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()) + ")" +

                                // info section
                                "\n - mija: " + daysBetween + (!(daysBetween == 1) ? " dni" : " dzień") + ", " +
                                "tygodni " + (((int) ((daysBetween / 7.0) * 100)) / 100.0) +
                                ((!durationToString(Period.between(from, to)).equals(""))? ("\n - kalendarzowo: " + durationToString(Period.between(from, to))) : "");
            }


            return res;
        } catch (Exception e){
            return "*** " + e.getClass().getName() + ": " + e.getMessage();
        }
    }

    private static String durationToString(Period period){
        StringBuilder res = new StringBuilder();

        if (period.getYears() >= 1){
           res.append(period.getYears() + ((period.getYears() == 1)? " rok" : (period.getYears() == 2)? " lata" : " lat"));
        }

        if (period.getMonths() >= 1){
            res.append(((res.length() > 0)? " " : "") + period.getMonths() + ((period.getMonths() == 1)? " miesiąc" : (period.getMonths() > 4)? " miecięcy" : " miesiące"));
        }

        if (period.getDays() >= 1) {
            res.append(((res.length() > 0)? " " : "") + period.getDays() + ((period.getDays() == 1) ? " dzień" : " dni"));
        }

        return res.toString();
    }

    public static void main(String[] args) {
        //"2020-03-27T10:00","2020-03-28T10:00"
        System.out.println(Time.passed("2020-03-27","2020-03-28"));
    }
}
