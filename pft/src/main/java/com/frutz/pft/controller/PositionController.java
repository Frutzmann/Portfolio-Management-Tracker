package com.frutz.pft.controller;

import com.frutz.pft.dto.PositionDTO;
import com.frutz.pft.entity.Position;
import com.frutz.pft.services.position.PositionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/position")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PositionController {

    private final PositionService positionService;

    @GetMapping("/portfolio/{id}")
    ResponseEntity<?> getPositionByPorfolioId(@PathVariable Integer id) {
        try {
            List<Position> positions = positionService.getPositionByPortfolioId(id);
            return ResponseEntity.ok(positions);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/coins/{id}")
    ResponseEntity<?> getPositionByCoinId(@PathVariable String id) {
        try{
            List<Position> positions = positionService.findPositionByCoin(id);
            return ResponseEntity.ok(positions);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    ResponseEntity<?> createPosition(@RequestBody PositionDTO positionDTO) {
        Position position = positionService.createPosition(positionDTO);

        if (position == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(position);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePosition(@PathVariable Integer id) {
        try {
            positionService.deletePosition(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updatePosition(@PathVariable Integer id, @RequestBody PositionDTO positionDTO) {
        try {
            return ResponseEntity.ok(positionService.updatePosition(id, positionDTO));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
