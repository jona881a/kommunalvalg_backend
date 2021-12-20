package com.examproject.kommunalvalg.controller;

import com.examproject.kommunalvalg.payload.MandateDto;
import com.examproject.kommunalvalg.payload.PoliticalPartyResponse;
import com.examproject.kommunalvalg.service.MandateService;
import com.examproject.kommunalvalg.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class MandateController {

    MandateService mandateService;

    public MandateController(MandateService mandateService) {
        this.mandateService = mandateService;
    }

    @PostMapping(value="/politicalparties/mandates", consumes="application/json")
    public ResponseEntity<MandateDto> createMandate(@RequestBody MandateDto mandateDto) {
        return new ResponseEntity<>(mandateService.createMandate(mandateDto), HttpStatus.CREATED);
    }

    @GetMapping("/politicalparties/{politicalParty_id}/mandates")
    List<MandateDto> getMandatesByPoliticalPartyId(@PathVariable(value="politicalParty_id") long politicalParty_id) {
        return mandateService.getMandatesByPoliticalPartyId(politicalParty_id);
    }

    @PutMapping("/politicalparties/{politicalPartyId}/mandates/{mandateId}")
    public ResponseEntity<MandateDto> updateComment(@PathVariable long politicalPartyId,
                                                    @PathVariable long mandateId,
                                                    @RequestBody MandateDto mandateDto){
        MandateDto updatedMandate = mandateService.updateMandate(politicalPartyId,mandateId, mandateDto);
        return new ResponseEntity<>(updatedMandate, HttpStatus.OK);
    }

    @DeleteMapping("/politicalparties/mandates/{mandateId}")
    public ResponseEntity<String> deleteMandate(@PathVariable long mandateId) {
        mandateService.deleteMandate(mandateId);

        return new ResponseEntity<>("Mandat var slettet med succes", HttpStatus.OK);
    }

}
