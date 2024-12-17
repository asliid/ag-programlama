package com.example.AgProgramlama.controller;

import com.example.AgProgramlama.models.Device;
import com.example.AgProgramlama.models.SnmpData;
import com.example.AgProgramlama.service.DeviceService;
import com.example.AgProgramlama.service.SnmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api") // Temel yol belirleniyor
public class SnmpController {

    @Autowired
    private SnmpService snmpService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/snmp") // Tam yol: /api/snmp
    public ResponseEntity<String> getSnmpData(
            @RequestParam String ipAddress,
            @RequestParam String oid
    ) {
        try {
            String result = snmpService.getSnmpData(ipAddress, oid);
            return ResponseEntity.ok(result); // 200 OK ile yanıt
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage()); // 500 Internal Server Error
        }
    }


    @GetMapping("/snmp/all")
    public List<SnmpData> getAllSnmpData() {
        return snmpService.getAllSnmpData();  // Tüm SNMP verilerini döndürür
    }

    // IP adresine göre SNMP verilerini almak için

    @GetMapping("/snmp/ip/{ipAddress}")
    public ResponseEntity<List<SnmpData>> getSnmpDataByIp(@PathVariable String ipAddress) {
        try {
            List<SnmpData> result = snmpService.getSnmpDataByIp(ipAddress);  // Assuming the service returns a list
            return ResponseEntity.ok(result);  // Return list of SnmpData
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList()); // Empty list in case of error
        }
    }
    @GetMapping("/snmp/oid-description")
    public ResponseEntity<String> getOidDescription(@RequestParam String oid) {
        String description = snmpService.getOidDescription(oid);
        return ResponseEntity.ok(description);
    }

    // Cihaza ait SNMP verilerini alma endpoint'i
    /*@GetMapping("/device/{deviceId}/snmp-data")
    public ResponseEntity<List<SnmpData>> getSnmpDataByDeviceId(@RequestParam Long deviceId) {
        List<SnmpData> snmpData = snmpService.getSnmpDataByDevice(deviceId);
        return ResponseEntity.ok(snmpData);
    }*/

    // Cihaza SNMP verisi eklemek için POST endpointi
    @PostMapping("/snmp/device/{deviceId}")
    public ResponseEntity<SnmpData> addSnmpDataToDevice(
            @PathVariable Long deviceId,
            @RequestBody SnmpData snmpData) {
        try {
            // Cihazla ilişkilendirilmiş SNMP verisini ekliyoruz
            SnmpData savedSnmpData = snmpService.addSnmpDataToDevice(deviceId, snmpData);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSnmpData);  // 201 Created ile yanıt
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    // Cihaz ile ilişkili SNMP verilerini getirmek için
   /* @GetMapping("/device/{ipAddress}/snmp-data")
    public ResponseEntity<List<SnmpData>> getSnmpDataByDevice(@PathVariable String ipAddress) {
        Device device = deviceService.findByIpAddress(ipAddress)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        List<SnmpData> snmpDataList = device.getSnmpData(); // Cihazla ilişkilendirilmiş SnmpData'ları alıyoruz

        return ResponseEntity.ok(snmpDataList);
    }*/
    @GetMapping("/device/{ipAddress}/snmp-data")
    public ResponseEntity<List<SnmpData>> getSnmpDataByIpAddress(@PathVariable String ipAddress) {
        Device device = deviceService.findByIpAddress(ipAddress)
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));

        if (device.getSnmpData() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Eğer snmpData null ise
        }

        List<SnmpData> snmpDataList = device.getSnmpData();

        return ResponseEntity.ok(snmpDataList);
    }
}