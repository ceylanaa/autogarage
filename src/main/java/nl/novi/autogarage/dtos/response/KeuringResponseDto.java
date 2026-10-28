package nl.novi.autogarage.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class KeuringResponseDto {

    private Long id;
    private Long autoId;
    private String kenteken;
    private LocalDate keuringsDatum;
    private String tekortkomingen;
    private String status;
    private BigDecimal keuringsBedrag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }

    public String getKenteken() {
        return kenteken;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public LocalDate getKeuringsDatum() {
        return keuringsDatum;
    }

    public void setKeuringsDatum(LocalDate keuringsDatum) {
        this.keuringsDatum = keuringsDatum;
    }

    public String getTekortkomingen() {
        return tekortkomingen;
    }

    public void setTekortkomingen(String tekortkomingen) {
        this.tekortkomingen = tekortkomingen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getKeuringsBedrag() {
        return keuringsBedrag;
    }

    public void setKeuringsBedrag(BigDecimal keuringsBedrag) {
        this.keuringsBedrag = keuringsBedrag;
    }
}
