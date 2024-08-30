package com.frutz.pft.controller;

import com.frutz.pft.dto.PortfolioDTO;
import com.frutz.pft.entity.Portfolio;
import com.frutz.pft.services.portfolio.PortfolioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    public ResponseEntity<?> createPortfolio(@RequestBody PortfolioDTO portfolioDTO) {
        Portfolio createdPortfolio = portfolioService.postPortfolio(portfolioDTO);

        if (createdPortfolio == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPortfolio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPortfolio(@PathVariable long id) {
        try {
            return ResponseEntity.ok(portfolioService.getPortfolioById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getPortfolioByUserId(@PathVariable long id) {
        try {
            Portfolio portfolio = portfolioService.getPortfolioByUserId(id);
            return ResponseEntity.ok(portfolio);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePortfolio(@PathVariable long id, @RequestBody PortfolioDTO portfolioDTO) {
        try {
            return ResponseEntity.ok(portfolioService.updatePortfolio(id, portfolioDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePortfolio(@PathVariable long id) {
        try {
            portfolioService.deletePortfolio(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPortfolios() {
        return ResponseEntity.ok(portfolioService.getAllPortfolios());
    }


}
