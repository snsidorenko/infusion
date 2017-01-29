package com.realfuture.model;

import javax.persistence.Embeddable;
/**
 * Created by Serge on 18.01.2017.
 */
@Embeddable
public class EmployerInfo {
    private String empposition;

    public EmployerInfo() {
    }
    public EmployerInfo(String empposition) {
        this.empposition = empposition;
    }

    public String getEmpposition() {
        return empposition;
    }

    public void setEmpposition(String empposition) {
        this.empposition = empposition;
    }
}
