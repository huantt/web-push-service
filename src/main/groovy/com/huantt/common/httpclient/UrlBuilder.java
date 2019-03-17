package com.huantt.common.httpclient;

import okhttp3.HttpUrl;

import java.util.List;
import java.util.Map;

/**
 * @author huantt on 10/23/18
 * This class provides method to build okhttp3.HttpUrl
 */
public class UrlBuilder {

    private HttpUrl.Builder urlBuilder;

    public UrlBuilder(String url) {
        urlBuilder = HttpUrl.get(url).newBuilder();
    }

    public UrlBuilder addQueryParameter(String name, String value) {
        this.urlBuilder.addQueryParameter(name, value);
        return this;
    }

    public UrlBuilder addQueryParameters(Map<String, String> queryParameters) {
        queryParameters.forEach((name, value) -> this.urlBuilder.addQueryParameter(name, value));
        return this;
    }

    public UrlBuilder addPath(String path) {
        this.urlBuilder.addPathSegment(path);
        return this;
    }

    public UrlBuilder addPaths(List<String> paths) {
        paths.forEach(path -> this.urlBuilder.addPathSegment(path));
        return this;
    }

    public UrlBuilder basicAuth(String username,String password){
        this.urlBuilder.username(username);
        this.urlBuilder.password(password);
        return this;
    }

    /**
     * @return okhttp3.HttpUrl
     * */
    public HttpUrl build() {
        return this.urlBuilder.build();
    }
}