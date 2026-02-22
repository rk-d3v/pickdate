package com.pickdate.iam.domain;

public record AESKeySettings(
        String info,
        String salt,
        String master
) {
}
