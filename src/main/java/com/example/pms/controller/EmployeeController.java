package com.example.pms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pms.entity.Employee;
import com.example.pms.service.EmployeeService;
import com.example.pms.utility.Messenger;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Integer id) {
        return new ResponseEntity<>(employeeService.getEmployee(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }
    
    @PutMapping
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
    	return new ResponseEntity<>(employeeService.save(employee), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Integer id) {
        boolean isDeleted = employeeService.deleteEmployee(id);
        
        if (isDeleted) {
            return new ResponseEntity<>(new Messenger("Employee and associated salary records deleted successfully"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Messenger("Employee not found"), HttpStatus.NOT_FOUND);
        }
    }
    
//    @PatchMapping("/{id}")
//    public ResponseEntity<Employee> updateEmployeePartially(@PathVariable Integer id,@RequestBody Map<String,Object> updates){
//    	Employee employee = employeeService.getEmployee(id);
//    	updates.forEach((key, value)->{
//    		Field field = ReflectionUtils.findField(Employee.class, key);
//    		if(field!=null) {
//    			field.setAccessible(true);
//    			ReflectionUtils.setField(field,employee,value);
//    		}
//    	});
//        return new ResponseEntity<>(employeeService.save(employee), HttpStatus.OK);
//
//    }
}

