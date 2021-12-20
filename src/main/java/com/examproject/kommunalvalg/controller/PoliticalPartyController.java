package com.examproject.kommunalvalg.controller;

import com.examproject.kommunalvalg.payload.MandateDto;
import com.examproject.kommunalvalg.payload.PoliticalPartyResponse;
import com.examproject.kommunalvalg.service.PoliticalPartyService;
import com.examproject.kommunalvalg.util.AppConstants;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/politicalparties")
public class PoliticalPartyController {

    PoliticalPartyService politicalPartyService;

    public PoliticalPartyController(PoliticalPartyService politicalPartyService) {
        this.politicalPartyService = politicalPartyService;
    }

    @GetMapping
    public PoliticalPartyResponse getAllPoliticalParties(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return politicalPartyService.getAllPoliticalParties(pageNo,pageSize, sortBy, sortDir);
    }

    /*
    @GetMapping("/{id}")
    public List<MandateDto> getAllMandatesFromSpecificPoliticalParty(@PathVariable long id) {
        return politicalPartyService.getAllMandatesFromSpecificPoliticalParty(id);
    }

     */

}
