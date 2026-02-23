package com.pickdate.iam.infrastructure;

import com.pickdate.iam.domain.UserRepository;
import com.pickdate.iam.domain.UserService;
import com.pickdate.iam.domain.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class UserServiceConfiguration {

    @Bean
    public UserUseCase userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
