package com.edy.interview.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class User {

    private long uid;
    private String token;
    private String apiToken;
    private boolean jsonStrictMode;
    private boolean jsonVerboseResponse;

    /**
     * Class representing user
     * @param uid, user id
     * @param token, user token
     * @param apiToken, user api token
     * @param jsonStrictMode, json strict mode
     * @param jsonVerboseResponse, json verbose response
     */
    public User(long uid, String token, String apiToken, boolean jsonStrictMode, boolean jsonVerboseResponse){
        this.uid = uid;
        this.token = token;
        this.apiToken = apiToken;
        this.jsonStrictMode = jsonStrictMode;
        this.jsonVerboseResponse = jsonVerboseResponse;
    }

    @JsonProperty("uid")
    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("api-token")
    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    @JsonProperty("json-strict-mode")
    public boolean isJsonStrictMode() {
        return jsonStrictMode;
    }

    public void setJsonStrictMode(boolean jsonStrictMode) {
        this.jsonStrictMode = jsonStrictMode;
    }

    @JsonProperty("json-verbose-response")
    public boolean isJsonVerboseResponse() {
        return jsonVerboseResponse;
    }

    public void setJsonVerboseResponse(boolean jsonVerboseResponse) {
        this.jsonVerboseResponse = jsonVerboseResponse;
    }
}
