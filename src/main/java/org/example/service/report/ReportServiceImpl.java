package org.example.service.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.entity.CitizenEntity;
import org.example.repository.CitizenRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final CitizenRepository citizenRepository;

    public ReportServiceImpl(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Override
    public ByteArrayOutputStream generatePdfReport() {
        List<CitizenEntity> citizens = (List<CitizenEntity>) citizenRepository.findAll();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            PdfPTable table = new PdfPTable(3);
            table.addCell("NIC");
            table.addCell("Birthday");
            table.addCell("Gender");

            for (CitizenEntity citizen : citizens) {
                table.addCell(citizen.getNic());
                table.addCell(citizen.getBirthday());
                table.addCell(citizen.getGender());
            }

            document.add(table);
        } catch (DocumentException e) {
            log.error("Error generating PDF report", e);
        } finally {
            document.close();
        }
        return outputStream;
    }

    @Override
    public List<String[]> generateCsvReportData() {
        List<CitizenEntity> citizens = (List<CitizenEntity>) citizenRepository.findAll();
        return citizens.stream()
                .map(citizen -> new String[]{citizen.getNic(), citizen.getBirthday(), citizen.getGender()})
                .toList();
    }

    @Override
    public ByteArrayOutputStream generateExcelReport() {
        List<CitizenEntity> citizens = (List<CitizenEntity>) citizenRepository.findAll();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Citizens");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("NIC");
            headerRow.createCell(1).setCellValue("Birthday");
            headerRow.createCell(2).setCellValue("Gender");

            int rowNum = 1;
            for (CitizenEntity citizen : citizens) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(citizen.getNic());
                row.createCell(1).setCellValue(citizen.getBirthday());
                row.createCell(2).setCellValue(citizen.getGender());
            }
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("Error generating Excel report", e);
        }
        return outputStream;
    }
}