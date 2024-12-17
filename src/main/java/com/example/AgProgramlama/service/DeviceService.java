package com.example.AgProgramlama.service;

import com.example.AgProgramlama.models.Device;
import com.example.AgProgramlama.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
    // IP adresine göre cihazı bulan metod
    public Device getDeviceByIpAddress(String ipAddress) {
        return deviceRepository.findByIpAddress(ipAddress)
                .orElseThrow(() -> new IllegalArgumentException("Device not found")); // Cihaz bulunamazsa hata fırlat
    }   // IP adresine göre cihazı bulan metot

    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    public Device updateDevice(Long id, Device deviceDetails) {
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isPresent()) {
            Device existingDevice = device.get();
            existingDevice.setIpAddress(deviceDetails.getIpAddress());
            existingDevice.setName(deviceDetails.getName());
            return deviceRepository.save(existingDevice);
        }
        return null;
    }

    public boolean deleteDevice(Long id) {
        Optional<Device> device = deviceRepository.findById(id);
        if (device.isPresent()) {
            deviceRepository.delete(device.get());
            return true;
        }
        return false;
    }

    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    // IP adresine göre cihazı bulacak metodu ekliyoruz
    public Optional<Device> findByIpAddress(String ipAddress) {
        return deviceRepository.findByIpAddress(ipAddress);
    }


}