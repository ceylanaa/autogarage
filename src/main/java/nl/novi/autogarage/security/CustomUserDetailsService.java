package nl.novi.autogarage.security;

import nl.novi.autogarage.models.Gebruiker;
import nl.novi.autogarage.repositories.GebruikerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final GebruikerRepository gebruikerRepository;

    public CustomUserDetailsService(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Gebruiker gebruiker = gebruikerRepository.findByGebruikersnaam(username)
                .orElseThrow(() -> new UsernameNotFoundException("Gebruiker " + username + " niet gevonden"));

        return new User(gebruiker.getGebruikersnaam(), gebruiker.getWachtwoord(),
                List.of(new SimpleGrantedAuthority("ROLE_" + gebruiker.getRol().getNaam())));
    }
}
