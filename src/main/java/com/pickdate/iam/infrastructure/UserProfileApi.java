package com.pickdate.iam.infrastructure;

import com.pickdate.iam.application.UserUseCase;
import com.pickdate.iam.domain.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.pickdate.shared.domain.Value.valueOrNull;


@RestController
@RequestMapping("/api/v1/profile")
@AllArgsConstructor
@Tag(name = "Users", description = "User account endpoints")
@SecurityRequirement(name = "basicAuth")
class UserProfileApi {

    private final UserUseCase userUseCase;

    @GetMapping
    ResponseEntity<UserProfile> getUserProfile(Authentication authentication) {
        if (authentication == null
                || authentication.getPrincipal() == null
                || !authentication.isAuthenticated()) {
            return ResponseEntity.noContent().build();
        }

        String username = authentication.getName();
        User user = userUseCase.getUserByEmail(username);

        return ResponseEntity.ok(
                new UserProfile(
                        valueOrNull(user.getDisplayName()),
                        valueOrNull(user.getEmail()),
                        user.getAuthoritiesAsString()
                ));
    }

    record UserProfile(
            String displayName,
            String email,
            List<String> authorities
    ) {
    }
}
