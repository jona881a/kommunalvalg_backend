package com.examproject.kommunalvalg.repository;

import com.examproject.kommunalvalg.model.Mandate;
import com.examproject.kommunalvalg.model.PoliticalParty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MandateRepositroy extends JpaRepository<Mandate, Long> {

    Page<Mandate> findAllByPoliticalParty(PoliticalParty politicalParty, Pageable pageable);

    List<Mandate> findAllByPoliticalPartyId(long politicalPartyId);
}
