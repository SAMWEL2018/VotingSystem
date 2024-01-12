package com.example.votingsystem.service;

import com.example.votingsystem.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * @author samwel.wafula
 * Created on 04/01/2024
 * Time 12:38
 * Project VotingSystem
 */
public interface VoterService {

    Response voterRegistration(Voter voter);
    Response castVote(Vote vote);
    List<VoteCount> getCastedVotes() throws JsonProcessingException;

    JwtTokenResponse voterLogin(LoginReq loginReq) throws Exception;


}
