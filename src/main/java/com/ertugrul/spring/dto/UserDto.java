package com.ertugrul.spring.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private final Long id;
    private final String name;
    private final String username;
    private final String surname;
    private final String email;
    private final String phone;
}
