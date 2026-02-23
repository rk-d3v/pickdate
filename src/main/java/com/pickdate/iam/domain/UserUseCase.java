package com.pickdate.iam.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserUseCase {

    User getUserById(String id);

    User getUserByEmail(String email);

    Page<User> getAllUsers(Pageable pageable);

    User createUser(User user);
}
