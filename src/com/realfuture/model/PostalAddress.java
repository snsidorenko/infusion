package com.realfuture.model;

import javax.persistence.Embeddable;

@Embeddable
public class PostalAddress extends ModelUtil{
    private String unit = "";
    private String streetNumber = "";
    private String streetName = "";
    private String bldgcplxname = "";
    private String city = "";
    private String state = "";
    private String zip = "";
    protected String country = "";

    public PostalAddress() {
        // Empty
    }

    public PostalAddress(String unit, String streetNumber, String streetName, String bldgcplxname, String city, String state, String zip, String country) {
        this.unit = unit;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.bldgcplxname = bldgcplxname;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBldgcplxname() {
        return bldgcplxname;
    }

    public void setBldgcplxname(String bldgcplxname) {
        this.bldgcplxname = bldgcplxname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine1Short() {
        return encodeNotNull(getStreetNumber()) + encodeNotNull(" ", getStreetName()) +
                encodeNotNull(", #", getUnit()!=null?getUnit().replaceAll("#",""):getUnit());
    }


    public String getStreet(){
        return notNull(encodeNotNull(getStreetNumber()) + encodeNotNull(" ", getStreetName()));
    }


    public String getAptUnit(){
        return notNull(encodeNotNull(getUnit()) + (!isEmptyTrim(getUnit()) && !isEmptyTrim(getBldgcplxname())?"/":"")+ encodeNotNull(getBldgcplxname()));
    }

    
}
