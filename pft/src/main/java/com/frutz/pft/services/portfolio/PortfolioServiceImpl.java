package com.frutz.pft.services.portfolio;

import com.frutz.pft.dto.PortfolioDTO;
import com.frutz.pft.entity.Portfolio;
import com.frutz.pft.repository.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;

    @Override
    public Portfolio saveOrUpdatePortfolio(Portfolio portfolio, PortfolioDTO pDTO) {
        portfolio.setName(pDTO.getName());
        portfolio.setType(pDTO.getType());
        portfolio.setCashBalanceUsd(0);

        return portfolioRepository.save(portfolio);
    }

    @Override
    public Portfolio getPortfolioById(long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElse(null);

        if (portfolio == null)
            throw new EntityNotFoundException("Portfolio not found");

        return portfolio;
    }

    @Override
    public Portfolio updatePortfolio(long id, PortfolioDTO pDTO) {
        Portfolio portfolio = portfolioRepository.findById(id).orElse(null);

        if (portfolio == null)
            throw new EntityNotFoundException("Portfolio not found");

        return saveOrUpdatePortfolio(portfolio, pDTO);
    }

    @Override
    public Portfolio postPortfolio(PortfolioDTO pDTO) {
        return saveOrUpdatePortfolio(new Portfolio(), pDTO);
    }

    @Override
    public void deletePortfolio(long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElse(null);

        if (portfolio == null)
            throw new EntityNotFoundException("Portfolio not found");

        portfolioRepository.delete(portfolio);
    }
}
