package io.github.isharipov.globalids.us;

import io.github.isharipov.globalids.us.ssn.Ssn;
import io.github.isharipov.globalids.us.zipcode.ZipCode;

public class EmployeeUsDto {
    @Ssn.SsnValidator
    private String ssn;
    @ZipCode.ZipCodeValidator
    private String zipCode;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
