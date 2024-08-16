package com.frutz.pft.services.user;


import com.frutz.pft.dto.UserDTO;
import com.frutz.pft.entity.User;

public interface UserService {

    User saveOrUpdateUser(User user, UserDTO userDTO);
    User register(UserDTO userDTO);
    User getUserById(long id);
    User updateUser(long id, UserDTO userDTO);
    void deleteUser(long id);
}
