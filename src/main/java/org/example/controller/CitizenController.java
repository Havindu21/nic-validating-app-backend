package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.CitizenEntity;
import org.example.service.CitizenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/citizen")
@Slf4j
public class CitizenController {

    final CitizenService citizenService;

    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<Set<CitizenEntity>> uploadCsvFiles(@RequestParam("csvFiles") List<MultipartFile> csvFiles) {
        Set<CitizenEntity> citizenEntities = citizenService.validateNICsAndSave(csvFiles);
        return ResponseEntity.ok(citizenEntities);
    }

    @GetMapping("/validated-nics/{csvFileName}")
    public ResponseEntity<List<CitizenEntity>> getValidatedNICsByFileName(@PathVariable String csvFileName) {
        List<CitizenEntity> validatedNICs = citizenService.getValidatedNICsByFileName(csvFileName);
        return ResponseEntity.ok(validatedNICs);
    }

    @GetMapping("/validated-nics")
    public ResponseEntity<List<CitizenEntity>> getAllValidatedNICs() {
        List<CitizenEntity> validatedNICs = citizenService.getAllValidatedNICs();
        return ResponseEntity.ok(validatedNICs);
    }
}