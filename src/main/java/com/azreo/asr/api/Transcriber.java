/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azreo.asr.api;

import java.io.File;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author toghrul
 */
public class Transcriber {
    private String userId = null;
    private String token = null;
    private String lang = null;
    private static final String API_URL = "http://api.azreco.az/transcribe";
    
    public Transcriber(String userId, String token, String lang) {
        this.userId = userId;
        this.token = token;
        this.lang = lang;
    }
    
    public String transcribe(String audiofile) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = null;
        try {
            HttpPost httpPost = new HttpPost(API_URL);
            FileBody binary = new FileBody(new File(audiofile));
            StringBody idContent = new StringBody(userId, ContentType.TEXT_PLAIN);
            StringBody tokenContent = new StringBody(token, ContentType.TEXT_PLAIN);
            StringBody langContent = new StringBody(lang, ContentType.TEXT_PLAIN);
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("api_id", idContent)
                    .addPart("api_token", tokenContent)
                    .addPart("lang", langContent)
                    .addPart("file", binary)
                    .build();
            httpPost.setEntity(reqEntity);
            CloseableHttpResponse response = null;
            try {
                response = (CloseableHttpResponse) httpClient.execute(httpPost);
                if(response.getStatusLine().getStatusCode() != 200) {
                    System.err.println("Transcription error: " + response.getStatusLine().getReasonPhrase());
                    return null;
                }
                HttpEntity resEntity = response.getEntity();

                try {
                    result = EntityUtils.toString(resEntity);
                    EntityUtils.consume(resEntity);
                } catch (IOException | ParseException ex) {
                    System.err.println("Parsing response failed: " + ex.getMessage());
                }
            } catch (IOException ex) {
                System.err.println("Making transcription request failed: " + ex.getMessage());
                return null;
            }
            finally {
                if(response != null) {
                    try {
                        response.close();
                    } catch (IOException ex) {
                    }
                }
            }
        }
        finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
            }
        }
        return result;
    }
}
