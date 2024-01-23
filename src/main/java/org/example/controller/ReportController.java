package org.example.controller;

import org.example.service.report.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/pdf")
    public ResponseEntity<List<String[]>> generatePdfReport() {
        List<String[]> reportData = reportService.generatePdfReportData();
        return ResponseEntity.ok(reportData);
    }

    @GetMapping("/csv")
    public ResponseEntity<List<String[]>> generateCsvReport() {
        List<String[]> reportData = reportService.generateCsvReportData();
        return ResponseEntity.ok(reportData);
    }

    @GetMapping("/excel")
    public ResponseEntity<List<String[]>> generateExcelReport() {
        List<String[]> reportData = reportService.generateExcelReportData();
        return ResponseEntity.ok(reportData);
    }
}