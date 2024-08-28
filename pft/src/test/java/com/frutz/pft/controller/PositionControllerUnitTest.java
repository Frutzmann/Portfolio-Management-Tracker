package com.frutz.pft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frutz.pft.dto.PositionDTO;
import com.frutz.pft.entity.Position;
import com.frutz.pft.services.position.PositionService;
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

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        // Initialisation d'une entité PositionDTO
        positionDTO = new PositionDTO();
        positionDTO.setCoinId("BTC");
        positionDTO.setNumberOfCoins(10L);
        positionDTO.setAmountUsd(new BigDecimal("40000"));

        // Initialisation d'une entité Position
        position = new Position();
        position.setNumberOfCoins(10L);
        position.setAmountUsd(new BigDecimal("40000"));
        // Configurez `coin` et `portfolio` si nécessaire
    }

    @Test
    public void testCreatePosition() throws Exception {
        // Arrange
        Mockito.when(positionService.createPosition(any(PositionDTO.class))).thenReturn(position);

        // Act & Assert
        mockMvc.perform(post("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(positionDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numberOfCoins", is(10)))
                .andExpect(jsonPath("$.amountUsd", is(40000.0)));
    }

    @Test
    public void testCreatePosition_BadRequest() throws Exception {
        // Arrange
        Mockito.when(positionService.createPosition(any(PositionDTO.class))).thenReturn(null);

        // Act & Assert
        mockMvc.perform(post("")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(positionDTO)))
                .andExpect(status().isBadRequest());
    }
}
