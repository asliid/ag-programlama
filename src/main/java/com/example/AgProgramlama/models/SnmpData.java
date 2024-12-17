package com.example.AgProgramlama.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Data
//@Table(name="snmp")
public class SnmpData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipAddress;
    private String oid;
    private String value;
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }

    // Cihazla ilişkilendirme
    @ManyToOne
    @JoinColumn(name = "device_id")
    @JsonBackReference // Bu, Device ile ilişkilendirilen SnmpData'ya geri referans oluşturuyor
    private Device device;  // Cihaz bilgisi

}
