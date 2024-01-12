package com.example.votingsystem.service;

import com.example.votingsystem.model.*;
import com.example.votingsystem.repository.DataLayer;
import com.example.votingsystem.repository.VoterRepository;
import com.example.votingsystem.repository.VotesRepository;
import com.example.votingsystem.service.jwt.JwtService;
import com.example.votingsystem.service.jwt.JwtServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author samwel.wafula
 * Created on 04/01/2024
 * Time 12:38
 * Project VotingSystem
 */


@Service
@RequiredArgsConstructor
@Slf4j
public class VoterServiceImpl implements VoterService {

    private final DataLayer dataLayer;
    private final VoterRepository voterRepository;
    private final VotesRepository votesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public Response voterRegistration(Voter voter) {
        Voter user = Voter.builder()
                .voterName(voter.getVoterName())
                .voterIdNumber(voter.getVoterIdNumber())
                .password(passwordEncoder.encode(voter.getPassword()))
                .role(Role.ADMIN)
                .build();

        return dataLayer.createAccount(user);

    }

    @Override
    public Response castVote(Vote vote) {
        return dataLayer.voteCasting(vote);
    }

    @Override
    public List<VoteCount> getCastedVotes() throws JsonProcessingException {
        VoteCount voteCount = null;
        List<VoteCount> votes = dataLayer.getVotes();
        log.info("votes {}", objectMapper.writeValueAsString(votes));

        for (VoteCount v : votes) {
            log.info("loop ");
            voteCount = VoteCount.builder()
                    .count(v.getCount())
                    .candidate_id(v.getCandidate_id())
                    .build();

        }
        return votes;
    }

    @Override
    public JwtTokenResponse voterLogin(LoginReq loginReq) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getVoterIdNumber(), loginReq.getPassword()));
            log.info("auth user principal {} authority {} credentials{} details{}", objectMapper.writeValueAsString(authentication.getPrincipal()), authentication.getAuthorities(), authentication.getCredentials(), authentication.getDetails());
            var user = voterRepository.findByVoterIdNumber(loginReq.getVoterIdNumber()).orElseThrow(() -> new UsernameNotFoundException("User with credentials not found"));
            log.info("voter {}", objectMapper.writeValueAsString(user));
            try {
                var token = jwtService.generateToken(user);
                return JwtTokenResponse.builder().token(token).build();
            } catch (Exception e) {
                log.info("Token generation Error ", e);
                throw new RuntimeException(e);
            }

        } catch (BadCredentialsException badCredentialsException) {
            log.info("Authentication Manager Error {}", badCredentialsException);
            throw badCredentialsException;
        } catch (Exception e) {
            log.info("Other Exception ", e);
        }
        return null;
    }
}
