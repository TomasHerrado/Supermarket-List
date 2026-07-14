package com.tomasherrado.sl.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateQuantityDTO {

    @Min(1)
    private Integer quantity;

    @NotBlank
    private String userName;
}