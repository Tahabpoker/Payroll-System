package com.example.pms.service;

import com.example.pms.entity.Employee;
import com.example.pms.entity.Salary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SalaryService salaryService;

    public byte[] generateSalaryReport(Integer employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        List<Salary> salaries = salaryService.getSalaryByEmployeeId(employeeId);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Title
            document.add(new Paragraph("Salary Report")
                    .setBold()
                    .setFontSize(18)
                    .setMarginBottom(20));

            // Employee Details
            document.add(new Paragraph("Employee Details:")
                    .setBold());
            document.add(new Paragraph("Name: " + employee.getFirstName() + " " + employee.getLastName()));
            document.add(new Paragraph("Department: " + employee.getDepartment()));
            document.add(new Paragraph("Designation: " + employee.getDesignation()));
            document.add(new Paragraph("\n"));

            // Salary Details
            document.add(new Paragraph("Salary Details:")
                    .setBold());

            Table table = new Table(new float[]{100, 100, 100, 100, 100});
            table.addCell("Payment Date");
            table.addCell("Basic Salary");
            table.addCell("Bonus");
            table.addCell("Deductions");
            table.addCell("Total Salary");
            for (Salary salary : salaries) {
                table.addCell(salary.getPaymentDate().toString());
                table.addCell(salary.getBasicSalary().toString());
                table.addCell(salary.getBonus().toString());
                table.addCell(salary.getDeductions().toString());
                table.addCell(salary.getTotalSalary().toString());
            }

            document.add(table);
            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
