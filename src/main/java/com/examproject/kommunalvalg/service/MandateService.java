package com.examproject.kommunalvalg.service;

import com.examproject.kommunalvalg.payload.MandateDto;
import com.examproject.kommunalvalg.payload.PoliticalPartyResponse;

import java.util.List;

public interface MandateService {

    MandateDto createMandate(MandateDto mandateDto);

    List<MandateDto> getMandatesByPoliticalPartyId(long id);

    MandateDto updateMandate(long politicalPartyId, long mandateId, MandateDto updatedMandateDto);

    void deleteMandate(long mandateId);
}
