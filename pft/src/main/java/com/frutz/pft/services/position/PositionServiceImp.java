package com.frutz.pft.services.position;

import com.frutz.pft.dto.PositionDTO;
import com.frutz.pft.entity.Coin;
import com.frutz.pft.entity.Portfolio;
import com.frutz.pft.entity.Position;
import com.frutz.pft.repository.CoinRepository;
import com.frutz.pft.repository.PortfolioRepository;
import com.frutz.pft.repository.PositionRepository;
import com.frutz.pft.services.portfolio.PortfolioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionServiceImp implements  PositionService{

    private final PositionRepository positionRepository;
    private final PortfolioRepository portfolioRepository;
    private final CoinRepository coinRepository;

    @Override
    public Position saveOrUpdatePosition(Position position, PositionDTO positionDTO) {
        long id = positionDTO.getPortfolioId();
        Optional<Portfolio> portfolio = portfolioRepository.findById(positionDTO.getPortfolioId());
        Optional<Coin> coin = coinRepository.findById(positionDTO.getCoin());
        if(portfolio.isEmpty() || coin.isEmpty())
            return null;

        position.setPortfolio(portfolio.get());
        position.setCoin(coin.get());
        position.setNumberOfCoins(positionDTO.getNumberOfCoins());
        position.setAmountUsd(positionDTO.getAmountUsd());

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
