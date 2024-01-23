package org.example.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.Citizen;
import org.example.entity.CitizenEntity;
import org.example.repository.CitizenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CitizenServiceImpl implements CitizenService {

    private String age;
    private String gender;
    final CitizenRepository citizenRepository;

    public CitizenServiceImpl(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Override
    public Set<CitizenEntity> validateNICsAndSave(List<MultipartFile> files) {
        Set<CitizenEntity> citizenEntities = parseCsvToEntities(files);
        citizenRepository.saveAll(citizenEntities);
        return citizenEntities;
    }

    @Override
    public List<CitizenEntity> getValidatedNICsByFileName(String csvFileName) {
        return citizenRepository.findByFileName(csvFileName+".csv");
    }

    @Override
    public List<CitizenEntity> getAllValidatedNICs() {
        return (List<CitizenEntity>) citizenRepository.findAll();
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
                            .age(age+"y")
                            .gender(gender)
                            .build()
                    )
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading or processing CSV file: " + e.getMessage(), e);
        }
    }

    private String validateNIC(String nic) {
        Pattern oldNicPattern = Pattern.compile("^\\d{9}[VX]$", Pattern.CASE_INSENSITIVE);
        Pattern newNicPattern = Pattern.compile("^\\d{12}$");

        Matcher oldNicMatcher = oldNicPattern.matcher(nic);
        Matcher newNicMatcher = newNicPattern.matcher(nic);

        if (oldNicMatcher.matches()) {
            return validateOldNic(nic);
        } else if (newNicMatcher.matches()) {
            return validateNewNic(nic);
        } else {
            throw new IllegalArgumentException("Invalid NIC format: " + nic);
        }
    }

    private String validateOldNic(String nic) {
        int birthYear = Integer.parseInt(nic.substring(0, 2));
        int daysFromYearStart = Integer.parseInt(nic.substring(2, 5));

        if(!Year.isLeap(birthYear)) daysFromYearStart-=1;

        if (daysFromYearStart > 500) {
            daysFromYearStart -= 500;
            gender = "female";
        } else {
            gender = "male";
        }

        LocalDate birthDate = LocalDate.ofYearDay(1900 + birthYear, daysFromYearStart);
        age = String.valueOf(LocalDate.now().getYear() - birthDate.getYear()-1);
        return birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private String validateNewNic(String nic) {
        int birthYear = Integer.parseInt(nic.substring(0, 4));
        int daysFromYearStart = Integer.parseInt(nic.substring(4, 7))-1;

        if(!Year.isLeap(birthYear)) daysFromYearStart-=1;

        if (daysFromYearStart > 500) {
            daysFromYearStart -= 500;
            gender = "female";
        } else {
            gender = "male";
        }

        LocalDate birthDate = LocalDate.of(birthYear, 1, 1).plusDays(daysFromYearStart);
        age = String.valueOf(LocalDate.now().getYear() - birthDate.getYear()-1);
        return birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
