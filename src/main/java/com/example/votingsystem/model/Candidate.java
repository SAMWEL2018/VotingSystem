package com.example.votingsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author samwel.wafula
 * Created on 04/01/2024
 * Time 12:01
 * Project VotingSystem
 */

@Entity
@Table(name = "candidates")
@Getter
@Setter
public class Candidate {

    @Id
    @Column(name = "candidateId")
    private int id;

    @Column(name = "candidateName")
    private String candidateName;
}
