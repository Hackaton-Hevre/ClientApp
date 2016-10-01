package com.hackaton.hevre.clientapp.Control;

/**
 * Created by אביחי on 30/09/2016.
 */
public class Utils {

    private static final String BASE_REST_SERVER = "http://10.0.0.11:7300";
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

    public static String constructRestUrlForLogin(String username, String password) {
        return USERS_SERVICE_ROUTE + "/login/" + username + "/" + password;
    }

    public static String constructRestUrlForRegister(String username, String password, String email) {
        String email_username = email.split("@")[0];
        String email_domain = email.split("@")[1];
        return USERS_SERVICE_ROUTE + "/register/" + username + "/" + password + "/" + email_username + "/" + email_domain ;
    }
}
