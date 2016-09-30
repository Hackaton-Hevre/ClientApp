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
}

