package com.rahul.spring.batch.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rahul.spring.batch.entity.Employee;
import com.rahul.spring.batch.repository.EmployeeRepository;

@Component
public class EmployeeWriter implements ItemWriter<Employee> {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeWriter.class);

  @Autowired
  EmployeeRepository employeeRepository;

  @Override
  @Transactional
  public void write(List<? extends Employee> items) throws Exception {
    LOGGER.info("Writing list in DB..");
    employeeRepository.saveAll(items);
  }
}
