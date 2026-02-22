package com.pickdate.iam.domain;


public interface ApplicationSetupUseCase {

    void setupDomain(DomainUrl domainUrl);

    AESKeySettings setupEncryption();

    boolean setupCompleted();

    void completeSetup();

    void setupAdmin(User user);
}
