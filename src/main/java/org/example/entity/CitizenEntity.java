package org.example.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
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
    private String fileName;
    @JsonProperty
    private String nic;
    @JsonProperty
    private String age;
    @JsonProperty
    private String birthday;
    @JsonProperty
    private String gender;
}
