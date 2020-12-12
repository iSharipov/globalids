package io.github.isharipov.globalids.us.ssn;

import io.github.isharipov.globalids.us.ssn.Ssn;

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
