package nl.novi.autogarage.controllers;

import nl.novi.autogarage.dtos.request.KeuringRequestDto;
import nl.novi.autogarage.dtos.request.KeuringStatusRequestDto;
import nl.novi.autogarage.dtos.response.KeuringResponseDto;
import nl.novi.autogarage.services.KeuringService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/keuringen")
public class KeuringController {

    private final KeuringService keuringService;

    public KeuringController(KeuringService keuringService) {
        this.keuringService = keuringService;
    }

    @GetMapping
    public ResponseEntity<List<KeuringResponseDto>> getAllKeuringen() {
        return ResponseEntity.ok(keuringService.getAllKeuringen());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeuringResponseDto> getKeuringById(@PathVariable Long id) {
        return ResponseEntity.ok(keuringService.getKeuringById(id));
    }

    @PostMapping
    public ResponseEntity<KeuringResponseDto> createKeuring(@Valid @RequestBody KeuringRequestDto dto) {
        KeuringResponseDto nieuw = keuringService.createKeuring(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(nieuw.id).toUri();
        return ResponseEntity.created(uri).body(nieuw);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<KeuringResponseDto> updateStatus(@PathVariable Long id, @Valid @RequestBody KeuringStatusRequestDto dto) {
        return ResponseEntity.ok(keuringService.updateKeuringStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeuring(@PathVariable Long id) {
        keuringService.deleteKeuring(id);
        return ResponseEntity.noContent().build();
    }
}
