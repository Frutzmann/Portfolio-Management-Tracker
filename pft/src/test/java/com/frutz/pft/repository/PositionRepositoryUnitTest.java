package com.frutz.pft.repository;

import com.frutz.pft.entity.Coin;
import com.frutz.pft.entity.Portfolio;
import com.frutz.pft.entity.Position;
import com.frutz.pft.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PositionRepositoryUnitTest {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    private User savedUser;
    private Portfolio savedPortfolio;
    private Coin savedCoin;

    @BeforeEach
    public void setUp() {
        savedUser = createAndSaveUser("john_doe", "john.doe@example.com", "password123");
        savedPortfolio = createAndSavePortfolio(savedUser, 10000L, "Main Portfolio", "Investment");
        savedCoin = createAndSaveCoin("BTC", "Bitcoin");
    }

    private User createAndSaveUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }

    private Portfolio createAndSavePortfolio(User user, long cashBalanceUsd, String name, String type) {
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setCashBalanceUsd(cashBalanceUsd);
        portfolio.setName(name);
        portfolio.setType(type);
        return portfolioRepository.save(portfolio);
    }

    private Coin createAndSaveCoin(String symbol, String name) {
        Coin coin = new Coin();
        coin.setId(symbol);
        coin.setSymbol(symbol);
        coin.setName(name);
        return coinRepository.save(coin);
    }

    private Position createAndSavePosition(Coin coin, Portfolio portfolio, long numberOfCoins, BigDecimal amountUsd) {
        Position position = new Position();
        position.setCoin(coin);
        position.setPortfolio(portfolio);
        position.setNumberOfCoins(numberOfCoins);
        position.setAmountUsd(amountUsd);
        return positionRepository.save(position);
    }

    @Test
    public void testSavePosition() {
        // Act
        Position savedPosition = createAndSavePosition(savedCoin, savedPortfolio, 2L, BigDecimal.valueOf(50000));

        // Assert
        assertThat(savedPosition.getId()).isNotNull();
        assertThat(savedPosition.getCoin()).isEqualTo(savedCoin);
        assertThat(savedPosition.getPortfolio()).isEqualTo(savedPortfolio);
        assertThat(savedPosition.getNumberOfCoins()).isEqualTo(2L);
        assertThat(savedPosition.getAmountUsd()).isEqualTo(BigDecimal.valueOf(50000));
    }

    @Test
    public void testFindPositionById() {
        // Arrange
        Position savedPosition = createAndSavePosition(savedCoin, savedPortfolio, 2L, BigDecimal.valueOf(50000));

        // Act
        Optional<Position> foundPosition = positionRepository.findById(savedPosition.getId());

        // Assert
        assertThat(foundPosition).isPresent();
        assertThat(foundPosition.get().getCoin().getSymbol()).isEqualTo("BTC");
    }

    @Test
    public void testFindAllPositions() {
        // Arrange
        Coin anotherCoin = createAndSaveCoin("ETH", "Ethereum");
        createAndSavePosition(savedCoin, savedPortfolio, 2L, BigDecimal.valueOf(50000));
        createAndSavePosition(anotherCoin, savedPortfolio, 10L, BigDecimal.valueOf(30000));

        // Act
        List<Position> positions = positionRepository.findAll();

        // Assert
        assertThat(positions).hasSize(2);
        assertThat(positions).extracting(Position::getCoin).extracting(Coin::getSymbol)
                .containsExactlyInAnyOrder("BTC", "ETH");
    }

    @Test
    public void testUpdatePosition() {
        // Arrange
        Position savedPosition = createAndSavePosition(savedCoin, savedPortfolio, 2L, BigDecimal.valueOf(50000));

        // Act
        savedPosition.setNumberOfCoins(3L);
        Position updatedPosition = positionRepository.save(savedPosition);

        // Assert
        assertThat(updatedPosition.getNumberOfCoins()).isEqualTo(3L);
    }

    @Test
    public void testDeletePosition() {
        // Arrange
        Position savedPosition = createAndSavePosition(savedCoin, savedPortfolio, 2L, BigDecimal.valueOf(50000));

        // Act
        positionRepository.delete(savedPosition);

        // Assert
        Optional<Position> deletedPosition = positionRepository.findById(savedPosition.getId());
        assertThat(deletedPosition).isNotPresent();
    }

    @Test
    public void testFindByPortfolioId() {
        // Arrange
        createAndSavePosition(savedCoin, savedPortfolio, 2L, BigDecimal.valueOf(50000));

        // Act
        List<Position> foundPositions = positionRepository.findByPortfolioId(savedPortfolio.getId());

        // Assert
        assertThat(foundPositions).hasSize(1);
        assertThat(foundPositions.get(0).getCoin().getSymbol()).isEqualTo("BTC");
    }

    @Test
    public void testFindByCoinId() {
        // Arrange
        createAndSavePosition(savedCoin, savedPortfolio, 2L, BigDecimal.valueOf(50000));

        // Act
        List<Position> foundPositions = positionRepository.findByCoinId(savedCoin.getId());

        // Assert
        assertThat(foundPositions).hasSize(1);
        assertThat(foundPositions.get(0).getPortfolio().getName()).isEqualTo("Main Portfolio");
    }
}
