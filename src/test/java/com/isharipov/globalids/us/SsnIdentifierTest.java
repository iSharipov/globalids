package com.isharipov.globalids.us;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.io.IOException;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {JpaConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class SsnIdentifierTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void givenEmployee_whenSave_thenGetOk() {
        Employee employee = new Employee();
        employee.setSsn(new Ssn("856-45-6789"));
        Employee persistedEmployee = employeeRepository.save(employee);
        Assert.assertEquals(1, employeeRepository.findAll().size());
        Ssn ssn = persistedEmployee.getSsn();
        Assert.assertEquals("856-45-6789", ssn.formattedValue());
        Assert.assertEquals("856456789", ssn.value());
        Assert.assertEquals("856", ssn.getArea());
        Assert.assertEquals("45", ssn.getGroup());
        Assert.assertEquals("6789", ssn.getSerial());
    }

    @Test
    public void givenEmployee_whenValidate_thenGetOk() throws IOException {
        EmployeeDto employee = new ObjectMapper().readValue(getClass().getClassLoader().getResourceAsStream("us/employee.json"), EmployeeDto.class);
        Set<ConstraintViolation<EmployeeDto>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(employee);
        Assert.assertEquals(0, constraintViolations.size());
    }

    @Test
    public void givenEmployee_whenValidate_thenGetNotOk() throws IOException {
        EmployeeDto employee = new ObjectMapper().readValue(getClass().getClassLoader().getResourceAsStream("us/employee-error.json"), EmployeeDto.class);
        Set<ConstraintViolation<EmployeeDto>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(employee);
        Assert.assertEquals(1, constraintViolations.size());
    }
}