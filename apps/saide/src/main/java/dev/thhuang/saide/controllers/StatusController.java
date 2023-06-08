package dev.thhuang.saide.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("healthy");
    }
}