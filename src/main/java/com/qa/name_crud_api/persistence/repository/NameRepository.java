package com.qa.name_crud_api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.name_crud_api.persistence.domain.BabyName;

@Repository
public interface NameRepository extends JpaRepository<BabyName, Long> {
}
