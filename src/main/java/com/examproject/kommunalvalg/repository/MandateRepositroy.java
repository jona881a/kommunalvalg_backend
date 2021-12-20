package com.examproject.kommunalvalg.repository;

import com.examproject.kommunalvalg.model.Mandate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MandateRepositroy extends JpaRepository<Mandate, Long> {

    List<Mandate> findMandatesById(long politicalParty_id);

}
