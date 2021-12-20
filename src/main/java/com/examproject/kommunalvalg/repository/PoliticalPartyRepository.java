package com.examproject.kommunalvalg.repository;

import com.examproject.kommunalvalg.model.PoliticalParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliticalPartyRepository extends JpaRepository<PoliticalParty, Long> {
}
