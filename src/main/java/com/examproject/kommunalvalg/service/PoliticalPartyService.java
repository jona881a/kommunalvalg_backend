package com.examproject.kommunalvalg.service;

import com.examproject.kommunalvalg.payload.MandateDto;
import com.examproject.kommunalvalg.payload.PoliticalPartyResponse;

import java.util.List;

public interface PoliticalPartyService {

    PoliticalPartyResponse getAllPoliticalParties(int pageNo, int pageSize, String sortBy, String sortDir);

    //List<MandateDto> getAllMandatesFromSpecificPoliticalParty(long id);

    //void deleteMandateById(Long id);
}
