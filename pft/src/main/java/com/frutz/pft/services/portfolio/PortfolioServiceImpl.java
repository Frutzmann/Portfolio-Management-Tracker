package com.frutz.pft.services.portfolio;

import com.frutz.pft.dto.PortfolioDTO;
import com.frutz.pft.entity.Portfolio;
import com.frutz.pft.entity.User;
import com.frutz.pft.repository.PortfolioRepository;
import com.frutz.pft.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    @Override
    public Portfolio saveOrUpdatePortfolio(Portfolio portfolio, PortfolioDTO pDTO) {
        Optional<User> user = userRepository.findById( pDTO.getUserId());

        if(user.isEmpty())
            return null;

        portfolio.setUser(user.get());
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
    public Portfolio getPortfolioByUserId(long userId) {
        Portfolio portfolio = portfolioRepository.findByUserId(userId);

        if (portfolio == null)
            throw new EntityNotFoundException("Portfolio not found");

        return portfolio;
    }

    @Override
    public void deletePortfolio(long id) {
        Portfolio portfolio = portfolioRepository.findById(id).orElse(null);

        if (portfolio == null)
            throw new EntityNotFoundException("Portfolio not found");

        portfolioRepository.delete(portfolio);
    }
}
