package com.example.ixirus;
import android.content.Context;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class HttpUtils  {
    public static final String BASE_URL = "https://ixirus.azurewebsites.net/api/auth";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(Context context, String url, Header[] headers, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.get(context,getAbsoluteUrl(url),headers, params, responseHandler);
    }

    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void getByUrl(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}