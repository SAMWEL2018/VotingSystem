package com.example.votingsystem.service;

import com.example.votingsystem.model.JwtTokenResponse;
import com.example.votingsystem.repository.VoterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author samwel.wafula
 * Created on 09/01/2024
 * Time 09:05
 * Project VotingSystem
 */

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final VoterRepository voterRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return voterRepository.findByVoterIdNumber(username).orElseThrow(() -> new UsernameNotFoundException("Voter not found"));
    }


}
