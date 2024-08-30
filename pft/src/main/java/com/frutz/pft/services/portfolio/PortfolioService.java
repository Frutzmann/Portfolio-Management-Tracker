package com.frutz.pft.services.portfolio;

import com.frutz.pft.dto.PortfolioDTO;
import com.frutz.pft.entity.Portfolio;

import java.util.List;

public interface PortfolioService {
    Portfolio saveOrUpdatePortfolio(Portfolio portfolio, PortfolioDTO pDTO);
    Portfolio getPortfolioById(long id);
    Portfolio updatePortfolio(long id, PortfolioDTO pDTO);
    Portfolio postPortfolio(PortfolioDTO pDTO);
    Portfolio getPortfolioByUserId(long userId);
    List<Portfolio> getAllPortfolios();
    void deletePortfolio(long id);
}
