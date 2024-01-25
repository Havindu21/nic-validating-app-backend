package org.example.service.dashboard;

import org.example.dto.DashboardDetails;
import org.example.entity.CitizenEntity;
import org.example.repository.CitizenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    final CitizenRepository citizenRepository;

    public DashboardServiceImpl(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Override
    public DashboardDetails getDashboardDetails() {
        List<CitizenEntity> ageGroup16To30 = citizenRepository.findByAgeBetween("16", "30");
        List<CitizenEntity> ageGroup31To60 = citizenRepository.findByAgeBetween("31", "60");
        List<CitizenEntity> ageGroup60Plus = citizenRepository.findByAgeGreaterThanEqual("60");

        return DashboardDetails.builder()
                .totalRecords(citizenRepository.count())
                .maleCitizens(citizenRepository.countByGender("male"))
                .femaleCitizens(citizenRepository.countByGender("female"))
                .ageGroup16To30(ageGroup16To30.size())
                .ageGroup31To60(ageGroup31To60.size())
                .ageGroup60Plus(ageGroup60Plus.size())
                .build();
    }
}