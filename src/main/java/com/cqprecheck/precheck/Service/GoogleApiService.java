package com.cqprecheck.precheck.Service;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.language.v1beta2.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleApiService {

    private static LanguageServiceSettings serviceSettings = null;

    static{
        try {
            ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(GoogleApiService.class.getClassLoader().getResourceAsStream("CQ-Precheck.json"));
            CredentialsProvider provider = FixedCredentialsProvider.create(credentials);
            serviceSettings = LanguageServiceSettings.newBuilder().setCredentialsProvider(provider).build();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Entity> analyzeDocument(String documentText){
        List<Entity> entities = new ArrayList<>();
        try {
            LanguageServiceClient language = LanguageServiceClient.create(serviceSettings);
            Document doc = Document.newBuilder()
                    .setContent(documentText).setType(Document.Type.PLAIN_TEXT).build();

            entities = language.analyzeEntitySentiment(doc, EncodingType.UTF8).getEntitiesList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entities;
    }
}
