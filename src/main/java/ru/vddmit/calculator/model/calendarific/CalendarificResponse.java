package ru.vddmit.calculator.model.calendarific;

import lombok.Data;

import java.util.List;

@Data
public class CalendarificResponse {
    private Meta meta;
    private CalendarificData response;

    @Data
    public static class Meta {
        private int code;
    }

    @Data
    public static class CalendarificData {
        private List<Holiday> holidays;
    }

    @Data
    public static class Holiday {
        private String name;
        private DateWrapper date;

        @Data
        public static class DateWrapper {
            private String iso;
        }
    }
}
