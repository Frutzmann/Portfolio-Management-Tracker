package com.frutz.pft.unit.repository;

import com.frutz.pft.entity.Portfolio;
import com.frutz.pft.entity.User;
import com.frutz.pft.repository.PortfolioRepository;
import com.frutz.pft.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PortfolioRepositoryUnitTest {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSavePortfolio() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        User savedUser = userRepository.save(user);

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(savedUser);
        portfolio.setCashBalanceUsd(10000L);
        portfolio.setName("Main Portfolio");
        portfolio.setType("Investment");

        // Act
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // Assert
        assertThat(savedPortfolio.getId()).isNotNull();
        assertThat(savedPortfolio.getUser()).isEqualTo(savedUser);
        assertThat(savedPortfolio.getCashBalanceUsd()).isEqualTo(10000L);
    }

    @Test
    public void testFindPortfolioById() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        User savedUser = userRepository.save(user);

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(savedUser);
        portfolio.setCashBalanceUsd(10000L);
        portfolio.setName("Main Portfolio");
        portfolio.setType("Investment");
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // Act
        Optional<Portfolio> foundPortfolio = portfolioRepository.findById(savedPortfolio.getId());

        // Assert
        assertThat(foundPortfolio).isPresent();
        assertThat(foundPortfolio.get().getName()).isEqualTo("Main Portfolio");
    }

    @Test
    public void testFindAllPortfolios() {
        // Arrange
        User user1 = new User();
        user1.setUsername("john_doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password123");
        User savedUser1 = userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("jane_doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password456");
        User savedUser2 = userRepository.save(user2);

        Portfolio portfolio1 = new Portfolio();
        portfolio1.setUser(savedUser1);
        portfolio1.setCashBalanceUsd(10000L);
        portfolio1.setName("Main Portfolio");
        portfolio1.setType("Investment");

        Portfolio portfolio2 = new Portfolio();
        portfolio2.setUser(savedUser2);
        portfolio2.setCashBalanceUsd(5000L);
        portfolio2.setName("Savings Portfolio");
        portfolio2.setType("Savings");

        portfolioRepository.save(portfolio1);
        portfolioRepository.save(portfolio2);

        // Act
        List<Portfolio> portfolios = portfolioRepository.findAll();

        // Assert
        assertThat(portfolios).hasSize(2);
        assertThat(portfolios).extracting(Portfolio::getName).containsExactlyInAnyOrder("Main Portfolio", "Savings Portfolio");
    }

    @Test
    public void testUpdatePortfolio() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        User savedUser = userRepository.save(user);

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(savedUser);
        portfolio.setCashBalanceUsd(10000L);
        portfolio.setName("Main Portfolio");
        portfolio.setType("Investment");
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // Act
        savedPortfolio.setCashBalanceUsd(15000L);
        Portfolio updatedPortfolio = portfolioRepository.save(savedPortfolio);

        // Assert
        assertThat(updatedPortfolio.getCashBalanceUsd()).isEqualTo(15000L);
    }

    @Test
    public void testDeletePortfolio() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        User savedUser = userRepository.save(user);

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(savedUser);
        portfolio.setCashBalanceUsd(10000L);
        portfolio.setName("Main Portfolio");
        portfolio.setType("Investment");
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        // Act
        portfolioRepository.delete(savedPortfolio);

        // Assert
        Optional<Portfolio> deletedPortfolio = portfolioRepository.findById(savedPortfolio.getId());
        assertThat(deletedPortfolio).isNotPresent();
    }

    @Test
    public void testFindByUserId() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        User savedUser = userRepository.save(user);

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(savedUser);
        portfolio.setCashBalanceUsd(10000L);
        portfolio.setName("Main Portfolio");
        portfolio.setType("Investment");
        portfolioRepository.save(portfolio);

        // Act
        Portfolio foundPortfolio = portfolioRepository.findByUserId(savedUser.getId());

        // Assert
        assertThat(foundPortfolio).isNotNull();
        assertThat(foundPortfolio.getUser().getUsername()).isEqualTo("john_doe");
        assertThat(foundPortfolio.getCashBalanceUsd()).isEqualTo(10000L);
    }
}
