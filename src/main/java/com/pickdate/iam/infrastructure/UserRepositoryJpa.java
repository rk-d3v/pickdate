package com.pickdate.iam.infrastructure;

import com.pickdate.iam.domain.User;
import com.pickdate.iam.domain.UserRepository;
import com.pickdate.shared.domain.Email;
import com.pickdate.shared.domain.Identifier;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
interface UserRepositoryJpa extends UserRepository, JpaRepository<User, Identifier> {

    @Override
    boolean existsByEmail(@NonNull Email email);

    @Override
    Optional<User> findByEmail(@NonNull Email email);

    @Override
    @NonNull
    Optional<User> findById(@NonNull Identifier id);

    @Override
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    @Override
    @NonNull
    User save(@NonNull User user);

    @Override
    void deleteById(@NonNull Identifier identifier);
}
