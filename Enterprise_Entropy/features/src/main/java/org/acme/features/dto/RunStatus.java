package org.acme.features.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RunStatus {
    OK("ok"),
    ERROR("error");

    @JsonValue
    private final String value;
}
