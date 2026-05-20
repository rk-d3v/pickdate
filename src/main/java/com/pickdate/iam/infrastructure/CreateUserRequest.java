package com.pickdate.iam.infrastructure;

import jakarta.annotation.Nullable;

record CreateUserRequest(
        String email,
        String password,
        @Nullable String displayName
) {
}
