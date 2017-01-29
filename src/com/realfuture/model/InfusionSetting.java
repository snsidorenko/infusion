package com.realfuture.model;

import org.joda.time.DateTime;
import org.joda.time.Instant;

import java.util.Date;

/**
 * Created by Serge on 16.01.2017.
 */
public class InfusionSetting {
    String token;
    String refreshToken;
    Long readOn;
    int expiredIn;

    public InfusionSetting(String token, String refreshToken, Long readOn, int expiredIn) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.readOn = readOn;
        this.expiredIn = expiredIn;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Date getReadOn() {
        return new Date(readOn);
    }

    public int getExpiredIn() {
        return expiredIn;
    }

    public boolean isExpired(){
        Instant saved = new Instant().withMillis(readOn).toDateTime().plusSeconds(expiredIn).toInstant();
        Instant now = new DateTime().minusSeconds(5).toInstant();
        System.out.print(saved.toDate() + " ? " + now.toDate());
        return saved.isBefore(now);
    }

}
