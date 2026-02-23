package com.pickdate.bootstrap.encryption

import spock.lang.Specification


class EncryptorSpec extends Specification {

    def "noop should return input as-is for '#value'"() {
        given:
        def encryptor = Encryptor.noop()

        expect:
        encryptor.encrypt(value) == value
        encryptor.decrypt(value) == value

        where:
        value                  | _
        null                   | _
        ""                     | _
        "plain-text"           | _
        "  text with spaces  " | _
        "123!@#"               | _
    }
}
