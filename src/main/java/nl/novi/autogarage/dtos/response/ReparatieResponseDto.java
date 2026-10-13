package nl.novi.autogarage.dtos.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReparatieResponseDto {

    public Long id;
    public Long keuringId;
    public LocalDate reparatieDatum;
    public String status;
    public boolean betaald;
    public List<ReparatieOnderdeelResponseDto> onderdelen = new ArrayList<>();
    public List<ReparatieRegelResponseDto> handelingen = new ArrayList<>();
    public List<OverigeHandelingResponseDto> overigeHandelingen = new ArrayList<>();
}
