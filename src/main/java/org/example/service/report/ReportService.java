package org.example.service.report;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface ReportService {
    ByteArrayOutputStream generatePdfReport();

    ByteArrayOutputStream generateExcelReport();

    List<String[]> generateCsvReportData();
}