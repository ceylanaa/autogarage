package nl.novi.autogarage.dtos.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BonResponseDto {

    public Long reparatieId;
    public String kenteken;
    public String klantNaam;
    public BigDecimal keuringBedragExcl;
    public BigDecimal keuringBedragIncl;
    public List<BonRegelDto> handelingen = new ArrayList<>();
    public List<BonRegelDto> overigeHandelingen = new ArrayList<>();
    public List<BonOnderdeelRegelDto> onderdelen = new ArrayList<>();
    public BigDecimal totalExcl;
    public BigDecimal btwBedrag;
    public BigDecimal totalIncl;
    public boolean betaald;
}
