package com.realfuture.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "contact")
public class Contact2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PersonName personName = new PersonName();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "title", column = @Column(name = "SPOUSE_TITLE")),
            @AttributeOverride(name = "fname", column = @Column(name = "SPOUSE_FNAME")),
            @AttributeOverride(name = "mi", column = @Column(name = "SPOUSE_MI")),
            @AttributeOverride(name = "lname", column = @Column(name = "SPOUSE_LNAME"))
    })

    private PersonName spouseName= new PersonName();

    @Embedded
    private PostalAddress postalAddress = new PostalAddress();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "unit", column = @Column(name = "unit2")),
            @AttributeOverride(name = "streetNumber", column = @Column(name = "streetNumber2")),
            @AttributeOverride(name = "streetName", column = @Column(name = "streetName2")),
            @AttributeOverride(name = "bldgcplxname", column = @Column(name = "bldgcplxname2")),
            @AttributeOverride(name = "city", column = @Column(name = "city2")),
            @AttributeOverride(name = "state", column = @Column(name = "state2")),
            @AttributeOverride(name = "zip", column = @Column(name = "zip2")),
            @AttributeOverride(name = "country", column = @Column(name = "country2"))
    })
    private PostalAddress postalAddress2 = new PostalAddress();

    @Embedded
    private EmployerInfo employerInfo = new EmployerInfo(null);

    private Date anniversaryDate;
    private Date birthday;
    private String note;
    private String email1;
    private String email2;
    private String spouseemail;
    private String fax;

    private String language;
    private String source;
    private String preferedPhone;

    private String clientsite;
    private String hphone;
    private String cphone;
    private String wphone;
    private String hphone2;

    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

    public PersonName getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(PersonName spouseName) {
        this.spouseName = spouseName;
    }

    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    public PostalAddress getPostalAddress2() {
        return postalAddress2;
    }

    public void setPostalAddress2(PostalAddress postalAddress2) {
        this.postalAddress2 = postalAddress2;
    }

    public Date getAnniversaryDate() {
        return anniversaryDate;
    }

    public void setAnniversaryDate(Date anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getSpouseemail() {
        return spouseemail;
    }

    public void setSpouseemail(String spouseemail) {
        this.spouseemail = spouseemail;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public EmployerInfo getEmployerInfo() {
        return employerInfo;
    }

    public void setEmployerInfo(EmployerInfo employerInfo) {
        this.employerInfo = employerInfo;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPreferedPhone() {
        return preferedPhone;
    }

    public void setPreferedPhone(String preferedPhone) {
        this.preferedPhone = preferedPhone;
    }

    public String getClientsite() {
        return clientsite;
    }

    public void setClientsite(String clientsite) {
        this.clientsite = clientsite;
    }

    public String getHphone() {
        return hphone;
    }

    public void setHphone(String hphone) {
        this.hphone = hphone;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public String getWphone() {
        return wphone;
    }

    public void setWphone(String wphone) {
        this.wphone = wphone;
    }

    public String getHphone2() {
        return hphone2;
    }

    public void setHphone2(String hphone2) {
        this.hphone2 = hphone2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
