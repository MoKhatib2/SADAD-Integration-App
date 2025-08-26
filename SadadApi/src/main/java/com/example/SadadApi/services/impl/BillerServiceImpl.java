package com.example.SadadApi.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.SadadApi.dtos.CodeNameDto;
import com.example.SadadApi.models.Biller;
import com.example.SadadApi.repositories.BillerRepository;
import com.example.SadadApi.responses.CodeNameResponse;
import com.example.SadadApi.responses.GenericResponse;
import com.example.SadadApi.services.BillerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BillerServiceImpl implements BillerService{
    final private BillerRepository billerRepository;
    
    @Override
    public GenericResponse<CodeNameResponse> create(CodeNameDto dto) {
        Optional<Biller> existingBiller = billerRepository.findByCode(dto.code());
        if (existingBiller.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another biller with this code");
        }

        Biller biller = new Biller();
        biller.setCode(dto.code());
        biller.setName(dto.name());

        Biller saved = billerRepository.save(biller);

        CodeNameResponse response = new CodeNameResponse(saved.getId(), saved.getCode(), saved.getName());
        return new GenericResponse<>("Biller created successfully", response);
    }

    @Override
    public GenericResponse<CodeNameResponse> update(CodeNameDto dto, Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Biller id is required");
        }

        Biller biller = billerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Biller not found"));

        // Ensure unique code
        Optional<Biller> existing = billerRepository.findByCode(dto.code());
        if (existing.isPresent() && !existing.get().getId().equals(biller.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is another biller with this code");
        }

        if (biller.getCode() != null) {
            biller.setCode(dto.code());
        }
        if (biller.getName() != null) {
            biller.setName(dto.name());
        }
        
        Biller updated = billerRepository.save(biller);

        CodeNameResponse response = new CodeNameResponse(updated.getId(), updated.getCode(), updated.getName());
        return new GenericResponse<>("Biller updated successfully", response);
    }

    @Override
    public GenericResponse<List<CodeNameResponse>> findAll() {
        List<CodeNameResponse> billers = billerRepository.findAll().stream()
                .map(b -> new CodeNameResponse(b.getId(), b.getCode(), b.getName()))
                .toList();

        return new GenericResponse<>("Billers retrieved successfully", billers);
    }

    @Override
    public GenericResponse<CodeNameResponse> findById(Long id) {
        Biller biller = billerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Biller not found"));

        CodeNameResponse response = new CodeNameResponse(biller.getId(), biller.getCode(), biller.getName());
        return new GenericResponse<>("Biller retrieved successfully", response);
    }

}
