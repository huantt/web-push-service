package com.huantt.common.webpush

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

/**
 * Read about how to use each field in here: https://docs.eway.vn/x/ewFJAQ
 * @author huantt on 11/4/18
 */
@Builder(builderStrategy = SimpleStrategy)
public class Notification {

    // Visual Options
    String title
    String body
    @JsonProperty('icon')
    String iconURL
    @JsonProperty('image')
    String imageURL
    @JsonProperty('bage')
    String badgeURL
    @JsonProperty('sound')
    String soundURL
    List<Integer> vibrate
    @JsonProperty("dir")
    String direction

    // Behavioural Options
    String tag
    Map<String, Object> data
    @JsonProperty("requireInteraction")
    Boolean requireInteraction
    Boolean renotify
    Boolean silent

    // Both Visual & Behavioural Options
    String[] actions

    // Information Option. No visual affect
    Long timestamp
}
