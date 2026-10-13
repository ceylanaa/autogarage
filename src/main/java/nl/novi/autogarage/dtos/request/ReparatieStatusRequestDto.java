package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class ReparatieStatusRequestDto {

    @NotBlank
    public String status;
}
