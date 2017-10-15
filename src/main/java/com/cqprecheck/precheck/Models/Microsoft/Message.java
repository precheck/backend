package com.cqprecheck.precheck.Models.Microsoft;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private String subject;
    private Body body;
    private List<Recipient> toRecipients;
    private List<Attachment> attachments;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public List<Recipient> getToRecipients() {
        return toRecipients;
    }

    public void setToRecipients(List<Recipient> toRecipients) {
        this.toRecipients = toRecipients;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Message(String emailAddress){
        this.subject = "CQ Results";
        this.body = new Body("Here are the results of your CQ request");
        List<Recipient> recipients = new ArrayList<>();
        Address address = new Address(emailAddress);
        Recipient recipient = new Recipient(address);
        recipients.add(recipient);
        this.toRecipients = recipients;
        List<Attachment> attachments = new ArrayList<>();
        Attachment attachment = new Attachment("test.txt", "123456");
        attachments.add(attachment);
        this.attachments = attachments;

    }
}
