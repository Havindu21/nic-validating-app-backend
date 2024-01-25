package org.example.controller;

import org.example.service.report.ReportService;
import org.example.util.CsvUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/report")
@CrossOrigin
public class ReportController {

    private static final String INLINE_DISPOSITION_TYPE = "inline";
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generatePdfReport() {
        ByteArrayOutputStream pdfStream = reportService.generatePdfReport();
        byte[] pdfBytes = pdfStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(INLINE_DISPOSITION_TYPE, "report.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/csv")
    public ResponseEntity<byte[]> generateCsvReport() {
        List<String[]> csvData = reportService.generateCsvReportData();
        byte[] csvBytes = CsvUtil.convertCsvToBytes(csvData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData(INLINE_DISPOSITION_TYPE, "report.csv");

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/excel")
    public ResponseEntity<byte[]> generateExcelReport() {
        ByteArrayOutputStream excelStream = reportService.generateExcelReport();
        byte[] excelBytes = excelStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData(INLINE_DISPOSITION_TYPE, "report.xlsx");

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}