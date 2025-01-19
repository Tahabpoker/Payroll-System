package com.example.pms.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pms.entity.Attendance;
import com.example.pms.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
	@Autowired
	private AttendanceService attendanceService;
	
	@PostMapping
	public Attendance markAttendance(@RequestBody Attendance attendance) {
		return attendanceService.markAttendance(attendance);
	}
	
	@GetMapping("/employee/{employeeId}")
	public List<Attendance> getAttendanceByEmployeeId(@PathVariable Integer employeeId){
		return attendanceService.getAttendanceByEmployeeId(employeeId);
	}
	
	@GetMapping("/date/{date}")
	public List<Attendance> getAttendanceByDate(@PathVariable LocalDate date){
		return attendanceService.getAttendanceForDate(date);
	}
}
