package nl.novi.autogarage.dtos.response;

public class GebruikerResponseDto {

    private Long id;
    private String gebruikersnaam;
    private String rolNaam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getRolNaam() {
        return rolNaam;
    }

    public void setRolNaam(String rolNaam) {
        this.rolNaam = rolNaam;
    }
}
