package org.example.service.citizen;

import org.example.entity.CitizenEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface CitizenService {
    Set<CitizenEntity> validateNICsAndSave(List<MultipartFile> files);

    List<CitizenEntity> getValidatedNICsByFileName(String fileName);

    List<CitizenEntity> getAllValidatedNICs();
}