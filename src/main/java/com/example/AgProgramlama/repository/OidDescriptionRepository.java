package com.example.AgProgramlama.repository;

import com.example.AgProgramlama.models.OidDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OidDescriptionRepository extends JpaRepository<OidDescription, Long> {
    // OID'ye göre açıklamayı almak için metod
   OidDescription findByOid(String oid);
    //Optional<OidDescription> findByOid(String oid); // Optional döndüren metod

}