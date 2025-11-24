package com.detailempire.service.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceRequest {

    @NotBlank
    private String name;

    @NotNull
    @Min(0)
    private Integer price;

    private String description;

    // puede ser null
    private String photoUrl;

    // opcional, si no viene → true por defecto
    private Boolean active;
}
