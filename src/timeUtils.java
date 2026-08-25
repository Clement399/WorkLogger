import java.time.*;
import java.time.temporal.ChronoUnit;

import static java.time.Period.between;

public class timeUtils {
    public static void main(String[] args) {

        System.out.println("Java time format experiment");

        System.out.println("1. Java time module :");
        System.out.println("- Instant :"+ java.time.Instant.now());
        System.out.println("- Localtime :"+ java.time.LocalTime.now());
        System.out.println("- Zone :"+ java.time.ZoneId.systemDefault());
        System.out.println("- Localdatetime :"+ java.time.LocalDateTime.now());

        // Localdate
        System.out.println("2. Trying to get date only ");
        System.out.println("- Zoneid :"+ java.time.ZoneId.systemDefault().getId());
        LocalDate localDate = LocalDate.now();
        System.out.println("- Local Date :"+ localDate);
        System.out.println("- Year :"+ localDate.getYear());
        System.out.println("- Month :"+ localDate.getMonthValue());
        System.out.println("- Day :"+ localDate.getDayOfMonth());
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("- Local DateTime :"+ localDateTime);
        System.out.println("- Year : "+ localDateTime.getYear());
        System.out.println("- Month : "+ localDateTime.getMonthValue());
        System.out.println("- Day : "+ localDateTime.getDayOfMonth());
        System.out.println("- Max : "+ LocalTime.MAX);

        System.out.println("3. Calculations");
        System.out.println("- Period : "+ localDateTime.toLocalDate());
        System.out.println("- Max time of today :"+ LocalTime.MAX);
        System.out.println("- Local Time :"+ LocalTime.now());
        Duration duration = Duration.between( LocalTime.now(),LocalTime.MAX).truncatedTo(ChronoUnit.MINUTES);
        System.out.println("- Duration : "+ duration);
        System.out.println("- Period : "+ duration.getSeconds());
        System.out.println("- Duration (hours): "+ duration.toHours());
        System.out.println("- Duration (hours part): "+ duration.toHoursPart());
        System.out.println("- Duration (minutes): "+ duration.toMinutes());
        System.out.println("- Duration (minutes part): "+ duration.toMinutesPart());
    }

}
