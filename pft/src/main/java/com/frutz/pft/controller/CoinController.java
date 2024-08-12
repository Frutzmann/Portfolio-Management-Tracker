package com.frutz.pft.controller;

import com.frutz.pft.services.coin.CoinService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/coin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CoinController {

    private final CoinService coinService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCoins() {
        return ResponseEntity.ok(coinService.getAllCoins());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCoinById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(coinService.getCoinById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
