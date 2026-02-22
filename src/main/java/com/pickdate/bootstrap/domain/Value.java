package com.pickdate.bootstrap.domain;

import java.io.Serializable;


public interface Value<T> extends Serializable {

    T value();
}
