package nl.novi.autogarage.controllers;

import nl.novi.autogarage.dtos.request.OnderdeelRequestDto;
import nl.novi.autogarage.dtos.response.OnderdeelResponseDto;
import nl.novi.autogarage.services.OnderdeelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/onderdelen")
public class OnderdeelController {

    private final OnderdeelService onderdeelService;

    public OnderdeelController(OnderdeelService onderdeelService) {
        this.onderdeelService = onderdeelService;
    }

    @GetMapping
    public ResponseEntity<List<OnderdeelResponseDto>> getAllOnderdelen() {
        return ResponseEntity.ok(onderdeelService.getAllOnderdelen());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OnderdeelResponseDto> getOnderdeelById(@PathVariable Long id) {
        return ResponseEntity.ok(onderdeelService.getOnderdeelById(id));
    }

    @PostMapping
    public ResponseEntity<OnderdeelResponseDto> createOnderdeel(@Valid @RequestBody OnderdeelRequestDto dto) {
        OnderdeelResponseDto nieuw = onderdeelService.createOnderdeel(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(nieuw.getId()).toUri();
        return ResponseEntity.created(uri).body(nieuw);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OnderdeelResponseDto> updateOnderdeel(@PathVariable Long id, @Valid @RequestBody OnderdeelRequestDto dto) {
        return ResponseEntity.ok(onderdeelService.updateOnderdeel(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderdeel(@PathVariable Long id) {
        onderdeelService.deleteOnderdeel(id);
        return ResponseEntity.noContent().build();
    }
}
