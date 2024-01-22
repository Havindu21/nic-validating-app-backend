package org.example.controller;

import org.example.service.CitizenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/citizen")
public class CitizenController {

    final CitizenService citizenService;

    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadCsvFiles(@RequestParam("files") List<MultipartFile> files) {
        try {
            int noOfRecords = citizenService.validateNICsAndSave(files);
            return ResponseEntity.ok(noOfRecords + " NICs Validated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("NIC validation Failed : " + e.getMessage());
        }
    }
}