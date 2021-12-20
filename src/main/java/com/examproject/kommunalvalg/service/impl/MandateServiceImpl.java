package com.examproject.kommunalvalg.service.impl;

import com.examproject.kommunalvalg.exception.ResourceNotFoundException;
import com.examproject.kommunalvalg.model.Mandate;
import com.examproject.kommunalvalg.model.PoliticalParty;
import com.examproject.kommunalvalg.payload.MandateDto;
import com.examproject.kommunalvalg.repository.MandateRepositroy;
import com.examproject.kommunalvalg.repository.PoliticalPartyRepository;
import com.examproject.kommunalvalg.service.MandateService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MandateServiceImpl implements MandateService {

    private MandateRepositroy mandateRepositroy;
    private PoliticalPartyRepository politicalPartyRepository;
    private ModelMapper modelMapper;

    //Constructor injection, sørger for at den ikke er null når vi kører programmet (enforcer det kontra autowired)
    public MandateServiceImpl(MandateRepositroy mandateRepositroy,PoliticalPartyRepository politicalPartyRepository, ModelMapper modelMapper) {
        Objects.requireNonNull(mandateRepositroy);
        Objects.requireNonNull(politicalPartyRepository);
        Objects.requireNonNull(modelMapper);

        this.mandateRepositroy = mandateRepositroy;
        this.politicalPartyRepository = politicalPartyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MandateDto createMandate(MandateDto mandateDto) {
        //Konverterer vores mandateDTO fra klienten til en entity
        Mandate mandate = mapToEntity(mandateDto);

        //Gemmer entitien til databasen
        Mandate newMandate = mandateRepositroy.save(mandate);

        //returnerer vores nye object i DTO format
        return mapToDTO(newMandate);
    }

    @Override
    public List<MandateDto> getMandatesByPoliticalPartyId(long politicalParty_id) {
        List<Mandate> mandates = mandateRepositroy.findMandatesById(politicalParty_id);

        return mandates.stream().map(mandate -> mapToDTO(mandate)).collect(Collectors.toList());
    }


    @Override
    public MandateDto updateMandate(long politicalPartyId, long mandateId, MandateDto mandateDto) {

        Mandate mandate = mandateRepositroy.findById(mandateId).orElseThrow(
                () -> new ResourceNotFoundException("Mandate","id",mandateId));

        PoliticalParty politicalParty = politicalPartyRepository.findById(politicalPartyId).orElseThrow(
                () -> new ResourceNotFoundException("Political Party","id",politicalPartyId));

        mandate.setFirstname(mandateDto.getFirstname());
        mandate.setLastname(mandateDto.getLastname());

        mandate.setPoliticalParty(politicalParty);

        Mandate updatedComment = mandateRepositroy.save(mandate);

        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteMandate(long mandateId) {
        Mandate mandate = mandateRepositroy.findById(mandateId).orElseThrow(
                () -> new ResourceNotFoundException("Mandate","id",mandateId));

        mandateRepositroy.delete(mandate);
    }

    //Konverterer et Dto object tilbage til dens oprindelige Entity form
    private MandateDto mapToDTO(Mandate mandate) {
        MandateDto mandateDto = modelMapper.map(mandate, MandateDto.class);

        return mandateDto;
    }
    //Konverterer en entity til Dto så vi kan sende den afsted til klienten
    private Mandate mapToEntity(MandateDto mandateDto) {
        Mandate mandate = modelMapper.map(mandateDto, Mandate.class);

        return mandate;
    }
}

