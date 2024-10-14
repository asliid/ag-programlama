package com.example.AgProgramlama.controller;

import com.example.AgProgramlama.models.SnmpData;
import com.example.AgProgramlama.service.SnmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api") // Temel yol belirleniyor
public class SnmpController {

    @Autowired
    private SnmpService snmpService;

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
}