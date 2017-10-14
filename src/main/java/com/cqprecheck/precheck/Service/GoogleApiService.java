package com.cqprecheck.precheck.Service;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.language.v1beta2.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class GoogleApiService {

    public void analyzeDocument(String documentText){
        ServiceAccountCredentials credentials = null;
        try {
            credentials = ServiceAccountCredentials.fromStream(new FileInputStream(getClass().getClassLoader().getResource("CQ-Precheck.json").getFile()));
            CredentialsProvider provider = FixedCredentialsProvider.create(credentials);
            LanguageServiceSettings serviceSettings = LanguageServiceSettings.newBuilder().setCredentialsProvider(provider).build();
            LanguageServiceClient language = LanguageServiceClient.create(serviceSettings);
            Document doc = Document.newBuilder()
                    .setContent(documentText).setType(Document.Type.PLAIN_TEXT).build();

            // Detects the sentiment of the text
            List<Entity> entities = language.analyzeEntitySentiment(doc, EncodingType.UTF8).getEntitiesList();

            System.out.printf("Text: %s%n", documentText);
            for(Entity entity : entities){
                System.out.println("Entity: " + entity.getName() + " Type: " + entity.getType());
                for (EntityMention mention : entity.getMentionsList()){
                    System.out.println(" mention: " + mention.getType());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
