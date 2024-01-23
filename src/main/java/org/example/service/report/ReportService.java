package org.example.service.report;

import java.util.List;

public interface ReportService {
    List<String[]> generatePdfReportData();
    List<String[]> generateCsvReportData();
    List<String[]> generateExcelReportData();
}
