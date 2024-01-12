package com.example.votingsystem.repository;

import com.example.votingsystem.model.Vote;
import com.example.votingsystem.model.VoteCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author samwel.wafula
 * Created on 04/01/2024
 * Time 12:18
 * Project VotingSystem
 */
@Repository
public interface VotesRepository extends JpaRepository<Vote, Integer> {

    @Query(nativeQuery = true,value = "select distinct v.candidate_id as candidate_id,count(candidate_id) as count  from votes v group by v.candidate_id ")
    List<Map<String, Object>> getTotalVotes();
}
