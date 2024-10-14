package com.example.AgProgramlama.repository;

import com.example.AgProgramlama.models.SnmpData;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnmpDataRepository extends JpaRepository<SnmpData, Long> {

    List<SnmpData> findAll();
}