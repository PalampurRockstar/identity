package com.common;

public class Constants {
    public static final String APP_DATE_FORMAT = "dd-MM-yyyy";
    public static final String APP_DATETIME_FORMAT = APP_DATE_FORMAT+" HH:mm:ss Z";
    public static final int APP_ZONE_OFFSET = 7;
    public static final String SECRET = "secret";
    public static final String BEARER = "Bearer";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final int MIN = 60_000;
    public static final int HOUR = 60 *MIN;
    public static final int ACCESS_TKN_TIMEOUT = 10*MIN; //10 min
    public static final int REFRESH_TKN_TIMEOUT = 24*HOUR;//24 hours
    public static final int JWT_SKEWING = HOUR; //1 hour
}
