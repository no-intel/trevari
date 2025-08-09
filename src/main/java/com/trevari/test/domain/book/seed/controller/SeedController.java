package com.trevari.test.domain.book.seed.controller;

import com.trevari.test.domain.book.seed.service.SeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seeds")
public class SeedController {
    private final SeedService seedService;

    @PostMapping
    public ResponseEntity<Void> setSeeds() {
        seedService.setSeeds();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
