package com.rahul.spring.batch.processor;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.rahul.spring.batch.entity.Employee;

public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeProcessor.class);

  @Override
  public Employee process(Employee employee) throws Exception {
    employee.setEmployeeId(generateEmployeeId(employee));
    LOGGER.info(employee.toString());
    return employee;
  }

  private String generateEmployeeId(Employee employee) {
    Random random = new Random();
    Integer randomId = random.ints(111111, 999999).findFirst().getAsInt();
    return employee.getFirstName().substring(0, 1) + employee.getLastName().substring(0, 1) + randomId;
  }
}
