package com.abhee.login.springbatch;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.abhee.login.model.Employee;
@Component
public class BatchItemProcessor implements org.springframework.batch.item.ItemProcessor<Employee, Employee> {
	private static final Logger logger = LogManager.getLogger(BatchItemProcessor.class);
	
	private static final Map<String,String> DEPT_NAMES = new HashMap<>();
	
	public BatchItemProcessor() {
		DEPT_NAMES.put("001", "Human Resource");
		DEPT_NAMES.put("002", "Admin");
		DEPT_NAMES.put("003", "Information Technology");
	}

	@Override
	public Employee process(Employee employee) throws Exception {
		String deptName = DEPT_NAMES.get(employee.getDept());
		logger.info("Getting department name for code :"+employee.getDept()+" --> "+deptName);
		employee.setDept(deptName);
		return employee;
	}

}
