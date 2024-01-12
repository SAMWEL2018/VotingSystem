package com.example.votingsystem.repository;

import com.example.votingsystem.model.Response;
import com.example.votingsystem.model.VoteCount;
import com.example.votingsystem.model.Voter;
import com.example.votingsystem.model.Vote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author samwel.wafula
 * Created on 04/01/2024
 * Time 12:37
 * Project VotingSystem
 */

@Repository
@Slf4j
public class DataLayer {

    private final VoterRepository voterRepository;
    private final VotesRepository votesRepository;

    @Autowired
    public DataLayer(VoterRepository voterRepository, VotesRepository votesRepository) {
        this.voterRepository = voterRepository;
        this.votesRepository = votesRepository;
    }

    public Response createAccount(Voter voter) {
        Response response = null;
        voterRepository.save(voter);
        response = Response.builder()
                .responseCode("200")
                .responseDesc("Voter Created")
                .build();
        return response;
    }

    public Response voteCasting(Vote vote) {

        votesRepository.save(vote);
        return Response.builder()
                .responseCode("200")
                .responseDesc("Vote casted successfully")
                .data(vote)
                .build();
    }

    public List<VoteCount> getVotes() throws JsonProcessingException {
         List<Map<String,Object>> votes = votesRepository.getTotalVotes();
         log.info("mapped {}",new ObjectMapper().writeValueAsString(votes));
         if (!votes.isEmpty()){
             return new ObjectMapper().convertValue(votes, new TypeReference<>() {
             });
         }
        return null;
    }
}
