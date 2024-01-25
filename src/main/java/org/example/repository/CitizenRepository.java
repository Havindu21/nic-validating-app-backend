package org.example.repository;

import org.example.entity.CitizenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenRepository extends CrudRepository<CitizenEntity, Long> {
    List<CitizenEntity> findByFileName(String csvFileName);

    long countByGender(String male);

    List<CitizenEntity> findByAgeBetween(String i, String i1);

    List<CitizenEntity> findByAgeGreaterThanEqual(String i);
}
