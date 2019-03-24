package com.abhee.login.springbatch;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abhee.login.model.Employee;
import com.abhee.login.repository.EmployeeRepository;

@Component
public class DBWriter implements ItemWriter<Employee> {
	
	private static final Logger logger = LogManager.getLogger(DBWriter.class);
     
	@Autowired
	private EmployeeRepository employeeRepository;
	@Override
	public void write(List<? extends Employee> employee) throws Exception {
		logger.info("Saving all employee");
		List<? extends Employee> saveAll = employeeRepository.saveAll(employee);
		logger.info("Saved all employee" +saveAll);
	}

}
