package org.example.repository;

import org.example.entity.CitizenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends CrudRepository<CitizenEntity, Long> {
}