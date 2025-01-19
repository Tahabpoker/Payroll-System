package com.example.pms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pms.entity.Employee;
import com.example.pms.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SalaryService salaryService;
    
    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    @Transactional
    public boolean deleteEmployee(Integer id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            // Delete associated salary records first
            salaryService.deleteSalaryByEmployeeId(id);

            // Then delete the employee record
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }}
