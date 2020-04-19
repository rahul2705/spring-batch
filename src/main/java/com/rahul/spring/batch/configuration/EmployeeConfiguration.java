package com.rahul.spring.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.rahul.spring.batch.entity.Employee;
import com.rahul.spring.batch.processor.EmployeeProcessor;

@Configuration
@EnableBatchProcessing
public class EmployeeConfiguration {

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  @Autowired
  EmployeeWriter employeeWriter;

  @Bean
  public FlatFileItemReader<Employee> reader() {
    return new FlatFileItemReaderBuilder<Employee>().name("employeeItemReader")
        .resource(new ClassPathResource("employee-data.csv"))
        .delimited()
        .names(new String[] {"firstName", "lastName"})
        .fieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {{
          setTargetType(Employee.class);
        }})
        .build();
  }

  @Bean
  public EmployeeProcessor employeeProcessor() {
    return new EmployeeProcessor();
  }
  //  @Bean
  //  public JdbcBatchItemWriter<Employee> writer(DataSource dataSource) {
  //    return new JdbcBatchItemWriterBuilder<Employee>().itemSqlParameterSourceProvider(
  //        new BeanPropertyItemSqlParameterSourceProvider<>())
  //        .sql("INSERT INTO employee (employee_id,first_name, last_name) VALUES (:employeeId,:firstName, :lastName)")
  //        .dataSource(dataSource)
  //        .build();
  //  }

  @Bean
  public Job importUserJob(JobCompletionListener listener, Step step1) {
    return jobBuilderFactory.get("importEmployeeJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step1)
        .end()
        .build();
  }

  @Bean
  public TaskExecutor taskExecutor() {
    SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor("spring_batch");
    asyncTaskExecutor.setConcurrencyLimit(5);
    return asyncTaskExecutor;
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1").<Employee, Employee>chunk(100).reader(reader())
        .processor(employeeProcessor())
        .writer(employeeWriter)
        .taskExecutor(taskExecutor())
        .build();
  }
}
