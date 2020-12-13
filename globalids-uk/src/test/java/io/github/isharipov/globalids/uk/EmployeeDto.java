package io.github.isharipov.globalids.uk;

import io.github.isharipov.globalids.uk.nino.Nino;

public class EmployeeDto {
    @Nino.NinoValidator
    private String nino;

    public String getNino() {
        return nino;
    }

    public void setNino(String nino) {
        this.nino = nino;
    }
}
