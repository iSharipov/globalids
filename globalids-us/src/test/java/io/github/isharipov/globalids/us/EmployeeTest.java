package io.github.isharipov.globalids.us;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.isharipov.globalids.us.ssn.Ssn;
import io.github.isharipov.globalids.us.zipcode.ZipCode;
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
public class EmployeeTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void givenEmployee_whenSaveSsn_thenGetOk() {
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
    public void givenEmployee_whenValidateSsn_thenGetOk() throws IOException {
        EmployeeDto employee = new ObjectMapper().readValue(getClass().getClassLoader().getResourceAsStream("ssn/ssn.json"), EmployeeDto.class);
        Set<ConstraintViolation<EmployeeDto>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(employee);
        Assert.assertEquals(0, constraintViolations.size());
    }

    @Test
    public void givenEmployee_whenValidateSsn_thenGetNotOk() throws IOException {
        EmployeeDto employee = new ObjectMapper().readValue(getClass().getClassLoader().getResourceAsStream("ssn/ssn-error.json"), EmployeeDto.class);
        Set<ConstraintViolation<EmployeeDto>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(employee);
        Assert.assertEquals(1, constraintViolations.size());
    }

    @Test
    public void givenEmployee_whenSaveZipCode_thenGetOk() {
        Employee employee = new Employee();
        employee.setZipCode(new ZipCode("99750-0077"));
        Employee persistedEmployee = employeeRepository.save(employee);
        Assert.assertEquals(1, employeeRepository.findAll().size());
        ZipCode zipCode = persistedEmployee.getZipCode();
        Assert.assertEquals("99750-0077", zipCode.formattedValue());
        Assert.assertEquals("99750 0077", zipCode.value());
        Assert.assertEquals("9", zipCode.getGroupOfStates());
        Assert.assertEquals("97", zipCode.getRegion());
        Assert.assertEquals("50", zipCode.getGroupOfAddresses());
        Assert.assertEquals("0077", zipCode.getGeographicSegment());
    }

    @Test
    public void givenEmployee_whenSaveZipCodeWithoutGeographicSegment_thenGetOk() {
        Employee employee = new Employee();
        employee.setZipCode(new ZipCode("99750"));
        Employee persistedEmployee = employeeRepository.save(employee);
        Assert.assertEquals(1, employeeRepository.findAll().size());
        ZipCode zipCode = persistedEmployee.getZipCode();
        Assert.assertEquals("99750", zipCode.formattedValue());
        Assert.assertEquals("99750", zipCode.value());
        Assert.assertEquals("9", zipCode.getGroupOfStates());
        Assert.assertEquals("97", zipCode.getRegion());
        Assert.assertEquals("50", zipCode.getGroupOfAddresses());
    }

    @Test
    public void givenEmployee_whenSaveZipCodeSpaceInsteadOfHyphen_thenGetOk() {
        Employee employee = new Employee();
        employee.setZipCode(new ZipCode("99750 0077"));
        Employee persistedEmployee = employeeRepository.save(employee);
        Assert.assertEquals(1, employeeRepository.findAll().size());
        ZipCode zipCode = persistedEmployee.getZipCode();
        Assert.assertEquals("99750-0077", zipCode.formattedValue());
        Assert.assertEquals("99750 0077", zipCode.value());
        Assert.assertEquals("9", zipCode.getGroupOfStates());
        Assert.assertEquals("97", zipCode.getRegion());
        Assert.assertEquals("50", zipCode.getGroupOfAddresses());
        Assert.assertEquals("0077", zipCode.getGeographicSegment());
    }

    @Test
    public void givenEmployee_whenValidateZipCode_thenGetOk() throws IOException {
        EmployeeDto employee = new ObjectMapper().readValue(getClass().getClassLoader().getResourceAsStream("zipcode/zipcode.json"), EmployeeDto.class);
        Set<ConstraintViolation<EmployeeDto>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(employee);
        Assert.assertEquals(0, constraintViolations.size());
    }

    @Test
    public void givenEmployee_whenValidateZipCode_thenGetNotOk() throws IOException {
        EmployeeDto employee = new ObjectMapper().readValue(getClass().getClassLoader().getResourceAsStream("zipcode/zipcode-error.json"), EmployeeDto.class);
        Set<ConstraintViolation<EmployeeDto>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(employee);
        Assert.assertEquals(1, constraintViolations.size());
    }
}