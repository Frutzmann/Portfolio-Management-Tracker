package com.frutz.pft.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class UserDTO {
    private long id;

    @Getter
    private String username;

    @Getter
    private String email;

    @Getter
    private String password;
}
