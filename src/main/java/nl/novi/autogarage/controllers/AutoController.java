package nl.novi.autogarage.controllers;

import nl.novi.autogarage.dtos.request.AutoRequestDto;
import nl.novi.autogarage.dtos.response.AutoResponseDto;
import nl.novi.autogarage.services.AutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/autos")
public class AutoController {

    private final AutoService autoService;

    public AutoController(AutoService autoService) {
        this.autoService = autoService;
    }

    @GetMapping
    public ResponseEntity<List<AutoResponseDto>> getAllAutos() {
        return ResponseEntity.ok(autoService.getAllAutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoResponseDto> getAutoById(@PathVariable Long id) {
        return ResponseEntity.ok(autoService.getAutoById(id));
    }

    @PostMapping
    public ResponseEntity<AutoResponseDto> createAuto(@Valid @RequestBody AutoRequestDto dto) {
        AutoResponseDto nieuw = autoService.createAuto(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(nieuw.id).toUri();
        return ResponseEntity.created(uri).body(nieuw);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutoResponseDto> updateAuto(@PathVariable Long id, @Valid @RequestBody AutoRequestDto dto) {
        return ResponseEntity.ok(autoService.updateAuto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuto(@PathVariable Long id) {
        autoService.deleteAuto(id);
        return ResponseEntity.noContent().build();
    }
}
