package com.frutz.pft.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frutz.pft.controller.CoinController;
import com.frutz.pft.dto.CoinDTO;
import com.frutz.pft.entity.Coin;
import com.frutz.pft.services.coin.CoinService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CoinController.class)
public class CoinControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoinService coinService;

    @Autowired
    private ObjectMapper objectMapper;

    private Coin coin;
    private CoinDTO coinDTO;

    @BeforeEach
    public void setUp() {
        // Initialisation d'une entité Coin
        coin = createCoin();
        // Initialisation d'une entité CoinDTO
        coinDTO = createCoinDTO();
    }

    private Coin createCoin() {
        coin = new Coin();
        coin.setId("BTC");
        coin.setRank("1");
        coin.setSymbol("BTC");
        coin.setName("Bitcoin");
        coin.setSupply(21000000);
        coin.setMarketCapUsd(800000000000f);
        coin.setVolumeUsd24Hr(35000000000f);
        coin.setChangePercent24Hr(-1.5f);
        coin.setPriceUsd(40000f);

        return coin;
    }

    private CoinDTO createCoinDTO() {
        coinDTO = new CoinDTO();
        coinDTO.setId("BTC");
        coinDTO.setRank("1");
        coinDTO.setSymbol("BTC");
        coinDTO.setName("Bitcoin");
        coinDTO.setSupply(21000000);
        coinDTO.setMarketCapUsd(800000000000f);
        coinDTO.setVolumeUsd24Hr(35000000000f);
        coinDTO.setChangerPercent24Hr(-1.5f);
        coinDTO.setPriceUsd(40000f);

        return coinDTO;
    }

    @Test
    public void testGetAllCoins() throws Exception {
        // Arrange
        List<Coin> coins = Arrays.asList(coin);
        when(coinService.getAllCoins()).thenReturn(coins);

        // Act
        ResultActions response = mockMvc.perform(get("/api/coin/all"));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(coin.getId()))
                .andExpect(jsonPath("$[0].name").value(coin.getName()))
                .andExpect(jsonPath("$[0].priceUsd").value(coin.getPriceUsd()));
    }

    @Test
    public void testGetCoinById_Ok() throws Exception {
        // Arrange
        when(coinService.getCoinById(coin.getId())).thenReturn(coin);

        // Act
        ResultActions response = mockMvc.perform(get("/api/coin/{id}", coin.getId()));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(coin.getId()))
                .andExpect(jsonPath("$.name").value(coin.getName()))
                .andExpect(jsonPath("$.priceUsd").value(coin.getPriceUsd()));
    }

    @Test
    public void testGetCoinById_NotFound() throws Exception {
        // Arrange
        when(coinService.getCoinById(coin.getId())).thenThrow(new EntityNotFoundException("Coin not found"));

        // Act
        ResultActions response = mockMvc.perform(get("/api/coin/{id}", coin.getId()));

        // Assert
        response.andExpect(status().isNotFound())
                .andExpect(content().string("Coin not found"));
    }

    @Test
    public void testGetAllSymbols() throws Exception {
        // Arrange
        List<String> symbols = Arrays.asList(coin.getSymbol());
        when(coinService.getCoinsSymbol()).thenReturn(symbols);

        // Act
        ResultActions response = mockMvc.perform(get("/api/coin/all/symb"));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(coin.getSymbol()));
    }
}
