package nl.novi.autogarage.controllers;

import nl.novi.autogarage.dtos.request.KlantRequestDto;
import nl.novi.autogarage.dtos.response.KlantResponseDto;
import nl.novi.autogarage.services.KlantService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/klanten")
public class KlantController {

    private final KlantService klantService;

    public KlantController(KlantService klantService) {
        this.klantService = klantService;
    }

    @GetMapping
    public ResponseEntity<List<KlantResponseDto>> getAllKlanten() {
        return ResponseEntity.ok(klantService.getAllKlanten());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KlantResponseDto> getKlantById(@PathVariable Long id) {
        return ResponseEntity.ok(klantService.getKlantById(id));
    }

    @PostMapping
    public ResponseEntity<KlantResponseDto> createKlant(@Valid @RequestBody KlantRequestDto dto) {
        KlantResponseDto nieuw = klantService.createKlant(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(nieuw.getId()).toUri();
        return ResponseEntity.created(uri).body(nieuw);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KlantResponseDto> updateKlant(@PathVariable Long id, @Valid @RequestBody KlantRequestDto dto) {
        return ResponseEntity.ok(klantService.updateKlant(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKlant(@PathVariable Long id) {
        klantService.deleteKlant(id);
        return ResponseEntity.noContent().build();
    }
}
