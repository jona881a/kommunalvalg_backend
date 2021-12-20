package com.examproject.kommunalvalg.service.impl;

import com.examproject.kommunalvalg.exception.ResourceNotFoundException;
import com.examproject.kommunalvalg.model.Mandate;
import com.examproject.kommunalvalg.model.PoliticalParty;
import com.examproject.kommunalvalg.payload.MandateDto;
import com.examproject.kommunalvalg.payload.PoliticalPartyDto;
import com.examproject.kommunalvalg.payload.PoliticalPartyResponse;
import com.examproject.kommunalvalg.repository.MandateRepositroy;
import com.examproject.kommunalvalg.repository.PoliticalPartyRepository;
import com.examproject.kommunalvalg.service.PoliticalPartyService;
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
public class PoliticalPartyServiceImpl implements PoliticalPartyService {
    
    private MandateRepositroy mandateRepositroy;
    private PoliticalPartyRepository politicalPartyRepository;
    private ModelMapper modelMapper;

    //Constructor injection, sørger for at den ikke er null når vi kører programmet (enforcer det kontra autowired)
    public PoliticalPartyServiceImpl(MandateRepositroy mandateRepositroy, PoliticalPartyRepository politicalPartyRepository, ModelMapper modelMapper) {
        Objects.requireNonNull(mandateRepositroy);
        Objects.requireNonNull(politicalPartyRepository);
        Objects.requireNonNull(modelMapper);

        this.mandateRepositroy = mandateRepositroy;
        this.politicalPartyRepository = politicalPartyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PoliticalPartyResponse getAllPoliticalParties(int pageNo, int pageSize, String sortBy, String sortDir) {

        //Vi laver et sorteringsobject baseret på om sortDir er asc eller desc
        //Samtidigt bruger vi sortBy til at vide hvilke attribut vi prøver at sortere for
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //Vi laver et pageRequest med sideantal, sidestørrelse og vores sorteringsrækkefølge
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        //Pageobject der indeholder vores liste af objecter ud fra de specifikke argumenter vi har lavet
        Page<PoliticalParty> politicalParties = politicalPartyRepository.findAll(pageable);

        //Vi laver en liste af politicalParties fra Page objectet
        List<PoliticalParty> listOfPoliticalParties = politicalParties.getContent();

        //Vi skal konverterer politicalPartyEntities til DTOs som vi sender til klienten
        List<PoliticalPartyDto> content = listOfPoliticalParties.stream().map(this::mapToDTO).collect(Collectors.toList());

        PoliticalPartyResponse politicalPartyResponse = new PoliticalPartyResponse();
        politicalPartyResponse.setContent(content);
        politicalPartyResponse.setPageNo(politicalParties.getNumber());
        politicalPartyResponse.setPageSize(politicalParties.getSize());
        politicalPartyResponse.setTotalElements(politicalParties.getTotalElements());
        politicalPartyResponse.setTotalPages(politicalParties.getTotalPages());
        politicalPartyResponse.setLast(politicalParties.isLast());

        return politicalPartyResponse;
    }

    /*
    @Override
     //Retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        //Convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }


    @Override
    public void deleteMandateById(Long id) {
        Mandate mandate = mandateRepositroy.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mandate", "id", id));

        mandateRepositroy.delete(mandate);
    }

     */

    //Konverterer et Dto object tilbage til dens oprindelige Entity form
    private PoliticalPartyDto mapToDTO(PoliticalParty politicalParty) {
        PoliticalPartyDto politicalPartyDto = modelMapper.map(politicalParty, PoliticalPartyDto.class);

        return politicalPartyDto;
    }
    //Konverterer en entity til Dto så vi kan sende den afsted til klienten
    private PoliticalParty mapToEntity(PoliticalPartyDto politicalPartyDto) {
        PoliticalParty politicalParty = modelMapper.map(politicalPartyDto, PoliticalParty.class);

        return politicalParty;
    }
}
