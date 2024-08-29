package com.frutz.pft.unit.repository;

import com.frutz.pft.entity.Coin;
import com.frutz.pft.repository.CoinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CoinRepositoryUnitTest {

    @Autowired
    private CoinRepository coinRepository;

    private Coin bitcoin;
    private Coin ethereum;

    @BeforeEach
    public void setUp() {
        bitcoin = createAndSaveCoin("BTC", "1", "Bitcoin", "BTC", 21000000, 800000000000f, 35000000000f, -1.5f, 40000f);
        ethereum = createAndSaveCoin("ETH", "2", "Ethereum", "ETH", 115000000, 300000000000f, 20000000000f, 0.5f, 3000f);
    }

    private Coin createAndSaveCoin(String id, String rank, String name, String symbol, float supply,
                                   float marketCapUsd, float volumeUsd24Hr, float changePercent24Hr, float priceUsd) {
        Coin coin = new Coin();
        coin.setId(id);
        coin.setRank(rank);
        coin.setName(name);
        coin.setSymbol(symbol);
        coin.setSupply(supply);
        coin.setMarketCapUsd(marketCapUsd);
        coin.setVolumeUsd24Hr(volumeUsd24Hr);
        coin.setChangePercent24Hr(changePercent24Hr);
        coin.setPriceUsd(priceUsd);
        return coinRepository.save(coin);
    }

    @Test
    public void testGetAllCoins() {
        // Act
        List<Coin> coins = coinRepository.findAll();

        // Assert
        assertThat(coins).hasSize(2);
        assertThat(coins).extracting(Coin::getId).containsExactlyInAnyOrder("BTC", "ETH");
    }

    @Test
    public void testGetCoinById() {
        // Act
        Optional<Coin> foundCoin = coinRepository.findById("BTC");

        // Assert
        assertThat(foundCoin).isPresent();
        assertThat(foundCoin.get().getName()).isEqualTo("Bitcoin");
    }

    @Test
    public void testGetAllSymbols() {
        // Act
        List<String> symbols = coinRepository.findAll()
                .stream()
                .map(Coin::getSymbol)
                .collect(Collectors.toList());

        // Assert
        assertThat(symbols).containsExactlyInAnyOrder("BTC", "ETH");
    }
}
