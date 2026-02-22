package com.pickdate.bootstrap.exception;

import com.pickdate.bootstrap.domain.Property;
import lombok.Getter;

import java.util.List;


@Getter
public class FailedDependencyException extends RuntimeException {

    private final List<Property<?>> properties;
    private final String detail;

    public FailedDependencyException(List<Property<?>> properties, String detail) {
        detail = detail == null ? "" : detail;
        this.properties = properties;
        this.detail = detail;
    }

    public FailedDependencyException(Property<?> property, String detail) {
        this(List.of(property), detail);
    }
}
