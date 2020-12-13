package io.github.isharipov.globalids.us;

import io.github.isharipov.globalids.us.ssn.Ssn;
import io.github.isharipov.globalids.us.zipcode.ZipCode;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @Convert(converter = Ssn.SsnAttributeConverter.class)
    private Ssn ssn;
    @Column(name = "zip_code")
    @Convert(converter = ZipCode.ZipCodeAttributeConverter.class)
    private ZipCode zipCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Ssn getSsn() {
        return ssn;
    }

    public void setSsn(Ssn ssn) {
        this.ssn = ssn;
    }

    public ZipCode getZipCode() {
        return zipCode;
    }

    public void setZipCode(ZipCode zipCode) {
        this.zipCode = zipCode;
    }
}