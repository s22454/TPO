/**
 *
 *  @author Sasor-Adamczyk Mateusz S22454
 *
 */

package zad1;


import java.time.*;

public class Time {
    public static String passed(String from, String to) {

        LocalDate x = LocalDate.parse(from);
        LocalDate y = LocalDate.parse(to);

        Period period = Period.between(x, y);

        return period;
    }
}
