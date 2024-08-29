package com.frutz.pft.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frutz.pft.controller.PositionController;
import com.frutz.pft.dto.PositionDTO;
import com.frutz.pft.entity.Coin;
import com.frutz.pft.entity.Portfolio;
import com.frutz.pft.entity.Position;
import com.frutz.pft.entity.User;
import com.frutz.pft.services.position.PositionService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PositionController.class)
public class PositionControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PositionService positionService;

    @Autowired
    private ObjectMapper objectMapper;

    private PositionDTO positionDTO;
    private Position position;

    @BeforeEach
    public void setUp() {
        //Initialisation Coin & Portfolio
        Coin coin = createCoin("ANKR", "106", "ankr", "ANKR");
        Portfolio portfolio = createPortfolio();
        // Initialisation d'une entité PositionDTO
        positionDTO = createPositionDTO();
        // Initialisation d'une entité Position
        position = createPosition(coin, portfolio);
    }

    public PositionDTO createPositionDTO() {
        positionDTO = new PositionDTO();
        positionDTO.setId(1L);
        positionDTO.setCoinId("ANKR");
        positionDTO.setPortfolioId(1);
        positionDTO.setNumberOfCoins(10L);
        positionDTO.setAmountUsd(new BigDecimal("40000"));

        return positionDTO;
    }

    public Position createPosition(Coin coin, Portfolio portfolio) {
        position = new Position();
        position.setId(1L);
        position.setNumberOfCoins(10L);
        position.setAmountUsd(BigDecimal.valueOf(40000));
        position.setCoin(coin);
        position.setPortfolio(portfolio);

        return position;
    }


    public Coin createCoin(String id, String rank, String name, String symbol) {
        Coin coin = new Coin();
        coin.setId(id);
        coin.setRank(rank);
        coin.setName(name);
        coin.setSymbol(symbol);

        return coin;
    }

    public Portfolio createPortfolio() {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1);
        portfolio.setUser(new User());

        return portfolio;
    }

    @Test
    public void testCreatePosition() throws Exception {
        // Arrange
        Mockito.when(positionService.createPosition(any(PositionDTO.class))).thenReturn(position);

        // Act & Assert
        mockMvc.perform(post("/api/position")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(positionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numberOfCoins", is(10)))
                .andExpect(jsonPath("$.amountUsd", is(40000)));
    }

    @Test
    public void testCreatePosition_BadRequest() throws Exception {
        // Arrange
        Mockito.when(positionService.createPosition(any(PositionDTO.class))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(post("/api/position")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(positionDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPositionByPortfolioIdShouldReturnListOfValues() throws Exception {
        //Arrange
        List<Position> positionList = new ArrayList<>();
        positionList.add(position);
        positionList.add(createPosition(createCoin("ANKR", "106", "ankr", "ANKR"), createPortfolio()));
        Mockito.when(positionService.getPositionByPortfolioId(positionDTO.getPortfolioId())).thenReturn(positionList);

        //Act
        ResultActions response = mockMvc.perform(get("/api/position/portfolio/{id}", positionDTO.getPortfolioId()));

        //Assert
        response.andExpect(status().isOk())
                // .andExpect(jsonPath("$.portfolio", is(position.getPortfolio())))
                .andExpect(jsonPath("$[0].coin.id", is("ANKR")))
                .andExpect(jsonPath("$[0].portfolio.id", is(1)))
                .andExpect(jsonPath("$[1].portfolio.id", is(1)));
                //.andExpect(jsonPath("$.coin", is(position.getCoin())));
    }

    @Test
    public void testGetPositionByPortfolioId_BadRequest() throws Exception {
        //Arrange
        List<Position> positionList = new ArrayList<>();
        positionList.add(position);
        positionList.add(createPosition(createCoin("ANKR", "106", "ankr", "ANKR"), createPortfolio()));

        Mockito.when(positionService.getPositionByPortfolioId(2)).thenThrow(new EntityNotFoundException("Position not found"));

        //Act
        ResultActions response = mockMvc.perform(get("/api/position/portfolio/{id}", 2));

        //Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPositionByCoin_Ok() throws Exception {
        List<Position> positionList = new ArrayList<>();
        positionList.add(position);
        positionList.add(createPosition(createCoin("zilliqa", "100", "ZIL", "Zilliqa"), createPortfolio()));

        Mockito.when(positionService.findPositionByCoin("zilliqa")).thenReturn(positionList.subList(1, 2));

        ResultActions response = mockMvc.perform(get("/api/position/coins/{id}", "zilliqa"));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].coin.id", is("zilliqa")))
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    public void testGetPositionByCoin_BadRequest() throws Exception {
        List<Position> positionList = new ArrayList<>();
        positionList.add(position);

        Mockito.when(positionService.findPositionByCoin("bitcoin")).thenThrow(new EntityNotFoundException("Position not found"));

        ResultActions response = mockMvc.perform(get("/api/position/coins/{id}", "bitcoin"));

        response.andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePosition_BadRequest() throws Exception {
        Mockito.when(positionService.updatePosition(2L, positionDTO)).thenThrow(new EntityNotFoundException("Position not found"));

        ResultActions response = mockMvc.perform(put("/api/position/{id}", 2L));

        response.andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdatePosition_Ok() throws Exception {
        // Arrange
        PositionDTO newPositionDTO = positionDTO;
        newPositionDTO.setAmountUsd(BigDecimal.valueOf(80000));

        position.setAmountUsd(newPositionDTO.getAmountUsd());

        // Act
        Mockito.when(positionService.updatePosition(position.getId(), newPositionDTO)).thenReturn(position);

        ResultActions response = mockMvc.perform(put("/api/position/{id}", 1L)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(newPositionDTO)));

        //Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.amountUsd", is(80000)));
    }

    @Test
    public void testDeletePosition_BadRequest() throws Exception {
        //Arrange
        Mockito.doThrow(new EntityNotFoundException("Position not found")).when(positionService).deletePosition(2L);
        //Act
        ResultActions response = mockMvc.perform(delete("/api/position/{id}", 2L));

        //Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePosition_Ok() throws Exception {
        //Arrange
        Mockito.doNothing().when(positionService).deletePosition(2L);

        ResultActions response = mockMvc.perform(delete("/api/position/{id}", 2L));

        response.andExpect(status().isOk());
    }

}