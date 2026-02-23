package com.pickdate.iam.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyProperties {

    private String masterKey;
    private String rememberMeKey;

    public boolean isMasterKeySet() {
        return masterKey != null
                && !masterKey.isBlank()
                && !masterKey.equals("none");
    }
}
