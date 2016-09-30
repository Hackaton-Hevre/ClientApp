package com.hackaton.hevre.clientapp.Service;

/**
 * Created by אביחי on 30/09/2016.
 */

import android.os.AsyncTask;

/**
 * An AsyncTask implementation for performing POSTs on the Hypothetical REST APIs.
 */
public class PostTask extends AsyncTask<String, String, String> {
    private String mRestUrl;
    private RestTaskCallback mCallback;
    private String mRequestBody;

    /**
     * Creates a new instance of PostTask with the specified URL, callback, and
     * request body.
     *
     * @param restUrl The URL for the REST API.
     * @param callback The callback to be invoked when the HTTP request
     *            completes.
     * @param requestBody The body of the POST request.
     *
     */
    public PostTask(String restUrl, String requestBody, RestTaskCallback callback){
        this.mRestUrl = restUrl;
        this.mRequestBody = requestBody;
        this.mCallback = callback;
    }

    @Override
    protected String doInBackground(String... arg0) {
        //Use HTTP client API's to do the POST
        //Return response.
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        mCallback.onTaskComplete(result);
        super.onPostExecute(result);
    }
}
