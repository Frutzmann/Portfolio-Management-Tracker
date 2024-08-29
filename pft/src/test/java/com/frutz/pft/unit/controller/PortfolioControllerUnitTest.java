package com.frutz.pft.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frutz.pft.controller.PortfolioController;
import com.frutz.pft.dto.PortfolioDTO;
import com.frutz.pft.entity.Portfolio;
import com.frutz.pft.entity.User;
import com.frutz.pft.services.portfolio.PortfolioService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PortfolioController.class)
public class PortfolioControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioService portfolioService;

    @Autowired
    private ObjectMapper objectMapper;

    private PortfolioDTO portfolioDTO;
    private Portfolio portfolio;
    private User user;

    @BeforeEach
    public void setUp() {
        // Initialisation d'une entité User
        user = createUser();
        // Initialisation d'une entité Portfolio
        portfolio = createPortfolio();
        // Initialisation d'une entité PortfolioDTO
        portfolioDTO = createPortfolioDTO();
    }

    public User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        return user;
    }

    public Portfolio createPortfolio() {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setUser(user);
        portfolio.setCashBalanceUsd(100000L);
        portfolio.setName("Main Portfolio");
        portfolio.setType("Investment");

        return portfolio;
    }

    public PortfolioDTO createPortfolioDTO() {
        portfolioDTO = new PortfolioDTO();
        portfolioDTO.setUserId(user.getId());
        portfolioDTO.setCash_balance_usd(100000L);
        portfolioDTO.setName("Main Portfolio");
        portfolioDTO.setType("Investment");

        return portfolioDTO;
    }

    @Test
    public void testCreatePortfolio() throws Exception {
        // Arrange
        when(portfolioService.postPortfolio(any(PortfolioDTO.class))).thenReturn(portfolio);

        // Act
        ResultActions response = mockMvc.perform(post("/api/portfolio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(portfolioDTO)));

        // Assert
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(portfolio.getId()))
                .andExpect(jsonPath("$.name").value(portfolio.getName()))
                .andExpect(jsonPath("$.cashBalanceUsd").value(portfolio.getCashBalanceUsd()))
                .andExpect(jsonPath("$.type").value(portfolio.getType()));
    }

    @Test
    public void testCreatePortfolio_BadRequest() throws Exception {
        // Arrange
        when(portfolioService.postPortfolio(any(PortfolioDTO.class))).thenReturn(null);

        // Act
        ResultActions response = mockMvc.perform(post("/api/portfolio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(portfolioDTO)));

        // Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPortfolioById_Ok() throws Exception {
        // Arrange
        when(portfolioService.getPortfolioById(portfolio.getId())).thenReturn(portfolio);

        // Act
        ResultActions response = mockMvc.perform(get("/api/portfolio/{id}", portfolio.getId()));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(portfolio.getId()))
                .andExpect(jsonPath("$.name").value(portfolio.getName()))
                .andExpect(jsonPath("$.cashBalanceUsd").value(portfolio.getCashBalanceUsd()))
                .andExpect(jsonPath("$.type").value(portfolio.getType()));
    }

    @Test
    public void testGetPortfolioById_NotFound() throws Exception {
        // Arrange
        when(portfolioService.getPortfolioById(portfolio.getId())).thenThrow(new EntityNotFoundException("Portfolio not found"));

        // Act
        ResultActions response = mockMvc.perform(get("/api/portfolio/{id}", portfolio.getId()));

        // Assert
        response.andExpect(status().isNotFound())
                .andExpect(content().string("Portfolio not found"));
    }

    @Test
    public void testGetPortfolioByUserId_Ok() throws Exception {
        // Arrange
        when(portfolioService.getPortfolioByUserId(user.getId())).thenReturn(portfolio);

        // Act
        ResultActions response = mockMvc.perform(get("/api/portfolio/user/{id}", user.getId()));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(portfolio.getId()))
                .andExpect(jsonPath("$.name").value(portfolio.getName()))
                .andExpect(jsonPath("$.cashBalanceUsd").value(portfolio.getCashBalanceUsd()))
                .andExpect(jsonPath("$.type").value(portfolio.getType()));
    }

    @Test
    public void testGetPortfolioByUserId_NotFound() throws Exception {
        // Arrange
        when(portfolioService.getPortfolioByUserId(user.getId())).thenThrow(new EntityNotFoundException("Portfolio not found"));

        // Act
        ResultActions response = mockMvc.perform(get("/api/portfolio/user/{id}", user.getId()));

        // Assert
        response.andExpect(status().isNotFound())
                .andExpect(content().string("Portfolio not found"));
    }

    @Test
    public void testUpdatePortfolio_Ok() throws Exception {
        // Arrange
        PortfolioDTO updatedPortfolioDTO = portfolioDTO;
        updatedPortfolioDTO.setCash_balance_usd(200000L);
        portfolio.setCashBalanceUsd(updatedPortfolioDTO.getCash_balance_usd());

        when(portfolioService.updatePortfolio(portfolio.getId(), updatedPortfolioDTO)).thenReturn(portfolio);

        // Act
        ResultActions response = mockMvc.perform(put("/api/portfolio/{id}", portfolio.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPortfolioDTO)));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(portfolio.getId()))
                .andExpect(jsonPath("$.cashBalanceUsd").value(updatedPortfolioDTO.getCash_balance_usd()));
    }

    @Test
    public void testUpdatePortfolio_NotFound() throws Exception {
        // Arrange
        when(portfolioService.updatePortfolio(portfolio.getId(), portfolioDTO)).thenThrow(new EntityNotFoundException("Portfolio not found"));

        // Act
        ResultActions response = mockMvc.perform(put("/api/portfolio/{id}", portfolio.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(portfolioDTO)));

        // Assert
        response.andExpect(status().isNotFound())
                .andExpect(content().string("Portfolio not found"));
    }

    @Test
    public void testDeletePortfolio_Ok() throws Exception {
        // Arrange
        Mockito.doNothing().when(portfolioService).deletePortfolio(portfolio.getId());

        // Act
        ResultActions response = mockMvc.perform(delete("/api/portfolio/{id}", portfolio.getId()));

        // Assert
        response.andExpect(status().isOk());
    }

    @Test
    public void testDeletePortfolio_NotFound() throws Exception {
        // Arrange
        Mockito.doThrow(new EntityNotFoundException("Portfolio not found")).when(portfolioService).deletePortfolio(portfolio.getId());

        // Act
        ResultActions response = mockMvc.perform(delete("/api/portfolio/{id}", portfolio.getId()));

        // Assert
        response.andExpect(status().isNotFound())
                .andExpect(content().string("Portfolio not found"));
    }
}
