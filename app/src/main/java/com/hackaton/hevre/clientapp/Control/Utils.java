package com.hackaton.hevre.clientapp.Control;

/**
 * Created by אביחי on 30/09/2016.
 */
public class Utils {

    private static final String BASE_REST_SERVER = "http://10.0.0.8:5070";
    private static final String USERS_SERVICE_ROUTE = BASE_REST_SERVER + "/users";

    public static String constructRestUrlForProfile(String userName) {
        return USERS_SERVICE_ROUTE + "/" + userName;
    }

    public static String parseResponseAsProfile(String response)
    {
        return response;
    }

    public static String serializeProfileAsString(String profile) {
        return profile;
    }
}
