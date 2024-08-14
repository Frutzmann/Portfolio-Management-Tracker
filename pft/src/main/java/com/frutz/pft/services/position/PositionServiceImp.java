package com.frutz.pft.services.position;

import com.frutz.pft.dto.PositionDTO;
import com.frutz.pft.entity.Portfolio;
import com.frutz.pft.entity.Position;
import com.frutz.pft.repository.PositionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImp implements  PositionService{

    private final PositionRepository positionRepository;
    @Override
    public Position saveOrUpdatePosition(Position position, PositionDTO positionDTO) {
        position.setPortfolio(positionDTO.getPortfolio());
        position.setCoin(positionDTO.getCoin());
        position.setNumberOfCoins(positionDTO.getNumberOfCoins());
        position.setAmountUsd(positionDTO.getAmoundUsd());

        return positionRepository.save(position);
    }

    @Override
    public Position createPosition(PositionDTO positionDTO) {
        return saveOrUpdatePosition(new Position(), positionDTO);
    }

    @Override
    public void deletePosition(long id) {
        Position position = positionRepository.findById(id).orElse(null);

        if (position == null)
            throw new EntityNotFoundException("Position not found");

        positionRepository.delete(position);
    }

    @Override
    public List<Position> getPositionByPortfolioId(long portfolioId) {
        List<Position> position = positionRepository.findByPortfolioId(portfolioId);

        if (position == null)
            throw new EntityNotFoundException("Position not found");

        return position;
    }

    @Override
    public List<Position> findPositionByCoin(String coinId) {
        List<Position> position = positionRepository.findByCoinId(coinId);

        if (position == null)
            throw new EntityNotFoundException("Position not found");

        return position;
    }

    @Override
    public Position updatePosition(long id, PositionDTO positionDTO) {
        Position position = positionRepository.findById(id).orElse(null);

        if (position == null)
            throw new EntityNotFoundException("Position not found");

        return saveOrUpdatePosition(position, positionDTO);
    }
}
