package com.cqprecheck.precheck.Models.Microsoft;

public class Body {
    private String contentType = "Text";
    private String content;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Body(String content) {
        this.content = content;
    }
}
