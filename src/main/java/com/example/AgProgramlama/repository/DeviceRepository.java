package com.example.AgProgramlama.repository;

import com.example.AgProgramlama.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    // IP adresine göre cihaz arama
   //Device findByIpAddress(String ipAddress);
    Optional<Device> findByIpAddress(String ipAddress);

}