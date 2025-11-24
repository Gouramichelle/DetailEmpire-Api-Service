package com.detailempire.service.service;


import com.detailempire.service.model.ServiceRequest;
import com.detailempire.service.model.ServiceResponse;
import com.detailempire.service.model.ServiceEntity;
import com.detailempire.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    // Para el combo de clientes: solo activos
    public List<ServiceResponse> getActiveServices() {
        return serviceRepository.findByActiveTrueOrderByNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Para la pestaña de administración: todos
    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ServiceResponse getById(Long id) {
        ServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        return toResponse(entity);
    }

    public ServiceResponse create(ServiceRequest request) {
        Boolean active = request.getActive() != null ? request.getActive() : true;

        ServiceEntity entity = ServiceEntity.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .photoUrl(request.getPhotoUrl())
                .active(active)
                .build();

        ServiceEntity saved = serviceRepository.save(entity);
        return toResponse(saved);
    }

    public ServiceResponse update(Long id, ServiceRequest request) {
        ServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        entity.setName(request.getName());
        entity.setPrice(request.getPrice());
        entity.setDescription(request.getDescription());
        entity.setPhotoUrl(request.getPhotoUrl());

        if (request.getActive() != null) {
            entity.setActive(request.getActive());
        }

        ServiceEntity saved = serviceRepository.save(entity);
        return toResponse(saved);
    }

    // "Eliminar": lo hacemos como desactivar (active = false)
    public void deactivate(Long id) {
        ServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        entity.setActive(false);
        serviceRepository.save(entity);
    }

    private ServiceResponse toResponse(ServiceEntity e) {
        return ServiceResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .price(e.getPrice())
                .description(e.getDescription())
                .photoUrl(e.getPhotoUrl())
                .active(e.getActive())
                .build();
    }
}
