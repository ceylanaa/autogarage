package nl.novi.autogarage.dtos.response;

import java.math.BigDecimal;

public class ReparatieOnderdeelResponseDto {

    public Long id;
    public Long onderdeelId;
    public String naam;
    public BigDecimal prijsExclBtw;
    public Integer aantal;
}
