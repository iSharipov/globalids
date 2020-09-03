package com.isharipov.globalids.us;

import javax.validation.Valid;

public class EmployeeDto {
    @Ssn.SsnValidator
    private String ssn;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
