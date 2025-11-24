package com.detailempire.service.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceResponse {

    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String photoUrl;
    private Boolean active;
}
