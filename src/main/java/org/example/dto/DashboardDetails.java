package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardDetails {
    private long totalRecords;
    private long maleCitizens;
    private long femaleCitizens;
    private long ageGroup16To30;
    private long ageGroup31To60;
    private long ageGroup60Plus;
}