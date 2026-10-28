package nl.novi.autogarage.services;

import nl.novi.autogarage.dtos.request.OnderdeelRequestDto;
import nl.novi.autogarage.dtos.response.OnderdeelResponseDto;
import nl.novi.autogarage.exceptions.RecordNotFoundException;
import nl.novi.autogarage.models.Onderdeel;
import nl.novi.autogarage.repositories.OnderdeelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OnderdeelServiceTest {

    @Mock
    OnderdeelRepository onderdeelRepository;

    @InjectMocks
    OnderdeelService onderdeelService;

    Onderdeel remblokken;
    OnderdeelRequestDto requestDto;

    @BeforeEach
    void setUp() {
        remblokken = new Onderdeel();
        remblokken.setId(1L);
        remblokken.setNaam("Remblokken set");
        remblokken.setPrijsExclBtw(new BigDecimal("45.00"));
        remblokken.setVoorraad(20);

        requestDto = new OnderdeelRequestDto();
        requestDto.setNaam("Remblokken set");
        requestDto.setPrijsExclBtw(new BigDecimal("45.00"));
        requestDto.setVoorraad(20);
    }

    @Test
    void getAllOnderdelen_returnsLijst() {
        // Given
        when(onderdeelRepository.findAll()).thenReturn(List.of(remblokken));

        // When
        List<OnderdeelResponseDto> result = onderdeelService.getAllOnderdelen();

        // Then
        assertEquals(1, result.size());
        assertEquals("Remblokken set", result.get(0).naam);
    }

    @Test
    void getOnderdeelById_bestaandOnderdeel_returnsDto() {
        // Given
        when(onderdeelRepository.findById(1L)).thenReturn(Optional.of(remblokken));

        // When
        OnderdeelResponseDto result = onderdeelService.getOnderdeelById(1L);

        // Then
        assertEquals(1L, result.getId());
        assertEquals(new BigDecimal("45.00"), result.getPrijsExclBtw());
        assertEquals(20, result.getVoorraad());
    }

    @Test
    void getOnderdeelById_onbestaandOnderdeel_throwsException() {
        // Given
        when(onderdeelRepository.findById(99L)).thenReturn(Optional.empty());

        // When + Then
        assertThrows(RecordNotFoundException.class, () -> onderdeelService.getOnderdeelById(99L));
    }

    @Test
    void createOnderdeel_valideData_returnsDto() {
        // Given
        when(onderdeelRepository.save(any(Onderdeel.class))).thenReturn(remblokken);

        // When
        OnderdeelResponseDto result = onderdeelService.createOnderdeel(requestDto);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("Remblokken set", result.getNaam());
        verify(onderdeelRepository).save(any(Onderdeel.class));
    }

    @Test
    void updateOnderdeel_bestaandOnderdeel_returnsDto() {
        // Given
        requestDto.setVoorraad(5);
        requestDto.setPrijsExclBtw(new BigDecimal("50.00"));
        when(onderdeelRepository.findById(1L)).thenReturn(Optional.of(remblokken));
        when(onderdeelRepository.save(any(Onderdeel.class))).thenAnswer(i -> i.getArgument(0));

        // When
        OnderdeelResponseDto result = onderdeelService.updateOnderdeel(1L, requestDto);

        // Then
        assertEquals(5, result.getVoorraad());
        assertEquals(new BigDecimal("50.00"), result.getPrijsExclBtw());
    }

    @Test
    void updateOnderdeel_onbestaandOnderdeel_throwsException() {
        // Given
        when(onderdeelRepository.findById(99L)).thenReturn(Optional.empty());

        // When + Then
        assertThrows(RecordNotFoundException.class, () -> onderdeelService.updateOnderdeel(99L, requestDto));
    }

    @Test
    void deleteOnderdeel_bestaandOnderdeel_roeptDeleteAan() {
        // Given
        when(onderdeelRepository.existsById(1L)).thenReturn(true);

        // When
        onderdeelService.deleteOnderdeel(1L);

        // Then
        verify(onderdeelRepository).deleteById(1L);
    }

    @Test
    void deleteOnderdeel_onbestaandOnderdeel_throwsException() {
        // Given
        when(onderdeelRepository.existsById(99L)).thenReturn(false);

        // When + Then
        assertThrows(RecordNotFoundException.class, () -> onderdeelService.deleteOnderdeel(99L));
        verify(onderdeelRepository, never()).deleteById(any());
    }
}
