package com.cqprecheck.precheck.Service;

import com.cqprecheck.precheck.Models.Microsoft.Message;
import com.cqprecheck.precheck.Models.Microsoft.MessageHolder;
import okhttp3.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class MicrosoftGraphService {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    private String url = "https://graph.microsoft.com/beta/me/sendMail";


    public void sendMail(MessageHolder messageHolder, String oAuthKey){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String messageAsString = objectMapper.writeValueAsString(messageHolder);
            messageAsString = messageAsString.replace("odata123", "@odata.type");
            int response = post(messageAsString, oAuthKey);
            System.out.println("response: " + response);
            System.out.println(messageAsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int post(String json, String oAuthKey) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + oAuthKey)
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.code();
    }
}
