package com.seafta.meterhj.domain.persistence.model.enums;

import lombok.Getter;

public enum MeterType {

    G1("G1,6"),
    G2("G2,5"),
    G4("G4"),
    G6("G6"),
    G10("G10"),
    G16("G16"),
    G25("G25"),
    G40("G40"),
    G65("G65");

    @Getter
    private final String type;

    MeterType(String type) {
        this.type = type;
    }
}
