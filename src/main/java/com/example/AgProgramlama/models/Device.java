package com.example.AgProgramlama.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Getter
@Setter
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress;
    private String name;

    // Cihaza ait tüm SNMP verileri

    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    @JsonManagedReference // Burada cihazın SnmpData listesine bakıyoruz
    private List<SnmpData> snmpData;  // Cihazla ilişkilendirilmiş SNMP verileri
}