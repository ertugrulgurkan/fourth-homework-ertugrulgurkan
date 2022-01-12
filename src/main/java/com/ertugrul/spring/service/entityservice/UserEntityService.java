package com.ertugrul.spring.service.entityservice;


import com.ertugrul.spring.entity.User;
import com.ertugrul.spring.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserEntityService extends BaseEntityService<User, UserRepository> {
    public UserEntityService(UserRepository userRepository) {
        super(userRepository);
    }
}