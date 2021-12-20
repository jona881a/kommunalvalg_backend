package com.examproject.kommunalvalg.payload;

import com.examproject.kommunalvalg.model.PoliticalParty;
import lombok.Data;

@Data
public class MandateDto {

    private long id;
    private String firstname;
    private String lastname;
    private PoliticalParty politicalParty;
}
