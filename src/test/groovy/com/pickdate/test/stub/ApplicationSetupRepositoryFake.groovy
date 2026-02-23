package com.pickdate.test.stub

import com.pickdate.iam.domain.ApplicationSetup
import com.pickdate.iam.domain.ApplicationSetupRepository


class ApplicationSetupRepositoryFake implements ApplicationSetupRepository {

    ApplicationSetup applicationSetup

    @Override
    Optional<ApplicationSetup> findAppConfig() {
        Optional.ofNullable(applicationSetup)
    }

    @Override
    ApplicationSetup save(ApplicationSetup config) {
        this.applicationSetup = config
    }
}
