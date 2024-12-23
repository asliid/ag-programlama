package com.example.AgProgramlama.service;

import com.example.AgProgramlama.models.OidDescription;
import com.example.AgProgramlama.repository.OidDescriptionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OidDescriptionService {

    private final OidDescriptionRepository oidDescriptionRepository;

    public OidDescriptionService(OidDescriptionRepository oidDescriptionRepository) {
        this.oidDescriptionRepository = oidDescriptionRepository;
    }

    public List<OidDescription> getAllOids() {
        return oidDescriptionRepository.findAll();
    }
}