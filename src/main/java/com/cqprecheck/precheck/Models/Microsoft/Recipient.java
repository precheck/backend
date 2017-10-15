package com.cqprecheck.precheck.Models.Microsoft;

public class Recipient {
    private Address emailAddress;

    public Address getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(Address emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Recipient(Address emailAddress) {
        this.emailAddress = emailAddress;
    }
}
