package com.example.votingsystem.controller;

import com.example.votingsystem.model.*;
import com.example.votingsystem.service.VoterServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author samwel.wafula
 * Created on 04/01/2024
 * Time 11:59
 * Project VotingSystem
 */


@Component
@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ApiController {

    private final VoterServiceImpl voterService;

    @Autowired
    public ApiController(VoterServiceImpl voterService) {
        this.voterService = voterService;
    }

    @RequestMapping(value = "/p1/voterRegistration", method = RequestMethod.POST)
    public ResponseEntity<?> registration(@RequestBody Voter voter) {
        Response response = voterService.voterRegistration(voter);
        log.info("code {}", response.getResponseCode());
        return ResponseEntity.status(Integer.parseInt(response.getResponseCode())).body(response);
    }

    @RequestMapping(value = "/p1/voterLogin", method = RequestMethod.POST)
    public ResponseEntity<JwtTokenResponse> login(@RequestBody LoginReq loginReq) throws Exception {
        log.info("Hit");
        JwtTokenResponse jwtTokenResponse = voterService.voterLogin(loginReq);
        log.info("Token info {}", jwtTokenResponse);
        return ResponseEntity.ok(jwtTokenResponse);
    }

    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    ResponseEntity<?> vote(@RequestBody Vote vote) {
        Response response = voterService.castVote(vote);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/getVotes", method = RequestMethod.GET)
    ResponseEntity<List<VoteCount>> getVotes() throws JsonProcessingException {
        return ResponseEntity.ok(voterService.getCastedVotes());
    }
}
