package org.example.controller;

import org.example.dto.DashboardDetails;
import org.example.service.dashboard.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/details")
    public ResponseEntity<DashboardDetails> getDashboardSummary() {
        DashboardDetails summary = dashboardService.getDashboardDetails();
        return ResponseEntity.ok(summary);
    }
}