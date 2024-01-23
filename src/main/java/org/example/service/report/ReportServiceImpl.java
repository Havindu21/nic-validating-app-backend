package org.example.service.report;

import org.example.entity.CitizenEntity;
import org.example.repository.CitizenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService{
    private final CitizenRepository citizenRepository;

    public ReportServiceImpl(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Override
    public List<String[]> generatePdfReportData() {
        List<CitizenEntity> citizens = (List<CitizenEntity>) citizenRepository.findAll();
        return citizens.stream()
                .map(this::mapToCsvData)
                .toList();
    }

    @Override
    public List<String[]> generateCsvReportData() {
        return generatePdfReportData();
    }

    @Override
    public List<String[]> generateExcelReportData() {
        return generatePdfReportData();
    }

    private String[] mapToCsvData(CitizenEntity citizen) {
        return new String[]{citizen.getNic(), citizen.getBirthday(), citizen.getGender()};
    }
}
