package io.github.isharipov.globalids.uk;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.isharipov.globalids.test.IntegrationTest;
import io.github.isharipov.globalids.uk.nino.Nino;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.io.IOException;
import java.util.Set;

@IntegrationTest
public class EmployeeTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void givenEmployee_whenSaveNino_thenGetOk() {
        Employee employee = new Employee();
        employee.setNino(new Nino("JG103759A"));
        Employee persistedEmployee = employeeRepository.save(employee);
        Assert.assertEquals(1, employeeRepository.findAll().size());
        Nino nino = persistedEmployee.getNino();
        Assert.assertEquals("JG103759A", nino.formattedValue());
        Assert.assertEquals("JG", nino.getPrefix());
        Assert.assertEquals("103759", nino.getDigits());
        Assert.assertEquals("A", nino.getSuffix());
    }

    @Test
    public void givenEmployee_whenValidateNino_thenGetOk() throws IOException {
        EmployeeDto employee = new ObjectMapper().readValue(getClass().getClassLoader().getResourceAsStream("nino/nino.json"), EmployeeDto.class);
        Set<ConstraintViolation<EmployeeDto>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(employee);
        Assert.assertEquals(0, constraintViolations.size());
    }

    @Test
    public void givenEmployee_whenValidateNino_thenGetNotOk() throws IOException {
        EmployeeDto employee = new ObjectMapper().readValue(getClass().getClassLoader().getResourceAsStream("nino/nino-error.json"), EmployeeDto.class);
        Set<ConstraintViolation<EmployeeDto>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(employee);
        Assert.assertEquals(1, constraintViolations.size());
    }
}