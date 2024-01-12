package com.example.votingsystem.repository;

import com.example.votingsystem.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author samwel.wafula
 * Created on 04/01/2024
 * Time 12:18
 * Project VotingSystem
 */

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Integer> {
}
