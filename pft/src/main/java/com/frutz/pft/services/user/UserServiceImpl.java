package com.frutz.pft.services.user;

import com.frutz.pft.dto.UserDTO;
import com.frutz.pft.entity.User;
import com.frutz.pft.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveOrUpdateUser(User user, UserDTO userDTO) {
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        return userRepository.save(user);
    }

    @Override
    public User register(UserDTO userDTO) {
        return saveOrUpdateUser(new User(), userDTO);
    }

    @Override
    public User getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null)
            throw new EntityNotFoundException("User not found");

        return user;
    }

    @Override
    public User updateUser(long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null)
            throw new EntityNotFoundException("User not found");

        return saveOrUpdateUser(user, userDTO);
    }

    @Override
    public void deleteUser(long id) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null)
            throw new EntityNotFoundException("User not found");

        userRepository.delete(user);
    }
}
