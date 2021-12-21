package com.examproject.kommunalvalg.service.impl;

import com.examproject.kommunalvalg.exception.ResourceNotFoundException;
import com.examproject.kommunalvalg.model.Mandate;
import com.examproject.kommunalvalg.model.PoliticalParty;
import com.examproject.kommunalvalg.payload.MandateDto;
import com.examproject.kommunalvalg.payload.MandateResponse;
import com.examproject.kommunalvalg.payload.PoliticalPartyDto;
import com.examproject.kommunalvalg.payload.PoliticalPartyResponse;
import com.examproject.kommunalvalg.repository.MandateRepositroy;
import com.examproject.kommunalvalg.repository.PoliticalPartyRepository;
import com.examproject.kommunalvalg.service.MandateService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public MandateResponse getMandatesByPoliticalParty(PoliticalParty politicalParty, int pageNo, int pageSize, String sortBy, String sortDir) {
        //List<Mandate> mandatesTest = mandateRepositroy.findAllByPoliticalParty(politicalParty);

        //Vi laver et sorteringsobject baseret på om sortDir er asc eller desc
        //Samtidigt bruger vi sortBy til at vide hvilke attribut vi prøver at sortere for
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //Vi laver et pageRequest med sideantal, sidestørrelse og vores sorteringsrækkefølge
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        //Pageobject der indeholder vores liste af objecter ud fra de specifikke argumenter vi har lavet
        //Page<Mandate> mandates = mandateRepositroy.findAll(pageable);
        Page<Mandate> mandates = mandateRepositroy.findAllByPoliticalParty(politicalParty, pageable);

        //Vi laver en liste af politicalParties fra Page objectet
        List<Mandate> listOfMandates = mandates.getContent();

        //Vi skal konverterer politicalPartyEntities til DTOs som vi sender til klienten
        List<MandateDto> content = listOfMandates.stream().map(this::mapToDTO).collect(Collectors.toList());

        MandateResponse mandateResponse = new MandateResponse();
        mandateResponse.setContent(content);
        mandateResponse.setPageNo(mandates.getNumber());
        mandateResponse.setPageSize(mandates.getSize());
        mandateResponse.setTotalElements(mandates.getTotalElements());
        mandateResponse.setTotalPages(mandates.getTotalPages());
        mandateResponse.setLast(mandates.isLast());

        return mandateResponse;
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

