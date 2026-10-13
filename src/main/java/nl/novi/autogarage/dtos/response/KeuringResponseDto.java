package nl.novi.autogarage.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class KeuringResponseDto {

    public Long id;
    public Long autoId;
    public String kenteken;
    public LocalDate keuringsDatum;
    public String tekortkomingen;
    public String status;
    public BigDecimal keuringsBedrag;
}
