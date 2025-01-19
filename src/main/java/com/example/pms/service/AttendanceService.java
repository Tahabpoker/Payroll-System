package com.example.pms.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pms.entity.Attendance;
import com.example.pms.repository.AttendanceRepository;

@Service
public class AttendanceService {
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	public Attendance markAttendance(Attendance attendance) {
		if(attendance.getCheckInTime() != null && attendance.getCheckOutTime() != null) {
			Duration workedHours = Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime());
			attendance.setTotalHoursWorked(workedHours.toHours()+(workedHours.toMinutesPart()/60.0));
		}else {
			attendance.setTotalHoursWorked(0.0);
		}
		return attendanceRepository.save(attendance);
	}
	
	public List<Attendance> getAttendanceByEmployeeId(Integer id){
		return attendanceRepository.findByEmployeeId(id);
	}
	
	public List<Attendance> getAttendanceForDate(LocalDate date){
		return attendanceRepository.findByDate(date);
	}
}
