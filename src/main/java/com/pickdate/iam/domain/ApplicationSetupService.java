package com.pickdate.iam.domain;

import com.pickdate.bootstrap.domain.Property;
import com.pickdate.bootstrap.encryption.Encryptor;
import com.pickdate.bootstrap.exception.FailedDependencyException;
import com.pickdate.bootstrap.exception.ResourceAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.transaction.annotation.Transactional;

import static com.pickdate.iam.domain.Authority.ADMIN;


@RequiredArgsConstructor
public class ApplicationSetupService implements ApplicationSetupUseCase {

    private static final String KEY_NAME = "pickdate:encryptor";

    private final ApplicationSetupRepository applicationSetupRepository;
    private final UserRepository userRepository;
    private final Encryptor encryptor;
    private final KeyProperties keyProperties;

    @Override
    @Transactional
    public void setupDomain(DomainUrl domainUrl) {
        ApplicationSetup config = createOrFetchApplicationSetup();
        config.setDomainUrl(domainUrl);
        applicationSetupRepository.save(config);
    }

    @Override
    @Transactional
    public AESKeySettings setupEncryption() {
        assertMasterKey();
        ApplicationSetup appConfig = createOrFetchApplicationSetup();
        Key key = Key.initKey(KEY_NAME);
        appConfig.setKey(key);
        applicationSetupRepository.save(appConfig);
        var settings = new AESKeySettings(
                key.info(),
                key.getSalt(),
                keyProperties.getMasterKey()
        );
        initializeEncryptor(settings);
        return settings;
    }

    @Override
    @Transactional
    public void setupAdmin(User user) {
        assertAdminNotTaken();
        assertEmailNotTaken(user.getEmail());
        user.addAuthority(ADMIN);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void completeSetup() {
        ApplicationSetup appConfig = createOrFetchApplicationSetup();
        if (appConfig.isSetupCompleted()) {
            return;
        }
        appConfig.completeSetup();
        applicationSetupRepository.save(appConfig);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean setupCompleted() {
        return applicationSetupRepository.findAppConfig()
                .map(ApplicationSetup::isSetupCompleted)
                .orElse(false);
    }

    private @NonNull ApplicationSetup createOrFetchApplicationSetup() {
        return applicationSetupRepository.findAppConfig()
                .orElseGet(ApplicationSetup::new);
    }

    private void assertMasterKey() {
        if (!keyProperties.isMasterKeySet()) {
            throw new FailedDependencyException(Property.of("master key", ""), "Master key is not set");
        }
    }

    private void initializeEncryptor(AESKeySettings settings) {
        String aesKey = AESKeyGenerator.generateAesKeyFromSettings(settings);
        Encryptor encryptor = AESEncryptor.create(aesKey);
        if (this.encryptor instanceof EncryptorDelegate encryptorDelegate) {
            encryptorDelegate.setDelegate(encryptor);
        }
    }

    private void assertAdminNotTaken() {
        if (userRepository.findAll().stream().anyMatch(user -> user.getAuthorities().contains(ADMIN)))
            throw new ResourceAlreadyExistException(
                    Property.of("user", ADMIN), "Admin already exists"
            );
    }

    private void assertEmailNotTaken(Email email) {
        if (userRepository.existsByEmail(email))
            throw new ResourceAlreadyExistException(
                    Property.of("email", email.value()), "User with email %s already exists".formatted(email)
            );
    }
}
