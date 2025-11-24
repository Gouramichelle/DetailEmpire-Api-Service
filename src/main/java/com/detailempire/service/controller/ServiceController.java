package com.detailempire.service.controller;

import com.detailempire.service.model.ServiceRequest;
import com.detailempire.service.model.ServiceResponse;
import com.detailempire.service.security.UserPrincipal;
import com.detailempire.service.service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin
public class ServiceController {

    private final ServiceService serviceService;

    // -------------------
    //   USO CLIENTE
    // -------------------

    // Para el combo en el formulario de reserva:
    // lista solo servicios activos, cualquier usuario (CLIENT o ADMIN)
    @GetMapping
    public List<ServiceResponse> getActiveServices(
            @AuthenticationPrincipal UserPrincipal user
    ) {
        // solo exigimos que esté autenticado, el rol da igual acá
        return serviceService.getActiveServices();
    }

    // Obtener detalle de un servicio (por si lo necesitaras)
    @GetMapping("/{id}")
    public ServiceResponse getById(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long id
    ) {
        return serviceService.getById(id);
    }

    // -------------------
    //   USO ADMIN
    // -------------------

    private void assertAdmin(UserPrincipal user) {
        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Solo ADMIN puede realizar esta operación");
        }
    }

    // Ver todos los servicios (activos e inactivos) en la pestaña de administración
    @GetMapping("/admin/all")
    public List<ServiceResponse> getAllServices(
            @AuthenticationPrincipal UserPrincipal user
    ) {
        assertAdmin(user);
        return serviceService.getAllServices();
    }

    // Crear servicio (solo ADMIN)
    @PostMapping
    public ServiceResponse create(
            @AuthenticationPrincipal UserPrincipal user,
            @Valid @RequestBody ServiceRequest request
    ) {
        assertAdmin(user);
        return serviceService.create(request);
    }

    // Actualizar servicio (solo ADMIN)
    @PutMapping("/{id}")
    public ServiceResponse update(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long id,
            @Valid @RequestBody ServiceRequest request
    ) {
        assertAdmin(user);
        return serviceService.update(id, request);
    }

    // Desactivar servicio (solo ADMIN)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long id
    ) {
        assertAdmin(user);
        serviceService.deactivate(id);
    }
}
