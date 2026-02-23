package com.pickdate.iam.domain;

import com.pickdate.bootstrap.encryption.Encryptor;

import java.util.concurrent.atomic.AtomicReference;


public class EncryptorDelegate implements Encryptor {

    private final AtomicReference<Encryptor> delegate = new AtomicReference<>();

    public EncryptorDelegate(Encryptor encryptor) {
        this.delegate.set(encryptor);
    }

    public void setDelegate(Encryptor delegate) {
        this.delegate.set(delegate);
    }

    @Override
    public String encrypt(String value) {
        return delegate.get().encrypt(value);
    }

    @Override
    public String decrypt(String value) {
        return delegate.get().decrypt(value);
    }
}
