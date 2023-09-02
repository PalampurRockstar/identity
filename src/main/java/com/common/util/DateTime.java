package com.common.util;

import java.time.format.DateTimeFormatter;

import static com.common.Constants.*;

public class DateTime {

    public static DateTimeFormatter appDateFormat() {return DateTimeFormatter.ofPattern(APP_DATE_FORMAT);}
    public static DateTimeFormatter appDateTimeFormat() {return DateTimeFormatter.ofPattern(APP_DATETIME_FORMAT);}


}
