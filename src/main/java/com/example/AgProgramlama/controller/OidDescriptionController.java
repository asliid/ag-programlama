package com.example.AgProgramlama.controller;

import com.example.AgProgramlama.models.OidDescription;
import com.example.AgProgramlama.service.OidDescriptionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000") // Frontend'inizin çalıştığı port

@RestController
@RequestMapping("/api")
public class OidDescriptionController {

    private final OidDescriptionService service;

    public OidDescriptionController(OidDescriptionService service) {
        this.service = service;
    }

    @GetMapping("/oids")
    public List<OidDescription> getAllOids() {
        return service.getAllOids();
    }
}