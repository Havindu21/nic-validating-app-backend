package org.example.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitizenEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String nic;
    private String age;
    private String birthday;
    private String gender;
}
