package com.example.AgProgramlama.repository;

import com.example.AgProgramlama.models.OidDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OidDescriptionRepository extends JpaRepository<OidDescription, Long> {
    // OID'ye göre açıklamayı almak için metod
    OidDescription findByOid(String oid);
}