package ru.vddmit.calculator.model.calendarific;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CalendarificResponse {
    Meta meta;
    CalendarificData response;

    @Data
    public static class Meta {
        int code;
    }

    @Data
    public static class CalendarificData {
        List<Holiday> holidays;
    }

    @Data
    public static class Holiday {
        String name;
        DateWrapper date;

        @Data
        public static class DateWrapper {
            String iso;
        }
    }
}
