package com.examproject.kommunalvalg.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( //name of the table og uniqueconstraints gør at title bliver unique i table
        name = "politicalparties", uniqueConstraints = {@UniqueConstraint(columnNames = {"politicalPartyName"})}
)
public class PoliticalParty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String politicalPartyName;

    @Column(nullable = false)
    private char alias;

    @JsonBackReference
    @OneToMany(mappedBy = "politicalParty", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Mandate> mandates = new HashSet<>();

}
