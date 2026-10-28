package nl.novi.autogarage.dtos.response;

public class AutoResponseDto {

    private Long id;
    private String kenteken;
    private String merk;
    private String model;
    private Long klantId;
    private String klantNaam;
    private boolean heeftAutopapier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKenteken() {
        return kenteken;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getKlantId() {
        return klantId;
    }

    public void setKlantId(Long klantId) {
        this.klantId = klantId;
    }

    public String getKlantNaam() {
        return klantNaam;
    }

    public void setKlantNaam(String klantNaam) {
        this.klantNaam = klantNaam;
    }

    public boolean getHeeftAutopapier() {
        return heeftAutopapier;
    }

    public void setHeeftAutopapier(boolean heeftAutopapier) {
        this.heeftAutopapier = heeftAutopapier;
    }
}
