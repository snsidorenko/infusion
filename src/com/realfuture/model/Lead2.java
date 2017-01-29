package com.realfuture.model;

import javax.persistence.*;

/**
 * Created by Serge on 18.01.2017.
 */
@Entity
@Table(name = "web_lead")
public class Lead2 {
    @Id
    private Long id;

    @OneToOne(targetEntity = Contact2.class)
    private Contact2 contact;

    private String zip4;

    public Contact2 getContact() {
        return contact;
    }

    public void setContact(Contact2 contact) {
        this.contact = contact;
    }

    public String getZip4() {
        return zip4;
    }

    public void setZip4(String zip4) {
        this.zip4 = zip4;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lead2() {

    }

    public Lead2(Contact2 contact, String zip4) {
        this.contact = contact;
        this.zip4 = zip4;
    }

}
