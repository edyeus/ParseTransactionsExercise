package com.edy.interview.model;

public class DefaultUser {

    private static final long UID=1110590645;
    private static final String TOKEN="BFBC0D8DAAA0C9F1AB570531A4649D07";
    private static final String API_TOKEN="AppTokenForInterview";
    private static final boolean JSON_STRICT_MODE=false;
    private static final boolean JSON_VERBOSE_RESPONSE=false;

    public static long getDefaultUid(){
        return UID;
    }

    public static String getDefaultToken(){
        return TOKEN;
    }

    public static String getDefaultApiToken(){
        return API_TOKEN;
    }

    public static boolean getDefaultJsonStrictMode(){
        return JSON_STRICT_MODE;
    }

    public static boolean getDefaultJsonVerboseResponse(){
        return JSON_VERBOSE_RESPONSE;
    }

    /**
     * Class to represent default user
     * @return default user
     */
    public static User getDefaultUser(){
        return new User(UID, TOKEN, API_TOKEN, JSON_STRICT_MODE, JSON_VERBOSE_RESPONSE);
    }
}
