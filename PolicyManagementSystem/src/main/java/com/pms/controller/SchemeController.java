package com.pms.controller;

import com.pms.exception.InvalidEntityException;
import com.pms.entity.Scheme;
import com.pms.repository.SchemeRepository;
import com.pms.service.SchemeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/schemes")
@CrossOrigin(origins = "http://localhost:8031")
public class SchemeController {

    @Autowired
    private SchemeService schemeService;
    @Autowired
    private SchemeRepository schemeRepository;

    @GetMapping
    public List<Scheme> getAllSchemes() {
        return schemeService.getAllSchemes();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Scheme> getSchemeById(@PathVariable int id) throws InvalidEntityException {
        Scheme scheme = schemeService.getSchemeById(id)
                .orElseThrow(() -> new InvalidEntityException("Scheme not found with ID: " + id));
        return ResponseEntity.ok(scheme);
    }
    @PostMapping
    public Scheme createScheme(@Valid @RequestBody Scheme scheme) {
        return schemeService.createScheme(scheme);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Scheme> updateScheme(@PathVariable int id, @RequestBody Scheme schemeDetails) throws InvalidEntityException {
        Scheme updatedScheme = schemeService.updateScheme(id, schemeDetails);
        return ResponseEntity.ok(updatedScheme);
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Scheme> updateSchemeActiveStat(@PathVariable int id, @RequestParam boolean isActive) throws InvalidEntityException {
        Scheme updatedScheme = schemeService.setSchemeActiveStatus(id, isActive);
        return ResponseEntity.ok(updatedScheme);
    }
    @GetMapping("/active")
    public List<Scheme> getActiveSchemes(){
        return schemeService.getAllActiveSchemes();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Scheme>> searchSchemeByName(@RequestParam String schemeName) {
        List<Scheme> filteredSchemes = schemeService.getAllSchemesBySchemeName(schemeName);
        if (filteredSchemes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(filteredSchemes);
    }
    @GetMapping("/viewSchemes")
    public List<Scheme> viewSchemes(){
        return schemeService.getAllSchemes();
    } 
}
