package com.pickdate.test.stub

import com.pickdate.iam.domain.UserService
import com.pickdate.iam.domain.UserUseCase
import com.pickdate.test.fixture.UserFixture


class UserUseCaseTestConfig {

    static final def repo = new UserRepositoryFake()

    static UserUseCase userUseCase() {
        new UserService(repo)
    }

    static void setupTestData() {
        repo.save(UserFixture.SOME_ADMIN)
        repo.save(UserFixture.SOME_USER)
    }
}
