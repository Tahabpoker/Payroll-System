package com.example.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pms.entity.Salary;
import com.example.pms.entity.SalaryStatistics;
import com.example.pms.service.SalaryService;
import com.example.pms.utility.SalaryResponse;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {
    @Autowired
    private SalaryService salaryService;

    @PostMapping
    public Salary addSalary(@RequestBody Salary salary) {
        return salaryService.addSalary(salary);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Salary> getSalaryByEmployeeId(@PathVariable Integer employeeId) {
        return salaryService.getSalaryByEmployeeId(employeeId);
    }
    
    // Endpoint to get salary statistics
    @GetMapping("/statistics")
    public SalaryStatistics getSalaryStatistics() {
        return salaryService.calculateSalaryStatistics();
    }
    
    // Monthly Salary
    /**
     * Endpoint to calculate monthly salary for an employee.
     * 
     * @param employeeId Employee ID
     * @param month      Month (1-12)
     * @param year       Year (e.g., 2025)
     * @return ResponseEntity containing the calculated salary or error
     */
    @GetMapping("/salary/monthly")
    public ResponseEntity<SalaryResponse> getMonthlySalary(@RequestParam Integer employeeId,@RequestParam int month, @RequestParam int year){
             // Call the service to calculate monthly salary
             BigDecimal monthlySalary = salaryService.calculateMonthlySalary(employeeId, month, year);

             // Prepare the response object
             SalaryResponse response = new SalaryResponse(
                 employeeId,
                 monthlySalary,
                 month + "/" + year
             );

             return ResponseEntity.ok(response); // Return a structured response
    }
    @GetMapping("/salary/{employeeId}")
    public ResponseEntity<List<Salary>> getSalaryReport(@PathVariable Integer employeeId) {
        List<Salary> salaryReport = salaryService.getSalaryByEmployeeId(employeeId);
        return ResponseEntity.ok(salaryReport);
    }

}

