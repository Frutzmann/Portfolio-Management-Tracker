package com.frutz.pft.repository;

import com.frutz.pft.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryUnitTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("john_doe");
        assertThat(savedUser.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    public void testFindUserById() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        User savedUser = userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("john_doe");
    }

    @Test
    public void testFindAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setUsername("john_doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password123");

        User user2 = new User();
        user2.setUsername("jane_doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("password456");

        userRepository.save(user1);
        userRepository.save(user2);

        // Act
        List<User> users = userRepository.findAll();

        // Assert
        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getUsername).containsExactlyInAnyOrder("john_doe", "jane_doe");
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        User savedUser = userRepository.save(user);

        // Act
        savedUser.setEmail("john.doe@newdomain.com");
        User updatedUser = userRepository.save(savedUser);

        // Assert
        assertThat(updatedUser.getEmail()).isEqualTo("john.doe@newdomain.com");
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        User savedUser = userRepository.save(user);

        // Act
        userRepository.delete(savedUser);

        // Assert
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertThat(deletedUser).isNotPresent();
    }
}