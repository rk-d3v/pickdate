package com.pickdate.iam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "keys")
@NoArgsConstructor
public class Key {

    @Id
    private String id;
    private String salt;
    private int version;

    public Key(String id, String salt, int version) {
        this.id = id;
        this.salt = salt;
        this.version = version;
    }

    public static Key initKey(String id) {
        return new Key(id, AESKeyGenerator.generateSalt(), 1);
    }

    public String info() {
        return id + ":v" + version;
    }
}
