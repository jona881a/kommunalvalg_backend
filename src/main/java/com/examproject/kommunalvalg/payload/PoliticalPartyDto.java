package com.examproject.kommunalvalg.payload;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PoliticalPartyDto {

    private long id;
    private String politicalPartyName;
    private char alias;
    private Set<MandateDto> mandates = new HashSet<>();
}
