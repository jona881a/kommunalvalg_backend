package com.examproject.kommunalvalg.service;

import com.examproject.kommunalvalg.model.Mandate;
import com.examproject.kommunalvalg.model.PoliticalParty;
import com.examproject.kommunalvalg.payload.MandateDto;
import com.examproject.kommunalvalg.payload.MandateResponse;
import com.examproject.kommunalvalg.payload.PoliticalPartyResponse;

import java.util.List;

public interface MandateService {

    MandateDto createMandate(MandateDto mandateDto);

    MandateResponse getMandatesByPoliticalParty(PoliticalParty politicalParty, int pageNo, int pageSize, String sortBy, String sortDir);

    MandateDto updateMandate(long politicalPartyId, long mandateId, MandateDto updatedMandateDto);

    void deleteMandate(long mandateId);
}
