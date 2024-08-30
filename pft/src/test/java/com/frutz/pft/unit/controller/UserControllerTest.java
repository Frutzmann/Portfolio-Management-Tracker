package com.frutz.pft.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frutz.pft.controller.UserController;
import com.frutz.pft.dto.UserDTO;
import com.frutz.pft.entity.User;
import com.frutz.pft.services.user.UserService;
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


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    public void setUp() {
        // Initialisation d'une entité User
        user = createUser();
        // Initialisation d'une entité UserDTO
        userDTO = createUserDTO();
    }

    public User createUser() {
        user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        return user;
    }

    public UserDTO createUserDTO() {
        userDTO = new UserDTO();
        userDTO.setUsername("john_doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");

        return userDTO;
    }

    @Test
    public void testAddUser() throws Exception {
        // Arrange
        when(userService.register(any(UserDTO.class))).thenReturn(user);

        // Act
        ResultActions response = mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        // Assert
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void testAddUser_BadRequest() throws Exception {
        // Arrange
        when(userService.register(any(UserDTO.class))).thenReturn(null);

        // Act
        ResultActions response = mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        // Assert
        response.andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserById_Ok() throws Exception {
        // Arrange
        when(userService.getUserById(user.getId())).thenReturn(user);

        // Act
        ResultActions response = mockMvc.perform(get("/api/user/{id}", user.getId()));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void testGetUserById_NotFound() throws Exception {
        // Arrange
        when(userService.getUserById(user.getId())).thenThrow(new EntityNotFoundException("User not found"));

        // Act
        ResultActions response = mockMvc.perform(get("/api/user/{id}", user.getId()));

        // Assert
        response.andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    public void testUpdateUser_Ok() throws Exception {
        // Arrange
        UserDTO updatedUserDTO = userDTO;
        updatedUserDTO.setEmail("new.email@example.com");
        user.setEmail(updatedUserDTO.getEmail());

        when(userService.updateUser(user.getId(), updatedUserDTO)).thenReturn(user);

        // Act
        ResultActions response = mockMvc.perform(put("/api/user/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUserDTO)));

        // Assert
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(updatedUserDTO.getEmail()));
    }

    @Test
    public void testUpdateUser_NotFound() throws Exception {
        // Arrange
        when(userService.updateUser(user.getId(), userDTO)).thenThrow(new EntityNotFoundException("User not found"));

        // Act
        ResultActions response = mockMvc.perform(put("/api/user/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)));

        // Assert
        response.andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    public void testDeleteUser_Ok() throws Exception {
        // Arrange
        Mockito.doNothing().when(userService).deleteUser(user.getId());

        // Act
        ResultActions response = mockMvc.perform(delete("/api/user/{id}", user.getId()));

        // Assert
        response.andExpect(status().isOk());
    }

    @Test
    public void testDeleteUser_NotFound() throws Exception {
        // Arrange
        Mockito.doThrow(new EntityNotFoundException("User not found")).when(userService).deleteUser(user.getId());

        // Act
        ResultActions response = mockMvc.perform(delete("/api/user/{id}", user.getId()));

        // Assert
        response.andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }
}
