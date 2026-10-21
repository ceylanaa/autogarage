package nl.novi.autogarage.controllers;

import nl.novi.autogarage.dtos.request.ReparatieHandelingRequestDto;
import nl.novi.autogarage.dtos.response.ReparatieHandelingResponseDto;
import nl.novi.autogarage.services.ReparatieHandelingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reparatiehandelingen")
public class ReparatieHandelingController {

    private final ReparatieHandelingService reparatieHandelingService;

    public ReparatieHandelingController(ReparatieHandelingService reparatieHandelingService) {
        this.reparatieHandelingService = reparatieHandelingService;
    }

    @GetMapping
    public ResponseEntity<List<ReparatieHandelingResponseDto>> getAllReparatieHandelingen() {
        return ResponseEntity.ok(reparatieHandelingService.getAllReparatieHandelingen());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReparatieHandelingResponseDto> getReparatieHandelingById(@PathVariable Long id) {
        return ResponseEntity.ok(reparatieHandelingService.getReparatieHandelingById(id));
    }

    @PostMapping
    public ResponseEntity<ReparatieHandelingResponseDto> createReparatieHandeling(@Valid @RequestBody ReparatieHandelingRequestDto dto) {
        ReparatieHandelingResponseDto nieuw = reparatieHandelingService.createReparatieHandeling(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(nieuw.id).toUri();
        return ResponseEntity.created(uri).body(nieuw);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReparatieHandelingResponseDto> updateReparatieHandeling(@PathVariable Long id, @Valid @RequestBody ReparatieHandelingRequestDto dto) {
        return ResponseEntity.ok(reparatieHandelingService.updateReparatieHandeling(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReparatieHandeling(@PathVariable Long id) {
        reparatieHandelingService.deleteReparatieHandeling(id);
        return ResponseEntity.noContent().build();
    }
}
