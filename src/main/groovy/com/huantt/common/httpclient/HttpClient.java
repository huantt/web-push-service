package com.huantt.common.httpclient;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author huantt on 10/23/18
 */
public class HttpClient {

    private static final int DEFAULT_TIME_OUT = 5; // Minutes

    private static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MINUTES)
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.MINUTES)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.MINUTES)
                .build();
    }

    public static void setClient(OkHttpClient client) {
        HttpClient.client = client;
    }

    public static Response get(String url, Map<String, Object> headers) throws IOException {
        HttpUrl httpUrl = new UrlBuilder(url).build();
        return HttpClient.get(httpUrl, headers);
    }

    public static Response get(HttpUrl httpUrl, Map<String, Object> headers) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(httpUrl)
                .get();
        if (headers != null) {
            headers.forEach((key, value) -> {
                requestBuilder.addHeader(key, String.valueOf(value));
            });
        }

        Request request = requestBuilder.build();
        return client.newCall(request).execute();
    }

    public static Response post(String url, Map<String, Object> headers, String json) throws IOException {
        HttpUrl httpUrl = new UrlBuilder(url).build();
        return HttpClient.post(httpUrl, headers, json);
    }

    public static Response post(HttpUrl httpUrl, Map<String, Object> headers, String json) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);

        Request.Builder requestBuilder = new Request.Builder()
                .url(httpUrl);
        if (headers != null) {
            headers.forEach((key, value) -> {
                requestBuilder.addHeader(key, String.valueOf(value));
            });
        }

        Request request = requestBuilder.post(body).build();
        return client.newCall(request).execute();
    }

    public static Response post(String url, Map<String, Object> headers, Map<String, Object> form) throws IOException {
        HttpUrl httpUrl = new UrlBuilder(url).build();
        return HttpClient.post(httpUrl, headers, form);
    }

    public static Response post(HttpUrl httpUrl, Map<String, Object> headers, Map<String, Object> form) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(httpUrl);
        if (headers != null) {
            headers.forEach((key, value) -> {
                requestBuilder.addHeader(key, String.valueOf(value));
            });
        }

        FormBody.Builder formBodyBuidler = new FormBody.Builder();
        if (form != null) {
            form.forEach((key, value) -> {
                formBodyBuidler.add(key, String.valueOf(value));
            });
        }
        RequestBody body = formBodyBuidler.build();

        Request request = requestBuilder.post(body).build();
        return client.newCall(request).execute();
    }

    public static Response put(String url, Map<String, Object> headers, Map<String, Object> form) throws IOException {
        HttpUrl httpUrl = new UrlBuilder(url).build();
        return HttpClient.put(httpUrl, headers, form);
    }

    public static Response put(HttpUrl httpUrl, Map<String, Object> headers, Map<String, Object> form) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(httpUrl);
        if (headers != null) {
            headers.forEach((key, value) -> {
                requestBuilder.addHeader(key, String.valueOf(value));
            });
        }

        FormBody.Builder formBodyBuidler = new FormBody.Builder();
        if (form != null) {
            form.forEach((key, value) -> {
                formBodyBuidler.add(key, String.valueOf(value));
            });
        }
        RequestBody body = formBodyBuidler.build();

        Request request = requestBuilder.put(body).build();
        return client.newCall(request).execute();
    }

    public static Response put(String url, Map<String, Object> headers, String json) throws IOException {
        HttpUrl httpUrl = new UrlBuilder(url).build();
        return HttpClient.put(httpUrl, headers, json);
    }

    public static Response put(HttpUrl httpUrl, Map<String, Object> headers, String json) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);

        Request.Builder requestBuilder = new Request.Builder()
                .url(httpUrl);
        if (headers != null) {
            headers.forEach((key, value) -> {
                requestBuilder.addHeader(key, String.valueOf(value));
            });
        }

        Request request = requestBuilder.put(body).build();
        return client.newCall(request).execute();
    }

    public static Response delete(String url, Map<String, Object> headers) throws IOException {
        HttpUrl httpUrl = new UrlBuilder(url).build();
        return HttpClient.delete(httpUrl, headers);
    }

    public static Response delete(HttpUrl httpUrl, Map<String, Object> headers) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(httpUrl)
                .delete();
        if (headers != null) {
            headers.forEach((key, value) -> {
                requestBuilder.addHeader(key, String.valueOf(value));
            });
        }

        Request request = requestBuilder.build();
        return client.newCall(request).execute();
    }
}
