package org.example.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.example.dto.Citizen;
import org.example.entity.CitizenEntity;
import org.example.repository.CitizenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CitizenServiceImpl implements CitizenService {

    private String age;
    private String gender;
    final CitizenRepository citizenRepository;

    public CitizenServiceImpl(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Override
    public int validateNICsAndSave(List<MultipartFile> files) {
        Set<CitizenEntity> citizenEntities = parseCsvToEntities(files);
        citizenRepository.saveAll(citizenEntities);
        return citizenEntities.size();
    }

    private Set<CitizenEntity> parseCsvToEntities(List<MultipartFile> files) {
        return files.stream()
                .map(this::parseCsvFile)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    private List<CitizenEntity> parseCsvFile(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<Citizen> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Citizen.class);
            CsvToBean<Citizen> csvToBean = new CsvToBeanBuilder<Citizen>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse()
                    .stream()
                    .map(csvLine -> CitizenEntity.builder()
                            .fileName(file.getOriginalFilename())
                            .nic(csvLine.getNic())
                            .birthday(validateNIC(csvLine.getNic()))
                            .age(age)
                            .gender(gender)
                            .build()
                    )
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }

    private String validateNIC(String nic) {
        age="10";
        gender="male";
        return "2002 july 11";
    }
}
