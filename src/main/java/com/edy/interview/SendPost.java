package com.edy.interview;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SendPost {

    private static Logger logger = LoggerFactory.getLogger(SendPost.class);

    /**
     * Send post and return response in string
     * @param urlAsString, url
     * @param entity, json payload
     * @return response from server
     */
    public static String sendPost(String urlAsString, StringEntity entity) {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(urlAsString);


        post.setEntity(entity);
        post.setHeader("Accept","application/json");
        post.setHeader("Content-Type","application/json");

        try {
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();

        } catch (IOException e) {
            logger.error("Url: "+urlAsString+"\tEntity: "+entity,e);
        }

        return "";
    }

}
