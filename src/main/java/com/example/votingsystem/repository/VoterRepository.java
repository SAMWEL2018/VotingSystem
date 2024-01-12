package com.example.votingsystem.repository;

import com.example.votingsystem.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author samwel.wafula
 * Created on 04/01/2024
 * Time 12:18
 * Project VotingSystem
 */
@Repository
public interface VoterRepository extends JpaRepository<Voter,Integer> {

    Optional<Voter> findByVoterIdNumber(String IdNumber);
}
