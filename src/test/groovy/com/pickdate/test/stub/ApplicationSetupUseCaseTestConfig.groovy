package com.pickdate.test.stub

import com.pickdate.bootstrap.encryption.Encryptor
import com.pickdate.iam.domain.ApplicationSetupService
import com.pickdate.iam.domain.ApplicationSetupUseCase
import com.pickdate.iam.domain.KeyProperties


class ApplicationSetupUseCaseTestConfig {

    static final def setupRepo = new ApplicationSetupRepositoryFake()
    static final def userRepo = new UserRepositoryFake()

    static final def keyProperties = new KeyProperties(
            masterKey: "dl87R1gXPKQMC+5blbZFNRP7gvgY7fgS6KrZObqt/L0=",
            rememberMeKey: "TTfGPhiEs1ZLzl/tWgGZ0eBgbck59OwBFW7y/ezX2ZQ="
    )

    static ApplicationSetupUseCase applicationSetupUseCase() {
        new ApplicationSetupService(setupRepo, userRepo, Encryptor.noop(), keyProperties)
    }
}
