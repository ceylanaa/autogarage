package nl.novi.autogarage.controllers;

import nl.novi.autogarage.dtos.request.*;
import nl.novi.autogarage.dtos.response.*;
import nl.novi.autogarage.services.ReparatieService;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reparaties")
public class ReparatieController {

    private final ReparatieService reparatieService;

    public ReparatieController(ReparatieService reparatieService) {
        this.reparatieService = reparatieService;
    }

    @GetMapping
    public ResponseEntity<List<ReparatieResponseDto>> getAllReparaties() {
        return ResponseEntity.ok(reparatieService.getAllReparaties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReparatieResponseDto> getReparatieById(@PathVariable Long id) {
        return ResponseEntity.ok(reparatieService.getReparatieById(id));
    }

    @PostMapping
    public ResponseEntity<ReparatieResponseDto> createReparatie(@Valid @RequestBody ReparatieRequestDto dto) {
        ReparatieResponseDto nieuw = reparatieService.createReparatie(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(nieuw.id).toUri();
        return ResponseEntity.created(uri).body(nieuw);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ReparatieResponseDto> updateStatus(@PathVariable Long id, @Valid @RequestBody ReparatieStatusRequestDto dto) {
        return ResponseEntity.ok(reparatieService.updateReparatieStatus(id, dto));
    }

    @PostMapping("/{id}/onderdelen")
    public ResponseEntity<ReparatieOnderdeelResponseDto> addOnderdeel(@PathVariable Long id, @Valid @RequestBody ReparatieOnderdeelRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reparatieService.addOnderdeel(id, dto));
    }

    @DeleteMapping("/{id}/onderdelen/{regelId}")
    public ResponseEntity<Void> removeOnderdeel(@PathVariable Long id, @PathVariable Long regelId) {
        reparatieService.removeOnderdeel(id, regelId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/handelingen")
    public ResponseEntity<ReparatieRegelResponseDto> addHandeling(@PathVariable Long id, @Valid @RequestBody ReparatieRegelRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reparatieService.addHandeling(id, dto));
    }

    @DeleteMapping("/{id}/handelingen/{regelId}")
    public ResponseEntity<Void> removeHandeling(@PathVariable Long id, @PathVariable Long regelId) {
        reparatieService.removeHandeling(id, regelId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/overigehandelingen")
    public ResponseEntity<OverigeHandelingResponseDto> addOverigeHandeling(@PathVariable Long id, @Valid @RequestBody OverigeHandelingRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reparatieService.addOverigeHandeling(id, dto));
    }

    @DeleteMapping("/{id}/overigehandelingen/{regelId}")
    public ResponseEntity<Void> removeOverigeHandeling(@PathVariable Long id, @PathVariable Long regelId) {
        reparatieService.removeOverigeHandeling(id, regelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/bon")
    public ResponseEntity<BonResponseDto> getBon(@PathVariable Long id) {
        return ResponseEntity.ok(reparatieService.genereerBon(id));
    }

    @PutMapping("/{id}/betaald")
    public ResponseEntity<ReparatieResponseDto> markeerBetaald(@PathVariable Long id) {
        return ResponseEntity.ok(reparatieService.markeerBetaald(id));
    }
}
