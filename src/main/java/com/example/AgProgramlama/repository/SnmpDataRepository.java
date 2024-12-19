package com.example.AgProgramlama.repository;

import com.example.AgProgramlama.models.Device;
import com.example.AgProgramlama.models.SnmpData;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SnmpDataRepository extends JpaRepository<SnmpData, Long> {

    List<SnmpData> findAll();

    // IP adresine göre veri almak için
    List<SnmpData> findByIpAddress(String ipAddress);

    List<SnmpData> findByDevice(Device device);  // Cihaza ait SNMP verilerini almak için

    List<SnmpData> findByIpAddressAndOidAndTimestampBetween(String ipAddress, String oid, LocalDateTime start, LocalDateTime end);

}