package com.codestates.edusync.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class UriCreator {
    public static URI createUri(String defaultUrl, long resourceId) {
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
    }
    public static URI createUriAdditional(String firstUrl, long resourceId, String secondUrl) {
        return UriComponentsBuilder
                .newInstance()
                .path(firstUrl + "/{resource-id}" + secondUrl)
                .buildAndExpand(resourceId)
                .toUri();
    }
}
