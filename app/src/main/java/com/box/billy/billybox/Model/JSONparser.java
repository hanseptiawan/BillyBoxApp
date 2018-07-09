package com.box.billy.billybox.Model;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JSONparser {
    static InputStream is = null;
    static JSONObject jsonObj = null;
    static String json = "";

    // default no argument constructor for jsonpaser class
    public JSONparser() {

    }

    public JSONObject getJSONFromUrl(String url) {

        // Making HTTP request
        try {
            Log.d("my", "htt client done");
            DefaultHttpClient httpClient = new DefaultHttpClient();
            Log.d("my", "htt ost done");
            HttpPost httpPost = new HttpPost(url);

            // Executing POST request & storing the response from server
            // locally.
            Log.d("my", "htt resonse done");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            Log.d("my", "htt entity done");
            HttpEntity httpEntity = httpResponse.getEntity();
            Log.d("my", "htt entity get content done");
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            // Create a BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            // Declaring string builder
            Log.d("my", "buffer reader crated");
            StringBuilder str = new StringBuilder();
            // string to store the JSON object.
            Log.d("my", "store json object");
            String strLine = null;

            // Building while we have string !equal null.
            Log.d("my", "readline done");
            while ((strLine = reader.readLine()) != null) {
                Log.d("my", "string builder working with string");
                str.append(strLine + "\n");
            }

            // Close inputstream.
            Log.d("my", "close inut stream");
            is.close();
            // string builder data conversion to string.
            Log.d("my", "convert data to string");
            json = str.toString();
        } catch (Exception e) {
            Log.e("Error"," something wrong with converting result " + e.toString());
        }

        // Try block used for pasrseing String to a json object
        try {
            Log.d("my", "arsering data from json");
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("json Parsering", "" + e.toString());
        }

        // Returning json Object.
        return jsonObj;

    }

    public JSONObject makeHttpRequest(String url, String method,
                                      List<NameValuePair> params) {

        // Make HTTP request
        try {

            // checking request method
            if (method == "POST") {

                // now defaultHttpClient object
                Log.d("my", "method equals POST is working");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                Log.d("my", "HTTp client is working");
                HttpPost httpPost = new HttpPost(url);
                Log.d("my", "HTTp post is working");
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                Log.d("my", "url encoded");
                HttpResponse httpResponse = httpClient.execute(httpPost);
                Log.d("my", "HTTp response is working");
                HttpEntity httpEntity = httpResponse.getEntity();
                Log.d("my", "HTTp entity is working");
                is = httpEntity.getContent();
                Log.d("my", "getcontent is working");

            } else if (method == "GET") {
                // request method is GET
                Log.d("my", "method equals POST is working");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                Log.d("my", "HTTp client is working");
                String paramString = URLEncodedUtils.format(params, "utf-8");
                Log.d("my", "HTTp post is working");
                url += "?" + paramString;
                Log.d("my", "url encoded");
                HttpGet httpGet = new HttpGet(url);
                Log.d("my", "HTTp response is working");
                HttpResponse httpResponse = httpClient.execute(httpGet);
                Log.d("my", "HTTp response is working");
                HttpEntity httpEntity = httpResponse.getEntity();
                Log.d("my", "HTTp entity is working");
                is = httpEntity.getContent();
                Log.d("my", "getcontent is working");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            // Create a BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            // Declaring string builder
            Log.d("my", "buffer reader crated");
            StringBuilder str = new StringBuilder();
            // string to store the JSON object.
            Log.d("my", "store json object");
            String strLine = null;

            // Building while we have string !equal null.
            Log.d("my", "readline done");
            while ((strLine = reader.readLine()) != null) {
                Log.d("my", "string builder working with string");
                str.append(strLine + "\n");
            }

            // Close inputstream.
            Log.d("my", "close inut stream");
            is.close();
            // string builder data conversion to string.
            Log.d("my", "convert data to string");
            json = str.toString();
        } catch (Exception e) {
            Log.e("Error"," something wrong with converting result " + e.toString());
        }

        // Try block used for pasrseing String to a json object
        try {
            Log.d("my", "arsering data from json");
            jsonObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("json Parsering", "" + e.toString());
        }

        // Returning json Object.
        return jsonObj;

    }

}