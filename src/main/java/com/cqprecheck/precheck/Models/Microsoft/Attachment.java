package com.cqprecheck.precheck.Models.Microsoft;

import com.cqprecheck.precheck.Storage.StorageService;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Attachment {

    //private String fileType = "#microsoft.graph.fileAttachment";
    private String name;
    private String contentBytes;

    @JsonGetter("@odata.type")
    public String getOdata123() {
        return "#microsoft.graph.fileAttachment";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentBytes() {
        return contentBytes;
    }

    public void setContentBytes(String contentBytes) {
        this.contentBytes = contentBytes;
    }

    public Attachment(String name, String contentBytes) {
        this.name = name;
        this.contentBytes = contentBytes;
    }

    public Attachment(String name){
        this.name = name;
    }

    public void encodeFileToBase64Binary(StorageService service) throws IOException {
        File file = service.load(this.name).toFile();
        FileInputStream in = new FileInputStream(file);
        byte fileData[] = new byte[(int) file.length()];
        in.read(fileData);
        this.contentBytes = Base64.encodeBase64String(fileData);
    }
}
