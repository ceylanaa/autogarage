package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.KlantRequestDto;
import nl.novi.autogarage.dtos.response.KlantResponseDto;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.Klant;
import nl.novi.autogarage.repositories.KlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KlantServiceTest {

    @Mock
    KlantRepository klantRepository;

    @InjectMocks
    KlantService klantService;

    Klant klant1;
    Klant klant2;
    KlantRequestDto requestDto;

    @BeforeEach
    void setUp() {
        klant1 = new Klant("Jan de Vries", "0612345678", "jan@mail.nl");
        klant1.setId(1L);
        klant2 = new Klant("Piet Bakker", "0687654321", "piet@mail.nl");
        klant2.setId(2L);

        requestDto = new KlantRequestDto();
        requestDto.setNaam("Jan de Vries");
        requestDto.setTelefoonnummer("0612345678");
        requestDto.setEmailadres("jan@mail.nl");
    }

    @Test
    void getAllKlanten_returnsLijst() {
        // Given
        when(klantRepository.findAll()).thenReturn(List.of(klant1, klant2));

        // When
        List<KlantResponseDto> result = klantService.getAllKlanten();

        // Then
        assertEquals(2, result.size());
        assertEquals("Jan de Vries", result.get(0).getNaam());
        assertEquals("Piet Bakker", result.get(1).getNaam());
    }

    @Test
    void getKlantById_bestaandeKlant_returnsDto() {
        // Given
        when(klantRepository.findById(1L)).thenReturn(Optional.of(klant1));

        // When
        KlantResponseDto result = klantService.getKlantById(1L);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("Jan de Vries", result.getNaam());
        assertEquals("0612345678", result.getTelefoonnummer());
        assertEquals("jan@mail.nl", result.getEmailadres());
    }

    @Test
    void getKlantById_onbestaandeKlant_throwsException() {
        // Given
        when(klantRepository.findById(99L)).thenReturn(Optional.empty());

        // When + Then
        assertThrows(RecordNotFoundException.class, () -> klantService.getKlantById(99L));
    }

    @Test
    void createKlant_valideData_returnsDto() {
        // Given
        when(klantRepository.save(any(Klant.class))).thenReturn(klant1);

        // When
        KlantResponseDto result = klantService.createKlant(requestDto);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("Jan de Vries", result.getNaam());
        verify(klantRepository, times(1)).save(any(Klant.class));
    }

    @Test
    void updateKlant_bestaandeKlant_returnsDto() {
        // Given
        requestDto.setNaam("Jan de Vries Jr");
        when(klantRepository.findById(1L)).thenReturn(Optional.of(klant1));
        when(klantRepository.save(any(Klant.class))).thenAnswer(i -> i.getArgument(0));

        // When
        KlantResponseDto result = klantService.updateKlant(1L, requestDto);

        // Then
        assertEquals("Jan de Vries Jr", result.getNaam());
        assertEquals(1L, result.getId());
    }

    @Test
    void updateKlant_onbestaandeKlant_throwsException() {
        // Given
        when(klantRepository.findById(99L)).thenReturn(Optional.empty());

        // When + Then
        assertThrows(RecordNotFoundException.class, () -> klantService.updateKlant(99L, requestDto));
        verify(klantRepository, never()).save(any());
    }

    @Test
    void deleteKlant_bestaandeKlant_roeptDeleteAan() {
        // Given
        when(klantRepository.existsById(1L)).thenReturn(true);

        // When
        klantService.deleteKlant(1L);

        // Then
        verify(klantRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteKlant_onbestaandeKlant_throwsException() {
        // Given
        when(klantRepository.existsById(99L)).thenReturn(false);

        // When + Then
        assertThrows(RecordNotFoundException.class, () -> klantService.deleteKlant(99L));
        verify(klantRepository, never()).deleteById(any());
    }
}
