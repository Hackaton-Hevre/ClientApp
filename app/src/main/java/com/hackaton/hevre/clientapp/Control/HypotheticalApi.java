package com.hackaton.hevre.clientapp.Control;

/**
 * Created by אביחי on 30/09/2016.
 */

import com.hackaton.hevre.clientapp.Service.GetTask;
import com.hackaton.hevre.clientapp.Service.PostTask;
import com.hackaton.hevre.clientapp.Service.RestTaskCallback;

/**
 * Entry point into the API.
 */
public class HypotheticalApi{

    private static HypotheticalApi instance = new HypotheticalApi();

    public static HypotheticalApi getInstance(){
        return instance;
    }

    /**
     * Request a User Profile from the REST server.
     * @param userName The user name for which the profile is to be requested.
     * @param callback Callback to execute when the profile is available.
     */
    public void getUserProfile(String userName, final GetResponseCallback callback){

        String restUrl = Utils.constructRestUrlForProfile(userName);
        new GetTask(restUrl, new RestTaskCallback(){
            @Override
            public void onTaskComplete(String response){
                String profile = Utils.parseResponseAsProfile(response);
                callback.onDataReceived(profile);
            }
        }).execute();
    }

    public void login(String username, String password, final GetResponseCallback callback)
    {

        /*// 1. Create the api string
        String restUrl = Utils.constructRestUrlForLogin(username, password);*/

    }

    /**
     * Submit a user profile to the server.
     * @param profile The profile to submit
     * @param callback The callback to execute when submission status is available.
     */
    public void postUserProfile(String profile, final PostCallback callback){
        String restUrl = Utils.constructRestUrlForProfile(profile);
        String requestBody = Utils.serializeProfileAsString(profile);
        new PostTask(restUrl, requestBody, new RestTaskCallback(){
            public void onTaskComplete(String response){
                callback.onPostSuccess();
            }
        }).execute();
    }

    public float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
}

