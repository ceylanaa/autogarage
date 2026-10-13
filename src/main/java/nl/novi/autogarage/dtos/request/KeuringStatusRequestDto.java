package nl.novi.autogarage.dtos.request;

import jakarta.validation.constraints.*;

public class KeuringStatusRequestDto {

    @NotBlank
    public String status;
    public String tekortkomingen;
}
