package org.example.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CitizenService {
    int validateNICsAndSave(List<MultipartFile> files);
}
