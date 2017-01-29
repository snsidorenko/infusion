package com.realfuture.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PersonName extends ModelUtil implements Serializable, Cloneable {

        private String fname;

        private String mi;

        private String lname;

        private String title = "";

        public PersonName() {
            // Empty constructor
        }


        public PersonName(String fname, String mi, String lname) {
            setFname(fname);
            setMi(mi);
            setLname(lname);
        }

        public PersonName(String title, String fname, String mi, String lname) {
            setFname(fname);
            setMi(mi);
            setLname(lname);
            setTitle(title);
        }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
            return join(selectNotBlank(fname, mi, lname), " ");
        }

}
