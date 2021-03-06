package com.hackaton.hevre.clientapp.Control.RESTService;

/**
 * Created by אביחי on 30/09/2016.
 */

import com.hackaton.hevre.clientapp.Communication.GetTask;
import com.hackaton.hevre.clientapp.Communication.PostTask;
import com.hackaton.hevre.clientapp.Communication.RestTaskCallback;
import com.hackaton.hevre.clientapp.Control.Utils;

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
        new GetTask(restUrl, new RestTaskCallback<String>(){
            @Override
            public void onTaskComplete(String response){
                String profile = Utils.parseResponseAsProfile(response);
                callback.onDataReceived(profile);
            }
        }).execute();
    }

    public void login(String username, String password, final GetResponseCallback callback)
    {

        // 1. Create the api string
        String restUrl = Utils.constructRestUrlForLogin(username, password);

        new PostTask(restUrl, "aaa", new RestTaskCallback<String>() {
            @Override
            public void onTaskComplete(String result) {
                callback.onDataReceived(result);
            }
        }).execute();
    }
     public void Register (String username, String email, String password, String repassword, final GetResponseCallback callback){
         String restUrl = Utils.constructRestUrlForRegister(username, password, email);

         new PostTask(restUrl, "aaa", new RestTaskCallback<String>() {
             @Override
             public void onTaskComplete(String result) {
                 callback.onDataReceived(result);
             }
         }).execute();
     }
    /**
     * Submit a user profile to the server.
     * @param profile The profile to submit
     * @param callback The callback to execute when submission status is available.
     */
    public void postUserProfile(String profile, final PostCallback callback){
        String restUrl = Utils.constructRestUrlForProfile(profile);
        String requestBody = Utils.serializeProfileAsString(profile);
        new PostTask(restUrl, requestBody, new RestTaskCallback<String>(){
            public void onTaskComplete(String response){
                callback.onPostSuccess("");
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

    public void findProduct(String prod, final GetResponseCallback callback) {
        String restUrl = Utils.constructRestUrlForFindProduct(prod);


        new GetTask(restUrl, new RestTaskCallback<String>(){
            @Override
            public void onTaskComplete(String response){
                callback.onDataReceived(response);
            }
        }).execute();
    }

    public void saveReminder(String userName, String product, final PostCallback postCallback) {

        String restUrl = Utils.constructRestUrlForSaveReminder(userName, product);

        String requestBody = Utils.serializeProfileAsString(userName + "_" + product);
        new PostTask(restUrl, requestBody, new RestTaskCallback<String>(){
            public void onTaskComplete(String response){
                String result = "";
                postCallback.onPostSuccess(result);
            }
        }).execute();

    }
}

